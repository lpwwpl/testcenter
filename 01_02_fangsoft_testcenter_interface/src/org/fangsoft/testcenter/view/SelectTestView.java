package org.fangsoft.testcenter.view;

import java.util.ArrayList;
import java.util.List;

import org.fangsoft.util.Console;

public class SelectTestView extends ConsoleView implements TestCenterView {
	private String testName;
	private List<String> testNames;
	public static final String sql_findAllTestNames = "select TT_NAME from TEST";

	public SelectTestView(List<String> testNames) {
		this.testNames = testNames;
	}
	public SelectTestView() {
		this.testNames = new ArrayList<String>();
	}
	@Override
	protected void displayView() {
		// TODO Auto-generated method stub
		Console.output("fangsoft考试中心提供的所有考试：");
		int count = 0;
		for (String name : this.testNames) {
			Console.output((++count) + "." + name + "考试" + ",输入：" + name);
		}
		while (true) {
			String inputTestName = Console.prompt("请选择考试名称:");
			for (String name : testNames) {
				if (name.equals(inputTestName)) {
					this.setTestName(name);
					return;
				}
			}
		}
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}
}
