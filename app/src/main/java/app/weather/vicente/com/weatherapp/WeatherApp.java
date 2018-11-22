package app.weather.vicente.com.weatherapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import app.weather.vicente.com.weatherapp.service.RequestService;

/**
 * Created by Vicente on 2/8/2017.
 */

public class WeatherApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Intent msgIntent = new Intent(context, RequestService.class);
        context.startService(msgIntent);
    }

    public static Context getContext() {
        return context;
    }
}
