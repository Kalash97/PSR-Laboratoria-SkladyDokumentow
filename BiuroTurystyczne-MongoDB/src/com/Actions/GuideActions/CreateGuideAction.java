package com.Actions.GuideActions;

import org.bson.Document;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateGuideAction implements Action{

	private MongoCollection<Document> guides;
	private ConsoleView cv;
	
	@Override
	public void launch() {
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		cv.print("Podaj imiê");
		String name = cv.read();

		cv.print("Podaj nazwisko");
		String lastName = cv.read();
		
		String salaryString = getValidDouble("Podaj pensjê");
		double salary = Double.parseDouble(salaryString);
		
		Document doc = new Document("_id", id)
				.append("name", name)
				.append("lastName", lastName)
				.append("salary", salary);
		
		try {
			guides.insertOne(doc);
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
		return "CreateGuide";
	}

}
