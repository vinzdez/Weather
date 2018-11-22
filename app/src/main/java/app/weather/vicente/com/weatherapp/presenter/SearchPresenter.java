package app.weather.vicente.com.weatherapp.presenter;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.weather.vicente.com.weatherapp.network.model.HourlyForecast;
import app.weather.vicente.com.weatherapp.service.SearchService;
import app.weather.vicente.com.weatherapp.service.SearchServiceImpl;
import app.weather.vicente.com.weatherapp.view.SearchView;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;

/**
 * Created by Vicente on 2/7/2017.
 */

public class SearchPresenter implements BaseContract.SearchPresenter {

    private static final String PATTERN = "^[a-zA-Z ]+$";

    private SearchView searchView;
    private SearchService searchService;

    public SearchPresenter(@NonNull SearchView searchView) {
        this.searchView = searchView;
        this.searchService = new SearchServiceImpl();
        //set this presenter to view
        searchView.setPresenter(this);
    }


    @Override
    public void onSearch(@NonNull String value) {
        searchService.find(new SearchService.SearchResultCallback() {
            @Override
            public void onResultLoaded(List<ResultViewModel> resultViewModelList, HourlyForecast forecast) {
                searchView.showResult(resultViewModelList);
            }
        }, value);
    }

    @Override
    public void findForcast(ResultViewModel resultViewModel) {
        if (resultViewModel.isState()) {
            showResult(resultViewModel.getName(), resultViewModel.getStateName(), resultViewModel.getType());
        } else {
            showResult(resultViewModel.getName(), resultViewModel.getCountry(), resultViewModel.getType());
        }
    }

    @Override
    public boolean isUserInputValid(String value) {
        Pattern ps = Pattern.compile(PATTERN);
        Matcher ms = ps.matcher(value);
        return ms.matches();
    }

    private void showResult(final String name, String parent, String type) {
        searchService.showResult(new SearchService.SearchResultCallback() {
            @Override
            public void onResultLoaded(List<ResultViewModel> resultViewModelList, HourlyForecast forecast) {
                if (resultViewModelList != null && !resultViewModelList.isEmpty()) {
                    searchView.showResult(resultViewModelList);
                } else {
                    searchView.showForecastDetail();
                }

            }
        }, parent, name, type);
    }

}
