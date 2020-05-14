package com.View;

import java.util.Scanner;

import com.rethinkdb.net.Cursor;

public class ConsoleView {

	private Scanner scanner = new Scanner(System.in);
	
	public void print(String msg) {
		System.out.println(msg);
	}
	
	@SuppressWarnings("rawtypes")
	public void print(Cursor cursor) {
		for (Object doc : cursor) {
		    System.out.println(doc);
		}
	}
	
	public String read() {
		return scanner.nextLine();
	}
}
