package edu.calvin.cs262.teama.timetracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

public class DataLoader extends AsyncTaskLoader<String> {

    private String mQueryString;
    private String mMethod;
    private Bundle mData;

    public DataLoader(Context context, String queryString, String method, Bundle data) {
        super(context);
        mQueryString = queryString;
        mMethod = method;
        mData = data;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return NetworkUtils.getPlayerInfo(mQueryString, mMethod, mData);
    }
}
