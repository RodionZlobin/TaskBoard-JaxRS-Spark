package se.rodion.taskdata.repository;

import org.springframework.transaction.annotation.Transactional;

import se.rodion.taskdata.model.User;
import se.rodion.taskdata.status.UserStatus;
import se.rodion.taskdata.status.WorkItemStatus;

public class UserDataRepositoryImpl implements UserDataRepositoryCustom
{

	@Override
	@Transactional
	public void inactivateUser(User user)
	{
		user.setUserStatus(UserStatus.NON_VALID);
		user.getWorkItems().forEach(e -> e.setStatus(WorkItemStatus.UNSTARTED));
	}
}
