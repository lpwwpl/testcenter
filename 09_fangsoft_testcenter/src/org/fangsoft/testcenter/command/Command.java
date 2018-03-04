package org.fangsoft.testcenter.command;

import java.awt.Component;

import org.fangsoft.testcenter.controller.ITestCenterController;

public class Command implements ICommand {
	private static final long serialVersionUID = 7165562896441066403L;
	protected transient ITestCenterController controller;

	public Command() {
	}

	public Command(ITestCenterController controller) {
		this.controller = controller;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	public ITestCenterController getController() {
		return controller;
	}

	public void setController(ITestCenterController controller) {
		this.controller = controller;
	}
}
