package se.rodion.taskdata.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Issue extends AbstractEntity
{
	@Column
	private String description;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private WorkItem workItem;

	protected Issue()
	{
	}

	public Issue(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}

	public WorkItem getWorkItem()
	{
		return workItem;
	}

	public void setWorkItem(WorkItem workItem)
	{
		this.workItem = workItem;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}

		if (other instanceof Issue)
		{
			Issue otherIssue = (Issue) other;

			if (this.description.equals(otherIssue.description))
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
		return result;
	}

}
