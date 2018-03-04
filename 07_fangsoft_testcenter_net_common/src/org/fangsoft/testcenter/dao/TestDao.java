package org.fangsoft.testcenter.dao;

import java.util.List;

import org.fangsoft.testcenter.model.Test;

public interface TestDao {
	public Test findTestByName(String testName);
	public List<Test> findAllTest();
	public void save(Test test);
	public List<String> findAllTestNames();
}
