package org.fangsoft.testcenter.view;

import org.fangsoft.util.Console;

public class IsTakeTestView extends ConsoleView {

	private boolean isTakeTest;

	public boolean isTakeTest() {
		return isTakeTest;
	}

	@Override
	protected void displayView() {
		// TODO Auto-generated method stub
		this.isTakeTest = Console
				.promptYesNo("确认参加考试吗？", "y", "是：y","否，退出：n");
	}

}
