package org.fangsoft.testcenter.net.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

import org.fangsoft.util.SocketUtil;

public class NetClient<R, T> {
	private String serverHost;
	private int serverPort;
	private int timeout;
	private InetSocketAddress serverAddr;
	private String proxyServer;
	private int proxyPort;
	private Proxy.Type proxyType;
	private Proxy proxy;
	private ClientTask clientTask;
	
	private Proxy getProxy() {
		if (this.proxy == null && this.proxyType != null
				&& this.proxyType != Proxy.Type.DIRECT) {
			InetSocketAddress proxyAddr = new InetSocketAddress(this
					.getProxyServer(), this.getProxyPort());
			this.proxy = new Proxy(this.getProxyType(), proxyAddr);
		} else {
			this.proxy = Proxy.NO_PROXY;
		}
		return this.proxy;
	}

	private T process(Socket socket, R request) {
		InputStream is;
		ObjectOutputStream oos;
		T response = null;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(request);
			socket.shutdownOutput();
			is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			response = (T) ois.readObject();
			socket.shutdownInput();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			SocketUtil.close(socket);
		}
		return response;
	}

	private void debug(Object msg) {
		System.out.println(msg);
	}

	public T synProcess(R request) {
		try {
			Socket s = new Socket(this.getProxy());
			s.connect(this.getServerAddr());
			return this.process(s, request);
		} catch (IOException ex) {
			debug("server response exception,pls check server is ok");
			ex.printStackTrace();
		}
		return null;
	}

	public void asyncProcess(R r) {
		Socket socket = new Socket(this.getProxy());
		this.clientTask = new ClientTask(socket,r);
	}

	public ClientTask getClientTask() {
		return this.clientTask;
	}
	
	public final class ClientTask implements Runnable {
		private Socket socket;
		private R request;
		private T response;
		public ClientTask(Socket socket,R request) {
			this.socket = socket;
			this.request = request;
			Thread t = new Thread(this);
			t.start();
		}
		public void run() {
			this.response = process(this.socket,this.request);
		}
		public T getResponse() {
			return response;
		}
		public void setRequest(R request) {
			this.request = request;
		}
	}
	
	private InetSocketAddress getServerAddr() {
		if (this.serverAddr == null) {
			this.serverAddr = new InetSocketAddress(this.getServerHost(), this
					.getServerPort());
		}
		return this.serverAddr;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setServerAddr(InetSocketAddress serverAddr) {
		this.serverAddr = serverAddr;
	}

	public String getProxyServer() {
		return proxyServer;
	}

	public void setProxyServer(String proxyServer) {
		this.proxyServer = proxyServer;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public Proxy.Type getProxyType() {
		return proxyType;
	}

	public void setProxyType(Proxy.Type proxyType) {
		this.proxyType = proxyType;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
}
