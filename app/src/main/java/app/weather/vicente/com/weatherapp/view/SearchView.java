package app.weather.vicente.com.weatherapp.view;

import java.util.List;

import app.weather.vicente.com.weatherapp.presenter.BaseContract;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;

/**
 * Created by Vicente on 2/7/2017.
 */

public interface SearchView extends BaseContract.BaseView<BaseContract.SearchPresenter> {

    void showResult(List<ResultViewModel> resultViewModels);

    void getSelectedItem(ResultViewModel resultViewModel);

    void showForecastDetail();

}
