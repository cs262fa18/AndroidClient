package edu.calvin.cs262.teama.timetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class viewTimes extends AppCompatActivity {

    float timesSpentOnProjects[] = {20, 50, 40, 80};
    String projectNames[] = {"Project Alpha", "Project Beta", "Project Gamma", "Project Zeta"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_times);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpPieChart();
    }

    private void setUpPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i = 0; i < timesSpentOnProjects.length; i++) {
            pieEntries.add(new PieEntry(timesSpentOnProjects[i], projectNames[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Time Spent on Projects");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);
        PieChart chart = (PieChart)findViewById(R.id.viewTimesChart);
        chart.setData(data);
        Legend legend = chart.getLegend();
        legend.setWordWrapEnabled(true);
        chart.animateY(1000);
        chart.invalidate();
    }

}
