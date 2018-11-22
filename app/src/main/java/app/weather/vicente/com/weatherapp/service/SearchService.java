package app.weather.vicente.com.weatherapp.service;

import android.support.annotation.NonNull;

import java.util.List;

import app.weather.vicente.com.weatherapp.network.model.HourlyForecast;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;

/**
 * Created by Vicente on 2/7/2017.
 */

public interface SearchService {


    interface SearchResultCallback {
        void onResultLoaded(List<ResultViewModel> resultViewModelList, HourlyForecast forecast);
    }

    interface SearchDetailCallback {
        void onResultLoaded();
    }

    /**
     * Search A Country or State
     */
    void find(@NonNull SearchResultCallback callback, String search);

    /**
     * Get A sub Category of a Country or State
     */
    void showResult(@NonNull SearchResultCallback callback, @NonNull String parent, @NonNull String name, @NonNull String type);

    /**
     * Get Forecast Detail
     */
    void findForecastDetail(@NonNull SearchDetailCallback callback, @NonNull String parent, @NonNull String name);

}
