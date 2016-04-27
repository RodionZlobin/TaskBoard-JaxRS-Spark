package se.rodion.taskdata.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import se.rodion.taskdata.status.TeamStatus;

@Entity
public class Team extends AbstractEntity
{
	@Column(unique = true)
	private String name;

	@Column
	@Enumerated(EnumType.STRING)
	private TeamStatus status;

	@Column
	@OneToMany(mappedBy = "team", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private Collection<User> users;

	protected Team()
	{
	}

	public Team(String name)
	{
		this.name = name;
		this.status = TeamStatus.ACTIVE;
		this.users = new HashSet<>();
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public TeamStatus getStatus()
	{
		return status;
	}

	public void setStatus(TeamStatus status)
	{
		this.status = status;
	}

	public Collection<User> getUsers()
	{
		return users;
	}

	public User addUser(User user)
	{
		users.add(user);
		return user;
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}

		if (other instanceof Team)
		{
			Team otherTeam = (Team) other;
			if (this.name.equals(otherTeam.name)
					&& this.status.equals(otherTeam.status))
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
		result += 37 * this.name.hashCode();
		result += 37 * this.status.hashCode();
		return result;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
