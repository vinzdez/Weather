package app.weather.vicente.com.weatherapp.network.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import app.weather.vicente.com.weatherapp.R;
import app.weather.vicente.com.weatherapp.WeatherApp;
import app.weather.vicente.com.weatherapp.network.APIServiceGenerator;
import app.weather.vicente.com.weatherapp.network.model.City;
import app.weather.vicente.com.weatherapp.network.model.Country;
import app.weather.vicente.com.weatherapp.network.model.Forecast;
import app.weather.vicente.com.weatherapp.network.model.HourlyForecast;
import app.weather.vicente.com.weatherapp.network.model.Result;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Vicente on 2/7/2017.
 */

public class SearchServiceAPIImpl implements SearchServiceAPI {
    public static final String STATE = "state";
    private Context context;

    public SearchServiceAPIImpl() {
        this.context = WeatherApp.getContext().getApplicationContext();
    }

    /**
     * Search A Country or State
     */
    @Override
    public void find(@NonNull final Callback<List<ResultViewModel>> callback, String value) {
        Call<Country> callSearchResult = APIServiceGenerator.getInstance().getApiSearch().findCountry(value.toLowerCase(), 0);
        callSearchResult.enqueue(new retrofit2.Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                List<Result> results = response.body().getResults();
                if (results != null && !results.isEmpty()) {
                    Result result = results.get(0);
                    List<ResultViewModel> resultViewModel = new ArrayList<>();
                    if (STATE.equals(result.getType())) {
                        String url = context.getResources().getString(R.string.single_format_url);
                        url = MessageFormat.format(url, result.getL());
                        findSubCategory(callback, url, result.getType());
                    } else {
                        resultViewModel.add(new ResultViewModel(result.getName(), result.getC(), result.getType()));
                        callback.onLoad(resultViewModel);
                    }
                } else {
                    callback.onLoad(null);
                }
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {
            }
        });
    }

    /**
     * Get A sub Category of a Country or State
     * A Country May not have a sun-category
     */
    @Override
    public void showResult(@NonNull Callback<List<ResultViewModel>> resultViewModelCallback, @NonNull final Callback<HourlyForecast> hourlyForecastCallback, @NonNull String parent, @NonNull String name, String type) {
        String url = context.getResources().getString(R.string.double_format_url);
        url = MessageFormat.format(url, parent.toLowerCase(), name.toLowerCase());
        findSubCategory(resultViewModelCallback, hourlyForecastCallback, url, type);
    }

    /**
     * Get Forecast Detail
     */
    @Override
    public void findForecastDetail(@NonNull final Callback<HourlyForecast> callback, @NonNull String parent, @NonNull String name) {
        String url = context.getResources().getString(R.string.double_format_url);
        url = MessageFormat.format(url, parent.toLowerCase(), name.toLowerCase());
        Call<Forecast> callSearchResult = APIServiceGenerator.getInstance().getApiSearch().getForCast(url);
        callSearchResult.enqueue(new retrofit2.Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                List<HourlyForecast> hourlyForecasts = response.body().getHourly_forecast();
                if (hourlyForecasts != null && !hourlyForecasts.isEmpty()) {
                    callback.onLoad(hourlyForecasts.get(0));
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
            }
        });
    }

    private void findSubCategory(@NonNull final Callback<List<ResultViewModel>> callback, String newUrl, final String type) {
        findSubCategory(callback, null, newUrl, type);
    }

    /**
     * Get the Sub-Category
     */
    private void findSubCategory(@NonNull final Callback<List<ResultViewModel>> resultViewModelCallback, @Nullable final Callback<HourlyForecast> hourlyForecastCallback, final String newUrl, final String type) {
        final List<ResultViewModel> resultViewModelList = new ArrayList<>();
        Call<Forecast> callSearchResult = APIServiceGenerator.getInstance().getApiSearch().findSubCategory(newUrl);
        callSearchResult.enqueue(new retrofit2.Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.body() != null && response.body().getResponse() != null) {
                    List<City> cityList = response.body().getResponse().getResults();
                    if (cityList != null) {
                        for (City city : cityList) {
                            ResultViewModel result = new ResultViewModel(city.getName(), city.getCountry(), type);
                            result.setStateName(city.getState());
                            result.setCategory(true);
                            resultViewModelList.add(result);
                        }
                        resultViewModelCallback.onLoad(resultViewModelList);
                    } else if (hourlyForecastCallback != null) {
                        List<HourlyForecast> hourlyForecasts = response.body().getHourly_forecast();
                        if (hourlyForecasts != null && !hourlyForecasts.isEmpty()) {
                            hourlyForecastCallback.onLoad(hourlyForecasts.get(0));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
            }
        });
    }
}
