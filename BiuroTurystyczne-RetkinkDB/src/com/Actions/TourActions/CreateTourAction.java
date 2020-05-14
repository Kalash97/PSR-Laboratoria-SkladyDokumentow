package com.Actions.TourActions;

import com.Actions.Action;
import com.View.ConsoleView;
import com.Utils.ValidUtil;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateTourAction implements Action{

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;
	
	@Override
	public void launch() {	
		
		//Line to run rethink from cmd: rethinkdb.exe --bind all --http-port 8085
		
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		cv.print("Podaj miasto");
		String city = cv.read();
		
		String daysString = getValidInt("Podaj liczbê dni");
		int daysOfTour = Integer.parseInt(daysString);
		
		String priceString = getValidDouble("Podaj cenê na osobê");
		double price = Double.parseDouble(priceString);
		
		r.table("tours").insert(r.array(r.hashMap("id", id)
				.with("city", city)
				.with("daysOfTour", daysOfTour)
				.with("price", price))).run(conn);
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
		return "CreateTour";
	}

}
