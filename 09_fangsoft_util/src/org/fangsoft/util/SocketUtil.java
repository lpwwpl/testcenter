package org.fangsoft.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketUtil {

	public static void close(Socket s) {
		try{
			if(s!=null&&!s.isClosed()) {
				s.close();
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public static void close(ServerSocket s) {
		try {
			if(s!=null&&!s.isClosed())s.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
