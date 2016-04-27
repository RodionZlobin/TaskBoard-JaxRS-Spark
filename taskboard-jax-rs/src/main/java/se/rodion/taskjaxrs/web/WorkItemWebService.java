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
import se.rodion.taskdata.model.User;
import se.rodion.taskdata.model.WorkItem;
import se.rodion.taskdata.service.UserDataService;
import se.rodion.taskdata.service.WorkItemDataService;
import se.rodion.taskdata.status.WorkItemStatus;

@Path("/workitem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class WorkItemWebService
{

	WorkItemDataService workItemDataService = getBean(WorkItemDataService.class);
	UserDataService userDataService = getBean(UserDataService.class);

	@Context
	private UriInfo uriInfo;

	// CREATE WORKITEM
	@POST
	public Response addWorkItem(WorkItem workItem)
	{
		workItemDataService.save(workItem);

		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getWorkItem").build(workItem.getId());

		return Response.created(location).build();
	}

	// READ WORKITEM
	@GET
	@Path("{id}")
	public Response getWorkItem(@PathParam("id") Long id)
	{
		WorkItem workItem = workItemDataService.findById(id);

		return Response.ok(workItem).build();
	}

	// CHANGE WORKITEMS STATUS
	@PUT
	@Path("/changeStatus/{id}/{status}")
	public Response changeWorkItemStatus(@PathParam("id") Long id, @PathParam("status") String statusAsString)
	{
		WorkItem workItem = workItemDataService.findById(id);
		WorkItemStatus status = WorkItemStatus.valueOf(statusAsString.toUpperCase());
		workItemDataService.changeStatus(workItem, status);

		return Response.ok(workItem).build();
	}

	// INACTIVATE/DELETE WORKITEM
	@DELETE
	@Path("{id}")
	public Response inactivateWorkItem(@PathParam("id") Long id)
	{
		workItemDataService.deleteWorkItem(id);

		return Response.ok().build();
	}

	// ADD WOTKITEM TO USER
	@PUT
	@Path("/{id}/assignUser/{userNumber}")
	public Response assignWorkItemToUser(@PathParam("id") Long id, @PathParam("userNumber") String userNumber) throws ServiceDataException
	{
		WorkItem workItem = workItemDataService.findById(id);
		User user = userDataService.findByUserNumber(userNumber);
		workItemDataService.addWorkItemToUser(workItem, user);

		return Response.ok(workItem).build();
	}

	// READ WORKITEMS FROM SPECIFIED TEAM
	@GET
	@Path("/workitems/team/{name}")
	public Response fetchWorkItemsByTeam(@PathParam("name") String name)
	{
		Collection<WorkItem> workItems = workItemDataService.fetchWorkItemsByTeam(name);

		GenericEntity<Collection<WorkItem>> entity = new GenericEntity<Collection<WorkItem>>(workItems)
		{
		};
		return Response.ok(entity).build();
	}

	// READ WORKITEMS WITH SPECIFIED STATUS
	@GET
	@Path("/workitems/status/{status}")
	public Response fetchWorkItemsByStatus(@PathParam("status") String statusAsString)
	{
		WorkItemStatus status = WorkItemStatus.valueOf(statusAsString.toUpperCase());
		Collection<WorkItem> workItems = workItemDataService.fetchAllWorkItemsByStatus(status);

		GenericEntity<Collection<WorkItem>> entity = new GenericEntity<Collection<WorkItem>>(workItems)
		{
		};
		return Response.ok(entity).build();
	}

	// READ ALL WORKITEMS
	@GET
	@Path("/workitems")
	public Response fetchAllWorkItems()
	{
		Collection<WorkItem> workItems = workItemDataService.fetchAllWorkItems();

		GenericEntity<Collection<WorkItem>> entity = new GenericEntity<Collection<WorkItem>>(workItems)
		{
		};
		return Response.ok(entity).build();
	}

	// READ WORKITEMS FROM SPECIFIED USER
	@GET
	@Path("/workitems/user/{userNumber}")
	public Response fetchWorkItemsForSpecifiedUser(@PathParam("userNumber") String userNumber)
	{
		User user = userDataService.findByUserNumber(userNumber);
		Collection<WorkItem> workItems = workItemDataService.fetchWorkItemsByUser(user);

		GenericEntity<Collection<WorkItem>> entity = new GenericEntity<Collection<WorkItem>>(workItems)
		{
		};

		return Response.ok(entity).build();
	}

	// READ WORKITEMS WITH SPECIFIED TEXT
	@GET
	@Path("/workitems/text/{id}")
	public Response fetchWorkItemsWithSpecifiedText(@PathParam("id") String text)
	{

		Collection<WorkItem> workItems = workItemDataService.fetchWorkItemsByDescription(text);

		GenericEntity<Collection<WorkItem>> entity = new GenericEntity<Collection<WorkItem>>(workItems)
		{
		};

		return Response.ok(entity).build();
	}

}
