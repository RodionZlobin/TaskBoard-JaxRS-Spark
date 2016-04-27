package se.rodion.taskdata.model;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity
{
	@Id
	@GeneratedValue
	protected Long id;

	@CreatedDate
	protected Date creatingDate;

	@LastModifiedDate
	protected Date modificationDate;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Date getCreatingDate()
	{
		return creatingDate;
	}

	public void setCreatingDate(Date creatingDate)
	{
		this.creatingDate = creatingDate;
	}

	public Date getModificationDate()
	{
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate)
	{
		this.modificationDate = modificationDate;
	}

}
