package org.fangsoft.testcenter.view;

import java.util.Date;

import org.fangsoft.testcenter.config.Configuration;
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
		tr.setEndTime(new Date());
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
