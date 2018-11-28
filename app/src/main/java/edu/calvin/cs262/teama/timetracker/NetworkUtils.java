package edu.calvin.cs262.teama.timetracker;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String EmplyeesUrl =  "https://calvincs262-fall2018-teama.appspot.com/monopoly/v1/employees";
    private static final String TimesUrl = "https://calvincs262-fall2018-teama.appspot.com/monopoly/v1/times";
    private static final String ProjectsUrl = "https://calvincs262-fall2018-teama.appspot.com/monopoly/v1/projects";


    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    static String getPlayerInfo(String queryString, String method) {
        Log.d("Quentins Log", queryString);
        if (method == "getData") {
            if (Integer.parseInt(queryString) == 0) {
                String playerJSONString = getFunction(EmplyeesUrl);
                playerJSONString += "--------------------------------------------------" + "\n" + getFunction(ProjectsUrl);
                playerJSONString += "--------------------------------------------------" + "\n" + getFunction(TimesUrl);
                Log.d("Quentins Log", playerJSONString);
                return playerJSONString;
            } else if (Integer.parseInt(queryString) == 1) {
                String playerJSONString = getFunction(TimesUrl);
                Log.d("Quentins Log", playerJSONString);
                return playerJSONString;
            } else if (Integer.parseInt(queryString) == 2) {
                String playerJSONString = getFunction(ProjectsUrl);
                Log.d("Quentins Log", playerJSONString);
                return playerJSONString;
            } else if (Integer.parseInt(queryString) == 3) {
                String playerJSONString = getFunction(EmplyeesUrl);
                Log.d("Quentins Log", playerJSONString);
                return playerJSONString;
            } else {
                Log.d("Quentins Log", "returned nothing");
                return null;
            }

        }
//        else {
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//            String playerJSONString = null;
//            try {
//                String specURL = Monopoly_ID_URL + queryString;
//                URL requestURL = new URL(specURL.toString());
//                urlConnection = (HttpURLConnection) requestURL.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//                while ((line = reader.readLine()) != null) {
//   /* Since it's JSON, adding a newline isn't necessary (it won't affect
//      parsing) but it does make debugging a *lot* easier if you print out the
//      completed buffer for debugging. */
//                    buffer.append(line + "\n");
//                }
//                if (buffer.length() == 0) {
//                    // Stream was empty.  No point in parsing.
//                    return null;
//                }
//                playerJSONString = buffer.toString();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            Log.d(LOG_TAG, playerJSONString);
//            return playerJSONString;
//        }
        return "Hi";
    }

    public static String getFunction(String website) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String playerJSONString = null;
        try {
            URL requestURL = new URL(website);
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
   /* Since it's JSON, adding a newline isn't necessary (it won't affect
      parsing) but it does make debugging a *lot* easier if you print out the
      completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            playerJSONString = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return playerJSONString;
    }
}


