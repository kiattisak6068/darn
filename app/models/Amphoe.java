package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Amphoe extends Model {

	@Id
	public Integer id;
	public String  th; // ชื่อภาษาอังกฤษ
	public String  en; // ชื่อโรมัน

	@ManyToOne(cascade = CascadeType.ALL)
	public Province province;

	@Override
	public String toString() {
		return ""+id+", "+th+", "+en;
	}

	public boolean contains(String term) {
		if (term.equals(""+id)) {
			return true;
		}
		if (th.contains(term) || en.contains(term)) {
			return true;
		}
		return false;
	}

	public static Model.Finder<Integer, Amphoe> find = new Model.Finder<>(Amphoe.class);
}
