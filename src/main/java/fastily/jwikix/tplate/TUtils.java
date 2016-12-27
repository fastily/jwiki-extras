package fastily.jwikix.tplate;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utilities used by the <code>tplate</code> package.
 * 
 * @author Fastily
 *
 */
public final class TUtils
{
	/**
	 * Constructors disallowed
	 */
	private TUtils()
	{

	}

	/**
	 * Creates a single Object ArrayList.
	 * 
	 * @param t The Object to create the ArrayList with.
	 * @return The single Object ArrayList
	 */
	private static <T> ArrayList<T> sgAL(T t)
	{
		return new ArrayList<>(Collections.singletonList(t));
	}

	/**
	 * Gets JSONObject for a key which could either be a JSONArray of JSONObject or just a single JSONObject.
	 * 
	 * @param r The Reply to use.
	 * @param key The key to look at.
	 * @return An ArrayList with any JSONObject found, or the empty ArrayList if nothing matching was found.
	 */
	protected static ArrayList<Reply> getJAOf(Reply r, String key)
	{
		if (r.has(key))
		{
			Object x = r.get(key);

			if (x instanceof JSONObject)
				return sgAL(new Reply((JSONObject) x));
			else if (x instanceof JSONArray)
				return r.getJAofJO(key);
		}

		return new ArrayList<>();
	}

	/**
	 * Gets Strings for a key which could either be a single String or a JSONArray of Strings.
	 * 
	 * @param r The Reply to use.
	 * @param key The key to look at.
	 * @return An ArrayList with any Strings found, or the empty ArrayList if nothing matching was found.
	 */
	protected static ArrayList<String> strsFromJA(Reply r, String key)
	{
		if (r.has(key))
		{
			Object x = r.get(key);

			if (x instanceof String)
				return sgAL((String) x);
			else if (x instanceof JSONArray)
				return JSONP.strsFromJA((JSONArray) x);
		}

		return new ArrayList<>();
	}
}