package org.fangsoft.testcenter.command;

import org.fangsoft.testcenter.model.TestResult;

public class CommitTestCommand extends Command {

	private static final long serialVersionUID = 5729091558778542099L;

	private TestResult tr;

	public CommitTestCommand() {

	}

	public CommitTestCommand(TestResult tr) {
		this.tr = tr;
	}

	public void execute() {
		if (this.getController() == null)
			return;
		this.getController().commitTest(tr);
	}

	public TestResult getTr() {
		return tr;
	}

	public void setTr(TestResult tr) {
		this.tr = tr;
	}

}
