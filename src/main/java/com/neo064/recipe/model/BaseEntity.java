package com.neo064.recipe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Represents a basic entity from where all application entities inherit from.
 * Mandatory fields (id, creation date, modification date).
 *
 * @author Neo
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 9167185454315714071L;

	public static final String ID_CONST = "id";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date creationDate;

	private Date modificationDate;

	public Date getCreationDate() {
		return creationDate;
	}

	public Long getId() {
		return id;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setModificationDate(final Date modificationDate) {
		this.modificationDate = modificationDate;
	}

}
