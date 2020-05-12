package com.Actions.ClientTourActions;

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
public class RemoveClientFromTourAction implements Action{

	private MongoCollection<Document> tours;
	private MongoCollection<Document> clients;
	private ConsoleView cv;
	
	@Override
	public void launch() {
		String idStringTour = getValidInt("Podaj ID wycieczki");
		if("".equals(idStringTour)) {
			return;
		}
		int idTour = Integer.parseInt(idStringTour);
		Document tour = tours.find(eq("_id", idTour)).first();
		if(tour==null) {
			cv.print("Nie ma takiej wycieczki");
			return;
		}
		
		String idStringClient = getValidInt("Podaj ID klienta");
		if("".equals(idStringClient)) {
			return;
		}
		int idClient = Integer.parseInt(idStringClient);
		Document client = clients.find(eq("_id", idClient)).first();
		if(client==null) {
			cv.print("Nie ma takiego klienta");
			return;
		}
		
		tours.updateOne(and(eq("_id", idTour), eq("finished", false)), pull("clients", client));
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
		return "RemoveClientFromTour";
	}

}
