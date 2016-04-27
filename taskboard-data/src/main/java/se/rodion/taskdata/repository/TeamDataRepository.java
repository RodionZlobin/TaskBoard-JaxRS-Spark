package se.rodion.taskdata.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.rodion.taskdata.model.Team;

public interface TeamDataRepository extends TeamDataRepositoryCustom, PagingAndSortingRepository<Team, Long>
{
	Collection<Team> findAll();
	
	Team findByName(String name);
	
	Page<Team> findAll(Pageable pageable);
}
