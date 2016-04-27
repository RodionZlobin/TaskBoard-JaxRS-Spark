package se.rodion.taskjaxrs.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import se.rodion.taskdata.model.User;

public class JaxRsClientTestUser
{
	Client client = ClientBuilder.newClient();

	WebTarget singleUserTarget;
	WebTarget allUsersTarget;
	WebTarget baseUserTarget;
	WebTarget userTarget;

	static class UsersData
	{
		public List<User> users;
	}

	@Before
	public void setUp()
	{
		baseUserTarget = client.target("http://localhost:8080/taskboard-jax-rs/");
		userTarget = baseUserTarget.path("user");
		singleUserTarget = userTarget.path("{userNumber}");
		allUsersTarget = userTarget.path("users");
	}

	@Test
	public void tryToFetchUser()
	{
		User user = singleUserTarget
				.resolveTemplate("userNumber", 1234)
				.request(MediaType.APPLICATION_JSON)
				.get(User.class);

		Response response = singleUserTarget
				.resolveTemplate("userNumber", 1234)
				.request()
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));
		assertThat(user.getFirstName(), is(equalTo("Rodion")));
	}

	@Test
	public void tryToFetchAllUsers()
	{
		UsersData usersFromClient = allUsersTarget
				.request(MediaType.APPLICATION_JSON)
				// .get(new GenericType<ArrayList<User>>(){});
				.get(UsersData.class);

		Response responseAllUsers = allUsersTarget.request().get();

		assertThat(responseAllUsers.getStatus(), is(equalTo(200)));
		assertThat(usersFromClient.users.size(), is(equalTo(9)));
	}

	@Test
	public void tryToFetchUsersByQueryParameters()
	{
		UsersData usersByQuery = userTarget
				.path("query")
				.queryParam("lastName", "Zlobin")
				.queryParam("userNumber", 1234)
				.request(MediaType.APPLICATION_JSON)
				// .get(new GenericType<ArrayList<User>>(){});
				.get(UsersData.class);

		Response response = userTarget
				.path("query")
				.queryParam("lastName", "Zlobin")
				.queryParam("userNumber", 1234)
				.request()
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));
		assertThat(usersByQuery.users.size(), is(equalTo(3)));
	}

	@Test
	public void tryToCreateUser()
	{
		User userToCreate = new User("RodionN", "ZlobinN", "username10", "1275");
		Response responsePostCreateUser = userTarget
				.request()
				.post(Entity.json(userToCreate));

		assertThat(responsePostCreateUser.getStatus(), is(equalTo(201)));

		User createduser = singleUserTarget
				.resolveTemplate("userNumber", userToCreate.getUserNumber())
				.request(MediaType.APPLICATION_JSON)
				.get(User.class);

		assertThat(userToCreate.getUserNumber(),
				is(equalTo(createduser.getUserNumber())));
		assertThat(userToCreate.getUsername(),
				is(equalTo(createduser.getUsername())));
		assertThat(userToCreate.getFirstName(),
				is(equalTo(createduser.getFirstName())));
	}

	@Test
	public void tryToUpdateUser()
	{
		User userToUpdate = new User("RodionU", "ZlobinU", "usernameU123",
				"1233");
		Response responsePutUpdateUser = userTarget
				.path("1232")
				.request()
				.put(Entity.json(userToUpdate));

		assertThat(responsePutUpdateUser.getStatus(), is(equalTo(201)));
	}

	@Test
	public void tryToDeleteUser()
	{
		Response response = singleUserTarget
				.resolveTemplate("userNumber", "1230")
				.request()
				.delete();

		assertThat(response.getStatus(), is(equalTo(200)));
	}

	@Test
	public void tryToFetchUsersByTeam()
	{
		UsersData usersByTeam = allUsersTarget
				.path("{team}")
				.resolveTemplate("team", "Galaxen1")
				.request(MediaType.APPLICATION_JSON)
				// .get(new GenericType<ArrayList<User>>(){});
				.get(UsersData.class);

		Response response = allUsersTarget
				.path("{team}")
				.resolveTemplate("team", "Galaxen1")
				.request()
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));
		assertThat(usersByTeam.users.size(), is(equalTo(2)));
	}

}
