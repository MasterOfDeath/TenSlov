package ru.rinat.tenslov;

import android.app.Application;

/**
 * Created by rinat on 03.06.15.
 */
public class App extends Application {
    public static DBHelper mDbHelper;

    @Override
    public void onCreate(){
        super.onCreate();

        mDbHelper = new DBHelper(getApplicationContext());
        mDbHelper.getWritableDatabase();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mDbHelper.close();
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        mDbHelper.close();
    }
}