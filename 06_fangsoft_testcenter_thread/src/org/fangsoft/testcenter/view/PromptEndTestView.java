package org.fangsoft.testcenter.view;


import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.util.Console;

public class PromptEndTestView  extends ConsoleView implements TestCenterView{

	private String read;
	private TestResult tr;
	private int numQuestion;
	
	public PromptEndTestView(int numQuestion) {
		this.numQuestion = numQuestion;
	}
	
	@Override
	protected void displayView() {
		read = Console.prompt("结束考试还是修改问题的答案", "结束考试：输入:end","修改试题答案：输入：试题序号(1-"+numQuestion+")");
	}

	public TestResult getTr() {
		return tr;
	}

	public void setTr(TestResult tr) {
		this.tr = tr;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public int getNumQuestion() {
		return numQuestion;
	}

	public void setNumQuestion(int numQuestion) {
		this.numQuestion = numQuestion;
	}

}
