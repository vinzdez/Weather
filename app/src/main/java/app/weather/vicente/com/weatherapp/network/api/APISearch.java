package app.weather.vicente.com.weatherapp.network.api;


import app.weather.vicente.com.weatherapp.network.model.Country;
import app.weather.vicente.com.weatherapp.network.model.Forecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Asus on 2/7/2017.
 */

public interface APISearch {

    @GET("aq")
    Call<Country> findCountry(@Query("query") String query, @Query("cities") int city);

    @GET
    Call<Forecast> findSubCategory(@Url String url);

    @GET
    Call<Forecast> getForCast(@Url String url);
}
