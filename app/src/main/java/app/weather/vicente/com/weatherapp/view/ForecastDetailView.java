package app.weather.vicente.com.weatherapp.view;

import java.util.List;

import app.weather.vicente.com.weatherapp.presenter.BaseContract;
import app.weather.vicente.com.weatherapp.view.model.ForecastViewModel;

/**
 * Created by Vicente  on 08/02/2017.
 */

public interface ForecastDetailView extends BaseContract.BaseView<BaseContract.ForecastDetailPresenter> {

    void onSwitch(int position);

    void updateList(List<ForecastViewModel> forecastViewModels);
}
