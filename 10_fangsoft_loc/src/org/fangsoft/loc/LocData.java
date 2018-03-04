package org.fangsoft.loc;

import java.util.Map;
import java.util.TreeMap;

public class LocData {
	private String basePath;
	private Map<String,Integer> locMap = new TreeMap<String,Integer>();
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public Map<String, Integer> getLocMap() {
		return locMap;
	}
	public void setLocMap(Map<String, Integer> locMap) {
		this.locMap = locMap;
	}
	
}
