package se.rodion.taskdata.repository;

import org.springframework.transaction.annotation.Transactional;

import se.rodion.taskdata.model.Issue;
import se.rodion.taskdata.model.WorkItem;
import se.rodion.taskdata.status.WorkItemStatus;

public class IssueDataRepositoryImpl implements IssueDataRepositoryCustom
{

	@Override
	@Transactional
	public Issue addIssueToWorkItem(Issue issue, WorkItem workItem)
	{
		
		workItem.addIssue(issue);
		workItem.setStatus(WorkItemStatus.UNSTARTED);
		issue.setWorkItem(workItem);
		return issue;
	}

}
