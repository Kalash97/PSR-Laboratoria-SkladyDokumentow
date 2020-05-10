package com.View;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.FindIterable;

public class ConsoleView {
	
	private Scanner scanner = new Scanner(System.in);
	
	public void print(String msg) {
		System.out.println(msg);
	}
	
	public void print(FindIterable<Document> docs) {
		
		for(Document d : docs) {
			print(d.toJson());
		}
	}
	
	public String read() {
		return scanner.nextLine();
	}
}
