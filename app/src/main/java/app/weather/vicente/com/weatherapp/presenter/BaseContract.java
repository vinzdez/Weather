package app.weather.vicente.com.weatherapp.presenter;

import android.graphics.drawable.Drawable;

import java.util.List;

import app.weather.vicente.com.weatherapp.view.model.ForecastViewModel;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;

/**
 * Created by Vicente on 2/7/2017.
 */

public interface BaseContract {

    interface BaseView<T> {
        void setPresenter(T presenter);
    }


    interface SearchPresenter {

        void onSearch(String value);

        void findForcast(ResultViewModel resultViewModel);

        boolean isUserInputValid(String value);
    }

    interface ForecastDetailPresenter {

        Drawable getIcon(String icon);

        void deleteForecast(String value);

        void getAllForeCast();

        void updateForecast();
    }
}
