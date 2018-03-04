package org.fangsoft.testcenter;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.controller.console.ConsoleTestCenterController;
import org.fangsoft.testcenter.view.WelcomeView;

public class TestCenterBySetupProps {
	public static void main(String[] args) {
		runMVC();
	}
	public static void runMVC() {
		ConsoleTestCenterController tcc = new ConsoleTestCenterController();
		tcc.setDaoFactory(Configuration.getPropsDaoFactory());
		WelcomeView view = new WelcomeView();
		view.addObserver(tcc);
		view.display();
	}
}
