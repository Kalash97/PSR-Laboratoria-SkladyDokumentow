package com.Actions.ClientActions;

import com.Actions.Action;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowAllClientsAction implements Action{

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void launch() {
		Cursor c = r.table("clients").run(conn);
		cv.print(c);
	}

	@Override
	public String getName() {
		return "ShowAllClients";
	}

}
