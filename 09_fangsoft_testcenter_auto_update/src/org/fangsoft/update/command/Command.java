package org.fangsoft.update.command;

import org.fangsoft.update.Component;
import org.fangsoft.update.IUpdateFacade;

public class Command implements ICommand {

	private Component component;
	private byte[] byteResponse;
	private String txtResponse;
	private transient IUpdateFacade updateFacade;
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public byte[] getByteResponse() {
		return byteResponse;
	}

	public void setByteResponse(byte[] byteResponse) {
		this.byteResponse = byteResponse;
	}

	public String getTxtResponse() {
		return txtResponse;
	}

	public void setTxtResponse(String txtResponse) {
		this.txtResponse = txtResponse;
	}

	public IUpdateFacade getUpdateFacade() {
		return updateFacade;
	}

	public void setUpdateFacade(IUpdateFacade updateFacade) {
		this.updateFacade = updateFacade;
	}
	
}
