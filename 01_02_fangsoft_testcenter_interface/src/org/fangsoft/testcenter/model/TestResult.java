package org.fangsoft.testcenter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestResult implements Serializable{

	private static final int UNKNOW_SCORE = -1;
	private static final int UNKNOW_PASS = -1;
	private static final int PASS_SUCCESS = 1;
	private static final int PASS_FAILURE = 0;
	private int score = UNKNOW_SCORE;
	private int pass = UNKNOW_PASS;
	private List<QuestionResult> questionResults;
	private Date startTime;
	private Date endTime;
	private Customer customer;
	private Test test;
	private long testDeadTime;
	private Status status;

	// private Result pass;

	public static enum Status {
		NEW(0, "new"), TESTING(1, "testing"), SUSPEND(2, "suspend"), FINISH(3,
				"finish");
		private int intVal;
		private String description;

		private Status(int intVal, String desc) {
			this.intVal = intVal;
			this.description = desc;
		}

		public int getIntVal() {
			return this.intVal;
		}

		public String getDescription() {
			return description;
		}
	}

	public static enum Result {
		PASS(1, "pass"), FAILURE(0, "fail"), UNKNOW(-1, "unknow");
		private String value;
		private int intVal;

		private Result(int intVal, String value) {
			this.value = value;
			this.intVal = intVal;
		}

		public String getValue() {
			return this.value;
		}

		public int getIntVal() {
			return this.intVal;
		}
	}

	public TestResult() {
		questionResults = new ArrayList<QuestionResult>();
	}
	
	public TestResult(Customer customer, Test test) {
		this.customer = customer;
		this.test = test;
		questionResults = new ArrayList<QuestionResult>();
	}

	protected int computeScore() {
		if (this.getScore() != UNKNOW_SCORE)
			return this.score;
		if (this.getScore() == UNKNOW_SCORE)
			setScore(0);
		for (QuestionResult qr : this.getQuestionResults()) {
			score += qr.computeAnswer();
		}
		return this.score;
	}

	protected boolean computePass() {
		if (this.pass != UNKNOW_PASS) {
			return this.pass == PASS_SUCCESS ? true : false;
		}
		int rights = 0;
		for (QuestionResult qr : this.questionResults) {
			if (qr.isResult())
				rights++;
		}
		if (100 * rights >= 70 * this.getQuestionResults().size()) {
			this.pass = PASS_SUCCESS;
			return true;
		} else {
			this.pass = PASS_FAILURE;
			return false;
		}
	}

	public boolean commitTest() {
		this.computeScore();
		return this.computePass();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<QuestionResult> getQuestionResults() {
		return questionResults;
	}

	public QuestionResult getQuestionResult(int index) {
		return questionResults.get(index);
	}

	public void setQuestionResults(List<QuestionResult> questionResults) {
		this.questionResults = questionResults;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public long getTestDeadTime() {
		return testDeadTime;
	}

	public void setTestDeadTime(long testDeadTime) {
		this.testDeadTime = testDeadTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
