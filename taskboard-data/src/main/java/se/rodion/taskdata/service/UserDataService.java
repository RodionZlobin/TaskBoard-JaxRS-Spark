package se.rodion.taskdata.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.rodion.taskdata.exception.ServiceDataException;
import se.rodion.taskdata.model.User;
import se.rodion.taskdata.repository.UserDataRepository;

@Service
public class UserDataService
{
	@Autowired
	private UserDataRepository userRepository;

	@Autowired
	public UserDataService(UserDataRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	public User save(User user) throws ServiceDataException
	{
		if (user.getUsername().length() < 10)
		{
			throw new ServiceDataException("Username could have at least 10 symbols");
		}
		return userRepository.save(user);
	}

	public void removeUser(User user)
	{
		userRepository.delete(user.getId());
	}

	@Transactional
	public void inactivateUser(User user)
	{
		userRepository.inactivateUser(user);
		userRepository.save(user);
	}

	public User findByUserNumber(String userNumber)
	{
		return userRepository.findByUserNumber(userNumber);
	}

	public User findByFirstNameAndLastNameAndUserNumber(String firstName, String lastName, String userNumber)
	{
		return userRepository.findByFirstNameAndLastNameAndUserNumber(firstName, lastName, userNumber);
	}

	public Collection<User> findByFirstNameOrLastNameOrUserNumber(String firstName, String lastName, String userNumber)
	{
		return userRepository.findByFirstNameOrLastNameOrUserNumber(firstName, lastName, userNumber);
	}

	public Collection<User> findByTeam(Long id)
	{
		return userRepository.findByTeamId(id);
	}

	public Page<User> findAll(int page, int size)
	{
		return userRepository.findAll(new PageRequest(page, size));
	}
	
	public Collection<User> findAll()
	{
		return (Collection<User>) userRepository.findAll();
	}
}
