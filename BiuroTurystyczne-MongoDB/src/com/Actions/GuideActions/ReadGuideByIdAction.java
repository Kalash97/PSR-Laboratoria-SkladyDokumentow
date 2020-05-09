package com.Actions.GuideActions;

import org.bson.Document;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReadGuideByIdAction implements Action{

	private MongoCollection<Document> guides;
	private ConsoleView cv;
	
	@Override
	public void launch() {
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		Document doc = guides.find(new BasicDBObject("_id", id)).first();
		if(doc == null) {
			cv.print("Nie ma takiego przewodnika");
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
		return "ReadGuideById";
	}

}
