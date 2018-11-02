package edu.calvin.cs262.teama.timetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import java.util.ArrayList;


public class addProject extends MainActivity {

    Button addButton;
    EditText addProjText;
    Button removeButton;
    Spinner removeSpinner;
    ArrayList<String> activitiesList = new ArrayList<String>();

    private static final String LOG_TAG = addProject.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitiesList.add("Project Leema");
        setContentView(R.layout.activity_add_project);
        Log.d(LOG_TAG, "1");
        Log.d(LOG_TAG, "6");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addButton=(Button)findViewById(R.id.newProjButton);
        addProjText=(EditText)findViewById(R.id.newProjText);
        removeButton=(Button)findViewById(R.id.removeProjButton);
        Log.d(LOG_TAG, "7");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String message=addProjText.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("MESSAGE",message);
                setResult(2,intent);
                finish();//finishing activity
            }
        });
    }

//    public void startSpinner() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
////        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "COMING SOON!", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//        Log.d(LOG_TAG, "2");
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        Log.d(LOG_TAG, "3");
//        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        Log.d(LOG_TAG, "4");
//        removeSpinner = (Spinner)findViewById(R.id.removeProjSpin);
//        Log.d(LOG_TAG, "4");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_item, activitiesList);
//        Log.d(LOG_TAG, "4");
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Log.d(LOG_TAG, "4");
//        removeSpinner.setAdapter(adapter);
//        Log.d(LOG_TAG, "5");
//    }
//
}


