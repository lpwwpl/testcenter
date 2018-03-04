package org.fangsoft.testcenter.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.fangsoft.util.SocketUtil;

public abstract class SocketAcceptor<R, T> implements Runnable {
	private int serverPort;
	private ServerSocket serverSocket;
	private boolean exit = false;
	private String serverName;

	private int corePoolSize;
	private int maxPoolSize;
	private int keepAliveTime;
	private TimeUnit timeUnit;
	private ThreadPoolExecutor threadPool;

	public SocketAcceptor(int port, String serverName) {
		this.serverPort = port;
		this.serverName = serverName;
	}

	public SocketAcceptor(int port) {
		this(port, "fangsoft testcenter server");
	}

	protected abstract ServerTask<R, T> generateServerTask(Socket socket);

	public void start() {
		try {
			this.threadPool = new ThreadPoolExecutor(this.getCorePoolSize(),
					this.getMaxPoolSize(), this.getKeepAliveTime(), this
							.getTimeUnit(), new LinkedBlockingQueue<Runnable>());
			serverSocket = new ServerSocket(serverPort);
			Thread acceptor = new Thread(this);
			acceptor.setPriority(Thread.NORM_PRIORITY);
			Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));
			acceptor.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while(!this.isExit()) {
				Socket socket = serverSocket.accept();
				this.threadPool.execute(this.generateServerTask(socket));
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally {
			SocketUtil.close(this.serverSocket);
		}
	}
	
	protected void shutdown() {
		try {
			this.setExit(true);
			this.threadPool.shutdown();
			while(!this.threadPool.isShutdown()) {
				try {
					Thread.sleep(1000);
				} catch(InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			try {
				this.serverSocket.close();
			} catch(IOException ex) {
				
			}
		}
	}

	protected class ShutdownHook implements Runnable {
		public void run() {
			shutdown();
		}
	}
	
	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public ThreadPoolExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolExecutor threadPool) {
		this.threadPool = threadPool;
	}
}
