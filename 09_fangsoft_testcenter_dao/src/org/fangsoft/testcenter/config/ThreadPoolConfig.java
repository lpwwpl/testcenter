package org.fangsoft.testcenter.config;

import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {
	private static final int corePoolSize = 5;
	private static final int maxPoolSize = 10;
	private static final int keepAliveTime = 3000;
	private static final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	public static final int getCorePoolSize(){return corePoolSize;}
	public static final int getMaxPoolSize() {return maxPoolSize;}
	public static final int getKeepAliveTime() {return keepAliveTime;}
	public static final TimeUnit getTimeUnit() {return timeUnit;}
}

