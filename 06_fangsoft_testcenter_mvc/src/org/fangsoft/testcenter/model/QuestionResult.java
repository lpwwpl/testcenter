package org.fangsoft.testcenter.model;

import java.io.Serializable;

public class QuestionResult implements Serializable {
	private int id;
	private int questionId;
	private int testResultId;
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_SCORE = -1;
	private Question question;
	private int score;
	private String answer;
	private boolean result;

	public QuestionResult() {
		result = false;
		score = 0;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getTestResultId() {
		return testResultId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTestResultId(int testResultId) {
		this.testResultId = testResultId;
	}

	public int computeAnswer() {

		if (this.getQuestion() != null && this.answer != null
				&& this.answer.length() != 0
				&& this.getQuestion().getAnswer().equals(this.answer)) {
			this.result = true;
			this.score = this.getQuestion().getScore();
			if (this.getQuestion().getScore() <= 0) {
				this.score = 1;
			}
		} else {
			this.result = false;
			this.score = 0;
		}
		return score;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}
