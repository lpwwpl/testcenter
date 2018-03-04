package org.fangsoft.testcenter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable{

	private String name;
	private String description;
	private int numQuestion;
	private int timeLimitMin;
	private int score;
	private int count;
	private List<Question> questions;

	public Test() {
		questions = new ArrayList<Question>();
	}

	public void addQuestion(Question q) {
		questions.add(q);
	}

	public Question getQuestion(int index) {
		return questions.get(index);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumQuestion() {
		return numQuestion;
	}

	public void setNumQuestion(int numQuestion) {
		this.numQuestion = numQuestion;
	}

	public int getTimeLimitMin() {
		return timeLimitMin;
	}

	public void setTimeLimitMin(int timeLimitMin) {
		this.timeLimitMin = timeLimitMin;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String toString() {
		String str = name + " " + description + " " + numQuestion + " "
				+ timeLimitMin + " " + score + " " + count + " ";
		for (Question question : questions) {
			str += question.toString();
			str += "_";
		}
		return str;
	}
}
