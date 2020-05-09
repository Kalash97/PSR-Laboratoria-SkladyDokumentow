package com.Actions.ClientActions;

import org.bson.Document;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateClientAction implements Action {

	private MongoCollection<Document> clients;
	private ConsoleView cv;

	@Override
	public void launch() {

		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		cv.print("Podaj imiê");
		String name = cv.read();

		cv.print("Podaj nazwisko");
		String lastName = cv.read();
		
		Document doc = new Document("_id", id)
				.append("name", name)
				.append("lastName", lastName);
		
		try {
			clients.insertOne(doc);
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
	
	@Override
	public String getName() {
		return "CreateClient";
	}

}
