package org.fangsoft.loc;

public class Comment {
	public static final String LINE_COMMENT = "//";
	public static final String START_BLOCK_COMMENT = "/*";
	public static final String FINISH_BLOCK_COMMENT = "*/";

	public static String noQuoted(String input) {
		int start = 0;
		int end = 0;
		while (end != -1) {
			start = input.indexOf("\"", end + 1);
			if (start != -1) {
				end = input.indexOf("\"", start + 1);
				while (end != -1) {
					if (input.charAt(end - 1) == '\\') {
						end = input.indexOf("\"", end + 1);
					} else {
						break;
					}
				}
				if (end < input.length() - 1) {
					input = input.substring(0, start)
							+ input.substring(end + 1);
				} else {
					input = input.substring(0, start);
				}
			} else {
				return input;
			}
		}
		return input;
	}
	
	public static String noBlockComment(String input) {
		int start = 0;
		int end = 0;
		while(start!=-1&&end!=-1) {
			start = input.indexOf(START_BLOCK_COMMENT);
			end = input.indexOf(FINISH_BLOCK_COMMENT,start+1);
			if(start!=-1&&end!=-1) {
				if(end<input.length()-2) {
					input = input.substring(0,start) + input.substring(end+2);
				} else {
					input = input.substring(0,start);
				}
			}
		}
		return input;
	}
}
