package edu.calvin.cs262.teama.timetracker;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class DataLoader extends AsyncTaskLoader<String> {

    private String mQueryString;
    private String mMethod;

    public DataLoader(Context context, String queryString, String method) {
        super(context);
        mQueryString = queryString;
        mMethod = method;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return NetworkUtils.getPlayerInfo(mQueryString, mMethod);
    }
}
