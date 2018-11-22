package app.weather.vicente.com.weatherapp.dao.realm;

/**
 * Created by Vicente on 2/8/2017.
 */

public interface DaoFactory {

    SearchServiceDao getSearchServiceDao();

    ForecastServiceDao getForecastServiceDao();
}
