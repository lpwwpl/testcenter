package org.fangsoft.update.server;

import java.net.Socket;

import org.fangsoft.testcenter.net.server.ServerTask;
import org.fangsoft.update.IUpdateFacade;
import org.fangsoft.update.command.Command;

public class UpdateServerTask extends ServerTask<Command, Command>{

	private IUpdateFacade updateFacade;
	
	public UpdateServerTask(Socket socket) {
		super(socket);
		// TODO Auto-generated constructor stub
	}

	public UpdateServerTask(Socket socket,IUpdateFacade updateFacade) {
		super(socket);
		this.updateFacade = updateFacade;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Command handle(Command request) {
		// TODO Auto-generated method stub
		request.setUpdateFacade(updateFacade);
		request.execute();
		request.setUpdateFacade(null);
		return request;
	}

	public IUpdateFacade getUpdateFacade() {
		return updateFacade;
	}

	public void setUpdateFacade(IUpdateFacade updateFacade) {
		this.updateFacade = updateFacade;
	}

}
