package edu.calvin.cs262.teama.timetracker;

import java.util.ArrayList;

public class Project {

    private static ArrayList<String> activitiesList = new ArrayList<String>();

    public static void projectStartUp() {
        activitiesList.add("Project Alpha");
        activitiesList.add("Project Beta");
        activitiesList.add("Project Gamma");
        activitiesList.add("Project Zeta");
    }

    public static ArrayList<String> getActivitiesList() {return Project.activitiesList;}

    public static void addProject(String project) {
        activitiesList.add(project);
    }

    public static void removeProject(String project) {
        activitiesList.remove(project);
    }


}
