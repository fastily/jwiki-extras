package fastily.jwikix.tplate;

import java.util.ArrayList;

import org.json.JSONArray;

import fastily.jwiki.util.FL;

/**
 * Static JSON-parsing methods
 * 
 * @author Fastily
 *
 */
public class JSONP
{
	/**
	 * Constructors disallowed
	 */
	private JSONP()
	{

	}

	/**
	 * Collects the Strings in a JSONArray as a List.
	 * 
	 * @param ja The JSONArray to collect Strings from
	 * @return An ArrayList with all Strings in <code>ja</code>
	 */
	public static ArrayList<String> strsFromJA(JSONArray ja)
	{
		return FL.toAL(FL.streamFrom(ja).filter(o -> o instanceof String).map(o -> (String) o));
	}
}