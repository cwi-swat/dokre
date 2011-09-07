package utils;

import java.util.Collection;
import java.util.Iterator;

public class StringUtils {
	
	/***
	 * Joins a collection of objects using a delimiter
	 * @param <T>
	 * @param objs
	 * @param delimiter
	 * @return
	 */
	public static <T> String join(final Collection<T> objs, final String delimiter) {
	    if (objs == null || objs.isEmpty())
	        return "";
	    Iterator<T> iter = objs.iterator();
	    // remove the following two lines, if you expect the Collection will behave well
	    if (!iter.hasNext())
	        return "";
	    StringBuffer buffer = new StringBuffer(String.valueOf(iter.next()));
	    while (iter.hasNext())
	        buffer.append(delimiter).append(String.valueOf(iter.next()));
	    return buffer.toString();
	}
	
}