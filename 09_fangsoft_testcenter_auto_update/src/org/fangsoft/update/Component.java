package org.fangsoft.update;

import java.io.Serializable;

public class Component implements Serializable {
	private static final long serialVersionUID = -3688345410605527341L;
	
	private String name;
	private String version;
	private String path;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
