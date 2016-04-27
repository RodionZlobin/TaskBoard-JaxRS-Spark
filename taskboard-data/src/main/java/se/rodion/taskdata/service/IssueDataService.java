package se.rodion.taskdata.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.rodion.taskdata.exception.ServiceDataException;
import se.rodion.taskdata.model.Issue;
import se.rodion.taskdata.model.WorkItem;
import se.rodion.taskdata.repository.IssueDataRepository;
import se.rodion.taskdata.status.WorkItemStatus;

@Service
public class IssueDataService
{
	IssueDataRepository issueRepository;

	@Autowired
	public IssueDataService(IssueDataRepository issueRepository)
	{
		this.issueRepository = issueRepository;
	}

	public Issue saveIssue(Issue issue)
	{
		return issueRepository.save(issue);
	}

	@Transactional
	public Issue addIssueToWorkItem(Issue issue, WorkItem workItem) throws ServiceDataException
	{
		if (workItem.getStatus().equals(WorkItemStatus.DONE))
		{
			Issue issueNew = issueRepository.addIssueToWorkItem(issue, workItem);
			return issueRepository.save(issueNew);
		}
		else
		{
			throw new ServiceDataException("WorkItem must have status 'DONE'");
		}
	}

	public Issue findIssueById(Long id)
	{
		return issueRepository.findOne(id);
	}

	public Collection<Issue> findIssuesWithWorkItems()
	{
		return issueRepository.findByWorkItemIsNotNull();
	}

	public Page<Issue> findAll(int page, int size)
	{
		return issueRepository.findAll(new PageRequest(page, size));
	}
	
	public Collection<Issue> findAll()
	{
		return (Collection<Issue>) issueRepository.findAll();
	}
	
	public Collection<WorkItem> findWorkItemsWithIssues()
	{
		return issueRepository.findWorkItemsHavingIssue();
	}
}
