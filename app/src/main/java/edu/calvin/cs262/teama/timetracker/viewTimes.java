package edu.calvin.cs262.teama.timetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for viewing times via a graph
 * Uses external library MPAndroidChart
 */
public class viewTimes extends AppCompatActivity {

    ArrayList<String> projectNames = new ArrayList<String>();
    ArrayList<Float> projectTimes = new ArrayList<Float>();

    /**
     * Loads in the names and the amount of time each one has
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_times);
        setTitle("Time Spent On Projects");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int size = Integer.parseInt(getIntent().getExtras().get("size").toString());
        for (int i = 0; i < size; i++) {
            projectNames.add(getIntent().getExtras().get("projectName" + i).toString());
            projectTimes.add(Float.parseFloat(getIntent().getExtras().get("projectTime" + i).toString()));
        }

        setUpPieChart();
    }

    /**
     * Uses the previously loaded data to create a graph
     */
    private void setUpPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < projectTimes.size(); i++) {
            if (projectTimes.get(i) > 0) {
                pieEntries.add(new PieEntry(projectTimes.get(i), projectNames.get(i)));
            }
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Time Spent on Projects");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        PieChart chart = (PieChart) findViewById(R.id.viewTimesChart);
        chart.setData(data);
        Legend legend = chart.getLegend();
        legend.setWordWrapEnabled(true);
        chart.animateY(1000);
        chart.invalidate();
    }

}
