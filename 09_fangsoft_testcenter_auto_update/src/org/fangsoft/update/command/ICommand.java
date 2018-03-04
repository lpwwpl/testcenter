package org.fangsoft.update.command;

import java.io.Serializable;

public interface ICommand extends Serializable{
	public void execute();
}
