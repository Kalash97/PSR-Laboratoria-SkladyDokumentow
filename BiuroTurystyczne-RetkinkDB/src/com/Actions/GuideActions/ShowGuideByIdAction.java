package com.Actions.GuideActions;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowGuideByIdAction implements Action{

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void launch() {
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		Cursor c = r.table("guides").filter(row -> row.g("id").eq(id)).run(conn);
		cv.print(c);
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
		return "ShowGuideById";
	}

}
