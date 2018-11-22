package app.weather.vicente.com.weatherapp.dao;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import app.weather.vicente.com.weatherapp.dao.model.ForecastDetailModel;
import app.weather.vicente.com.weatherapp.dao.realm.ForecastServiceDao;
import app.weather.vicente.com.weatherapp.view.model.ForecastViewModel;
import io.realm.Realm;

/**
 * Created by Vicente on 2/9/2017.
 */

public class ForecastServiceDaoImpl implements ForecastServiceDao {

    private Realm realm;

    public ForecastServiceDaoImpl(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void getAllForecast(ForecastServiceDao.ForecastFetchCallback callback) {
        List<ForecastViewModel> forecastViewModels = new ArrayList<>();
        List<ForecastDetailModel> forecastDetailModelList = realm.where(ForecastDetailModel.class).findAll();
        for (ForecastDetailModel forecastDetailModel : forecastDetailModelList) {
            forecastViewModels.add(new ForecastViewModel(forecastDetailModel.getTempF(), forecastDetailModel.getTempC(), forecastDetailModel.getIcon(), forecastDetailModel.getCondition(), forecastDetailModel.getName(), forecastDetailModel.getParent()));
        }
        callback.onLoad(forecastViewModels);
    }

    @Override
    public void delete(@NonNull final ForecastDeleteCallback forecastDeleteCallback, final String value) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ForecastDetailModel forecastViewModel = realm.where(ForecastDetailModel.class).equalTo("name", value).findFirst();
                forecastViewModel.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                forecastDeleteCallback.onLoad();
            }
        });
    }
}
