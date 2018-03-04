package org.fangsoft.testcenter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
	
	private static final long serialVersionUID = -3943904302773801208L;
	private int id;
	private int testId;
	private String name;
	private String answer;
	private int score;
	private List<ChoiceItem> choiceItems;
	private int count;

	public Question(int size) {
		choiceItems = new ArrayList<ChoiceItem>();
	}

	public Question() {
		choiceItems = new ArrayList<ChoiceItem>();
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addChoiceItem(ChoiceItem item) {
		choiceItems.add(item);
	}

	public ChoiceItem getChoiceItem(int index) {
		return choiceItems.get(index);
	}

	public void assignLabel(String[] labels) {
		int count = 0;
		StringBuffer rightAnswer = new StringBuffer();
		if (this.choiceItems != null) {
			for (ChoiceItem item : this.getChoiceItems()) {
				item.setLabel(labels[count++]);
				if (item.isCorrect()) {
					rightAnswer.append(labels[count - 1]);
				}
			}
		}
		this.setAnswer(rightAnswer.toString());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<ChoiceItem> getChoiceItems() {
		return choiceItems;
	}

	public void setChoiceItems(List<ChoiceItem> choiceItems) {
		this.choiceItems = choiceItems;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String toString() {
		String str = name + " " + answer + " " + score + " " + count + " ";
		for (ChoiceItem item : choiceItems) {
			str += item.toString();
			str += "_";
		}
		return str;
	}
}
