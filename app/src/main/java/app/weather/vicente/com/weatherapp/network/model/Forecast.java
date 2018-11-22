package app.weather.vicente.com.weatherapp.network.model;

import java.util.List;

/**
 * Created by Vicente on 2/7/2017.
 */

public class Forecast {

    private Response response;
    private List<HourlyForecast> hourly_forecast;
    
    public Response getResponse() {
        return response;
    }


    public List<HourlyForecast> getHourly_forecast() {
        return hourly_forecast;
    }
}
