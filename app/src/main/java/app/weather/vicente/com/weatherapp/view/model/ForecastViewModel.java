package app.weather.vicente.com.weatherapp.view.model;

/**
 * Created by Vicente on 2/9/2017.
 */

public class ForecastViewModel {

    private String tempF;
    private String tempC;
    private String icon;
    private String condition;

    private String name;
    private String parent;

    public ForecastViewModel(String tempF, String tempC, String icon, String condition, String name, String parent) {
        this.tempF = tempF;
        this.tempC = tempC;
        this.icon = icon;
        this.condition = condition;
        this.name = name;
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
