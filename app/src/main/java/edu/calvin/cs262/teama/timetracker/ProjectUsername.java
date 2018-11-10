package edu.calvin.cs262.teama.timetracker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.UUID;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ProjectUsername {

    private static ArrayList<String> activitiesList = new ArrayList<String>();
    private static Integer workingProject;
    private static String username;

    public static void projectStartUp() {
        workingProject = 0;

    }

    public static void usernameStartUp() {
        //run getUsername page
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
    }

    public static String getUsername() {
        return username;
    }

    public static ArrayList<String> getActivitiesList() {return ProjectUsername.activitiesList;}

    public static void addProject(String project) {
        activitiesList.add(project);
    }

    public static int removeProject(String project) {
        int pos;
        pos = activitiesList.indexOf(project);
        activitiesList.remove(project);
        return pos;
    }

    public static void saveSelectedProject(int project) {workingProject = project;}

    public static Integer returnSavedProject() {return workingProject;}

    public static void removeAllProjects() {
        try {
            for (String proj : activitiesList) {
                activitiesList.remove(proj);
            }
        } catch (ConcurrentModificationException e) {
            removeAllProjects();
        }
    }


}
