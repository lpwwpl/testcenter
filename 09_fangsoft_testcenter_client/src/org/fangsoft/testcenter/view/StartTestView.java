package org.fangsoft.testcenter.view;

import org.fangsoft.testcenter.model.Test;
import org.fangsoft.util.Console;

public class StartTestView extends ConsoleView implements TestCenterView {

	private Test test;

	public StartTestView(Test test) {
		this.test = test;
	}

	@Override
	protected void displayView() {
		// TODO Auto-generated method stub
		Console.output("开始考试");
		Console.output("考试名称：%1$5s%n" + "考试简述：%2$5s%n" + "考试问题：%3$5s%n"
				+ "考试时间：%4$5s分钟%n", getTest().getName(), this.getTest()
				.getDescription(), this.getTest().getNumQuestion(), this
				.getTest().getTimeLimitMin());
		
		long start = System.currentTimeMillis();
		Console.output("注意您有%1$s分钟答题，现在时间是" + "%2$tT%n", this.getTest()
				.getTimeLimitMin(), start);
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

}
