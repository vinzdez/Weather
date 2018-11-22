package app.weather.vicente.com.weatherapp.network.service;

import android.support.annotation.NonNull;

import java.util.List;

import app.weather.vicente.com.weatherapp.network.model.Forecast;
import app.weather.vicente.com.weatherapp.network.model.HourlyForecast;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;

/**
 * Created by Vicente on 2/7/2017.
 */

public interface SearchServiceAPI {

    interface Callback<T> {
        void onLoad(T callback);
    }

    /**
     * Search A Country or State
     */
    void find(@NonNull Callback<List<ResultViewModel>> callback, String value);

    /**
     * Get A sub Category of a Country or State
     * A Country May not have a sun-category
     */
    void showResult(@NonNull Callback<List<ResultViewModel>> resultViewModelCallback, @NonNull final Callback<HourlyForecast> hourlyForecastCallback , @NonNull String parent , @NonNull String name , @NonNull String type);

    /**
     * Get Forecast Detail
     */
    void findForecastDetail(@NonNull Callback<HourlyForecast> callback , @NonNull String parent , @NonNull String name);
}
