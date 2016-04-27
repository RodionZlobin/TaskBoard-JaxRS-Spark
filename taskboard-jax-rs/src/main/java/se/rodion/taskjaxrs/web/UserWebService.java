package se.rodion.taskjaxrs.web;

import static se.rodion.taskjaxrs.ContextLoader.getBean;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.data.domain.Page;

import se.rodion.taskdata.exception.ServiceDataException;
import se.rodion.taskdata.model.Team;
import se.rodion.taskdata.model.User;
import se.rodion.taskdata.service.TeamDataService;
import se.rodion.taskdata.service.UserDataService;
import se.rodion.taskjaxrs.web.exception.BadRequestException;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class UserWebService
{
	private static UserDataService userDataService = getBean(UserDataService.class);
	private static TeamDataService teamDataService = getBean(TeamDataService.class);

	@Context
	private UriInfo uriInfo;
	
	// CREATE USER
	@POST
	public Response addUser(User user) throws ServiceDataException
	{
		userDataService.save(user);

		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getUser").build(user.getUserNumber());

		return Response.created(location).build();
	}

	// READ USER
	@GET
	@Path("{id}")
	public Response getUser(@PathParam("id") String userNumber)
	{
		User user = userDataService.findByUserNumber(userNumber);

		if (user == null)
		{
			throw new BadRequestException("Could not find user with usernumber " + userNumber);
		}

		return Response.ok(user).build();
	}

	// INACTIVATE/DELETE USER
	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") String userNumber)
	{
		User user = userDataService.findByUserNumber(userNumber);
		userDataService.inactivateUser(user);

		return Response.ok().build();
	}

	// GET USER BY FIRSTNAME OR LASTNAME OR USERNUMBER
	@GET
	@Path("/query")
	public Response getUserByFirstNameOrLastNameOrUserNumber(@QueryParam("firstName") String firstName,
			@QueryParam("lastName") String lastName,
			@QueryParam("userNumber") String userNumber)
	{
		Collection<User> users = userDataService.findByFirstNameOrLastNameOrUserNumber(firstName, lastName, userNumber);

		if (users == null)
		{
			throw new BadRequestException("Could not find any user(s) with specified parameter(s): " + firstName + " " + lastName + " " + userNumber);
		}
		
		GenericEntity<Collection<User>> entity = new GenericEntity<Collection<User>>(users)
		{
		};
		

		return Response.ok(entity).build();
	}

	// UPDATE USER
	@PUT
	@Path("{id}")
	public Response updateUser(@PathParam("id") String userNumber, User user) throws ServiceDataException
	{
		User userToUpdate = userDataService.findByUserNumber(userNumber);
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		userToUpdate.setUsername(user.getUsername());

		userDataService.save(userToUpdate);

		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "updateUser").build(userToUpdate.getUserNumber());

		return Response.created(location).build();

	}

	// READ USERS FROM SPECIFIED TEAM
	@GET
	@Path("users/{team}")
	public Response getUsersFromSpecifiedTeam(@PathParam("team") String teamName)
	{
		Team team = teamDataService.findByName(teamName);

		Collection<User> users = userDataService.findByTeam(team.getId());
		
		if (users == null)
		{
			throw new BadRequestException("Could not find any user(s) with specified parameter(s): " + teamName);
		}

		GenericEntity<Collection<User>> entity = new GenericEntity<Collection<User>>(users)
		{
		};

		return Response.ok(entity).build();
	}

	// READ ALL USERS
	@GET
	@Path("users")
	public Response getAllUsers()
	{
		Collection<User> users = userDataService.findAll();
		
		if (users == null)
		{
			throw new BadRequestException("Could not find any user(s)");
		}
		
		GenericEntity<Collection<User>> entity = new GenericEntity<Collection<User>>(users)
		{
		};

		return Response.ok(entity).build();
	}

	// READ ALL USERS WITH PAGE
	@SuppressWarnings("unused")
	@GET
	@Path("users/pages")
	public Response getAllUsersByPages(@QueryParam("page") int page,
										@QueryParam("size") int size)
	{
		Page<User> usersPage = userDataService.findAll(page, size);
		Collection<User> users = new ArrayList<>();

		if (users == null)
		{
			throw new BadRequestException("Could not find any user(s)");
		}
		
		usersPage.forEach(e -> users.add(e));
		
		GenericEntity<Collection<User>> entity = new GenericEntity<Collection<User>>(users)
		{
		};

		return Response.ok(entity).build();
	}

}
