package edu.calvin.cs262.teama.timetracker;

import android.app.Application;

public class startUp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Project.projectStartUp();

    }

}
