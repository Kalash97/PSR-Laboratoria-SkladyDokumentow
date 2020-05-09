package com.View;

import java.util.Scanner;

public class ConsoleView {
	
	private Scanner scanner = new Scanner(System.in);
	public void print(String msg) {
		System.out.println(msg);
	}
	
	public String read() {
		return scanner.nextLine();
	}
}
