package com.Actions;

import com.mongodb.MongoClient;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExitAction implements Action{

	MongoClient client;
	
	@Override
	public void launch() {
		client.close();
		System.exit(1);
	}

	@Override
	public String getName() {
		return "Exit";
	}

}
