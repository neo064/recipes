package com.neo064.recipe.model.common;

import com.neo064.recipe.model.BaseEntity;
import com.neo064.recipe.model.Unit;

/**
 * Contains the conversion of all units.
 * 
 * @author Neo
 *
 */
public class Conversion extends BaseEntity {

	private Unit from;
	private Double conversionValue; // in ml

	public Double getConversionValue() {
		return conversionValue;
	}

	public Unit getFrom() {
		return from;
	}

	public void setConversionValue(final Double conversionValue) {
		this.conversionValue = conversionValue;
	}

	public void setFrom(final Unit from) {
		this.from = from;
	}
}
