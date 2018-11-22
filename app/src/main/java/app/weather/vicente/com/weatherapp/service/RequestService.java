package app.weather.vicente.com.weatherapp.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;

import app.weather.vicente.com.weatherapp.activity.ForecastDetailFragment;

/**
 * Created by Vicente on 10/02/2017.
 */

public class RequestService extends IntentService {

    private static final String TAG = RequestService.class.getSimpleName();

    public RequestService() {
        super(TAG);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        runServiceInFuture();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ForecastDetailFragment.NotifyDataReceiver.ON_UPDATE);
        sendBroadcast(broadcastIntent);

    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, RequestService.class);
    }

    public void runServiceInFuture() {

        PendingIntent pendingIntent =
                PendingIntent.getService(this, 0, getCallingIntent(getBaseContext()), 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 60);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
