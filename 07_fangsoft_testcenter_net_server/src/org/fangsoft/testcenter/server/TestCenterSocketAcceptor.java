package org.fangsoft.testcenter.server;

import java.net.Socket;

import org.fangsoft.testcenter.command.Command;
import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.controller.ServerTestCenterController;
import org.fangsoft.testcenter.controller.TestCenterController;
import org.fangsoft.testcenter.net.server.ServerTask;
import org.fangsoft.testcenter.net.server.SocketAcceptor;

public class TestCenterSocketAcceptor extends SocketAcceptor<Command,Command> {

	private ServerTestCenterController controller;
	
	public TestCenterSocketAcceptor(int port) {
		super(port);
	}
	@Override
	protected ServerTask<Command, Command> generateServerTask(Socket socket) {
		// TODO Auto-generated method stub
		return new TestCenterServerTask(socket,getTestCenterController());
	}

	public TestCenterController getTestCenterController() {
		if(this.controller ==null) {
			this.controller = new ServerTestCenterController();
			this.controller.setDaoFactory(Configuration.getDBDaoFactory());
		}
		return this.controller;
	}
	
	public ServerTestCenterController getController() {
		return controller;
	}
	public void setController(ServerTestCenterController controller) {
		this.controller = controller;
	}
}
