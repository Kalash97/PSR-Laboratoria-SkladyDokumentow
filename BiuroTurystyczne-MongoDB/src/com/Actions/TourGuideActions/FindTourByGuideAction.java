package com.Actions.TourGuideActions;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;

import com.Actions.Action;
import com.View.ConsoleView;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindTourByGuideAction implements Action {

	private MongoCollection<Document> guides;
	private MongoCollection<Document> tours;
	private ConsoleView cv;

	@Override
	public void launch() {
		cv.print("Podaj imiê przewodnika");
		String name = cv.read();

		cv.print("Podaj nazwisko przewodnika");
		String lastName = cv.read();

		FindIterable<Document> foundGuides = guides.find(and(eq("name", name), eq("lastName", lastName)));

		cv.print("Guides:-------------");
		cv.print(foundGuides);

		FindIterable<Document> foundTours = null;
		for (Document d : foundGuides) {
			foundTours = tours.find(eq("guides", d));
		}

		try {
			cv.print("Tours:-------------");
			cv.print(foundTours);
		} catch (NullPointerException e) {
			cv.print("No tours found!");
		}
	}

	@Override
	public String getName() {
		return "FindTourByGuide";
	}

}
