package se.rodion.taskdata.repository;

import se.rodion.taskdata.model.Team;
import se.rodion.taskdata.model.User;

public interface TeamDataRepositoryCustom
{
	void addUser(Team team, User user);

	void inactivateTeam(Team team);
}
