package org.fangsoft.testcenter.view;

import java.util.Observable;

import org.fangsoft.util.Console;

public abstract class ConsoleView extends Observable implements TestCenterView{

	@Override
	public void display() {
		// TODO Auto-generated method stub
		Console.output("=============fangsoft testcenter===============");
		displayView();
		Console.output("==copyright Fangsoft Inc,all rights reserved.==");
		Console.output("");
		setChanged();
		notifyObservers();
	}
	protected abstract void displayView();
}
