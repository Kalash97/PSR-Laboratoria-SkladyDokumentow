package com.Actions.ClientActions;

import static com.mongodb.client.model.Updates.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.client.MongoCollection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateClientAction implements Action{

	private MongoCollection<Document> clients;
	private ConsoleView cv;
	
	@Override
	public void launch() {
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		Bson updateName = null;
		Bson updateLastName = null;
		
		cv.print("Podaj imiê");
		String name = cv.read();
		if (!"".equals(name)) {
			updateName = set("name", name);
		}

		cv.print("Podaj nazwisko");
		String lastName = cv.read();
		if (!"".equals(lastName)) {
			updateLastName = set("lastName", lastName);
		}
		
		Bson filter = eq("_id", id);
		
		if(updateName!=null) {
			clients.updateOne(filter, updateName);
		}
		
		if(updateLastName!=null) {
			clients.updateOne(filter, updateLastName);
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
	
	@Override
	public String getName() {
		return "UpdateClient";
	}

}
