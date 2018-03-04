package org.fangsoft.update;

public interface IUpdateFacade {
	public String getUpdateFile();
	public byte[] getUpdateComponent(Component comp);
}
