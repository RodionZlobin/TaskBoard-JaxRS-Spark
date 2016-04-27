package se.rodion.taskjaxrs.web;

import static se.rodion.taskjaxrs.ContextLoader.getBean;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import se.rodion.taskdata.exception.ServiceDataException;
import se.rodion.taskdata.model.Team;
import se.rodion.taskdata.model.User;
import se.rodion.taskdata.service.TeamDataService;
import se.rodion.taskdata.service.UserDataService;

@Path("/team")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class TeamWebService
{

	private static TeamDataService teamDataService = getBean(TeamDataService.class);
	private static UserDataService userDataService = getBean(UserDataService.class);

	@Context
	private UriInfo uriInfo;

	// CREATE TEAM
	@POST
	public Response addTeam(Team team)
	{
		teamDataService.save(team);

		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getTeam").build(team.getId());

		return Response.created(location).build();
	}

	// READ TEAM
	@GET
	@Path("{id}")
	public Response getTeam(@PathParam("id") String name)
	{
		Team team = teamDataService.findByName(name);

		return Response.ok(team).build();
	}

	// INACTIVATE/DELETE TEAM
	@DELETE
	@Path("{id}")
	public Response deleteTeam(@PathParam("id") String name)
	{
		Team team = teamDataService.findByName(name);
		teamDataService.inactivateTeam(team);

		return Response.ok().build();
	}

	// UPDATE TEAM
	@PUT
	@Path("{id}")
	public Response updateTeam(@PathParam("id") String name, Team team)
	{
		Team teamToUpdate = teamDataService.findByName(name);
		teamToUpdate.setName(team.getName());

		teamDataService.save(teamToUpdate);

		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getTeam").build(teamToUpdate.getId());

		return Response.created(location).build();

	}

	// READ ALL TEAMS
	@GET
	@Path("/teams")
	public Response getAllTeams()
	{
		Collection<Team> teams = teamDataService.findAll();

		GenericEntity<Collection<Team>> entity = new GenericEntity<Collection<Team>>(teams)
		{
		};

		return Response.ok(entity).build();
	}

	// ADD USER TO TEAM
	@PUT
	@Path("{user}/team/{team}")
	public Response addUserToTeam(@PathParam("user") String userNumber, @PathParam("team") String teamName) throws ServiceDataException
	{
		User user = userDataService.findByUserNumber(userNumber);
		Team team = teamDataService.findByName(teamName);

		teamDataService.addUser(team, user);

		return Response.ok().build();
	}
}
