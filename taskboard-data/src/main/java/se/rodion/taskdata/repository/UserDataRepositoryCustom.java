package se.rodion.taskdata.repository;

import se.rodion.taskdata.model.User;

public interface UserDataRepositoryCustom
{
	void inactivateUser(User user);
}
