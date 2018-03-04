package org.fangsoft.testcenter.dao;

import java.util.List;

import junit.framework.TestCase;

import org.fangsoft.testcenter.model.TestResult;

public class TestResultDaoTest extends TestCase {
	protected void testAll(TestResultDao tdao) {

		String userId = "fangsoft.org@gmail.com";
		List<TestResult> results = tdao.findTestResultByCustomer(userId);
		this.assertEquals(results.size(), 2);
	}

	private String toString(TestResult tr) {
		return tr.toString();
	}
}
