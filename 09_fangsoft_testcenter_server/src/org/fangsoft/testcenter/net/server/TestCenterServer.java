package org.fangsoft.testcenter.net.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.fangsoft.testcenter.command.Command;
import org.fangsoft.testcenter.config.SocketConfig;
import org.fangsoft.testcenter.config.ThreadPoolConfig;
import org.fangsoft.util.SocketUtil;

public class TestCenterServer {
	private static final String START_SERVER = "start";
	private static final String SHUTDOWN_SERVER = "shutdown";

	public TestCenterServer() {

	}

	public void start() {
		SocketAcceptor<Command, Command> appAcceptor = new TestCenterSocketAcceptor(
				SocketConfig.getServerPort());
		appAcceptor.setCorePoolSize(ThreadPoolConfig.getCorePoolSize());
		appAcceptor.setMaxPoolSize(ThreadPoolConfig.getMaxPoolSize());
		appAcceptor.setKeepAliveTime(ThreadPoolConfig.getKeepAliveTime());
		appAcceptor.setTimeUnit(ThreadPoolConfig.getTimeUnit());
		AdminSocketAcceptor adminAcceptor = new AdminSocketAcceptor(
				SocketConfig.getAdminServerPort());
		adminAcceptor.addAcceptor(appAcceptor);
		adminAcceptor.start();

	}

	public void shutdown() {
		Socket s = null;
		try {
			s = new Socket();
			String adminHost = SocketConfig.getServerHost();
			int port = SocketConfig.getAdminServerPort();
			int timeout = SocketConfig.getSocketTimeout();
			s.connect(new InetSocketAddress(adminHost, port), timeout);
			Writer w = new OutputStreamWriter(s.getOutputStream());
			w.write(AdminSocketAcceptor.EXIT);
			w.flush();
			s.shutdownOutput();
			System.out.println("server is shutdowning...");
			Reader r = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(r);
			String response = br.readLine();
			s.shutdownInput();
			System.out.println("server response:");
		} catch (Exception ex) {
			System.out.println("cannot shutdown server,exceptions occur as the following:");
			ex.printStackTrace();
		} finally {
			SocketUtil.close(s);
		}
	}

	private static void showUsage() {
		System.out.println("start server:java org.fangsoft.testcenter.net.server.NetServer start");
		System.out.println("shutdown server:java org.fangsoft.testcenter.net.server.NetServer shutdown");
	}
	public static void main(String[] args) {
		if(args !=null&&args.length==1) {
			if(START_SERVER.equalsIgnoreCase(args[0])) {
				new TestCenterServer().start();
				System.out.println("server started");
				return;
			} else if(SHUTDOWN_SERVER.equalsIgnoreCase(args[0])) {
				new TestCenterServer().shutdown();
				return;
			}
		}
		showUsage();
	}
}
