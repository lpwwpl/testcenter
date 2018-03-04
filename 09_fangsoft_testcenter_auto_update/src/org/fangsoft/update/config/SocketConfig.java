package org.fangsoft.update.config;

import java.net.Proxy;

public class SocketConfig {
	public static final int getSocketTimeout() {
		return 300000;
	}
	public static final String getServerHost() {
		return "localhost";
	}
	public static int getServerPort() {
		return 8006;
	}
	public static final String getProxyServer() {
		return "localhost";
	}
	public static final int getProxyPort() {
		return 0;
	}
	public static Proxy.Type getProxyType() {
		return Proxy.Type.DIRECT;
	}
	public static int getAdminServerPort() {
		return 8007;
	}
}
