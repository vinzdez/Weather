package app.weather.vicente.com.weatherapp.dao.realm;

import android.support.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import app.weather.vicente.com.weatherapp.dao.model.ResultModel;
import app.weather.vicente.com.weatherapp.network.model.HourlyForecast;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;

/**
 * Created by Vicente on 2/8/2017.
 */

public interface SearchServiceDao {

    /**
     * @param key - user input , Country / State
     * @throws SQLException
     */
    List<ResultViewModel> find(@NonNull String key);


    /**
     * @param key - user input , Country / State
     * @throws SQLException
     */
    ResultModel search(@NonNull String key);

    /**
     * Create or Update
     *
     * @throws SQLException
     */
    void save(List<ResultViewModel> resultViewModelList);

    void update(List<ResultViewModel> resultViewModelList, ResultModel resultMode);

    /**
     * Create Country forecast Detail
     */
    void createBaseForecastDetail(HourlyForecast hourlyForecast, String name, String country);


}
