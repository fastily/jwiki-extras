package jwikix.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
	 * Random number generator.
	 * 
	 * @see #permuteFileName(String)
	 */
	private static final Random rand = new Random();

	/**
	 * Constructors disallowed
	 */
	private StrTool()
	{

	}

	/**
	 * Determines if two String Lists share elements. WARNING: This is potentially an expensive operation for big
	 * datasets
	 * 
	 * @param a List 1.
	 * @param b List 2.
	 * @return True if the Lists intersect.
	 */
	public static boolean arraysIntersect(List<String> a, List<String> b)
	{
		List<String> l1;
		HashSet<String> l2;

		if (a.size() > b.size())
		{
			l2 = new HashSet<>(a);
			l1 = b;
		}
		else
		{
			l2 = new HashSet<>(b);
			l1 = a;
		}

		return l1.parallelStream().anyMatch(l2::contains);
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
	 * @param index The index to insert <code>insert</code> at. The original character at this index will be shifted down
	 *           one slot to make room for <code>insert</code>
	 * @return The modified String.
	 */
	public static String insertAt(String s, String insert, int index)
	{
		return s.substring(0, index) + insert + s.substring(index);
	}

	/**
	 * Permutes a filename by adding a random number to the end before the file extension. PRECONDITION: <code>fn</code>
	 * is a valid filename with an extension, of the format (e.g. blahblah.jpg)
	 * 
	 * @param fn The base filename to permute
	 * @return The permuted filename
	 */
	public static String permuteFileName(String fn)
	{
		return insertAt(fn, " " + rand.nextInt(), fn.lastIndexOf('.'));
	}

	/**
	 * Determines if a List contains a String with at least one of the specified prefixes.
	 * 
	 * @param l The List of Strings to check for a String with a given prefix
	 * @param prefixes The prefixes to look for in the list
	 * @return True if the List contains a String that starts with one of the specified prefixes.
	 */
	public static boolean hasStrWithPrefix(ArrayList<String> l, ArrayList<String> prefixes)
	{
		return l.stream().anyMatch(s -> prefixes.stream().anyMatch(s::startsWith));
	}
}