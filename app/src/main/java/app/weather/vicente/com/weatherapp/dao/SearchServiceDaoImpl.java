package app.weather.vicente.com.weatherapp.dao;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import app.weather.vicente.com.weatherapp.dao.model.ForecastDetailModel;
import app.weather.vicente.com.weatherapp.dao.model.ResultListModel;
import app.weather.vicente.com.weatherapp.dao.model.ResultModel;
import app.weather.vicente.com.weatherapp.dao.realm.SearchServiceDao;
import app.weather.vicente.com.weatherapp.network.model.HourlyForecast;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;
import io.realm.Case;
import io.realm.Realm;

/**
 * Created by Vicente on 2/8/2017.
 */

public class SearchServiceDaoImpl implements SearchServiceDao {

    private Realm realm;

    public SearchServiceDaoImpl(Realm realm) {
        this.realm = realm;
    }


    @Override
    public List<ResultViewModel> find(@NonNull String key) {
        List<ResultViewModel> resultViewModels = new ArrayList<>();
        ResultModel resultModel = search(key);
        if (resultModel != null) {
            resultViewModels.addAll(resultModel.obtainFromResult());
        }
        return resultViewModels;
    }

    @Override
    public ResultModel search(@NonNull String key) {
        return realm.where(ResultModel.class).contains("key", key, Case.INSENSITIVE).findFirst();
    }

    @Override
    public void save(final List<ResultViewModel> resultViewModelList) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ResultModel resultModel = realm.createObject(ResultModel.class);
                List<ResultListModel> searchResultList = new ArrayList<>();
                String key = null;
                for (ResultViewModel resultViewModel : resultViewModelList) {
                    ResultListModel resultListModel = realm.createObject(ResultListModel.class);
                    resultListModel.setName(resultViewModel.getName());
                    key = resultViewModel.getName();
                    resultListModel.setStateName(resultViewModel.getStateName());
                    resultListModel.setCountry(resultViewModel.getCountry());
                    resultListModel.setType(resultViewModel.getType());
                    searchResultList.add(resultListModel);
                }
                resultModel.setKey(key);
                resultModel.getSearchResultList().addAll(searchResultList);
            }
        });
    }

    @Override
    public void update(final List<ResultViewModel> resultViewModelList, final ResultModel resultModelRealm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                resultModelRealm.getSearchResultList().clear();
                for (ResultViewModel result : resultViewModelList) {
                    ResultListModel resultModel = new ResultListModel();
                    resultModel.setType(result.getType());
                    resultModel.setCountry(result.getCountry());
                    resultModel.setStateName(result.getStateName());
                    resultModel.setName(result.getName());
                    resultModelRealm.getSearchResultList().add(resultModel);
                }
            }
        });
    }

    @Override
    public void createBaseForecastDetail(final HourlyForecast hourlyForecast, final String name, final String parent) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ForecastDetailModel forecastModel = realm.where(ForecastDetailModel.class).contains("name", name, Case.INSENSITIVE).findFirst();
                if (forecastModel == null) {
                    forecastModel = realm.createObject(ForecastDetailModel.class);
                    forecastModel.setName(name);
                    forecastModel.setParent(parent);
                    forecastModel.setTempC(hourlyForecast.getTemp().getMetric());
                    forecastModel.setTempF(hourlyForecast.getTemp().getEnglish());
                    forecastModel.setIcon(hourlyForecast.getIcon());
                    forecastModel.setCondition(hourlyForecast.getCondition());
                } else {
                    forecastModel.setTempC(hourlyForecast.getTemp().getMetric());
                    forecastModel.setTempF(hourlyForecast.getTemp().getEnglish());
                    forecastModel.setIcon(hourlyForecast.getIcon());
                    forecastModel.setCondition(hourlyForecast.getCondition());
                }
            }
        });
    }
}
