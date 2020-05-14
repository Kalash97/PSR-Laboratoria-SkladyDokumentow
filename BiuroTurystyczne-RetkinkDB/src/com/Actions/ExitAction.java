package com.Actions;

public class ExitAction implements Action{

	@Override
	public void launch() {
		System.exit(1);
	}

	@Override
	public String getName() {
		return "Exit";
	}

}
