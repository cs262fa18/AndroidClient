package edu.calvin.cs262.teama.timetracker;

import java.util.ArrayList;

public class Project {

    private static ArrayList<String> activitiesList = new ArrayList<String>();
    private static Integer workingProject;

    public static void projectStartUp() {
        activitiesList.add("Project Alpha");
        activitiesList.add("Project Beta");
        activitiesList.add("Project Gamma");
        activitiesList.add("Project Zeta");
        workingProject = 0;
    }

    public static ArrayList<String> getActivitiesList() {return Project.activitiesList;}

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


}