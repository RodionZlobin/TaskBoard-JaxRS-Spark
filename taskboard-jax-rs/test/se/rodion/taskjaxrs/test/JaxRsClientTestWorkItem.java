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
import se.rodion.taskdata.model.WorkItem;
import se.rodion.taskdata.status.WorkItemStatus;

public class JaxRsClientTestWorkItem
{
	Client client = ClientBuilder.newClient();

	WebTarget baseWorkItemTarget;
	WebTarget workItemTarget;
	WebTarget singleWorkItemTarget;
	WebTarget allWorkItemsTarget;
	
	static class WorkItemsData
	{
		public List<WorkItem> workItems;
	}

	@Before
	public void setUp()
	{
		baseWorkItemTarget = client.target("http://localhost:8080/taskboard-jax-rs/");
		workItemTarget = baseWorkItemTarget.path("workitem");
		singleWorkItemTarget = workItemTarget.path("{id}");
		allWorkItemsTarget = workItemTarget.path("workitems");
	}

	@Test
	public void tryToCreateWorkItem()
	{
		WorkItem workItemToCreate = new WorkItem("New Workitem");

		Response responseCreateWorkItem = workItemTarget
				.request()
				.post(Entity.json(workItemToCreate));

		assertThat(responseCreateWorkItem.getStatus(), is(equalTo(201)));
	}

	@Test
	public void tryToReadWorkItem()
	{
		WorkItem workItem = singleWorkItemTarget
				.resolveTemplate("id", 4)
				.request(MediaType.APPLICATION_JSON)
				.get(WorkItem.class);

		Response response = singleWorkItemTarget
				.resolveTemplate("id", 4)
				.request()
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));
		assertThat(workItem.getDescription(), is(equalTo("WorkItem1")));
	}

	@Test
	public void tryToReadAllWorkItems()
	{
		WorkItemsData allWorkItems = allWorkItemsTarget
				.request(MediaType.APPLICATION_JSON)
				.get(WorkItemsData.class);

		Response response = allWorkItemsTarget.request().get();

		assertThat(response.getStatus(), is(equalTo(200)));
		assertThat(allWorkItems.workItems.size(), is(equalTo(4)));
	}

	@Test
	public void tryToDeleteWorkItem()
	{
		Response response = singleWorkItemTarget
				.resolveTemplate("id", 63)
				.request()
				.delete();

		assertThat(response.getStatus(), is(equalTo(200)));
	}

	@Test
	public void tryToChangeWorkItemStatus()
	{
		Long id = 15L;
		WorkItemStatus status = WorkItemStatus.STARTED;

		WorkItem mockWorkItem = new WorkItem("description");
		mockWorkItem.setId(id);
		mockWorkItem.setStatus(status);

		Response response = workItemTarget
				.path("/changeStatus/{id}/{status}")
				.resolveTemplate("id", id)
				.resolveTemplate("status", "unstarted")
				.request()
				.put(Entity.json(mockWorkItem));

		WorkItem workItem = singleWorkItemTarget
				.resolveTemplate("id", id)
				.request(MediaType.APPLICATION_JSON)
				.get(WorkItem.class);

		assertThat(response.getStatus(), is(equalTo(200)));
		assertThat(workItem.getStatus(), is(equalTo(WorkItemStatus.UNSTARTED)));
	}

	@Test
	public void tryToAssignWorkItemToUser()
	{
		Long idWorkItem = 15L;
		WorkItemStatus status = WorkItemStatus.STARTED;

		WorkItem mockWorkItem = new WorkItem("description");
		mockWorkItem.setId(idWorkItem);
		mockWorkItem.setStatus(status);

		Long idUser = 18L;
		String userNumber = "4567";
		User mockUser = new User("RodionN", "ZlobinN", "username10", userNumber);
		mockUser.setId(idUser);

		Response response = workItemTarget
				.path("/{id}/assignUser/{userNumber}")
				.resolveTemplate("id", idWorkItem)
				.resolveTemplate("userNumber", userNumber)
				.request()
				.put(Entity.json(mockWorkItem));

		assertThat(response.getStatus(), is(equalTo(200)));
	}

	@Test
	public void tryToGetWorkItemsByTeam()
	{
		Response response = allWorkItemsTarget
				.path("team/{name}")
				.resolveTemplate("name", "Galaxen1")
				.request(MediaType.APPLICATION_JSON)
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));

		WorkItemsData allWorkItems = allWorkItemsTarget
				.path("team/{name}")
				.resolveTemplate("name", "Galaxen1")
				.request(MediaType.APPLICATION_JSON)
				.get(WorkItemsData.class);

		assertThat(allWorkItems.workItems.size(), is(equalTo(2)));

	}

	@Test
	public void tryToGetWorkItemsByStatus()
	{
		Response response = allWorkItemsTarget
				.path("status/{status}")
				.resolveTemplate("status", "unstarted")
				.request(MediaType.APPLICATION_JSON)
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));

		WorkItemsData allWorkItems = allWorkItemsTarget
				.path("status/{status}")
				.resolveTemplate("status", "unstarted")
				.request(MediaType.APPLICATION_JSON)
				.get(WorkItemsData.class);

		assertThat(allWorkItems.workItems.size(), is(equalTo(3)));
	}

	@Test
	public void tryToGetWorkItemsByUser()
	{
		Response response = allWorkItemsTarget
				.path("user/{userNumber}")
				.resolveTemplate("userNumber", 1234)
				.request(MediaType.APPLICATION_JSON)
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));

		WorkItemsData allWorkitems = allWorkItemsTarget
				.path("user/{userNumber}")
				.resolveTemplate("userNumber", 1234)
				.request(MediaType.APPLICATION_JSON)
				.get(WorkItemsData.class);

		assertThat(allWorkitems.workItems.size(), is(equalTo(1)));
	}

	@Test
	public void tryToGetWorkItemsByText()
	{
		Response response = allWorkItemsTarget
				.path("text/{id}")
				.resolveTemplate("id", "kit")
				.request(MediaType.APPLICATION_JSON)
				.get();

		assertThat(response.getStatus(), is(equalTo(200)));

		WorkItemsData allWorkitems = allWorkItemsTarget
				.path("text/{id}")
				.resolveTemplate("id", "kit")
				.request(MediaType.APPLICATION_JSON)
				.get(WorkItemsData.class);

		assertThat(allWorkitems.workItems.size(), is(equalTo(3)));
	}
}
