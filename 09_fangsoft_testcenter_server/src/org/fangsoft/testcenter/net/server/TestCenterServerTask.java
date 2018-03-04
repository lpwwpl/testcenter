package org.fangsoft.testcenter.net.server;

import java.net.Socket;

import org.fangsoft.testcenter.command.Command;
import org.fangsoft.testcenter.controller.TestCenterController;

public class TestCenterServerTask extends ServerTask<Command, Command> {
	private TestCenterController controller;

	public TestCenterServerTask(Socket socket, TestCenterController controller) {
		super(socket);
		this.controller = controller;
	}

	@Override
	protected Command handle(Command request) {
		request.setController(this.controller);
		request.execute();
		request.setController(null);
		return request;
	}

	public TestCenterController getController() {
		return controller;
	}

	public void setController(TestCenterController controller) {
		this.controller = controller;
	}
}
