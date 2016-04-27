package se.rodion.taskdata.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.rodion.taskdata.exception.ServiceDataException;
import se.rodion.taskdata.model.Team;
import se.rodion.taskdata.model.User;
import se.rodion.taskdata.repository.TeamDataRepository;

@Service
public class TeamDataService
{
	TeamDataRepository teamRepository;

	@Autowired
	public TeamDataService(TeamDataRepository teamRepository)
	{
		this.teamRepository = teamRepository;
	}

	public Team save(Team team)
	{
		return teamRepository.save(team);
	}

	@Transactional
	public void deleteTeam(Long id)
	{
		teamRepository.delete(id);
	}

	@Transactional
	public void inactivateTeam(Team team)
	{
		teamRepository.inactivateTeam(team);
		teamRepository.save(team);
	}
	
	public Team findByName(String name)
	{
		return teamRepository.findByName(name);
	}
	
	public Collection<Team> findAll()
	{
		return teamRepository.findAll();
	}

	@Transactional
	public void addUser(Team team, User user) throws ServiceDataException
	{

		if (team.getUsers().size() < 10)
		{
			if (user.getTeam() == null)
			{
				teamRepository.addUser(team, user);
				teamRepository.save(team);
			}
			else
			{
				throw new ServiceDataException("User could enter to 1 Team at once only");
			}
		}
		else
		{
			throw new ServiceDataException("This team have already 10 members");
		}
	}

	public Team findTeam(Long id)
	{
		return teamRepository.findOne(id);
	}

	public Team updateTeam(Team team)
	{
		return teamRepository.save(team);
	}

	public Page<Team> findAll(int page, int size)
	{
		return teamRepository.findAll(new PageRequest(page, size));
	}
}
