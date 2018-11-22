package app.weather.vicente.com.weatherapp.view.model;

import static app.weather.vicente.com.weatherapp.network.service.SearchServiceAPIImpl.STATE;

/**
 * Created by Vicente on 2/7/2017.
 */

public class ResultViewModel {

    private String country;
    private String name;

    private String type;
    private String stateName;
    private boolean isCategory;

    public ResultViewModel(String name, String country, String type) {
        this.name = name;
        this.country = country;
        this.type = type;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public boolean isState() {
        return STATE.equals(getType());
    }

    public String getType() {
        return type;
    }

    public boolean isCategory() {
        return isCategory;
    }

    public void setCategory(boolean category) {
        this.isCategory = category;
    }
}
