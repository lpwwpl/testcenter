package org.fangsoft.testcenter.view;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.util.Console;

public class LoginView extends ConsoleView implements TestCenterView {

	private String userId;
	private String password;
	private boolean error = false;
	private int loginCOunt = 0;

	public LoginView() {
	}

	@Override
	protected void displayView() {
		// TODO Auto-generated method stub
		if (!error) {
			Console.output("����fangsoft��������ǰ���ȵ�¼��������ɺ�enterȷ��");
		} else {
			Console.output("�û�������������ܵ�¼�������µ�¼��" + "ע�⣺��¼"
					+ Configuration.MAX_LOGIN + "�β��ɹ���ϵͳ���˳�������"
					+ (++this.loginCOunt) + "��");
		}
		this.userId = Console.prompt("�����û�����:");
		this.password = Console.prompt("�����û�����:");
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLoginCOunt() {
		return loginCOunt;
	}

	public void setLoginCOunt(int loginCOunt) {
		this.loginCOunt = loginCOunt;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}
