package com.Actions.GuideActions;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateGuideAction implements Action{

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
		
		String salaryString = getValidDouble("Podaj pensjê");
		double salary = Double.parseDouble(salaryString);
		
		r.table("guides").insert(r.array(r.hashMap("id", id)
				.with("name", name)
				.with("lastName", lastName)
				.with("salary", salary))).run(conn);
	}

	private String getValidInt(String msg) {
		String line;
		do {
			cv.print(msg);
			line = cv.read();
		}while(!ValidUtil.isIntInstance(line));
		return line;
	}
	
	private String getValidDouble(String msg) {
		String line;
		do {
			cv.print(msg);
			line = cv.read();
		}while(!ValidUtil.isDoubleInstance(line));
		return line;
	}
	
	@Override
	public String getName() {
		return "CreateGuide";
	}

}
