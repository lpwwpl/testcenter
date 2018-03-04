package org.fangsoft.update.server;

import java.util.Map;
import java.util.Set;

import org.fangsoft.update.Component;
import org.fangsoft.update.Component2XML;
import org.fangsoft.update.IOUtil;
import org.fangsoft.update.IUpdateFacade;
import org.fangsoft.update.config.Configuration;

public class UpdateFacadeImpl implements IUpdateFacade {
	private static final Component2XML c2x = new Component2XML();
	private String installPath = "";
	private String updateFileName;
	private Component comp;

	public UpdateFacadeImpl() {
		updateFileName = Configuration.getUpdateFileName();
		installPath = Configuration.getInstallPath();
	}

	@Override
	public byte[] getUpdateComponent(Component comp) {
		// TODO Auto-generated method stub
		Map<String, Component> serverComponents = c2x.xml2CompMap(updateFileName);
		Set<String> keys = serverComponents.keySet();
		for (String key : keys) {

			if (key.equals(comp.getName())) {
				Component sc = serverComponents.get(key);
				return IOUtil.readFromBINFile(this.getInstallPath()
						+ sc.getPath());
			}
		}
		return null;
	}

	@Override
	public String getUpdateFile() {
		// TODO Auto-generated method stub
		return IOUtil.readFromTXTFile(this.getInstallPath()
				+ this.getUpdateFileName());
	}

	public Component getComp() {
		return comp;
	}

	public void setComp(Component comp) {
		this.comp = comp;
	}

	public String getInstallPath() {
		return installPath;
	}

	public void setInstallPath(String installPath) {
		this.installPath = installPath;
	}

	public static Component2XML getC2x() {
		return c2x;
	}

	public String getUpdateFileName() {
		return updateFileName;
	}

	public void setUpdateFileName(String updateFileName) {
		this.updateFileName = updateFileName;
	}

}
