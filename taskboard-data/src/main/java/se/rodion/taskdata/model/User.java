package se.rodion.taskdata.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import se.rodion.taskdata.status.UserStatus;

@Entity
public class User extends AbstractEntity
{
	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String username;

	@Column(unique = true)
	private String userNumber;

	@Column
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@ManyToOne(fetch = FetchType.EAGER)
	private Team team;

	@Column
	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private Collection<WorkItem> workItems;

	protected User()
	{

	}

	public User(String firstName, String lastName, String username, String userNumber)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.userNumber = userNumber;
		this.userStatus = UserStatus.VALID;
		workItems = new ArrayList<WorkItem>();
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getUserNumber()
	{
		return userNumber;
	}

	public UserStatus getUserStatus()
	{
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus)
	{
		this.userStatus = userStatus;
	}

	public Collection<WorkItem> getWorkItems()
	{
		return workItems;
	}

	public WorkItem addWorkItem(WorkItem workItem)
	{
		workItems.add(workItem);
		return workItem;
	}

	public Team getTeam()
	{
		return team;
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof User)
		{
			User otherUser = (User) other;

			if (this.username.equals(otherUser.username)
					&& this.firstName.equals(otherUser.firstName)
					&& this.lastName.equals(otherUser.lastName)
					&& this.userNumber.equals(otherUser.userNumber)
					&& this.userStatus.equals(otherUser.userStatus))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += 37 * this.username.hashCode();
		result += 37 * this.firstName.hashCode();
		result += 37 * this.lastName.hashCode();
		result += 37 * this.userNumber.hashCode();
		result += 37 * this.userStatus.hashCode();
		return result;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
