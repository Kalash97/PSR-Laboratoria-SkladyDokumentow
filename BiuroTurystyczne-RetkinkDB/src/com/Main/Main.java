package com.Main;

import java.util.ArrayList;
import java.util.List;

import com.Actions.Action;
import com.Actions.DropDBAction;
import com.Actions.ExitAction;
import com.Actions.ClientActions.CreateClientAction;
import com.Actions.ClientActions.DeleteClientAction;
import com.Actions.ClientActions.ShowAllClientsAction;
import com.Actions.ClientActions.ShowClientByIdAction;
import com.Actions.ClientActions.UpdateClientAction;
import com.Actions.GuideActions.CreateGuideAction;
import com.Actions.GuideActions.DeleteGuideAction;
import com.Actions.GuideActions.ShowAllGuidesAction;
import com.Actions.GuideActions.ShowGuideByIdAction;
import com.Actions.GuideActions.UpdateGuideAction;
import com.Actions.TourActions.CreateTourAction;
import com.Actions.TourActions.DeleteTourByIdAction;
import com.Actions.TourActions.FinishTourAction;
import com.Actions.TourActions.ShowAllToursAction;
import com.Actions.TourActions.ShowTourByIdAction;
import com.Actions.TourActions.UpdateTourAction;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import com.rethinkdb.net.Connection;

public class Main {

	private static List<Action> actions;
	private static ConsoleView cv;
	

	public static void main(String[] args) {

		//Line to run rethink from cmd: rethinkdb.exe --bind all --http-port 8085
		
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
		actions.add(new ShowTourByIdAction(cv, r, conn));
		actions.add(new ShowAllToursAction(cv, r, conn));
		actions.add(new DeleteTourByIdAction(cv, r, conn));
		actions.add(new UpdateTourAction(cv, r, conn));
		actions.add(new FinishTourAction(cv, r, conn));
		
		actions.add(new CreateClientAction(cv, r, conn));
		actions.add(new ShowClientByIdAction(cv, r, conn));
		actions.add(new ShowAllClientsAction(cv, r, conn));
		actions.add(new DeleteClientAction(cv, r, conn));
		actions.add(new UpdateClientAction(cv, r, conn));
		
		actions.add(new CreateGuideAction(cv, r, conn));
		actions.add(new ShowGuideByIdAction(cv, r, conn));
		actions.add(new ShowAllGuidesAction(cv, r, conn));
		actions.add(new DeleteGuideAction(cv, r, conn));
		actions.add(new UpdateGuideAction(cv, r, conn));
		
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
