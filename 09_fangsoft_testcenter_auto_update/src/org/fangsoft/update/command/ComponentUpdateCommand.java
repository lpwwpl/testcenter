package org.fangsoft.update.command;

public class ComponentUpdateCommand extends Command {

	public void execute() {
		if(this.getUpdateFacade() == null)return;
		this.setByteResponse(this.getUpdateFacade().getUpdateComponent(this.getComponent()));
	}
}
