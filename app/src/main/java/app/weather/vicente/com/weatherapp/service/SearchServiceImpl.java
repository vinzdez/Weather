package app.weather.vicente.com.weatherapp.service;

import android.support.annotation.NonNull;

import java.util.List;

import app.weather.vicente.com.weatherapp.dao.model.ResultModel;
import app.weather.vicente.com.weatherapp.dao.realm.RealmDaoFactory;
import app.weather.vicente.com.weatherapp.dao.realm.SearchServiceDao;
import app.weather.vicente.com.weatherapp.network.model.HourlyForecast;
import app.weather.vicente.com.weatherapp.network.service.SearchServiceAPI;
import app.weather.vicente.com.weatherapp.network.service.SearchServiceAPIImpl;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;

/**
 * Created by Vicente on 2/7/2017.
 */

public class SearchServiceImpl implements SearchService {

    private SearchServiceAPIImpl searchServiceApi;
    private SearchServiceDao searchServiceDao;

    public SearchServiceImpl() {
        this.searchServiceDao = RealmDaoFactory.getInstance().getSearchServiceDao();
        this.searchServiceApi = new SearchServiceAPIImpl();
    }

    /**
     * Search A Country or State
     */
    @Override
    public void find(@NonNull final SearchResultCallback callback, final String search) {

        //DB
        List<ResultViewModel> resultViewModelList = searchServiceDao.find(search);
        if (resultViewModelList != null && !resultViewModelList.isEmpty()) {
            callback.onResultLoaded(resultViewModelList, null);
        } else {
            //NETWORK
            searchServiceApi.find(new SearchServiceAPI.Callback<List<ResultViewModel>>() {
                @Override
                public void onLoad(List<ResultViewModel> resultList) {
                    if (resultList != null) {
                        searchServiceDao.save(resultList);
                        callback.onResultLoaded(resultList, null);
                    } else {
                        callback.onResultLoaded(resultList, null);
                    }

                }
            }, search);
        }

    }

    @Override
    public void showResult(@NonNull final SearchResultCallback callback, @NonNull final String parent, @NonNull final String name, @NonNull String type) {
        //Network
        final ResultModel resultModel = searchServiceDao.search(name);
        searchServiceApi.showResult(new SearchServiceAPI.Callback<List<ResultViewModel>>() {
            @Override
            public void onLoad(List<ResultViewModel> resultList) {
                if (resultModel != null) {
                    searchServiceDao.update(resultList, resultModel);
                }
                callback.onResultLoaded(resultList, null);
            }
        }, new SearchServiceAPI.Callback<HourlyForecast>() {
            @Override
            public void onLoad(HourlyForecast hourlyForecast) {
                searchServiceDao.createBaseForecastDetail(hourlyForecast, name, parent);
                callback.onResultLoaded(null, hourlyForecast);
            }
        }, parent, name, type);

    }

    @Override
    public void findForecastDetail(@NonNull final SearchDetailCallback callback, @NonNull final String parent, @NonNull final String name) {
        //Network
        searchServiceApi.findForecastDetail(new SearchServiceAPI.Callback<HourlyForecast>() {
            @Override
            public void onLoad(HourlyForecast hourlyForecast) {
                searchServiceDao.createBaseForecastDetail(hourlyForecast, name, parent);
                callback.onResultLoaded();
            }
        }, parent, name);
    }
}
