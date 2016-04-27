package se.rodion.taskdata.repository;

import se.rodion.taskdata.model.Issue;
import se.rodion.taskdata.model.WorkItem;

public interface IssueDataRepositoryCustom
{
	Issue addIssueToWorkItem(Issue issue, WorkItem workItem);
}
