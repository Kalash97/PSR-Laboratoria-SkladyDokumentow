package com.Actions.ClientActions;

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
public class DeleteClientByIdAction implements Action{

	private MongoCollection<Document> clients;
	private MongoCollection<Document> tours;
	private ConsoleView cv;
	
	@Override
	public void launch() {
		String idString = getValidInt("Podaj ID");
		int id = Integer.parseInt(idString);
		
		Document client = clients.find(eq("_id", id)).first();
		
		tours.updateMany(and(eq("finished", false), eq("clients", client)), pull("clients", client));
		
		cv.print("Deleting client where id = "+id);
		clients.deleteOne(eq("_id", id));
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
		return "DeleteClientById";
	}

}
