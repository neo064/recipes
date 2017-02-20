package com.neo064.recipe.client.common.utils;

import java.util.List;

/**
 * Class utils handling Strings.
 * 
 * @author Neo
 *
 */
public final class StringUtils {

	public static final String EMPTY = "";

	/**
	 * Class utils : private constructor.
	 */
	private StringUtils() {

	}

	/**
	 * Concatenates string value with the given separator.
	 *
	 * @param separator
	 *            the separator between each value
	 * @param values
	 *            the values to concatenate
	 * @return the values concatenated.
	 */
	public static String join(final String separator, final List<String> values) {
		if (values == null || values.isEmpty()) {
			return EMPTY;
		}
		String currentSeparator = separator;
		if (org.apache.commons.lang3.StringUtils.isEmpty(separator)) {
			currentSeparator = EMPTY;
		}

		StringBuilder result = new StringBuilder();
		for (final String string : values) {
			result.append(string).append(currentSeparator);
		}
		final int lastSeparator = result.lastIndexOf(separator);
		result = result.replace(lastSeparator, lastSeparator + currentSeparator.length(), "");

		return result.toString();
	}

	/**
	 * Concatenates string value with the given separator between the given
	 * prefix and suffix.
	 *
	 * @param prefix
	 *            the string to add at the beginning of the concatenated value.
	 * @param suffix
	 *            the string to add at the end of the concatenated value.
	 * @param separator
	 *            the separator between each value
	 * @param values
	 *            the values to concatenate
	 * @return the values concatenated.
	 */
	public static String join(final String prefix, final String suffix, final String separator,
			final List<String> values) {
		return new StringBuilder().append(prefix).append(join(separator, values)).append(suffix).toString();
	}
}
