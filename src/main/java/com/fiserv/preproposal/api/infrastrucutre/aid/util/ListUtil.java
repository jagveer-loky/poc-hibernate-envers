package com.fiserv.preproposal.api.infrastrucutre.aid.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * The Class PropertiesUtil.
 */
public class ListUtil {

	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <T> List<T> asList(T... a) {
		if(a == null)
			return null;
		return Arrays.asList(a);
	}

	/**
	 * @param list Collection<String>
	 * @return String[]
	 */
	public static String[] toArray(final Collection<String> list) {
		if (list == null)
			return new String[]{};
		return list.toArray(new String[0]);
	}
}
