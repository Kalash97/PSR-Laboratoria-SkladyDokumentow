package com.Main;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.Actions.Action;
import com.Actions.DropDBAction;
import com.Actions.ExitAction;
import com.Actions.ClientActions.CreateClientAction;
import com.Actions.ClientActions.DeleteClientByIdAction;
import com.Actions.ClientActions.ReadClientByIdAction;
import com.Actions.ClientActions.UpdateClientAction;
import com.Actions.ClientTourActions.AddClientToTourAction;
import com.Actions.ClientTourActions.RemoveClientFromTourAction;
import com.Actions.GuideActions.CreateGuideAction;
import com.Actions.GuideActions.DeleteGuideByIdAction;
import com.Actions.GuideActions.ReadGuideByIdAction;
import com.Actions.GuideActions.UpdateGuideAction;
import com.Actions.TourActions.CreateTourAction;
import com.Actions.TourActions.DeleteTourByIdAction;
import com.Actions.TourActions.FinishTourAction;
import com.Actions.TourActions.ReadTourByIdAction;
import com.Actions.TourActions.UpdateTourAction;
import com.Actions.TourGuideActions.AddGuideToTourAction;
import com.Actions.TourGuideActions.FindTourByGuideAction;
import com.Actions.TourGuideActions.RemoveGuideFromTourAction;
import com.View.ConsoleView;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {

	private static List<Action> actions;
	private static ConsoleView cv;

	public static void main(String[] args) {
		
		init();
		
		while (true) {
			cv.print("Lista dost�pnych akcji:");
			showActions();
			cv.print("");
			cv.print("Podaj akcj�");
			runAction(cv.read());
		}
		
	}

	public static void init() {
		actions = new ArrayList<Action>();
		cv = new ConsoleView();

		String user = "AdminTravel";
		String password = "AdminTravel";
		String host = "localhost";
		int port = 27017;
		String database = "TravelAgencyDB";

		String clientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;
		MongoClientURI uri = new MongoClientURI(clientURI);

		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase(database);		

		MongoCollection<Document> tours = db.getCollection("tours");
		MongoCollection<Document> clients = db.getCollection("clients");
		MongoCollection<Document> guides = db.getCollection("guides");
		
		actions.add(new CreateTourAction(tours, cv));
		actions.add(new ReadTourByIdAction(tours, cv));
		actions.add(new UpdateTourAction(tours, cv));
		actions.add(new DeleteTourByIdAction(tours, cv));
		actions.add(new FinishTourAction(tours, cv));
		
		actions.add(new CreateClientAction(clients, cv));
		actions.add(new ReadClientByIdAction(clients, cv));
		actions.add(new UpdateClientAction(clients, cv));
		actions.add(new DeleteClientByIdAction(clients, tours, cv));
		
		actions.add(new CreateGuideAction(guides, cv));
		actions.add(new ReadGuideByIdAction(guides, cv));
		actions.add(new UpdateGuideAction(guides, cv));
		actions.add(new DeleteGuideByIdAction(guides, tours, cv));
		
		actions.add(new AddClientToTourAction(tours, clients, cv));
		actions.add(new RemoveClientFromTourAction(tours, clients, cv));
		
		actions.add(new AddGuideToTourAction(guides, tours, cv));
		actions.add(new RemoveGuideFromTourAction(guides, tours, cv));
		actions.add(new FindTourByGuideAction(guides, tours, cv));
		
		actions.add(new DropDBAction(db, cv, mongoClient));
		actions.add(new ExitAction(mongoClient));
	}

	private static void runAction(String name) {
		for (Action a : actions) {
			if (name.equals(a.getName())) {
				launchAction(a);
				return;
			}
		}
		cv.print("Nie ma takiej akcji: " + name);
	}
	
	private static void launchAction(Action a) {
			a.launch();
	}
	
	private static void showActions() {
		for (Action a : actions) {
			cv.print(" " + a.getName());
		}
	}
	
}
