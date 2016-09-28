package fastily.jwikix.tplate;

import java.util.ArrayList;

import org.json.JSONObject;

import fastily.jwiki.core.Reply;
import fastily.jwiki.util.FL;

/**
 * Represents a segment of parsed wikitext.
 * 
 * @author Fastily
 *
 */
public class ParsedItem
{
	/**
	 * List with parsed comments or miscellaneous page contents
	 */
	public final ArrayList<String> comments, contents;

	/**
	 * Templates contained by this object.
	 */
	public final ArrayList<Template> tplates;

	/**
	 * Creates a ParsedItem with the given Reply.
	 * 
	 * @param r The Reply to use. PRECONDITION: This is a <code>root</code> JSONObject for the ParsedItem.
	 */
	protected ParsedItem(Reply r)
	{
		comments = TUtils.strsFromJA(r, "comment");
		contents = TUtils.strsFromJA(r, "content");
		tplates = FL.toAL(TUtils.getJAOf(r, "template").stream().map(Template::new));
	}

	/**
	 * Creates a ParsedItem with the given JSONObject.
	 * 
	 * @param jo The Reply to use. PRECONDITION: This is a <code>root</code> JSONObject for the ParsedItem.
	 */
	protected ParsedItem(JSONObject jo)
	{
		this(new Reply(jo));
	}

	/**
	 * Creates a ParsedItem with the given JSONObject.
	 * 
	 * @param r The Reply to use.
	 * @return A ParsedItem of the parsed page.
	 */
	public static ParsedItem parse(JSONObject r)
	{
		return new ParsedItem(r.has("root") ? r.getJSONObject("root") : r);
	}

	/**
	 * Generates a wikitext representation of this Object.
	 */
	public String toString()
	{
		String x = "";
		for (Template t : tplates)
			x += t.toString() + "\n";

		x += String.join(" ", comments.toArray(new String[0]));
		x += String.join(" ", contents.toArray(new String[0]));

		return x;
	}
}