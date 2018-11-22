package app.weather.vicente.com.weatherapp.service;

import android.support.annotation.NonNull;

import java.util.List;

import app.weather.vicente.com.weatherapp.view.model.ForecastViewModel;

/**
 * Created by Vicente on 2/9/2017.
 */

public interface ForecastService {
    interface ForecastServiceCallback {
        void onSuccess();
    }

    interface ForecastCallback {
        void onSuccess(List<ForecastViewModel> list);
    }

    /**
     * Get all Forecast
     */
    void getAllForecast(@NonNull ForecastCallback callback);

    /**
     * Delete
     */
    void delete(@NonNull ForecastServiceCallback forecastServiceCallback, String value);

    /**
     * Since I will be using a developer account on wunderground
     * I will be  updating the top 5 forecast save from db
     * Wunderground will only provide 10 calls per minute
     */
    void updateForecast();
}
