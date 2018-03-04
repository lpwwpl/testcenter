package org.fangsoft.testcenter.dao.array;

import java.util.ArrayList;
import java.util.List;

import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.data.TestData;
import org.fangsoft.testcenter.model.Test;

public class TestArrayDao implements TestDao {

	private static final TestDao tdao = new TestArrayDao();
	
	public static final TestDao getInstance() {
		return tdao;
	}
	
	@Override
	public List<Test> findAllTest() {
		// TODO Auto-generated method stub
		ArrayList<Test> tests = new ArrayList<Test>(TestData.allTest.length);
		for(String[][] data:TestData.allTest) {
			tests.add(TestData.newTest(data));
		}
		tests.trimToSize();
		return tests;
	}

	@Override
	public List<String> findAllTestNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Test findTestByName(String testName) {
		// TODO Auto-generated method stub
		for(String[][] data:TestData.allTest) {
			if(data[0][0].equals(testName)) {
				return TestData.newTest(data);
			}
		}
		return null;
	}

	@Override
	public void save(Test test) {
		// TODO Auto-generated method stub
		
	}
}
