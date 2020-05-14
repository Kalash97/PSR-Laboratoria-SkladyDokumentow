package com.Main;

import java.util.ArrayList;
import java.util.List;

import com.Actions.Action;
import com.Actions.DropDBAction;
import com.Actions.ExitAction;
import com.Actions.TourActions.CreateTourAction;
import com.Actions.TourActions.ShowAllToursAction;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import com.rethinkdb.net.Connection;

public class Main {

	private static List<Action> actions;
	private static ConsoleView cv;
	

	public static void main(String[] args) {

		init();
		while(true) {
			cv.print("Lista dostêpnych akcji:");
			showActions();
			cv.print("");
			cv.print("Podaj akcjê");
			runAction(cv.read());
		}
		
	}

	public static void init() {
		actions = new ArrayList<Action>();
		cv = new ConsoleView();
		final RethinkDB r = RethinkDB.r;
		
		System.out.println("connected");
		Connection conn = r.connection().hostname("localhost").port(28015).connect();
		
		try {	
			r.db("test").tableCreate("tours").run(conn);
			System.out.println("table tours created");
		}catch (ReqlOpFailedError e) {
			System.out.println("table tours already exists");
		}
		
		try {	
			r.db("test").tableCreate("guides").run(conn);
			System.out.println("table guides created");
		}catch (ReqlOpFailedError e) {
			System.out.println("table guides already exists");
		}
		
		try {	
			r.db("test").tableCreate("clients").run(conn);
			System.out.println("table clients created");
		}catch (ReqlOpFailedError e) {
			System.out.println("table clients already exists");
		}
		
		actions.add(new CreateTourAction(cv, r, conn));
		actions.add(new ShowAllToursAction(cv, r, conn));
		
		actions.add(new ExitAction());
		actions.add(new DropDBAction(cv, r, conn));
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
