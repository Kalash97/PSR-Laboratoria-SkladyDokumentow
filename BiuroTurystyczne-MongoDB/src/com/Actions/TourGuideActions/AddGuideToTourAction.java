package com.Actions.TourGuideActions;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddGuideToTourAction implements Action{

	private MongoCollection<Document> guides;
	private MongoCollection<Document> tours;
	private ConsoleView cv;
	
	@Override
	public void launch() {
		String idStringTour = getValidInt("Podaj ID wycieczki");
		if("".equals(idStringTour)) {
			return;
		}
		int idTour = Integer.parseInt(idStringTour);
		Document tour = tours.find(eq("_id", idTour)).first();
		if(tour==null) {
			cv.print("Nie ma takiej wycieczki");
			return;
		}
		
		String idStringGuide = getValidInt("Podaj ID klienta");
		if("".equals(idStringGuide)) {
			return;
		}
		int idGuide = Integer.parseInt(idStringGuide);
		Document guide = guides.find(eq("_id", idGuide)).first();
		if(guide==null) {
			cv.print("Nie ma takiego klienta");
			return;
		}
		
		tours.updateOne(and(eq("_id", idTour), eq("finished", false)), Updates.addToSet("guides", guide));
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
		return "AddGuideToTour";
	}

}
