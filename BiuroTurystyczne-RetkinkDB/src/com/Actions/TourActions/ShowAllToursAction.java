package com.Actions.TourActions;

import com.Actions.Action;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowAllToursAction implements Action{

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void launch() {
		Cursor c = r.table("tours").run(conn);
		cv.print(c);
	}

	@Override
	public String getName() {
		return "ShowAllTours";
	}

}
