package com.Actions.TourActions;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.client.MongoCollection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteTourByIdAction implements Action{

	private MongoCollection<Document> tours;
	private ConsoleView cv;
	
	@Override
	public void launch() {
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		cv.print("Deleting tour where id = "+id);
		tours.deleteOne(eq("_id", id));
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
		return "DeleteTourById";
	}

}
