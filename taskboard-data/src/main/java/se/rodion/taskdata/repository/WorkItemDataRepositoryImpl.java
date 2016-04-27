package se.rodion.taskdata.repository;

import org.springframework.transaction.annotation.Transactional;

import se.rodion.taskdata.model.User;
import se.rodion.taskdata.model.WorkItem;
import se.rodion.taskdata.status.WorkItemStatus;

public class WorkItemDataRepositoryImpl implements WorkItemDataRepositoryCustom
{

	@Override
	public WorkItem changeStatus(WorkItem workItem, WorkItemStatus status)
	{
		workItem.setStatus(status);
		return workItem;
	}

	@Override
	@Transactional
	public WorkItem addWorkItemToUser(WorkItem workItem, User user)
	{
		user.addWorkItem(workItem);
		workItem.setUser(user);
		return workItem;
	}

}
