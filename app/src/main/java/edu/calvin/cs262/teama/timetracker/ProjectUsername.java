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

    private static ArrayList<Object[]> activitiesList = new ArrayList<Object[]>();
    private static Integer workingProject;
    private static String username;
    private static Integer usernameID;

    public static void projectStartUp() {
        workingProject = 0;

    }

    public static void setUsername(String newUsername) {
        username = newUsername;
    }

    public static void setUsernameID(int newUsernameID) {
        usernameID = newUsernameID;
    }

    public static String getUsername() {
        return username;
    }

    public static int getUsernameID() {
        return usernameID;
    }

    public static void removeUsername() {
        try {
            username = "" ;
        } catch (ConcurrentModificationException e) {
            removeUsername();
        }
    }

    public static ArrayList<Object[]> getActivitiesList() {return ProjectUsername.activitiesList;}

    public static void addProject(String project, int managerID, int projectID) {
        Object[] newProject = new Object[]{projectID, project, managerID};
        activitiesList.add(newProject);
    }

    public static int removeProject(Integer projectID) {
        int projectPos = -1;
        for (Object[] Pr : activitiesList) {
            if (Pr[0] == projectID) {
                projectPos = activitiesList.indexOf(Pr);
            }
        }
        try {
            activitiesList.remove(projectPos);
            return projectPos;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static void removeAllProjects() {
        try {
            for (Object[] proj : activitiesList) {
                activitiesList.remove(proj);
            }
        } catch (ConcurrentModificationException e) {
            removeAllProjects();
        }
    }


}
