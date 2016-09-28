package fastily.jwikix.tplate;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import fastily.jwiki.core.Reply;

/**
 * Represents a Template parsed from MediaWiki's <code>parse</code> module.
 * 
 * @author Fastily
 *
 */
public class Template
{
	/**
	 * The title of the Template
	 */
	public final String title;

	/**
	 * This Template's parameters.
	 */
	public final HashMap<String, TValue> params = new HashMap<>();

	/**
	 * Creates a new Template Object with the given Reply.
	 * 
	 * @param r The Reply to create the Template with. PRECONDITION: This is a JSONObject that contains a Tempalate
	 *           Object.
	 */
	protected Template(Reply r)
	{
		title = r.getString("title");
		TUtils.getJAOf(r, "part").stream().forEach(p -> params.put(resolveName(p), new TValue(p.get("value"))));
	}

	/**
	 * Resolves the name of a template parameter from raw JSON.
	 * 
	 * @param jo The JSONObject with template parameter data. PRECONDITION: this must contain a 'name' field.
	 * @return A String with the name of the template parameter, or null if nothing matching was found.
	 */
	private static String resolveName(JSONObject jo)
	{
		Object o = jo.get("name");

		if (o instanceof String)
			return (String) o;
		else if (o instanceof Integer)
			return "" + (Integer) o;
		else if (o instanceof JSONObject)
			return "" + ((JSONObject) o).getInt("index");

		return null;
	}

	/**
	 * Generates a wikitext representation of this Template.
	 */
	public String toString()
	{
		String x = "";
		for (Map.Entry<String, TValue> e : params.entrySet())
			x += String.format("|%s=%s", e.getKey(), e.getValue());

		return String.format("{{%s%s}}", title, x);
	}

	/**
	 * Represents a Template parameter value.
	 * 
	 * @author Fastily
	 *
	 */
	public static class TValue
	{
		/**
		 * The variable storing a String value if this TValue contains a String.
		 */
		private String sVal;

		/**
		 * The variable storing a ParsedItem value if this TValue contains a ParsedItem.
		 */
		private ParsedItem tVal;

		/**
		 * Constructor, creates a TValue
		 * 
		 * @param o A String or ParsedItem to be stored in the TValue.
		 */
		protected TValue(Object o)
		{
			setValue(o);
		}

		/**
		 * Sets the value of this TValue with the given Object.
		 * 
		 * @param o A String or ParsedItem to set this TValue to. PRECONDITION: This must be either a String or
		 *           ParsedItem.
		 */
		public void setValue(Object o)
		{
			sVal = null;
			tVal = null;

			if (o instanceof String)
				sVal = (String) o;
			else if (o instanceof Integer)
				sVal = "" + (Integer) o;
			else if (o instanceof JSONObject)
				tVal = new ParsedItem((JSONObject) o);
			else
				throw new IllegalArgumentException("What is " + o);
		}

		/**
		 * Creates a wikitext representation of this template value.
		 */
		public String toString()
		{
			return sVal == null ? tVal.toString() : sVal;
		}
	}
}