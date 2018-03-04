package org.fangsoft.testcenter.dao;

import java.io.File;
import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.fangsoft.testcenter.data.TestData;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class QuestionDaoTest extends TestCase {
	protected void testAll(QuestionDao tdao) {

		ArrayList<Test> tests = new ArrayList<Test>(TestData.allTest.length);
		for (String[][] data : TestData.allTest) {
			Test test = TestData.newTest(data);
			tests.add(test);
			TestData.loadQuestion(test, data);
		}
		tests.trimToSize();
		for (Test test : tests) {
			for (Question q : test.getQuestions()) {
				tdao.addQuestionToTest(test, q);
			}
		}
		
		for (Test test : tests) {
			tdao.loadQuestion(test);
			Assert.assertEquals(3, test.getQuestions().size());
		}
	}

	private String toString(Question question) {
		return question.toString();
	}
}
