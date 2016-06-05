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
		return new StringBuffer(s).insert(index, insert).toString();
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
	 * Checks the version String of a program with the version String of the server. PRECONDITION: <code>local</code> and
	 * <code>ext</code> ONLY contain numbers and '.' characters.
	 * 
	 * @param local The version String of the program. (e.g. 0.2.1)
	 * @param minVersion The version String of the server. (e.g. 1.3.2)
	 * @return True if the version of the local String is greater than or equal to the server's version String.
	 */
	public static boolean versionCheck(String local, String minVersion)
	{
		try
		{
			return Integer.parseInt(local.replace(".", "")) >= Integer.parseInt(minVersion.replace(".", ""));
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		return false;
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