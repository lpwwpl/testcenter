package org.fangsoft.util;

import java.io.File;
import java.io.FilenameFilter;

public class SuffixFilter implements FilenameFilter {
	private String suffix;

	public SuffixFilter(String extensionName) {
		super();
		this.suffix = extensionName;
	}

	public boolean accept(File dir, String name) {
		if (name.endsWith(this.suffix))
			return true;
		return false;
	}
}
