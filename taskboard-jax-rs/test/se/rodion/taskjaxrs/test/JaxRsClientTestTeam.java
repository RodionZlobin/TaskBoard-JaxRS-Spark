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

import se.rodion.taskdata.model.Team;

public class JaxRsClientTestTeam
{

	Client client = ClientBuilder.newClient();

	WebTarget baseTeamTarget;
	WebTarget teamTarget;
	WebTarget singleTeamTarget;
	WebTarget allTeamsTarget;

	static class TeamsData
	{
		public List<Team> teams;
	}

	@Before
	public void setUp()
	{
		baseTeamTarget = client.target("http://localhost:8080/taskboard-jax-rs/");
		teamTarget = baseTeamTarget.path("team");
		singleTeamTarget = teamTarget.path("{id}");
		allTeamsTarget = teamTarget.path("teams");
	}

	@Test
	public void tryToCreateTeam()
	{
		Team teamToCreate = new Team("Solen");

		Response response = teamTarget
				.request()
				.post(Entity.json(teamToCreate));

		assertThat(response.getStatus(), is(equalTo(201)));
	}

	@Test
	public void tryToGetTeam()
	{
		String teamName = "Galaxen1";

		Team team = singleTeamTarget
				.resolveTemplate("id", teamName)
				.request(MediaType.APPLICATION_JSON)
				.get(Team.class);

		Response response = singleTeamTarget
				.resolveTemplate("id", teamName)
				.request()
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));
		assertThat(team.getId(), is(equalTo(3L)));
	}

	@Test
	public void tryToUpdateTeam()
	{
		Team teamToUpdate = new Team("Stars");

		Response response = singleTeamTarget
				.resolveTemplate("id", "Solen")
				.request()
				.put(Entity.json(teamToUpdate));

		assertThat(response.getStatus(), is(equalTo(201)));
	}

	@Test
	public void tryToDeleteTeam()
	{
		Response response = singleTeamTarget
				.resolveTemplate("id", "Solen")
				.request()
				.delete();

		assertThat(response.getStatus(), is(equalTo(200)));
	}

	@Test
	public void tryToGetAllTeams()
	{
		TeamsData teamsFromClient = allTeamsTarget
				.request(MediaType.APPLICATION_JSON)
				.get(TeamsData.class);

		Response responseAllTeams = allTeamsTarget.request().get();

		assertThat(responseAllTeams.getStatus(), is(equalTo(200)));
		assertThat(teamsFromClient.teams.size(), is(equalTo(2)));
	}

	@Test
	public void tryToAssignUserToTeam()
	{
		String teamName = "Solen";
		String userNumber = "3456";
		Team mockTeam = new Team(teamName);

		Response response = teamTarget
				.path("{user}/team/{team}")
				.resolveTemplate("user", userNumber)
				.resolveTemplate("team", teamName)
				.request()
				.put(Entity.json(mockTeam));

		assertThat(response.getStatus(), is(equalTo(200)));
	}

}
