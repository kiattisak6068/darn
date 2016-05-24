package controllers;

import play.mvc.*;
import play.libs.*;
import java.util.*;
import models.*;

import java.util.stream.Collectors;
import views.html.*;

import com.fasterxml.jackson.databind.*;

public class ApiController extends Controller {

		//////////////////////// PROVINCE
		public Result provinces() {
			JsonNode json = Json.toJson(models.Province.find.all());
			return ok(Json.prettyPrint(json));
		}

		public Result searchProvince(String term) {
//			List<Province> result = new ArrayList<>();
//			for (Province p : Province.find.all()) {
//				if (p.contains(term)) {
//					result.add(p);
//				}
//			}
			List<Province> result = Province.find.all().stream()
					.filter(p->p.contains(term))
					.collect(Collectors.toList());

			return ok(Json.prettyPrint(Json.toJson(result)));
		}

		@BodyParser.Of(BodyParser.Json.class)
		public Result updateProvince() {
			JsonNode json;
			try {
				json = request().body().asJson();
				Province p = Json.fromJson(json, Province.class);
				//String name = json.findPath("id").textValue();
				p.save();
			} catch (Exception ex) {
				return badRequest("Missing parameter [name]");
			}
			return ok("added: \n" + Json.prettyPrint(json));
		}		

		//////////////////////// AMPHOE
		public Result amphoes() {
			JsonNode json = Json.toJson(models.Amphoe.find.all());
			return ok(Json.prettyPrint(json));
		}

		public Result searchAmphoe(String term) {
			List<Amphoe> result = new ArrayList<>();
			for (Amphoe p : Amphoe.find.all()) {
				if (p.contains(term)) {
					result.add(p);
				}
			}
			return ok(Json.prettyPrint(Json.toJson(result)));
		}

		@BodyParser.Of(BodyParser.Json.class)
		public Result updateAmphoe() {
			JsonNode json;
			try {
				json = request().body().asJson();
				Amphoe p = Json.fromJson(json, Amphoe.class);
				//String name = json.findPath("id").textValue();
				p.save();
			} catch (Exception ex) {
				return badRequest("Missing parameter [name]");
			}
			return ok("added: \n" + Json.prettyPrint(json));
		}		

    ///////////////// Utility
		@BodyParser.Of(BodyParser.Json.class)
		public Result listAmphoes() {
			JsonNode json;
			try {
				json = request().body().asJson();
				List<Amphoe> amphoes = Amphoe.find.select("*").where().eq("province.id", json.findPath("id").textValue()).findList();
				return ok(Json.prettyPrint(Json.toJson(amphoes)));
			} catch (Exception ex) {
			}
			return badRequest("Missing parameter [name]");
		}
}
