package app.weather.vicente.com.weatherapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.weather.vicente.com.weatherapp.R;
import app.weather.vicente.com.weatherapp.adapter.ListAdapter;
import app.weather.vicente.com.weatherapp.presenter.BaseContract;
import app.weather.vicente.com.weatherapp.presenter.ForecastDetailPresenter;
import app.weather.vicente.com.weatherapp.util.ActivityUtil;
import app.weather.vicente.com.weatherapp.view.SearchView;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Vicente on 2/7/2017.
 */

public class SearchFragment extends Fragment implements SearchView {

    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.id_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.pb_progress)
    ProgressBar progressBar;

    private View view;
    private Context context;
    private ListAdapter listAdapter;
    private BaseContract.SearchPresenter searchPresenter;

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            this.context = getActivity();
            this.view = inflater.inflate(R.layout.fragment_search, container, false);
            ButterKnife.bind(this, view);
            this.listAdapter = new ListAdapter(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(listAdapter);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(etSearch.getText())) {
            ivCancel.setVisibility(View.INVISIBLE);
        } else {
            ivCancel.setVisibility(View.VISIBLE);
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(BaseContract.SearchPresenter presenter) {
        this.searchPresenter = presenter;
    }

    @Override
    public void showResult(List<ResultViewModel> results) {
        progressBar.setVisibility(View.GONE);
        ActivityUtil.hideSoftKeyboard(getActivity());
        listAdapter.clearItems();

        if (results != null) {
            listAdapter.getSearchResultViewModels().addAll(results);
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void getSelectedItem(ResultViewModel resultViewModel) {
        progressBar.setVisibility(View.VISIBLE);
        searchPresenter.findForcast(resultViewModel);
    }

    @Override
    public void showForecastDetail() {
        reset();
        ForecastDetailFragment forecastDetailFragment = ForecastDetailFragment.newInstance();
        ActivityUtil.addFragmentToActivity(getFragmentManager(), forecastDetailFragment, R.id.contentFrame);
        ForecastDetailPresenter forecastDetailPresenter = new ForecastDetailPresenter(forecastDetailFragment, context);
    }

    private void reset() {
        timer.cancel();
        progressBar.setVisibility(View.GONE);
        cancelSearch();
    }

    @OnClick(R.id.iv_cancel)
    public void cancelSearch() {
        listAdapter.clearItems();
        listAdapter.notifyDataSetChanged();
        etSearch.getText().clear();
    }

    /**
     * When text is changed the timer is starting to wait for any next changes to happen.
     * When they occure timer is cancelled and then started once again.
     */
    @OnTextChanged(value = R.id.et_search,
            callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChange() {
        progressBar.setVisibility(View.VISIBLE);
        if (timer != null) {
            timer.cancel();
        }
    }

    @OnTextChanged(value = R.id.et_search,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChange() {
        if (!TextUtils.isEmpty(etSearch.getText())) {
            final String value = etSearch.getText().toString();
            if (searchPresenter.isUserInputValid(value)) {
                etSearch.setError(null);
                runTimer();
            } else {
                progressBar.setVisibility(View.GONE);
                etSearch.setError(getString(R.string.invalid_message));
            }
        } else {
            progressBar.setVisibility(View.GONE);
            listAdapter.clearItems();
            listAdapter.notifyDataSetChanged();
        }
    }

    private void runTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                searchUserInput();
            }
        }, DELAY);
    }

    private void searchUserInput() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                ivCancel.setVisibility(View.VISIBLE);
                listAdapter.clearItems();
                listAdapter.notifyDataSetChanged();
                searchPresenter.onSearch(etSearch.getText().toString());

            }
        });
    }

}
