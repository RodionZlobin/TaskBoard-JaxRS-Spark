package se.rodion.taskdata.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.rodion.taskdata.model.User;

public interface UserDataRepository extends UserDataRepositoryCustom, PagingAndSortingRepository<User, Long>
{
	User findByUserNumber(String userNumber);

	User findByFirstNameAndLastNameAndUserNumber(String firstName, String lastName, String userNumber);

	Collection<User> findByFirstNameOrLastNameOrUserNumber(String firstName, String lastName, String userNumber);

	Collection<User> findByTeamId(Long id);

	Page<User> findAll(Pageable pageable);
}
