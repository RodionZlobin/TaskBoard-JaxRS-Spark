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

import se.rodion.taskdata.model.Issue;
import se.rodion.taskdata.model.WorkItem;

public class JaxRsClientTestIssue
{

	Client client = ClientBuilder.newClient();

	WebTarget baseIssueTarget;
	WebTarget issueTarget;
	WebTarget singleIssueTarget;
	WebTarget allIssuesTarget;

	static class IssuesData
	{
		public List<Issue> issues;
	}

	static class WorkItemsData
	{
		public List<WorkItem> workItems;
	}

	@Before
	public void setUp()
	{
		baseIssueTarget = client.target("http://localhost:8080/taskboard-jax-rs/");
		issueTarget = baseIssueTarget.path("issue");
		singleIssueTarget = issueTarget.path("{id}");
		allIssuesTarget = issueTarget.path("issues");
	}

	@Test
	public void tryToGetIssue()
	{
		Issue issue = singleIssueTarget
				.resolveTemplate("id", 7)
				.request(MediaType.APPLICATION_JSON)
				.get(Issue.class);

		Response response = singleIssueTarget
				.resolveTemplate("id", 7)
				.request()
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));
		assertThat(issue.getId(), is(equalTo(7L)));
		assertThat(issue.getDescription(), is(equalTo("Issue2")));
	}

	@Test
	public void tryToCreateIssue()
	{
		Issue issueToCreate = new Issue("Issue4");

		Response response = issueTarget
				.request()
				.post(Entity.json(issueToCreate));

		assertThat(response.getStatus(), is(equalTo(201)));
	}

	@Test
	public void tryToUpdateIssue()
	{
		Issue issueToUpdate = new Issue("IssueNew");

		Response response = singleIssueTarget
				.resolveTemplate("id", 16)
				.request()
				.put(Entity.json(issueToUpdate));

		assertThat(response.getStatus(), is(equalTo(201)));
	}

	@Test
	public void tryToGetAllIssues()
	{
		IssuesData issuesFromClient = allIssuesTarget
				.request(MediaType.APPLICATION_JSON)
				.get(IssuesData.class);

		Response responseAllTeams = allIssuesTarget.request().get();

		assertThat(responseAllTeams.getStatus(), is(equalTo(200)));
		assertThat(issuesFromClient.issues.size(), is(equalTo(4)));
	}

	@Test
	public void tryToAddIssueToWorkItem()
	{
		Long issueId = 16L;
		Long workItemId = 15L;
		WorkItem mockWorkItem = new WorkItem("workitem3");

		Response response = issueTarget
				.path("/{issueId}/workitem/{workitemId}")
				.resolveTemplate("issueId", issueId)
				.resolveTemplate("workitemId", workItemId)
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.json(mockWorkItem));

		assertThat(response.getStatus(), is(equalTo(201)));
	}

	@Test
	public void tryToGetWorkItemsWhichHasIssue()
	{
		WorkItemsData issuesWithWorkItem = issueTarget
				.path("workitems")
				.request(MediaType.APPLICATION_JSON)
				.get(WorkItemsData.class);

		Response response = issueTarget
				.path("workitems")
				.request()
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));
		assertThat(issuesWithWorkItem.workItems.size(), is(equalTo(3)));
	}

}
