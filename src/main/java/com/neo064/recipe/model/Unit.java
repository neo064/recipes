package com.neo064.recipe.model;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * Represents a measurement unit in cooking, defined by: </br>
 * - a name </br>
 * - a unique code identifier </br>
 * - an abbreviation </br>
 * 
 * @author Neo
 *
 */
@Entity
public class Unit extends BaseEntity implements Serializable, ICode {

	private static final long serialVersionUID = 4950395623670630777L;

	public static final String ABBREVIATION_CONST = "abbreviation";

	private String name;

	private String code;

	private String abbreviation;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(final String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(final String code) {
		this.code = code;

	}

}
