package org.fangsoft.update.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.fangsoft.testcenter.config.ThreadPoolConfig;
import org.fangsoft.testcenter.net.server.AdminSocketAcceptor;
import org.fangsoft.testcenter.net.server.SocketAcceptor;
import org.fangsoft.update.command.Command;
import org.fangsoft.update.config.SocketConfig;
import org.fangsoft.util.SocketUtil;

public class UpdateServer {
	private static final String START_SERVER = "start";
	private static final String SHUTDOWN_SERVER = "shutdown";
	public UpdateServer() {
		
	}
	public void start() {
		SocketAcceptor<Command, Command> appAcceptor = new UpdateSocketAcceptor(
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
			System.out.println("update server is shutdowning...");
			Reader r = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(r);
			String response = br.readLine();
			s.shutdownInput();
			System.out.println("update server response:");
		} catch (Exception ex) {
			System.out.println("cannot shutdown update server,exceptions occur as the following:");
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
				new UpdateServer().start();
				System.out.println("server started");
				return;
			} else if(SHUTDOWN_SERVER.equalsIgnoreCase(args[0])) {
				new UpdateServer().shutdown();
				return;
			}
		}
		showUsage();
	}
}
