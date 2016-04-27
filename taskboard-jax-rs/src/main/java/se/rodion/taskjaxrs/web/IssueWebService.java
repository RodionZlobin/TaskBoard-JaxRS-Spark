package se.rodion.taskjaxrs.web;

import static se.rodion.taskjaxrs.ContextLoader.getBean;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.Consumes;
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
import se.rodion.taskdata.model.Issue;
import se.rodion.taskdata.model.WorkItem;
import se.rodion.taskdata.service.IssueDataService;
import se.rodion.taskdata.service.WorkItemDataService;

@Path("/issue")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class IssueWebService
{

	IssueDataService issueDataService = getBean(IssueDataService.class);
	WorkItemDataService workItemDataService = getBean(WorkItemDataService.class);

	@Context
	private UriInfo uriInfo;

	// CREATE ISSUE
	@POST
	public Response addIssue(Issue issue)
	{
		issueDataService.saveIssue(issue);

		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getIssue").build(issue.getId());

		return Response.created(location).build();

	}

	// READ ISSUE
	@GET
	@Path("{id}")
	public Response getIssue(@PathParam("id") Long id)
	{
		Issue issue = issueDataService.findIssueById(id);

		return Response.ok(issue).build();
	}

	// UPDATE ISSUE
	@PUT
	@Path("{id}")
	public Response updateIssue(@PathParam("id") Long id, Issue issue)
	{
		Issue issueToUpdate = issueDataService.findIssueById(id);
		issueToUpdate.setDescription(issue.getDescription());

		issueDataService.saveIssue(issueToUpdate);

		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getIssue").build(issueToUpdate.getId());

		return Response.created(location).build();
	}

	// ADD ISSUE TO WORKITEM
	@PUT
	@Path("/{issueId}/workitem/{workitemId}")
	public Response assignIssueToWorkItem(@PathParam("issueId") Long issueId,
			@PathParam("workitemId") Long workItemId) throws ServiceDataException
	{
		Issue issue = issueDataService.findIssueById(issueId);
		WorkItem workItem = workItemDataService.findById(workItemId);

		issueDataService.addIssueToWorkItem(issue, workItem);

		URI location = uriInfo.getAbsolutePathBuilder().path(getClass(), "getIssue").build(issue.getId());

		return Response.created(location).build();

	}

	// READ ALL ISSUES
	@GET
	@Path("/issues")
	public Response fetchAllIssues()
	{
		Collection<Issue> issues = issueDataService.findAll();

		GenericEntity<Collection<Issue>> entity = new GenericEntity<Collection<Issue>>(issues)
		{
		};

		return Response.ok(entity).build();
	}

	// READ ALL ISSUES WHICH HAS WORKITEM
	@GET
	@Path("/workitems")
	public Response fetchWorkItemsWithIssue()
	{
		Collection<WorkItem> workItems = issueDataService.findWorkItemsWithIssues();

		GenericEntity<Collection<WorkItem>> entity = new GenericEntity<Collection<WorkItem>>(workItems)
		{
		};

		return Response.ok(entity).build();

	}
}
