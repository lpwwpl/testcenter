package org.fangsoft.testcenter.view;

import java.util.Date;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.util.Console;

public class EndTestView extends ConsoleView implements TestCenterView {

	private TestResult tr;
	private boolean timeout = false;

	public EndTestView(TestResult tr, boolean timeout) {
		this.tr = tr;
		this.timeout = timeout;
	}

	@Override
	protected void displayView() {
		// TODO Auto-generated method stub
		Console.output("���Խ���������ʱ���ǣ�"
				+ Configuration.getDateFormat().format(tr.getEndTime()));
		long duration = 0;
		if(timeout) {
			Console.output("ʱ���ѵ�����������ϵͳ�ύ");
			duration = this.tr.getTest().getTimeLimitMin();
		} else {
			duration = (this.tr.getEndTime().getTime()-this.tr.getStartTime().getTime())/(1000*60);
			if(duration == 0)duration=1;
		}
		Console.output("�㻨��%1$s���ӿ���%n",duration);
		
		
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

	public boolean isTimeout() {
		return timeout;
	}

	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

}
