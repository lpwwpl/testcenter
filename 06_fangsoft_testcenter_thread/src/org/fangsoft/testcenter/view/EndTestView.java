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
		Console.output("考试结束，现在时间是："
				+ Configuration.getDateFormat().format(tr.getEndTime()));
		long duration = 0;
		if(timeout) {
			Console.output("时间已到，考试已由系统提交");
			duration = this.tr.getTest().getTimeLimitMin();
		} else {
			duration = (this.tr.getEndTime().getTime()-this.tr.getStartTime().getTime())/(1000*60);
			if(duration == 0)duration=1;
		}
		Console.output("你花了%1$s分钟考试%n",duration);
		
		
		Console.output("=========考试报告=========");
		Console
				.output("%1$-6s%2$-6s%3$-6s%4$-6s%n", "题号", "你的答案", "正确答案",
						"对错");
		int count = 0;
		for (QuestionResult qr : tr.getQuestionResults()) {
			Console.output("%1$-8s%2$-8s%3$-8s%4$-8s%n", ++count, qr
					.getAnswer(), qr.getQuestion().getAnswer(),
					qr.isResult() ? "right" : "wrong");
		}
		boolean pass = tr.getPass() > 0 ? true : false;
		String displayPass = "";
		if (pass)
			displayPass = "恭喜，你通过了考试";
		else
			displayPass = "很遗憾，你没有通过考试";
		Console.output("你的考试的得分是:" + tr.getScore() + "," + displayPass);
		
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
