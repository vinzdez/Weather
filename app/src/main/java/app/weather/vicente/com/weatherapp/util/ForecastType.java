package app.weather.vicente.com.weatherapp.util;

import app.weather.vicente.com.weatherapp.R;

/**
 * Created by Vicente on 2/8/2017.
 */

public enum ForecastType {
    CHANCEFLURRIES("chanceflurries", R.mipmap.hail),
    CHANCERAIN("chancerain", R.mipmap.rain),
    CHANCESLEET("chancesleet", R.mipmap.snow),
    CHANCESNOW("chancesnow", R.mipmap.snow),
    CHANCETSTORMS("chancetstorms", R.mipmap.thunderstorm),
    CLEAR("clear", R.mipmap.sun),
    SUNNY("sunny", R.mipmap.sun),
    CLOUDY("cloudy", R.mipmap.cloudy),
    FLURRIES("flurries", R.mipmap.snow),
    FOG("fog", R.mipmap.fog),
    HAZY("hazy", R.mipmap.fog_cloudy),
    MOSTLYCLOUDY("mostlycloudy", R.mipmap.cloud),
    MOSTLYSUNNY("mostlysunny", R.mipmap.cloud),
    PARTLYCLOUDY("partlycloudy", R.mipmap.cloud),
    PARTLYSUNNY("partlysunny", R.mipmap.cloud),
    SLEET("sleet", R.mipmap.cloudy),
    RAIN("rain", R.mipmap.rain),
    SNOW("snow", R.mipmap.snow),
    TSTORMS("tstorms", R.mipmap.thunderstorm),
    UNKNOWN("unknown", R.mipmap.hail);

    private String stringValue;
    private int intValue;

    ForecastType(String value, int icon) {
        this.stringValue = value;
        this.intValue = icon;

    }

    public String getStringValue() {
        return stringValue;
    }

    public static int findByValue(String value) {
        for (ForecastType s : values()) {
            if (s.getStringValue().equals(value)) {
                return s.intValue;
            }
        }
        return R.mipmap.sun;
    }
}
