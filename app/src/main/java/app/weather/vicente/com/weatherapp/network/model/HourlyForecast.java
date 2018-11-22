package app.weather.vicente.com.weatherapp.network.model;

/**
 * Created by Vicente on 2/7/2017.
 */

public class HourlyForecast {
    private Temp temp;
    private String icon;
    private String condition;

    public Temp getTemp() {
        return temp;
    }

    public String getCondition() {
        return condition;
    }

    public String getIcon() {
        return icon;
    }
}
