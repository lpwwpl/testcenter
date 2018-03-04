package org.fangsoft.testcenter.view;

import java.util.Date;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.util.Console;

public class WelcomeView extends ConsoleView implements TestCenterView {

	@Override
	protected void displayView() {
		// TODO Auto-generated method stub
		Console.output("ΩÒÃÏ «£∫" + Configuration.getDateFormat().format(new Date()));
	}

}
