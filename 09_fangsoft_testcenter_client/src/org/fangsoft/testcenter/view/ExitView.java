package org.fangsoft.testcenter.view;

import org.fangsoft.util.Console;

public class ExitView  extends ConsoleView implements TestCenterView{

	private String mesg;
	
	public ExitView(String mesg) {
		this.mesg = mesg;
	}
	
	@Override
	protected void displayView() {
		// TODO Auto-generated method stub
		Console.output(mesg);
	}

}
