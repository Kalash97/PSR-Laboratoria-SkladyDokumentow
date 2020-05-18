package com.Actions.TourActions;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateTourAction implements Action{

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;
	
	@Override
	public void launch() {
		
		int daysOfTour = 0;
		double price = 0;
		
		String idString = getValidInt("Podaj ID");
		if("".equals(idString)) {
			return;
		}
		int id = Integer.parseInt(idString);
		
		cv.print("Podaj miasto");
		String city = cv.read();
		if(!"".equals(city)) {
			r.table("tours").filter(row -> row.g("id").eq(id)
				.and(row.g("finished").eq(false)))
				.update(r.hashMap("city", city)).run(conn);
		}
		
		String daysString = getValidInt("Podaj liczbê dni");
		if (!"".equals(daysString)) {
			daysOfTour = Integer.parseInt(daysString);
			r.table("tours").filter(row -> row.g("id").eq(id)
				.and(row.g("finished").eq(false)))
				.update(r.hashMap("daysOfTour", daysOfTour)).run(conn);
		}
		
		String priceString = getValidDouble("Podaj cenê na osobê");
		if (!"".equals(priceString)) {
			price = Double.parseDouble(priceString);
			r.table("tours").filter(row -> row.g("id").eq(id)
				.and(row.g("finished").eq(false)))
				.update(r.hashMap("price", price)).run(conn);
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
		return "UpdateTour";
	}

}
