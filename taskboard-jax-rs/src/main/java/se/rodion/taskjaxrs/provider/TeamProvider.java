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

import se.rodion.taskdata.model.Team;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class TeamProvider implements MessageBodyWriter<Team>, MessageBodyReader<Team>
{

	private Gson gson = new GsonBuilder().registerTypeAdapter(Team.class, new TeamAdapter()).create();

	// ============= MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Team.class);
	}

	@Override
	public long getSize(Team t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return 0;
	}

	@Override
	public void writeTo(Team team, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{

		try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(team, Team.class, writer);
		}
	}

	// ============= MessageBodyReader

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(Team.class);
	}

	@Override
	public Team readFrom(Class<Team> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
			InputStream entityStream) throws IOException, WebApplicationException
	{

		return gson.fromJson(new InputStreamReader(entityStream), Team.class);
	}

	// GSON
	protected static final class TeamAdapter implements JsonSerializer<Team>, JsonDeserializer<Team>
	{

		@Override
		public JsonElement serialize(Team team, Type type, JsonSerializationContext context)
		{

			JsonObject json = new JsonObject();
			json.addProperty("id", team.getId());
			json.addProperty("name", team.getName());

			return json;
		}

		@Override
		public Team deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
		{

			JsonObject teamJson = json.getAsJsonObject();
//			Long id = teamJson.get("id").getAsLong();
			String description = teamJson.get("name").getAsString();

			return new Team(description);
		}

	}

}
