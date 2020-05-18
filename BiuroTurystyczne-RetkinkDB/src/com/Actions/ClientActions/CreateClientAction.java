package com.Actions.ClientActions;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateClientAction implements Action{

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;
	
	@Override
	public void launch() {
		
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		cv.print("Podaj imiê");
		String name = cv.read();

		cv.print("Podaj nazwisko");
		String lastName = cv.read();
		
		r.table("clients").insert(r.array(r.hashMap("id", id)
				.with("name", name)
				.with("lastName", lastName))).run(conn);
	}

	private String getValidInt(String msg) {
		String line;
		do {
			cv.print(msg);
			line = cv.read();
		}while(!ValidUtil.isIntInstance(line));
		return line;
	}
	
	@Override
	public String getName() {
		return "CreateClient";
	}

}
