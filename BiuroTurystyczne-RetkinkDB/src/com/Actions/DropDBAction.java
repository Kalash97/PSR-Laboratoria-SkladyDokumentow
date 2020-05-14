package com.Actions;

import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DropDBAction implements Action {

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;

	@Override
	public void launch() {

		cv.print("ARE YOU SURE?");
		String line = cv.read();

		if ("YES".equals(line)) {
			r.db("test").tableDrop("tours").run(conn);
			r.db("test").tableDrop("guides").run(conn);
			r.db("test").tableDrop("clients").run(conn);
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
