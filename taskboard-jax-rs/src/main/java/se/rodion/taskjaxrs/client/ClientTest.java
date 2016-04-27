//package se.rodion.taskjaxrs.client;
//
//import java.util.List;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import se.rodion.taskdata.model.User;
//
//public class ClientTest
//{
//	public static class UsersData
//	{
//		public List<User> users;
//	}
//	
//	public static void main(String[] args)
//	{
//		
//		Client client = ClientBuilder.newClient();
//		
//		WebTarget baseUserTarget = client.target("http://localhost:8080/taskboard-jax-rs/");
//		WebTarget usersTarget = baseUserTarget.path("user");
//		WebTarget allUsersTarget = usersTarget.path("users");
//		WebTarget singleUserTarget = usersTarget.path("{userNumber}");
//		
//		User user = singleUserTarget
//					.resolveTemplate("userNumber", 1234)
//					.request(MediaType.APPLICATION_JSON)
//					.get(User.class);
//		
//		System.out.println(user.getFirstName());
//		
////		List<User> users = allUsersTarget
//		UsersData usersFromClient = allUsersTarget
//							.request(MediaType.APPLICATION_JSON)
////							.get(new GenericType<ArrayList<User>>(){});
//							.get(UsersData.class);
////							.get(ArrayList.class);
//		
//		usersFromClient.users.forEach(e -> System.out.println(e.getUserNumber()));
//
//		Response responseAllUsers = allUsersTarget.request().get();
//		System.out.println(responseAllUsers.getStatus());
//
////		List<User> usersByQuery = usersTarget
//		UsersData usersByQuery = usersTarget
//									.path("query")
//									.queryParam("lastName", "Zlobin")
//									.queryParam("userNumber", 1234)
//									.request(MediaType.APPLICATION_JSON)
////									.get(new GenericType<ArrayList<User>>(){});
//									.get(UsersData.class);
//		
//		usersByQuery.users.forEach(System.out::println);
//				
//		User newUser = new User("RodionN", "ZlobinN", "username10", "1232");
//		Response responsePostCreateUser = usersTarget
//				.request()
//				.post(Entity.json(newUser));
//		if(responsePostCreateUser.getStatus() != 201)
//		{
//			System.out.println("ERROR");
//		}
//		
//		User createduser = singleUserTarget
//				.resolveTemplate("userNumber", newUser.getUserNumber())
//				.request(MediaType.APPLICATION_JSON)
//				.get(User.class);
//		
//		System.out.println(createduser);
//		
//		User userToUpdate = new User("RodionU", "ZlobinU", "usernameU123", "1233");
//		Response responsePutUpdateUser = usersTarget
//				.path("1232")
//				.request()
//				.put(Entity.json(userToUpdate));
//		System.out.println(responsePutUpdateUser.getStatus());
//		
//		
//		
//	}
//
//}
