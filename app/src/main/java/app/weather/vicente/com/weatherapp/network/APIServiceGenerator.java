package app.weather.vicente.com.weatherapp.network;

import app.weather.vicente.com.weatherapp.network.api.APISearch;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vicente on 2/7/2017.
 */

public class APIServiceGenerator {

    private static String SERVER_URL = "http://autocomplete.wunderground.com/";
    private static APIServiceGenerator instance;
    private APISearch apiSearch;
    private static OkHttpClient client;

    private APIServiceGenerator() {
        client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.apiSearch = retrofit.create(APISearch.class);
    }

    public static synchronized APIServiceGenerator getInstance() {
        if (instance == null) {
            instance = new APIServiceGenerator();
        }
        return instance;
    }

    public APISearch getApiSearch() {
        return apiSearch;
    }


}
