package org.fangsoft.testcenter.view;


import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.util.Console;

public class PromptEndTestView  extends ConsoleView implements TestCenterView{

	private TestResult tr;
	
	public PromptEndTestView(TestResult tr) {
		this.tr = tr;
	}
	
	@Override
	protected void displayView() {
		Console.output("=========���Ա���=========");
		Console
				.output("%1$-6s%2$-6s%3$-6s%4$-6s%n", "���", "��Ĵ�", "��ȷ��",
						"�Դ�");
		int count = 0;
		for (QuestionResult qr : tr.getQuestionResults()) {
			Console.output("%1$-8s%2$-8s%3$-8s%4$-8s%n", ++count, qr
					.getAnswer(), qr.getQuestion().getAnswer(),
					qr.isResult() ? "right" : "wrong");
		}
		tr.commitTest();
		boolean pass = tr.getPass() > 0 ? true : false;
		String displayPass = "";
		if (pass)
			displayPass = "��ϲ����ͨ���˿���";
		else
			displayPass = "���ź�����û��ͨ������";
		Console.output("��Ŀ��Եĵ÷���:" + tr.getScore() + "," + displayPass);
	}

	public TestResult getTr() {
		return tr;
	}

	public void setTr(TestResult tr) {
		this.tr = tr;
	}

}
