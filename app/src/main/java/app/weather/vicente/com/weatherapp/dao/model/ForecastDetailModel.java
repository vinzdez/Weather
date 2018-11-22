package app.weather.vicente.com.weatherapp.dao.model;

import io.realm.RealmObject;

/**
 * Created by Vicente on 09/02/2017.
 */

public class ForecastDetailModel extends RealmObject {

    private String tempF;
    private String tempC;
    private String icon;
    private String condition;

    private String name;
    private String parent;


    public ForecastDetailModel() {
    }

    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
