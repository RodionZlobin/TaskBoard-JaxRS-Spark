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

import se.rodion.taskdata.model.Team;
import se.rodion.taskjaxrs.provider.TeamProvider.TeamAdapter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class TeamsListProvider implements MessageBodyWriter<ArrayList<Team>>
{
	private Type teamListType = new TypeToken<ArrayList<Team>>(){}.getType();
	private Gson gson = new GsonBuilder().registerTypeAdapter(teamListType, new TeamsAdapter()).create();

	
	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		if(type.isAssignableFrom(ArrayList.class) && genericType instanceof ParameterizedType){
			ParameterizedType paramType = (ParameterizedType) genericType;
			Type[] actualTypesArgue = paramType.getActualTypeArguments();
			if(actualTypesArgue.length == 1 && actualTypesArgue[0].equals(Team.class)){
				return true;
			}
		}
		return false;
	}

	@Override
	public long getSize(ArrayList<Team> teams, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(ArrayList<Team> teams, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(teams, teamListType, writer);
		}
	}

	private static final class TeamsAdapter implements JsonSerializer<ArrayList<Team>>
	{

		@Override
		public JsonElement serialize(ArrayList<Team> teams, Type typeOfSrc,
				JsonSerializationContext context)
		{

			TeamAdapter adapter = new TeamAdapter();

			JsonObject jsonToReturn = new JsonObject();
			JsonArray jsonArrayTeams = new JsonArray();
			
			teams.forEach(e -> jsonArrayTeams.add(adapter.serialize(e, typeOfSrc, context).getAsJsonObject()));

			jsonToReturn.add("teams", jsonArrayTeams);

			return jsonToReturn;
		}
	}

}