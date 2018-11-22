package app.weather.vicente.com.weatherapp.presenter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import java.util.List;

import app.weather.vicente.com.weatherapp.R;
import app.weather.vicente.com.weatherapp.service.ForecastService;
import app.weather.vicente.com.weatherapp.service.ForecastServiceImpl;
import app.weather.vicente.com.weatherapp.util.ForecastType;
import app.weather.vicente.com.weatherapp.view.ForecastDetailView;
import app.weather.vicente.com.weatherapp.view.model.ForecastViewModel;

/**
 * Created by Vicente on 08/02/2017.
 */

public class ForecastDetailPresenter implements BaseContract.ForecastDetailPresenter {
    private Context context;
    private ForecastDetailView forecastDetailView;
    private ForecastService forecastService;

    public ForecastDetailPresenter(@NonNull ForecastDetailView forecastDetailView, @NonNull Context context) {
        this.context = context;
        this.forecastDetailView = forecastDetailView;
        this.forecastService = new ForecastServiceImpl();
        //set this presenter to view
        forecastDetailView.setPresenter(this);
    }


    @Override
    public Drawable getIcon(String icon) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, ForecastType.findByValue(icon)));
        int color = ContextCompat.getColor(context, R.color.colorAccent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(drawable, color);
        } else {
            drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return drawable;
    }

    @Override
    public void deleteForecast(String value) {
        forecastService.delete(new ForecastService.ForecastServiceCallback() {
            @Override
            public void onSuccess() {
                getAllForeCast();
            }
        }, value);
    }

    @Override
    public void getAllForeCast() {
        forecastService.getAllForecast(new ForecastService.ForecastCallback() {
            @Override
            public void onSuccess(List<ForecastViewModel> list) {
                forecastDetailView.updateList(list);
            }
        });
    }

    @Override
    public void updateForecast() {
        forecastService.updateForecast();
    }

}
