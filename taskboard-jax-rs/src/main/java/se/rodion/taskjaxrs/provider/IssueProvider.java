package se.rodion.taskjaxrs.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

import se.rodion.taskdata.model.Issue;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class IssueProvider implements MessageBodyWriter<Issue>, MessageBodyReader<Issue>
{

	private Gson gson = new GsonBuilder().registerTypeAdapter(Issue.class, new IssueAdapter()).create();

	// ============= MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Issue.class);
	}

	@Override
	public long getSize(Issue t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return 0;
	}

	@Override
	public void writeTo(Issue issue, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{

		try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(issue, Issue.class, writer);
		}
	}

	// ============= MessageBodyReader

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Issue.class);
	}

	@Override
	public Issue readFrom(Class<Issue> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException, WebApplicationException
	{

		return gson.fromJson(new InputStreamReader(entityStream), Issue.class);
	}

	// GSON
	protected static final class IssueAdapter implements JsonSerializer<Issue>, JsonDeserializer<Issue>
	{

		@Override
		public JsonElement serialize(Issue issue, Type type, JsonSerializationContext context)
		{

			JsonObject json = new JsonObject();
			json.addProperty("id", issue.getId());
			json.addProperty("description", issue.getDescription());

			return json;
		}

		@Override
		public Issue deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
		{

			JsonObject issueJson = json.getAsJsonObject();
			// Long id = issueJson.get("id").getAsLong();
			String description = issueJson.get("description").getAsString();

			return new Issue(description);
		}

	}

}
