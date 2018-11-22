package app.weather.vicente.com.weatherapp.dao.realm;

import android.support.annotation.NonNull;

import java.util.List;

import app.weather.vicente.com.weatherapp.view.model.ForecastViewModel;

/**
 * Created by Vicente on 2/9/2017.
 */

public interface ForecastServiceDao {

    interface ForecastDeleteCallback {
        void onLoad();
    }
    interface ForecastFetchCallback {

        void onLoad(List<ForecastViewModel> list);
    }

    /**
     * Get all Forecast
     */
    void getAllForecast(@NonNull ForecastServiceDao.ForecastFetchCallback callback);

    /**
     * Delete
     */
    void delete(@NonNull ForecastDeleteCallback forecastDeleteCallback, String value);

}
