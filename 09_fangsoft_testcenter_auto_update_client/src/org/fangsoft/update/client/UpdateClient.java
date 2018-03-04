package org.fangsoft.update.client;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.fangsoft.testcenter.net.client.NetClient;
import org.fangsoft.update.Component;
import org.fangsoft.update.Component2XML;
import org.fangsoft.update.IOUtil;
import org.fangsoft.update.command.Command;
import org.fangsoft.update.command.ComponentUpdateCommand;
import org.fangsoft.update.command.GetUpdateFileCommand;
import org.fangsoft.update.config.Configuration;
import org.fangsoft.update.config.SocketConfig;

public class UpdateClient {
	private static final Component2XML c2x = new Component2XML();
	private String updateFileName;
	private String backupPath;
	private String installPath="";
	private NetClient<Command, Command> netClient;

	public UpdateClient() {
	}

	private void output(Object msg) {
		System.out.println(msg);
	}

	private Map<String, Component> getClientUpdate() {
		String updates = IOUtil.readFromTXTFile(this.getInstallPath()
				+ this.getUpdateFileName());
		if (updates.length() != 0)
			return c2x.xml2CompMap(updates);
		return new HashMap<String, Component>();
	}

	private String getServerUpdateFile() {
		Command getUpdateFileReq = new GetUpdateFileCommand();
		return this.netClient.synProcess(getUpdateFileReq).getTxtResponse();
	}

	private boolean hasNewVersion(Component clientComp, Component serverComp) {
		if (serverComp == null)
			return false;
		else if (clientComp == null)
			return true;
		try {
			double clientVersion = Double.parseDouble(clientComp.getVersion());
			double serverVersion = Double.parseDouble(serverComp.getVersion());
			if (serverVersion - clientVersion > 0.0)
				return true;

		} catch (Exception ex) {

		}
		return false;
	}

	private Command getUpdateComponent(Component sc) {
		Command updateReq = new ComponentUpdateCommand();
		updateReq.setComponent(sc);
		Command updateResp = this.netClient.synProcess(updateReq);
		return updateResp;
	}

	private static NetClient<Command, Command> getNetClient() {
		NetClient<Command, Command> netClient = new NetClient<Command, Command>();
		netClient.setServerHost(SocketConfig.getServerHost());
		netClient.setServerPort(SocketConfig.getServerPort());
		netClient.setTimeout(SocketConfig.getSocketTimeout());
		netClient.setProxyServer(SocketConfig.getProxyServer());
		netClient.setProxyPort(SocketConfig.getProxyPort());
		netClient.setProxyType(SocketConfig.getProxyType());

		return netClient;
	}

	public static void main(String[] args) {
		UpdateClient updateClient = new UpdateClient();
		updateClient.setBackupPath(Configuration.getBackupPath());
		updateClient.setUpdateFileName(Configuration.getUpdateFileName());
		updateClient.setNetClient(getNetClient());
		updateClient.update();
	}

	private void backup(String fileName) {
		new File(this.getInstallPath() + this.getBackupPath()).mkdirs();
		new File(this.getInstallPath() + this.getBackupPath(), new File(
				fileName).getParent()).mkdirs();
		String destFile = this.getInstallPath() + this.getBackupPath() + "/"
				+ fileName;
		IOUtil.copyFile(this.getInstallPath() + fileName, destFile);
		new File(this.getInstallPath() + fileName).delete();
	}

	public void update() {
		Map<String, Component> clientUpdate = this.getClientUpdate();
		String serverUpdateFile = this.getServerUpdateFile();
		if ("".equals(serverUpdateFile))
			return;
		Map<String, Component> serverUpdate = c2x.xml2CompMap(serverUpdateFile);
		Set<String> keys = serverUpdate.keySet();
		for (String key : keys) {
			Component sc = serverUpdate.get(key);
			Component cc = clientUpdate.get(key);
			if (this.hasNewVersion(cc, sc)) {
				try {
					Command updateResp = getUpdateComponent(sc);
					if (cc != null) {
						this.backup(cc.getPath());
						output(cc.getName() + " has backuped");
					}
					IOUtil.saveAsFile(updateResp.getByteResponse(), this
							.getInstallPath()
							+ sc.getPath());
					output(sc.getName() + " has updated");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		this.backup(this.getUpdateFileName());
		IOUtil.saveAsFile(serverUpdateFile, this.getInstallPath()
				+ this.getUpdateFileName());
	}

	public String getUpdateFileName() {
		return updateFileName;
	}

	public void setUpdateFileName(String updateFileName) {
		this.updateFileName = updateFileName;
	}

	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
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

	public void setNetClient(NetClient<Command, Command> netClient) {
		this.netClient = netClient;
	}

}
