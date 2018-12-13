package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class handles the removeTimes page
 *
 * @author Quentin Barnes
 */
public class removeTimes extends AppCompatActivity {

    ListView timesList;

    /**
     * This activity lets the user remove times by selecting a past one
     * and then passes the selected one back to MainActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_times);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> allTimes = new ArrayList<String>();
        timesList = (ListView) findViewById(R.id.listTimes);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (TimeEntry te : TimeEntry.getAllTimeEntries()) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("Project", "place");
            datum.put("info", "holder");
            data.add(datum);
        }
        int sizeOfList = 0;
        for (TimeEntry te : TimeEntry.getAllTimeEntries()) {
            Map<String, String> datum = new HashMap<String, String>(2);
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            if (te.getEndTime() != null) {
                long millis = te.getEndTime().getTime() - te.getStartTime().getTime();
                long hours = millis / (1000 * 60 * 60);
                long mins = (millis / (1000 * 60)) % 60;
                String timeDiff = hours + ":" + mins;
                datum.put("Project", te.getProject());
                datum.put("info", df.format("yyyy-MM-dd hh:mm:ss", te.getStartTime()).toString() + " for " + timeDiff);
            } else {
                datum.put("Project", te.getProject());
                datum.put("info", df.format("yyyy-MM-dd hh:mm:ss", te.getStartTime()).toString() + ": Hasn't Finished Yet");
            }
            int index = (TimeEntry.getAllTimeEntries().size() - 1) - sizeOfList;
            data.add(index, datum);
            data.remove(index + 1);
            sizeOfList += 1;
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[]{"Project", "info"},
                new int[]{android.R.id.text1,
                        android.R.id.text2});
        timesList.setAdapter(adapter);

        timesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("position", position);
                setResult(1, intent);
                finish();

            }
        });
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}
