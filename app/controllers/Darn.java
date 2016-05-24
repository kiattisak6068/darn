package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.*;
import java.util.*;
import models.*;

import java.util.stream.Collectors;
import views.html.*;

import com.fasterxml.jackson.databind.*;

public class Darn extends Controller {

		public Result get() {
			List<DarnInfo> darns = DarnInfo.find.all();
			JsonNode json = Json.toJson(darns);
			return ok(Json.prettyPrint(json));
		}

	public Result getDarn(Integer id) {
		DarnInfo darn = DarnInfo.find.byId(id); 
		if (darn == null) {
			return badRequest("no darn id: "+id);
		}
		return ok(Json.toJson(darn));
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Result updateDarn(Integer id) {
		JsonNode json;
		try {
			json = request().body().asJson();
			DarnInfo darnPost = Json.fromJson(json, DarnInfo.class);
			DarnInfo darnInDB =  DarnInfo.find.byId(id);
			if (darnPost.id == darnInDB.id) {
				darnInDB.lat = darnPost.lat;
				darnInDB.lng = darnPost.lng;
				darnInDB.name = darnPost.name;
				darnInDB.update();
				return ok(Json.toJson(darnInDB));
			}
		} catch (Exception ex) {
			return badRequest("Missing parameter not DarnInfo object");
		}
		return ok("fail request");

	}

	public Result deleteDarn(Integer id) {
		DarnInfo darn = DarnInfo.find.byId(id);
		if (darn != null) {
			darn.delete();
			return ok(Json.toJson(darn));
		}
		return badRequest("no darn id: "+id);
	}


	// /reportdarn/lat,long
		public Result save(String latlong) {
			String[] a = latlong.split(",");
			DarnInfo darn = new DarnInfo();
			darn.id = nextId();
			darn.lat = a[0];
			darn.lng = a[1];
			darn.name = a[2];
			darn.save();
			return ok(Json.toJson(darn));
		}

	/**
	 *  url: /reportdarn
	 *  request method: POST
	 *  request body: application/json
	 *  {
	 *      "lat": "15.134",
	 *      "long": "1.00",
	 *      "name": "ต.เมืองศรีไค"
	 *  }
	 *
 	 */
	@BodyParser.Of(BodyParser.Json.class)
	public Result savePost() {

		JsonNode json;
		try {
			json = request().body().asJson();
			DarnInfo darn = Json.fromJson(json, DarnInfo.class);
			darn.id = nextId();
			darn.save();
		} catch (Exception ex) {
			return badRequest("Missing parameter not DarnInfo object");
		}
		return ok("added: \n" + Json.prettyPrint(json));

	}

	public Result saveForm() {
//		DynamicForm form = Form.form().bindFromRequest();
//		try {
//			if (!form.hasErrors()) {
//				JsonNode json = Json.toJson(form.data());
//				Logger.info(Json.prettyPrint(json));
//				DarnInfo darn = Json.fromJson(json, DarnInfo.class);
//				darn.id = nextId();
//				darn.save();
//			}
//		} catch (Exception ex) {
//			Logger.error(ex.toString());
//		}
		Form<DarnInfo> form = Form.form(DarnInfo.class).bindFromRequest();
		try {
			DarnInfo darn = form.get();
			if (!darn.lat.isEmpty() && !darn.lng.isEmpty()) {
				darn.id = nextId();
				darn.save();
			}
		} catch (Exception ex) {

		}
		return ok(views.html.addDarn.render(form));
	}

	public Integer nextId() {
		List<DarnInfo> list =  DarnInfo.find.where()
				.orderBy("id desc")
				.setMaxRows(1)
				.findList();
		return (list.isEmpty())?1:list.get(0).id+1;
	}


}
