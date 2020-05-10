package com.Actions.GuideActions;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.client.FindIterable;
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
		
		FindIterable<Document> docs = guides.find(eq("_id", id));
		
		cv.print(docs);
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
