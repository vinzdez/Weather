package app.weather.vicente.com.weatherapp.dao.realm;

import android.util.Log;

import app.weather.vicente.com.weatherapp.WeatherApp;
import app.weather.vicente.com.weatherapp.dao.ForecastServiceDaoImpl;
import app.weather.vicente.com.weatherapp.dao.RealmManager;
import app.weather.vicente.com.weatherapp.dao.SearchServiceDaoImpl;

/**
 * Created by Vicente on 2/8/2017.
 */

public class RealmDaoFactory implements DaoFactory {

    private static final String DATABASE_NAME = "weatherAPP.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = RealmDaoFactory.class.getSimpleName();

    private static RealmDaoFactory INSTANCE;
    private RealmManager realmManager;

    private SearchServiceDao searchServiceDao;
    private ForecastServiceDao forecastServiceDao;

    private RealmDaoFactory() {
        this.realmManager = new RealmManager(DATABASE_NAME, DATABASE_VERSION);
    }

    public static DaoFactory getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new RealmDaoFactory();
            } catch (Exception e) {
                String message = "Error initializing database: " + e.toString();
                Log.e(TAG, message);
            }
        }
        return INSTANCE;
    }

    public RealmManager getRealmManager() {
        return realmManager;
    }

    @Override
    public SearchServiceDao getSearchServiceDao() {
        if (searchServiceDao == null) {
            this.searchServiceDao = new SearchServiceDaoImpl(getRealmManager().getRealmInstance());
        }
        return searchServiceDao;
    }

    @Override
    public ForecastServiceDao getForecastServiceDao() {
        if (forecastServiceDao == null) {
            this.forecastServiceDao = new ForecastServiceDaoImpl(getRealmManager().getRealmInstance());
        }
        return forecastServiceDao;
    }
}
