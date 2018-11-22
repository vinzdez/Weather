package app.weather.vicente.com.weatherapp.util;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;

import app.weather.vicente.com.weatherapp.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Vicente on 2/7/2017.
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtil {

    private static final int HOUR1 = 19;
    private static final int HOUR2 = 7;

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /*
     * Hides the keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Handles the background transition
     */
    public static int getBackgroundColor() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int color = R.color.colorPrimary;
        if (hour >= HOUR1 || hour <= HOUR2) {
            color = R.color.colorPrimaryDark;
        }
        return color;
    }
}
