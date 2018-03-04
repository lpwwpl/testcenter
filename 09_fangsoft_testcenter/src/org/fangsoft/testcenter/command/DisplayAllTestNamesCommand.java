package org.fangsoft.testcenter.command;

import java.util.List;

public class DisplayAllTestNamesCommand extends Command {

	private static final long serialVersionUID = 7743597345459414629L;

	private List<String> testNames;

	public DisplayAllTestNamesCommand() {

	}

	public List<String> getTestNames() {
		return testNames;
	}

	public void execute() {
		if (this.getController() == null)
			return;
		this.testNames = this.getController().findAllTestNames();
	}

	public void setTestNames(List<String> testNames) {
		this.testNames = testNames;
	}
}
