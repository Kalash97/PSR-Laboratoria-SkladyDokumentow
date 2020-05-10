package com.Actions.GuideActions;

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
public class UpdateGuideAction implements Action {

	private MongoCollection<Document> guides;
	private ConsoleView cv;

	@Override
	public void launch() {

		double salary = 0;
		Bson updateName = null;
		Bson updateLastName = null;
		Bson updateSalary = null;

		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);

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

		String salaryString = getValidDouble("Podaj pensjê");
		if (!"".equals(salaryString)) {
			salary = Double.parseDouble(salaryString);
			updateSalary = set("salary", salary);
		}

		Bson filter = eq("_id", id);

		if(updateName!=null) {
			guides.updateOne(filter, updateName);
		}
		
		if(updateLastName!=null) {
			guides.updateOne(filter, updateLastName);
		}
		
		if(updateSalary!=null) {
			guides.updateOne(filter, updateSalary);
		}
	}

	private String getValidInt(String msg) {
		String line;
		do {
			cv.print(msg);
			line = cv.read();

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
		return "UpdateGuide";
	}

}
