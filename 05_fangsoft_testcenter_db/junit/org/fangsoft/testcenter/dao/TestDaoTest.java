package org.fangsoft.testcenter.dao;

import java.io.File;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.fangsoft.testcenter.dao.util.DaoIOConfig;
import org.fangsoft.testcenter.data.TestData;
import org.fangsoft.testcenter.model.Test;

public class TestDaoTest extends TestCase{

	protected void testAll(TestDao tdao) {
		new File(DaoIOConfig.getTestFilePath()).delete();
		for(String[][] data:TestData.allTest) {
			Test test = TestData.newTest(data);
			tdao.save(test);
			Test testFind = tdao.findTestByName(test.getName());
			Assert.assertEquals(toString(test), toString(testFind));
		}
		List<Test> allTest = tdao.findAllTest();
		Assert.assertEquals(TestData.allTest.length,allTest.size());
		for(Test tf:allTest) {
			Test t = tdao.findTestByName(tf.getName());
			Assert.assertEquals(toString(t), toString(tf));
		}
		new File(DaoIOConfig.getTestFilePath()).delete();
	}

	private String toString(Test test) {
		StringBuffer buf = new StringBuffer();
		buf.append(test.getName());
		buf.append(test.getNumQuestion());
		buf.append(test.getScore());
		buf.append(test.getTimeLimitMin());
		buf.append(test.getDescription());
		return buf.toString();
	}
}
