package edu.calvin.cs262.teama.timetracker;

import android.app.Application;

/**
 * This class runs only when the app starts up
 */
public class startUp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ProjectUsername.projectStartUp();

    }
}
