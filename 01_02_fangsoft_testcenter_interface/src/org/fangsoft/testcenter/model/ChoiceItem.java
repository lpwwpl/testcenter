package org.fangsoft.testcenter.model;

import java.io.Serializable;

public class ChoiceItem implements Serializable{

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

	public String toString() {
		return name + " " + label + " " + correct;
	}
}
