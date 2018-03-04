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
		Console.output("fangsoft���������ṩ�����п��ԣ�");
		int count = 0;
		for (String name : this.testNames) {
			Console.output((++count) + "." + name + "����" + ",���룺" + name);
		}
		while (true) {
			String inputTestName = Console.prompt("��ѡ��������:");
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
