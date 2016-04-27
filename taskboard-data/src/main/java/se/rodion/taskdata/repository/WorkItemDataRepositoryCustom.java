package se.rodion.taskdata.repository;

import se.rodion.taskdata.model.User;
import se.rodion.taskdata.model.WorkItem;
import se.rodion.taskdata.status.WorkItemStatus;

public interface WorkItemDataRepositoryCustom
{
	WorkItem changeStatus(WorkItem workItem, WorkItemStatus status);

	WorkItem addWorkItemToUser(WorkItem workItem, User user);
}
