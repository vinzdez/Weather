package app.weather.vicente.com.weatherapp.service;

import android.support.annotation.NonNull;

import java.util.List;

import app.weather.vicente.com.weatherapp.dao.realm.ForecastServiceDao;
import app.weather.vicente.com.weatherapp.dao.realm.RealmDaoFactory;
import app.weather.vicente.com.weatherapp.view.model.ForecastViewModel;

/**
 * Created by Vicente on 2/9/2017.
 */

public class ForecastServiceImpl implements ForecastService {

    private static final int MAX_COUNT = 4;

    private ForecastServiceDao forecastServiceDao;
    private SearchService searchService;

    public ForecastServiceImpl() {
        this.forecastServiceDao = RealmDaoFactory.getInstance().getForecastServiceDao();
        this.searchService = new SearchServiceImpl();
    }


    @Override
    public void getAllForecast(@NonNull final ForecastCallback callback) {
        forecastServiceDao.getAllForecast(new ForecastServiceDao.ForecastFetchCallback() {
            @Override
            public void onLoad(List<ForecastViewModel> list) {
                callback.onSuccess(list);
            }
        });

    }

    @Override
    public void delete(@NonNull final ForecastServiceCallback forecastServiceCallback, String value) {
        forecastServiceDao.delete(new ForecastServiceDao.ForecastDeleteCallback() {
            @Override
            public void onLoad() {
                forecastServiceCallback.onSuccess();
            }
        }, value);
    }

    /**
     * Since I will be using a developer account on wunderground
     * I will be  updating the top 5 forecast save from db
     * Wunderground will only provide 10 calls per minute
     */
    @Override
    public void updateForecast() {
        forecastServiceDao.getAllForecast(new ForecastServiceDao.ForecastFetchCallback() {
            @Override
            public void onLoad(List<ForecastViewModel> list) {
                int size = list.size() > MAX_COUNT ? MAX_COUNT : list.size();
                for (int i = 0; i < size; i++) {
                    ForecastViewModel forcastViewModel = list.get(i);
                    searchService.findForecastDetail(new SearchService.SearchDetailCallback() {
                        @Override
                        public void onResultLoaded() {
                        }
                    }, forcastViewModel.getParent(), forcastViewModel.getName());
                }
            }
        });
    }
}
