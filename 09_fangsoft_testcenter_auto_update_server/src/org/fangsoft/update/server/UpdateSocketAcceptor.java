package org.fangsoft.update.server;

import java.net.Socket;

import org.fangsoft.testcenter.net.server.ServerTask;
import org.fangsoft.testcenter.net.server.SocketAcceptor;
import org.fangsoft.update.IUpdateFacade;
import org.fangsoft.update.command.Command;

public class UpdateSocketAcceptor extends SocketAcceptor<Command,Command>{

	public UpdateSocketAcceptor(int port) {
		super(port);
	}

	private IUpdateFacade updateFacade;
	
	@Override
	protected ServerTask<Command, Command> generateServerTask(Socket socket) {
		// TODO Auto-generated method stub
		return new UpdateServerTask(socket,getUpdateFacade());
	}
	
	public IUpdateFacade getUpdateFacade() {
		if(this.updateFacade ==null) {
			this.updateFacade = new UpdateFacadeImpl();
		}
		return this.updateFacade;
	}
}
