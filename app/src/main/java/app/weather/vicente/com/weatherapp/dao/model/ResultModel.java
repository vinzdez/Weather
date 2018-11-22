package app.weather.vicente.com.weatherapp.dao.model;

import java.util.ArrayList;
import java.util.List;

import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Vicente  on 09/02/2017.
 */

public class ResultModel extends RealmObject {

    public ResultModel() {
    }

    private RealmList<ResultListModel> searchResultList;
    private String key;


    public RealmList<ResultListModel> getSearchResultList() {
        if (searchResultList == null) {
            this.searchResultList = new RealmList<>();
        }
        return searchResultList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ResultViewModel> obtainFromResult() {
        List<ResultViewModel> resultList = new ArrayList<>();
        for (ResultListModel result : getSearchResultList()) {
            ResultViewModel resultViewModel = new ResultViewModel(result.getName(), result.getCountry(), result.getType());
            resultViewModel.setStateName(result.getStateName());
            resultList.add(resultViewModel);
        }
        return resultList;
    }
}

