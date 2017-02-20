package com.neo064.recipe.client.common.utils;

/**
 * Class utils to handles path.
 * 
 * @author Neo
 *
 */
public final class Path {

	/**
	 * Class Utils : private constructor.
	 */
	private Path() {

	}

	/**
	 * Concatenates a list of string values to one string where each value is
	 * separated by a dot.
	 * 
	 * @param values
	 *            the strings to concatenates
	 * @return the concatenated value
	 */
	public static String concat(final String... values) {
		if (values == null || values.length == 0) {
			return null;
		}
		final StringBuilder stringBuilder = new StringBuilder();
		for (final String string : values) {
			stringBuilder.append(string).append('.');
		}
		final int lastIndexOfDot = stringBuilder.lastIndexOf(".");
		return stringBuilder.replace(lastIndexOfDot, lastIndexOfDot + 1, "").toString();
	}
}
