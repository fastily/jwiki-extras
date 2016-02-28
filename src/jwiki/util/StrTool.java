package jwiki.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Miscellaneous Wiki-related String processing/parsing methods.
 * 
 * @author Fastily
 *
 */
public final class StrTool
{
	/**
	 * A capturing group that matches any reserved regex operator character in the Java Pattern API.
	 */
	private static final String rrc = String.format("([%s])", Pattern.quote("()[]{}<>\\^-=$!|?*+."));

	/**
	 * Constructors disallowed
	 */
	private StrTool()
	{

	}

	/**
	 * Determines if two String Lists share elements. WARNING: This is an expensive operation for big datasets
	 * 
	 * @param a List 1. Ideally set this to the shorter list
	 * @param b List 2. Ideally set this to the longer list
	 * @return True if the Lists intersect.
	 */
	public static boolean arraysIntersect(List<String> a, List<String> b)
	{
		for (String s : a)
			if (b.contains(s))
				return true;
		return false;
	}

	/**
	 * Makes a regex which matches a title on a page. Converts regex operators to their escaped counterparts.
	 * 
	 * @param title The title to convert into a regex.
	 * @return The regex.
	 */
	public static String makePageTitleRegex(String title)
	{
		return String.format("(?si)(%s)", escapeRegexChars(title).replaceAll("( |_)", "( |_)"));
	}

	/**
	 * Escapes reserved regex characters of the Java Pattern API in a String.
	 * 
	 * @param s The String to escape regex chars from
	 * @return A copy of <code>s</code> with reserved regex chars escaped.
	 */
	public static String escapeRegexChars(String s)
	{
		return s.replaceAll(rrc, "\\\\" + "$1");
	}

	/**
	 * Gets the first substring of a String matching a regex.
	 * 
	 * @param p The regex to match
	 * @param s The String to find the regex-matching substring in
	 * @return The substring, or the empty string if no matches were found.
	 */
	public static String substringFromRegex(Pattern p, String s)
	{
		Matcher m = p.matcher(s);
		return m.find() ? m.group() : "";
	}

	/**
	 * Inserts a String into a String. PRECONDITION: <code>index</code> is a valid index for <code>s</code>
	 * 
	 * @param s The string to insert into
	 * @param insert The String to be inserted
	 * @param index The index to insert <code>insert</code> at.
	 * @return The modified String.
	 */
	public static String insertAt(String s, String insert, int index)
	{
		return s.substring(0, index) + insert + s.substring(index);
	}
}