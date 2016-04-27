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

import se.rodion.taskdata.model.WorkItem;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class WorkItemProvider implements MessageBodyWriter<WorkItem>, MessageBodyReader<WorkItem>
{

	private Gson gson = new GsonBuilder().registerTypeAdapter(WorkItem.class, new WorkItemAdapter()).create();

	// ============= MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(WorkItem.class);
	}

	@Override
	public long getSize(WorkItem t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return 0;
	}

	@Override
	public void writeTo(WorkItem workItem, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{

		try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(workItem, WorkItem.class, writer);
		}
	}

	// ============= MessageBodyReader

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(WorkItem.class);
	}

	@Override
	public WorkItem readFrom(Class<WorkItem> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException, WebApplicationException
	{

		return gson.fromJson(new InputStreamReader(entityStream), WorkItem.class);
	}

	// GSON
	protected static final class WorkItemAdapter implements JsonSerializer<WorkItem>, JsonDeserializer<WorkItem>
	{

		@Override
		public JsonElement serialize(WorkItem workItem, Type type, JsonSerializationContext context)
		{

			JsonObject json = new JsonObject();
			json.addProperty("id", workItem.getId());
			json.addProperty("description", workItem.getDescription());
			json.addProperty("status", workItem.getStatus().toString());

			return json;
		}

		@Override
		public WorkItem deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
		{

			JsonObject workItemJson = json.getAsJsonObject();
//			Long id = workItemJson.get("id").getAsLong();
			String description = workItemJson.get("description").getAsString();

			return new WorkItem(description);
		}

	}

}
