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
		Console.output("��ʼ����");
		Console.output("�������ƣ�%1$5s%n" + "���Լ�����%2$5s%n" + "�������⣺%3$5s%n"
				+ "����ʱ�䣺%4$5s����%n", getTest().getName(), this.getTest()
				.getDescription(), this.getTest().getNumQuestion(), this
				.getTest().getTimeLimitMin());
		
		long start = System.currentTimeMillis();
		Console.output("ע������%1$s���Ӵ��⣬����ʱ����" + "%2$tT%n", this.getTest()
				.getTimeLimitMin(), start);
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

}
