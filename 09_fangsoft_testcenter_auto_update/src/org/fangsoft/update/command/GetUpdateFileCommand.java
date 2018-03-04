package org.fangsoft.update.command;

public class GetUpdateFileCommand extends Command {

	public void execute() {
		if(this.getUpdateFacade() == null)return;
		this.setTxtResponse(this.getUpdateFacade().getUpdateFile());
	}
}
