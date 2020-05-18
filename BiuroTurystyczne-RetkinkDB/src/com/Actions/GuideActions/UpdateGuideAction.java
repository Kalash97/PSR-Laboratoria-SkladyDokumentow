package com.Actions.GuideActions;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateGuideAction implements Action{

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;
	
	@Override
	public void launch() {
		
		double salary = 0;
		
		String idString = getValidInt("Podaj ID");
		if("".equals(idString)) {
			return;
		}
		int id = Integer.parseInt(idString);
		
		cv.print("Podaj imiê");
		String name = cv.read();
		if (!"".equals(name)) {
			r.table("guides").filter(row -> row.g("id").eq(id))
			.update(r.hashMap("name", name)).run(conn);
		}

		cv.print("Podaj nazwisko");
		String lastName = cv.read();
		if (!"".equals(lastName)) {
			r.table("guides").filter(row -> row.g("id").eq(id))
			.update(r.hashMap("lastName", lastName)).run(conn);
		}
		
		String salaryString = getValidDouble("Podaj pensjê");
		if (!"".equals(salaryString)) {
			salary = Double.parseDouble(salaryString);
			r.table("guides").filter(row -> row.g("id").eq(id))
			.update(r.hashMap("salary", salary)).run(conn);
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

	private String getValidDouble(String msg) {
		String line;
		do {
			cv.print(msg);
			line = cv.read();
			if ("".equals(line)) {
				break;
			}
		} while (!ValidUtil.isDoubleInstance(line));
		return line;
	}
	
	@Override
	public String getName() {
		return "UpdateGuide";
	}

}
