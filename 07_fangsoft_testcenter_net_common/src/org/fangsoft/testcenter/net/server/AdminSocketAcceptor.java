package org.fangsoft.testcenter.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.fangsoft.util.Console;
import org.fangsoft.util.SocketUtil;

public class AdminSocketAcceptor implements Runnable {

	public static final String EXIT = "exit";
	private int port;

	public AdminSocketAcceptor(int port) {
		this.port = port;
	}

	private ArrayList<SocketAcceptor> acceptorList = new ArrayList<SocketAcceptor>();

	public void addAcceptor(SocketAcceptor sa) {
		acceptorList.add(sa);
	}

	public void start() {
		if (acceptorList != null) {
			for (SocketAcceptor sa : acceptorList) {
				Console.output(sa.getServerName() + "is starting...");
				sa.start();
				Console.output(sa.getServerName() + " has started");
			}
		}
		new Thread(this).start();
	}

	public void shutdown() {
		if (acceptorList != null) {
			for (SocketAcceptor sa : acceptorList) {
				Console.output(sa.getServerName() + "is shutdowning...");
				sa.shutdown();
				Console.output(sa.getServerName() + " has shutdown");
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket s = null;
		ServerSocket adminSocket = null;
		try {
			adminSocket = new ServerSocket(this.getPort());
			while(true) {
				s = adminSocket.accept();
				if(isShutdownCommand(s)) {
					this.shutdown();
					this.sendMessage(s, "server has shutdown");
					break;
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally {
			SocketUtil.close(s);
			SocketUtil.close(adminSocket);
		}
	}

	public boolean isShutdownCommand(Socket s) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(s
				.getInputStream()));
		String input = br.readLine();
		if (EXIT.equalsIgnoreCase(input)) {
			s.shutdownInput();
			return true;
		}
		return false;
	}

	private void sendMessage(Socket s, String message) throws IOException {
		Writer w = new OutputStreamWriter(s.getOutputStream());
		w.write(message);
		w.flush();
		s.shutdownOutput();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
