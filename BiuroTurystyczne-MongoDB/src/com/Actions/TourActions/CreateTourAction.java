package com.Actions.TourActions;

import org.bson.Document;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateTourAction implements Action{

	private MongoCollection<Document> tours;
	private ConsoleView cv;
	
	@Override
	public void launch() {	
	
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		cv.print("Podaj miasto");
		String city = cv.read();
		
		String daysString = getValidInt("Podaj liczbê dni");
		int daysOfTour = Integer.parseInt(daysString);
		
		String priceString = getValidDouble("Podaj cenê na osobê");
		double price = Double.parseDouble(priceString);
		
		Document doc = new Document("_id", id)
				.append("city", city)
				.append("daysOfTour", daysOfTour)
				.append("price", price)
				.append("finished", false);
		try {
			tours.insertOne(doc);
		} catch (MongoWriteException e) {
			cv.print("Podane id jest ju¿ zajête");
		}
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
