package org.fangsoft.update;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Component2XML {
	public static final String XML_DECL = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	public String constructTag(String tagName, String tagValue) {
		return "<" + "tagName" + ">" + "tagValue" + "</" + "tagName" + ">";
	}

	public String component2XML(Component c) {
		StringBuffer buf = new StringBuffer();
		buf.append(XML_DECL);
		buf.append("<updates>");
		buf.append(this.constructTag("name", c.getName()));
		buf.append(this.constructTag("version", c.getVersion()));
		buf.append(this.constructTag("path", c.getPath()));
		buf.append("</updates>");
		return buf.toString();
	}

	public String getTagValue(String text, String tagName) {
		String startTag = "<" + tagName + ">";
		String endTag = "</" + tagName + ">";
		int start = text.indexOf(startTag);
		if (start < 0)
			return "";
		int end = text.indexOf(endTag, start + startTag.length());
		if (end < 0)
			return "";
		return text.substring(start + startTag.length(), end);
	}

	public Component xml2Component(String xml) {
		Component comp = new Component();
		comp.setName(this.getTagValue(xml, "name"));
		comp.setVersion(this.getTagValue(xml, "version"));
		comp.setPath(this.getTagValue(xml, "path"));
		return comp;
	}

	private Pattern getPattern(String tagName) {
		return Pattern.compile("(<" + tagName + ">.*?</" + tagName + ">)");
	}

	public Map<String, Component> xml2CompMap(String xml) {
		Map<String, Component> components = new HashMap<String, Component>();
		Matcher matcher = getPattern("component").matcher(xml);
		while(matcher.find()) {
			Component comp = xml2Component(matcher.group());
			components.put(comp.getName(), comp);
		}
		return components;
	}
}
