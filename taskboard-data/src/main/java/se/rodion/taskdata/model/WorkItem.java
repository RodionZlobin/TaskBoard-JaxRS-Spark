package se.rodion.taskdata.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import se.rodion.taskdata.status.WorkItemStatus;

@Entity
public class WorkItem extends AbstractEntity
{
	@Column
	private String description;

	@Column
	@Enumerated(EnumType.STRING)
	private WorkItemStatus status;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@Column
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "workItem")
	private Collection<Issue> issues;

	protected WorkItem()
	{
	}

	public WorkItem(String description)
	{
		this.description = description;
		this.status = WorkItemStatus.STARTED;
		issues = new ArrayList<>();
	}

	public String getDescription()
	{
		return description;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Issue addIssue(Issue issue)
	{
		issues.add(issue);
		return issue;
	}

	public Collection<Issue> getIssues()
	{
		return issues;
	}

	public WorkItemStatus getStatus()
	{
		return status;
	}

	public void setStatus(WorkItemStatus status)
	{
		this.status = status;
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}

		if (other instanceof WorkItem)
		{
			WorkItem otherWorkItem = (WorkItem) other;

			if (this.description.equals(otherWorkItem.description)
					&& this.status.equals(otherWorkItem.status))
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
		result += 37 * this.description.hashCode();
		result += 37 * this.status.hashCode();
		return result;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
		// return user.getFirstName() + " " + description;
	}
}
