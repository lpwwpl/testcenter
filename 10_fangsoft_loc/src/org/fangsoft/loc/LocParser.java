package org.fangsoft.loc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LocParser {

	public static final boolean DEBUG = false;

	public final void debug(Object msg) {

	}

	public final int parseLoC(File javaFile) {
		int loc = 0;
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(javaFile);
			br = new BufferedReader(fr);
			String line = "";
			boolean inBlockComment = false;
			int count = 0;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				debug((++count) + ":" + line);
				if (inBlockComment) {
					int index = line.indexOf(Comment.FINISH_BLOCK_COMMENT);
					if (index == -1)
						continue;
					else {
						inBlockComment = false;
						if (line.endsWith(Comment.FINISH_BLOCK_COMMENT))
							continue;
						line = line.substring(index + 2);
					}
				}
				if (!line.startsWith(Comment.LINE_COMMENT) && line.length() > 0) {
					String noQuote = Comment.noQuoted(line);
					int lineCommentIndex = noQuote
							.indexOf(Comment.LINE_COMMENT);
					if (lineCommentIndex != -1) {
						noQuote = noQuote.substring(0, lineCommentIndex);
					}
					String noBlockComment = Comment.noBlockComment(noQuote);
					if (!noBlockComment.startsWith(Comment.START_BLOCK_COMMENT)) {
					}
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {

		} finally {
		}
		return 0;
	}

	public final int coutingLoc(String line) {
		return 0;
	}

	public final int countSemicolon(String line) {
		return 0;
	}

	public void countLoc(String srcPath, LocData data) {

	}

	public void countLoc(String... srcPath) {

	}

	public static void mian(String args[]) {

	}
}
