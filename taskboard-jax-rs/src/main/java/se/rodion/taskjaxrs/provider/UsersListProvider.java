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

import se.rodion.taskdata.model.User;
import se.rodion.taskjaxrs.provider.UserProvider.UserAdapter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class UsersListProvider implements MessageBodyWriter<ArrayList<User>>
{
	private Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
	private Gson gson = new GsonBuilder().registerTypeAdapter(userListType, new UsersAdapter()).create();

	
	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType)
	{
		
//		return genericType.equals(userListType);
		
		if(type.isAssignableFrom(ArrayList.class) && genericType instanceof ParameterizedType){
			ParameterizedType paramType = (ParameterizedType) genericType;
			Type[] actualTypesArgue = paramType.getActualTypeArguments();
			if(actualTypesArgue.length == 1 &&  actualTypesArgue[0].equals(User.class)){
				return true;
			}
		}
		return false;
	}

	@Override
	public long getSize(ArrayList<User> users, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType)
	{
		return -1;
	}

	@Override
	public void writeTo(ArrayList<User> users, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException
	{
		try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(users, userListType, writer);
		}
	}

	private static final class UsersAdapter implements JsonSerializer<ArrayList<User>> //, JsonDeserializer<ArrayList<User>>
	{

		@Override
		public JsonElement serialize(ArrayList<User> users, Type type,
				JsonSerializationContext context)
		{

			UserAdapter adapter = new UserAdapter();

			JsonObject jsonToReturn = new JsonObject();
			JsonArray jsonArrayUsers = new JsonArray();
			
			users.forEach(e -> jsonArrayUsers.add(adapter.serialize(e, type, context).getAsJsonObject()));
			
			jsonToReturn.add("users", jsonArrayUsers);

			return jsonToReturn;
		}

//		@Override
//		public ArrayList<User> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
//		{
//
//			UserAdapter adapter = new UserAdapter();
//
////			JsonObject userJson = json.getAsJsonObject();
////			JsonArray usersJson = userJson.getAsJsonArray("users");
//
//			JsonArray usersJson = json.getAsJsonArray();
//			ArrayList<User> users = new ArrayList<>();
//			
//			usersJson.forEach(e -> users.add(adapter.deserialize(e, typeOfT, context)));
//
//			return users;
//		}
	}
}