package app.weather.vicente.com.weatherapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import app.weather.vicente.com.weatherapp.R;
import app.weather.vicente.com.weatherapp.presenter.SearchPresenter;
import app.weather.vicente.com.weatherapp.util.ActivityUtil;


/**
 * Created by Vicente on 2/7/2017.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        getWindow().setBackgroundDrawableResource(ActivityUtil.getBackgroundColor());
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
        } else {
            finish();
        }
    }

    private void initFragment() {
        SearchFragment searchFragment = getListFregment();

        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), searchFragment, R.id.contentFrame);
        }
        SearchPresenter searchPresenter = new SearchPresenter(searchFragment);


    }

    @Nullable
    private SearchFragment getListFregment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment instanceof SearchFragment) {
            return (SearchFragment) fragment;
        }
        return null;
    }
}
