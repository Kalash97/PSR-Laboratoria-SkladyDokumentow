package com.Actions.ClientActions;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateClientAction implements Action{

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;
	
	@Override
	public void launch() {
		
		String idString = getValidInt("Podaj ID");
		if("".equals(idString)) {
			return;
		}
		int id = Integer.parseInt(idString);
		
		cv.print("Podaj imiê");
		String name = cv.read();
		if (!"".equals(name)) {
			r.table("clients").filter(row -> row.g("id").eq(id))
			.update(r.hashMap("name", name)).run(conn);
		}

		cv.print("Podaj nazwisko");
		String lastName = cv.read();
		if (!"".equals(lastName)) {
			r.table("clients").filter(row -> row.g("id").eq(id))
			.update(r.hashMap("lastName", lastName)).run(conn);
		}
	}

	private String getValidInt(String msg) {
		String line;
		do {
			cv.print(msg);
			line = cv.read();
			if ("".equals(line)) {
				break;
			}
		}while(!ValidUtil.isIntInstance(line));
		return line;
	}
	
	@Override
	public String getName() {
		return "UpdateClient";
	}

}
