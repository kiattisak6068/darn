package models;

import javax.persistence.Id;
import javax.persistence.Entity;
import com.avaje.ebean.Model;

/**
 * Created by Kanapan on 5/14/2016.
 */
@Entity
public class DarnInfo extends Model {
    @Id
    public Integer id;
    public String lat;
    public String lng;
    public String name;

    public static Model.Finder<Integer, DarnInfo> find =
            new Model.Finder<>(DarnInfo.class);
}
