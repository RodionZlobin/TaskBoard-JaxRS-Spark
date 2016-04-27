package se.rodion.taskdata.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.rodion.taskdata.model.Issue;
import se.rodion.taskdata.model.WorkItem;

public interface IssueDataRepository extends IssueDataRepositoryCustom, PagingAndSortingRepository<Issue, Long>
{
	Collection<Issue> findByWorkItemIsNotNull();

	Page<Issue> findAll(Pageable pageable);
	
	@Query("SELECT i.workItem FROM #{#entityName} i")
	Collection<WorkItem> findWorkItemsHavingIssue();
}
