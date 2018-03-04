package org.fangsoft.testcenter.model;

import java.io.Serializable;

public class ChoiceItem implements Serializable {

	private static final long serialVersionUID = 8118181734483320869L;
	private int id;
	private int questionId;
	private String name;
	private boolean correct;
	private String label;

	public ChoiceItem() {
		correct = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String toString() {
		return name + " " + label + " " + correct;
	}
}
