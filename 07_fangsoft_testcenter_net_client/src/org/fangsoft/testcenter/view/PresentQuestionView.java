package org.fangsoft.testcenter.view;

import java.util.Date;
import java.util.List;

import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.util.Console;

public class PresentQuestionView extends ConsoleView implements TestCenterView {

	public static final String[] CHOICE_LABEL = { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k" };
	private QuestionResult questionResult;
	private int sequence;
	private String[] labels;

	public PresentQuestionView() {

	}

	public PresentQuestionView(QuestionResult qr, int sequence, String[] labels) {
		this.questionResult = qr;
		this.sequence = sequence;
		this.labels = labels;
	}

	public PresentQuestionView(QuestionResult result, int sequence) {
		this(result, sequence, null);
	}

	public static String prompt(int pos, Question q) {
		Console.output("%1$s.%2$s%n", pos, q.getName());
		List<ChoiceItem> items = q.getChoiceItems();
		for(ChoiceItem item:items) {
			Console.output("%1$s.%2$s%n",item.getLabel(),item.getName());
		}
		Console.output("输入答案：");
		return Console.read();
	}

	@Override
	protected void displayView() {
		// TODO Auto-generated method stub
		Console.output("现在时间是：%1$tT%n",new Date());
		if(this.labels==null)this.labels=CHOICE_LABEL;
		this.questionResult.getQuestion().assignLabel(this.labels);
		String answer=prompt(this.sequence,this.questionResult.getQuestion());
		this.questionResult.setAnswer(answer);
		this.questionResult.computeAnswer();
	}

	public QuestionResult getQuestionResult() {
		return questionResult;
	}

	public void setQuestionResult(QuestionResult questionResult) {
		this.questionResult = questionResult;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}
}
