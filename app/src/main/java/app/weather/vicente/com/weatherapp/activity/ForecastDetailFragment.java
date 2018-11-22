package app.weather.vicente.com.weatherapp.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.List;

import app.weather.vicente.com.weatherapp.R;
import app.weather.vicente.com.weatherapp.adapter.ImageAdapater;
import app.weather.vicente.com.weatherapp.presenter.BaseContract;
import app.weather.vicente.com.weatherapp.view.ForecastDetailView;
import app.weather.vicente.com.weatherapp.view.model.ForecastViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by Vicente on 08/02/2017.
 */

public class ForecastDetailFragment extends Fragment implements ForecastDetailView {

    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.iv_forecast)
    ImageView imForecast;
    @BindView(R.id.ll_layout)
    LinearLayout lMainForecast;
    @BindView(R.id.tv_forecast_name)
    TextView tvForecastName;
    @BindView(R.id.tv_forecast_type)
    TextView tvForecastType;
    @BindView(R.id.tv_forecast_tempetature)
    TextView tvForecastTemparature;
    @BindView(R.id.rv_image_view)
    RecyclerView rvImageView;

    private View view;
    private Context context;
    private BaseContract.ForecastDetailPresenter forecastDetailPresenter;
    private ImageAdapater imageAdapter;
    private NotifyDataReceiver notifyDataReceiver;

    public static ForecastDetailFragment newInstance() {
        ForecastDetailFragment fragment = new ForecastDetailFragment();
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            this.context = getActivity();
            this.view = inflater.inflate(R.layout.fragment_forecast_detail, container, false);
            ButterKnife.bind(this, view);

            this.imageAdapter = new ImageAdapater(this);
            rvImageView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            rvImageView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            rvImageView.setAdapter(imageAdapter);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        forecastDetailPresenter.getAllForeCast();
        IntentFilter filter = new IntentFilter(NotifyDataReceiver.ON_UPDATE);
        this.notifyDataReceiver = new NotifyDataReceiver();
        context.registerReceiver(notifyDataReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        context.unregisterReceiver(notifyDataReceiver);
    }

    @Override
    public void setPresenter(BaseContract.ForecastDetailPresenter presenter) {
        this.forecastDetailPresenter = presenter;
    }

    @OnClick(R.id.iv_add)
    public void showSearchFragment() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
    }


    @OnLongClick(R.id.ll_layout)
    public boolean onDeleteForecast() {
        int position = imageAdapter.getForecastViewModelList().size() - 1;
        final ForecastViewModel forecastViewModel = imageAdapter.getForecastViewModelList().get(position);
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog);
        } else {
            builder = new AlertDialog.Builder(context);
        }

        builder.setTitle(getString(R.string.dialog_delete))
                .setMessage(forecastViewModel.getName())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        forecastDetailPresenter.deleteForecast(forecastViewModel.getName());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;
    }

    @Override
    public void onSwitch(int index) {
        int position = imageAdapter.getForecastViewModelList().size() - 1;
        ForecastViewModel forecastViewModel = imageAdapter.getForecastViewModelList().get(index);
        ForecastViewModel originalForecast = imageAdapter.getForecastViewModelList().get(position);

        imageAdapter.getForecastViewModelList().set(position, forecastViewModel);
        imageAdapter.getForecastViewModelList().set(index, originalForecast);

        showMainForecast(forecastViewModel);
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateList(List<ForecastViewModel> forecastViewModels) {
        imageAdapter.clearItems();
        imageAdapter.getForecastViewModelList().addAll(forecastViewModels);
        loadMainForecast();
        imageAdapter.notifyDataSetChanged();
    }

    private void loadMainForecast() {
        if (!imageAdapter.getForecastViewModelList().isEmpty()) {
            int position = imageAdapter.getForecastViewModelList().size() - 1;
            showMainForecast(imageAdapter.getForecastViewModelList().get(position));
            imageAdapter.notifyDataSetChanged();
        } else {
            showSearchFragment();
        }
    }

    private void showMainForecast(ForecastViewModel forecastViewModel) {
        tvForecastName.setText(forecastViewModel.getName().concat(", ".concat(forecastViewModel.getParent())));
        tvForecastType.setText(forecastViewModel.getCondition());
        String value = context.getResources().getString(R.string.temperature);
        value = MessageFormat.format(value, forecastViewModel.getTempF(), forecastViewModel.getTempC());
        tvForecastTemparature.setText(value);
        if (forecastDetailPresenter != null) {
            imForecast.setImageDrawable(forecastDetailPresenter.getIcon(forecastViewModel.getIcon()));
        }
        imageAdapter.notifyDataSetChanged();
    }

    public class NotifyDataReceiver extends BroadcastReceiver {
        public final String TAG = NotifyDataReceiver.class.getName();
        public static final String ON_UPDATE = "ON_UPDATE";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isAdded()) {
                forecastDetailPresenter.updateForecast();
                forecastDetailPresenter.getAllForeCast();
                imageAdapter.notifyDataSetChanged();
            }
        }
    }
}
