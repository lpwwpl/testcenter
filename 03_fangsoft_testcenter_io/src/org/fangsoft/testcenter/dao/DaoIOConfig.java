package org.fangsoft.testcenter.dao;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.util.SuffixFilter;

public abstract class DaoIOConfig {
	public static final String SUFFIX = ".fan";
	public static final String TESTCENTER_PATH = "fangsoft/";
	public static final String TESTRESULT_PATH = "testresult/";
	public static final String TEST_PATH = "test/";
	public static final String CUSTOMER_PATH = "customer/";
	public static final SuffixFilter FILTER = new SuffixFilter(SUFFIX);

	public static String getCustomerFilePath() {
		return getBasePath() + CUSTOMER_PATH;
	}

	public static String getTestResultFilePath() {
		return getBasePath() + TESTRESULT_PATH;
	}

	public static String getBasePath() {
		return TESTCENTER_PATH;
	}

	public static String getTestFilePath() {
		return getBasePath() + TEST_PATH;
	}

	public static String getFileName(Test test) {
		return test.getName() + SUFFIX;
	}

	public static String getQuestionResultFileName(QuestionResult qr) {
		return qr.getClass().getSimpleName() + "-" + qr.hashCode() + SUFFIX;
	}

	public static String getTestResultFileName(TestResult tr) {
		return tr.getTest().getName() + "-"
		+ Configuration.getDateFormat().format(tr.getEndTime())
		+ tr.getTest().hashCode()+SUFFIX;
	}
	
	public static String getQuestionFilePath(Test test) {
		return getTestFilePath() + test.getName() + "/";
	}

	public static String getFileName(Question question) {
		return question.getClass().getSimpleName() + "-"
				+ question.getName().hashCode() + SUFFIX;
	}

	public static String getChoiceItemFilePath(Test test, Question question) {
		return getQuestionFilePath(test) + "/"
				+ question.getClass().getSimpleName() + "-"
				+ question.getName().hashCode() + "/";
	}

	public static String getFileName(ChoiceItem item) {
		return item.getClass().getSimpleName() + "-"
				+ item.getName().hashCode() + SUFFIX;
	}
}
