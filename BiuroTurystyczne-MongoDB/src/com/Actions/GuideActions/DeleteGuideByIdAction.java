package com.Actions.GuideActions;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pull;

import org.bson.Document;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.client.MongoCollection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteGuideByIdAction implements Action{

	private MongoCollection<Document> guides;
	private MongoCollection<Document> tours;
	private ConsoleView cv;
	
	@Override
	public void launch() {
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		Document guide = guides.find(eq("_id", id)).first();
		tours.updateMany(and(eq("finished", false), eq("guides", guide)), pull("guides", guide));
		
		cv.print("Deleting guide where id = "+id);
		guides.deleteOne(eq("_id", id));
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
		return "DeleteGuideById";
	}

}
