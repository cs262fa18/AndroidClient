//package edu.calvin.cs262.teama.timetracker;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.Loader;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//public class DataBaseManager extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
//
////    private EditText mPlayerInput;
////    private TextView mPlayerText;
////    private Button mButton;
//
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////
////        mPlayerInput = (EditText)findViewById(R.id.playerInput);
////        mPlayerText = (TextView)findViewById(R.id.playerText);
////        mButton = (Button)findViewById(R.id.searchButton);
////
////        if(getSupportLoaderManager().getLoader(0)!=null){
////            getSupportLoaderManager().initLoader(0,null,this);
////        }
////
////        String queryString = "-1";
////
////        ConnectivityManager connMgr = (ConnectivityManager)
////                getSystemService(Context.CONNECTIVITY_SERVICE);
////        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
////
////        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
////            Bundle queryBundle = new Bundle();
////            queryBundle.putString("queryString", queryString);
////            getSupportLoaderManager().restartLoader(0, queryBundle, this);
////        }
////
////        else {
////            if (queryString.length() == 0) {
////                displayToast("Please enter a search term");
////            } else {
////                displayToast("Please check your network connection and try again.");
////            }
////        }
////    }
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_main, menu);
////        return true;
////    }
//
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        // Handle action bar item clicks here. The action bar will
////        // automatically handle clicks on the Home/Up button, so long
////        // as you specify a parent activity in AndroidManifest.xml.
////        int id = item.getItemId();
////
////        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            Intent intent = new Intent(this, SettingsActivity.class);
////            startActivity(intent);
////            return true;
////        }
////
////        return super.onOptionsItemSelected(item);
////    }
//
//    public void displayToast(String message) {
//        Toast.makeText(getApplicationContext(), message,
//                Toast.LENGTH_SHORT).show();
//    }
//
//    public void getData() {
////        String queryString = mPlayerInput.getText().toString();
////        if (queryString.toString().length() == 0) {
////            queryString = "-1";
////        }
//
//
//
//        String queryString = "1";
//
////        try {
////            InputMethodManager inputManager = (InputMethodManager)
////                    getSystemService(Context.INPUT_METHOD_SERVICE);
////            inputManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
////                    InputMethodManager.HIDE_NOT_ALWAYS);
////        } catch (Exception e) { }
//
//        if(getSupportLoaderManager().getLoader(0)!=null){
//            getSupportLoaderManager().initLoader(0,null,this);
//        }
//
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//
//        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
//            Bundle queryBundle = new Bundle();
//            queryBundle.putString("queryString", queryString);
//            queryBundle.putString("method", "getData");
//            getSupportLoaderManager().restartLoader(0, queryBundle, this);
//        }
//
//        else {
//            if (queryString.length() == 0) {
//                displayToast("Please enter a search term");
//            } else {
//                displayToast("Please check your network connection and try again.");
//            }
//        }
//    }
//
////    public void postTime(View view) {
////        String queryString = mPlayerInput.getText().toString();
////        if (queryString.toString().length() == 0) {
////            queryString = "-1";
////        }
////
////        try {
////            InputMethodManager inputManager = (InputMethodManager)
////                    getSystemService(Context.INPUT_METHOD_SERVICE);
////            inputManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
////                    InputMethodManager.HIDE_NOT_ALWAYS);
////        } catch (Exception e) { }
////
////        ConnectivityManager connMgr = (ConnectivityManager)
////                getSystemService(Context.CONNECTIVITY_SERVICE);
////        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
////
////        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
////            Bundle queryBundle = new Bundle();
////            queryBundle.putString("queryString", queryString);
////            getSupportLoaderManager().restartLoader(0, queryBundle, this);
////        }
////
////        else {
////            if (queryString.length() == 0) {
////                displayToast("Please enter a search term");
////            } else {
////                displayToast("Please check your network connection and try again.");
////            }
////        }
////    }
////
////    public void putTime(View view) {
////        String queryString = mPlayerInput.getText().toString();
////        if (queryString.toString().length() == 0) {
////            queryString = "-1";
////        }
////
////        try {
////            InputMethodManager inputManager = (InputMethodManager)
////                    getSystemService(Context.INPUT_METHOD_SERVICE);
////            inputManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
////                    InputMethodManager.HIDE_NOT_ALWAYS);
////        } catch (Exception e) { }
////
////        ConnectivityManager connMgr = (ConnectivityManager)
////                getSystemService(Context.CONNECTIVITY_SERVICE);
////        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
////
////        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
////            Bundle queryBundle = new Bundle();
////            queryBundle.putString("queryString", queryString);
////            getSupportLoaderManager().restartLoader(0, queryBundle, this);
////        }
////
////        else {
////            if (queryString.length() == 0) {
////                displayToast("Please enter a search term");
////            } else {
////                displayToast("Please check your network connection and try again.");
////            }
////        }
////    }
////
////    public void updatePorjects(View view) {
////        String queryString = mPlayerInput.getText().toString();
////        if (queryString.toString().length() == 0) {
////            queryString = "-1";
////        }
////
////        try {
////            InputMethodManager inputManager = (InputMethodManager)
////                    getSystemService(Context.INPUT_METHOD_SERVICE);
////            inputManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
////                    InputMethodManager.HIDE_NOT_ALWAYS);
////        } catch (Exception e) { }
////
////        ConnectivityManager connMgr = (ConnectivityManager)
////                getSystemService(Context.CONNECTIVITY_SERVICE);
////        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
////
////        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
////            Bundle queryBundle = new Bundle();
////            queryBundle.putString("queryString", queryString);
////            getSupportLoaderManager().restartLoader(0, queryBundle, this);
////        }
////
////        else {
////            if (queryString.length() == 0) {
////                displayToast("Please enter a search term");
////            } else {
////                displayToast("Please check your network connection and try again.");
////            }
////        }
////    }
////
////    public void addUser(View view) {
////        String queryString = mPlayerInput.getText().toString();
////        if (queryString.toString().length() == 0) {
////            queryString = "-1";
////        }
////
////        try {
////            InputMethodManager inputManager = (InputMethodManager)
////                    getSystemService(Context.INPUT_METHOD_SERVICE);
////            inputManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
////                    InputMethodManager.HIDE_NOT_ALWAYS);
////        } catch (Exception e) { }
////
////        ConnectivityManager connMgr = (ConnectivityManager)
////                getSystemService(Context.CONNECTIVITY_SERVICE);
////        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
////
////        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
////            Bundle queryBundle = new Bundle();
////            queryBundle.putString("queryString", queryString);
////            getSupportLoaderManager().restartLoader(0, queryBundle, this);
////        }
////
////        else {
////            if (queryString.length() == 0) {
////                displayToast("Please enter a search term");
////            } else {
////                displayToast("Please check your network connection and try again.");
////            }
////        }
////    }
//
//    @Override
//    public Loader<String> onCreateLoader(int id, Bundle args) {
//        return new DataLoader(getApplicationContext(), args.getString("queryString"), args.getString("method"));
//    }
//
//    @Override
//    public void onLoadFinished(Loader<String> loader, String data) {
//        try {
//            // Convert the response into a JSON object.
//            JSONObject jsonObject = new JSONObject(data);
//            // Get the JSONArray of book items.
//            JSONArray itemsArray = jsonObject.getJSONArray("items");
//
//            // Initialize iterator and results fields.
//            int i = 0;
//            String title = null;
//
//            // Look for results in the items array, exiting when both the title and author
//            // are found or when all items have been checked.
//            while (i < itemsArray.length() || (title == null)) {
//                // Get the current item information.
//                JSONObject player = itemsArray.getJSONObject(i);
//
//                // Try to get the author and title from the current item,
//                // catch if either field is empty and move on.
//                try {
//                    String id = player.getString("id");
//                    String eMail = player.getString("emailAddress");
//                    String name = null;
//                    try {
//                        name = player.getString("name");
//                    } catch (Exception e) {
//                        name = "No Name";
//                    }
//                    if (title == null) {
//                        title = id + ", " + name + ", " + eMail;
//                    } else {
//                        title = title + "\n" + id + ", " + name + ", " + eMail;
//                    }
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                // Move to the next item.
//                i++;
//            }
//
//            // If both are found, display the result.
//            if (title != null){
////                mPlayerText.setText(title);
////                mPlayerInput.setText("");
//            } else {
//                // If none are found, update the UI to show failed results.
//                displayToast("No results found");
//            }
//
//        } catch(Exception e) {
//            try {
//                JSONObject jsonObject = new JSONObject(data);
//                String info = null;
//                String id = jsonObject.getString("id");
//                String eMail = jsonObject.getString("emailAddress");
//                String name = null;
//                try {
//                    name = jsonObject.getString("name");
//                } catch (Exception q) {
//                    name = "No Name";
//                }
//                info = id + ", " + name + ", " + eMail;
//
//                if (info != null) {
////                    mPlayerText.setText(info);
////                    mPlayerInput.setText("");
//                } else {
//                    // If none are found, update the UI to show failed results.
//                    displayToast("No results found");
//                }
//            } catch (Exception q) {
//                // If onPostExecute does not receive a proper JSON string, update the UI to show failed results.
//                displayToast("Please enter nothing or a valid ID number.");
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<String> loader) {}
//}
