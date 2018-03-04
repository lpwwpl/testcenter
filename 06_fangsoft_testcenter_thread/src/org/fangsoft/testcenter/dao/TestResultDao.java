package org.fangsoft.testcenter.dao;

import java.util.List;

import org.fangsoft.testcenter.model.TestResult;

public interface TestResultDao {
	public List<TestResult> findTestResultByCustomer(String userId);
	public void save(TestResult testResult);
}
