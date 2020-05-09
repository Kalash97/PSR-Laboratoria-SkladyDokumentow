package com.Actions;

import com.View.ConsoleView;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DropDBAction implements Action {

	private MongoDatabase db;
	private ConsoleView cv;
	MongoClient client;

	@Override
	public void launch() {

		cv.print("ARE YOU SURE?");
		String line = cv.read();

		if ("YES".equals(line)) {
			db.getCollection("tours").drop();
			db.getCollection("clients").drop();
			db.getCollection("guides").drop();
			client.close();
			System.exit(1);
		}else {
			return;
		}
	}

	@Override
	public String getName() {
		return "DropDB";
	}

}
