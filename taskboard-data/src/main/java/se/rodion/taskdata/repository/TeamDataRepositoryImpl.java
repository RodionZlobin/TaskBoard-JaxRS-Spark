package se.rodion.taskdata.repository;

import org.springframework.transaction.annotation.Transactional;

import se.rodion.taskdata.model.Team;
import se.rodion.taskdata.model.User;
import se.rodion.taskdata.status.TeamStatus;

public class TeamDataRepositoryImpl implements TeamDataRepositoryCustom
{

	@Override
	@Transactional
	public void addUser(Team team, User user)
	{
		team.addUser(user);
		user.setTeam(team);
	}

	@Override
	public void inactivateTeam(Team team)
	{
		team.setStatus(TeamStatus.NON_ACTIVE);
	}

}
