package com.Actions.ClientTourActions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.Actions.Action;
import com.Utils.ValidUtil;
import com.View.ConsoleView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

import lombok.AllArgsConstructor;

//ZEPSUTE NIE DZIA³A

@AllArgsConstructor
public class AddClientToTourAction implements Action {

	private ConsoleView cv;
	private RethinkDB r;
	private Connection conn;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void launch() {
		String idStringTour = getValidInt("Podaj ID wycieczki");
		if ("".equals(idStringTour)) {
			return;
		}
		int idTour = Integer.parseInt(idStringTour);

		String idStringClient = getValidInt("Podaj ID klienta");
		if ("".equals(idStringClient)) {
			return;
		}
		int idClient = Integer.parseInt(idStringClient);

		Cursor client = r.table("clients").filter(row -> row.g("id").eq(idClient)).run(conn);
		// Object c = null;
//		for(Object doc : client) {
//			c = doc;
//		}
		List clientsSet = new ArrayList();
		Iterator c = client.iterator();
		while (c.hasNext()) {
			clientsSet.add(c.next());
		}

		cv.print(clientsSet.toArray());

		Cursor tour = r.table("tours").filter(row -> row.g("id").eq(idTour)).filter(row -> row.g("finished").eq(false))
				.run(conn);

		cv.print(tour);

		// cv.print(Arrays.asList(tour).toArray());

//		r.table("tours").filter(row -> row.g("id").eq(idTour).and(row.g("finished").eq(false)))
//				.update(row -> row.g("clients").append(c)).run(conn);

		Object run = r.table("tours").filter(row -> row.g("id").eq(idTour)).filter(row -> row.g("finished").eq(false))
				.update(

						r.hashMap("abc", arrayFrom(clientsSet))

				).run(conn);

		System.out.println(run);

//		r.table("torus").filter(row -> row.g("id").eq(idTour)
//				.and(row.g("finished").eq(false)))
//				.update(row -> row.g("clients").append(r.hashMap("client", client))).run(conn);	

		// r.table("tours").get(idStringTour).upda

//		r.table("tours").filter(row -> row.g("id").eq(idTour))
//			.update(row2 -> row2.g("clients").add(client));

		// r.table("tours").get(idStringTour).getField("clients").append(client);
		// r.table("tours").get(idTour).update(r.hashMap("clients", ));

		// r.table("tours").get(idTour).g("clients").append().run(conn);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object arrayFrom(List clientList) {
		LinkedTreeMap<String, String> fromJson = null;
		
		for(Object o : clientList) {
			System.out.println(o);
			fromJson = (LinkedTreeMap<String, String>) fromJson(o.toString(), Object.class);
		}
		
		List<MapObject> objs = new ArrayList<MapObject>();
		
		for( java.util.Map.Entry<String, String> e : fromJson.entrySet()) {
			objs.add(r.hashMap(e.getKey(), e.getValue()));
		}
		
		
		return 
				
				r.array(
				
//				r.hashMap("title", "qewqeqwe").with("content", "ddfsdsdggaggang over..."),
//				r.hashMap("title", "dsdfsfr").with("content", "Moments ago, thiasdfaweferfasfs ship received..."),
//				r.hashMap("title", "The newfeasfeasfas fEarth").with("content","The discoveriesaffesafesafes of the past few days...")
						from(objs)
						
						
						);
				
				
	}

	private Object from(List<MapObject> objs) {
		if(objs.size()==2) {
			//return objs.get(0).with(objs.get, objs);
		}
		
		if(objs.size()==3) {
			return objs.get(0).with(objs.get(1), objs).with(objs.get(2), objs);
		}
		return objs;
	}

	private Object fromJson(String json, Class<?> type) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(json);
        return gson.fromJson(object, type);
    }
	
	private String getValidInt(String msg) {
		String line;
		do {
			cv.print(msg);
			line = cv.read();
			if ("".equals(line)) {
				break;
			}
		} while (!ValidUtil.isIntInstance(line));
		return line;
	}

	@Override
	public String getName() {
		return "AddClientToTour";
	}

}
