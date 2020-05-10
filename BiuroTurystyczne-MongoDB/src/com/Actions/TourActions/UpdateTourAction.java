package com.Actions.TourActions;

import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.client.MongoCollection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateTourAction implements Action {

	private MongoCollection<Document> tours;
	private ConsoleView cv;

	@Override
	public void launch() {

		int daysOfTour = 0;
		double price = 0;
		
		Bson updateCity = null;
		Bson updateDays = null;
		Bson updatePrice = null;
		
		String idString = getValidInt("Podaj ID");
		if("".equals(idString)) {
			return;
		}
		int id = Integer.parseInt(idString);

		cv.print("Podaj miasto");
		String city = cv.read();
		if(!"".equals(city)) {
			updateCity = set("city", city);
		}

		String daysString = getValidInt("Podaj liczbê dni");
		if (!"".equals(daysString)) {
			daysOfTour = Integer.parseInt(daysString);
			updateDays = set("daysOfTour", daysOfTour);
		}

		String priceString = getValidDouble("Podaj cenê na osobê");
		if (!"".equals(priceString)) {
			price = Double.parseDouble(priceString);
			updatePrice = set("price", price);
		}

		Bson filter = eq("_id", id);
		
		if(updateCity!=null) {
			tours.updateOne(filter, updateCity);
		}
		
		if(updateDays!=null) {
			tours.updateOne(filter, updateDays);
		}
		
		if(updatePrice!=null) {
			tours.updateOne(filter, updatePrice);
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
		} while (!ValidUtil.isIntInstance(line));
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
