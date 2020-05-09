package com.Actions.TourActions;

import org.bson.Document;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReadTourByIdAction implements Action{

	private MongoCollection<Document> tours;
	private ConsoleView cv;
	
	@Override
	public void launch() {
		
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		Document doc = tours.find(new BasicDBObject("_id", id)).first();
		if(doc == null) {
			cv.print("Nie ma takiej wycieczki");
			return;
		}
		
		cv.print(doc.toJson());
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
		return "ReadTourById";
	}

}
