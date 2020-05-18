package com.Actions.TourActions;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteTourByIdAction implements Action{

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;
	
	@Override
	public void launch() {
		
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		r.table("tours").filter(row -> row.g("id").eq(id)).delete().run(conn);
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
		return "DeleteTourById";
	}

}
