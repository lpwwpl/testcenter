package org.fangsoft.testcenter;

import org.fangsoft.testcenter.command.ICommand;
import org.fangsoft.testcenter.config.SocketConfig;
import org.fangsoft.testcenter.controller.console.ConsoleTestCenterController;
import org.fangsoft.testcenter.net.client.NetClient;
import org.fangsoft.testcenter.view.WelcomeView;

public class TestCenterBySetupDB {

	public static void main(String[] args) {
		runMVC();
	}

	public static void runMVC() {
		ConsoleTestCenterController tcc = new ConsoleTestCenterController();
		tcc.setNetClient(getNetClient());
		WelcomeView view = new WelcomeView();
		view.addObserver(tcc);
		view.display();
	}

	private static NetClient<ICommand, ICommand> getNetClient() {
		NetClient<ICommand, ICommand> netClient = new NetClient<ICommand, ICommand>();
		netClient.setServerHost(SocketConfig.getServerHost());
		netClient.setServerPort(SocketConfig.getServerPort());
		netClient.setTimeout(SocketConfig.getSocketTimeout());
		netClient.setProxyServer(SocketConfig.getProxyServer());
		netClient.setProxyPort(SocketConfig.getProxyPort());
		netClient.setProxyType(SocketConfig.getProxyType());
		return netClient;
	}
}
