package com.Main;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class Main {

	public static void main(String[] args) {
		
		String user = "AdminTravel";
		String password = "AdminTravel";
		String host = "localhost";
		int port = 27017;
		String database = "TravelAgencyDB";

		String clientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;
		MongoClientURI uri = new MongoClientURI(clientURI);
		
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase(database);
		db.getCollection("people").drop();
		System.out.println("closing client");
		mongoClient.close();
	}

}
