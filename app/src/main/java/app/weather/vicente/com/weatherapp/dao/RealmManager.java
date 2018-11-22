package app.weather.vicente.com.weatherapp.dao;

import app.weather.vicente.com.weatherapp.WeatherApp;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Vicente on 2/8/2017.
 */

public class RealmManager {

    private Realm realm;

    private static RealmManager REALM_INSTANCE;

    private static RealmManager getInstance() {
        return REALM_INSTANCE;
    }

    public Realm getRealmInstance() {
        return realm;
    }

    public RealmManager(String dbName, int verion) {
        if (getInstance() == null) {
            REALM_INSTANCE = this;
            // Initialize Realm
            Realm.init(WeatherApp.getContext().getApplicationContext());
            this.realm = Realm.getDefaultInstance();
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                    .name(dbName)
                    .schemaVersion(verion).deleteRealmIfMigrationNeeded()
                    .build();
            realm.setDefaultConfiguration(realmConfiguration);
        }
    }
}
