package se.rodion.taskjaxrs.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import se.rodion.taskdata.model.Issue;
import se.rodion.taskjaxrs.provider.IssueProvider.IssueAdapter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class IssuesListProvider implements MessageBodyWriter<ArrayList<Issue>>
{
	private Type issueListType = new TypeToken<ArrayList<Issue>>(){}.getType();
	private Gson gson = new GsonBuilder().registerTypeAdapter(issueListType, new IssuesAdapter()).create();

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		if (type.isAssignableFrom(ArrayList.class) && genericType instanceof ParameterizedType)
		{
			ParameterizedType paramType = (ParameterizedType) genericType;
			Type[] actualTypesArgue = paramType.getActualTypeArguments();
			if (actualTypesArgue.length == 1 && actualTypesArgue[0].equals(Issue.class))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public long getSize(ArrayList<Issue> issues, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(ArrayList<Issue> issues, Class<?> arg1, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(issues, issueListType, writer);
		}
	}

	private static final class IssuesAdapter implements JsonSerializer<ArrayList<Issue>>
	{

		@Override
		public JsonElement serialize(ArrayList<Issue> issues, Type typeOfSrc,
				JsonSerializationContext context)
		{

			IssueAdapter adapter = new IssueAdapter();

			final JsonObject jsonToReturn = new JsonObject();
			final JsonArray jsonArrayIssues = new JsonArray();

			issues.forEach(e -> jsonArrayIssues.add(adapter.serialize(e, typeOfSrc, context).getAsJsonObject()));

			jsonToReturn.add("issues", jsonArrayIssues);

			return jsonToReturn;
		}
	}

}