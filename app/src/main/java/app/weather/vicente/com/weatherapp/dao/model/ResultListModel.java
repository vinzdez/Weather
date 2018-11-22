package app.weather.vicente.com.weatherapp.dao.model;

import io.realm.RealmObject;

/**
 * Created by Vicente on 09/02/2017.
 */

public class ResultListModel extends RealmObject {

    public ResultListModel() {
    }

    private String country;
    private String name;

    private String type;
    private String stateName;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
