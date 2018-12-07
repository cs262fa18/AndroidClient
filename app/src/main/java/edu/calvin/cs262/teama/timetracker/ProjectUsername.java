package edu.calvin.cs262.teama.timetracker;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ProjectUsername {

    private static ArrayList<Object[]> activitiesList = new ArrayList<Object[]>();
    private static ArrayList<Object[]> usernameList = new ArrayList<Object[]>();
    private static Integer usernameID;

    public static void projectStartUp() {
        usernameID = -1;
    }

    public static void addUsername(String newUsername, int newUserId) {

        usernameList.add(new Object[]{newUsername, newUserId});
    }

    public static String getUsername(int id) {
        for (Object[] o : usernameList) {
            if (Integer.parseInt(o[1].toString()) == id) {
                return o[0].toString();
            }
        }
        return "";
    }

    public static ArrayList<Object[]> getUsernameList() {
        return usernameList;
    }

    public static int getUsernameID() {
        if (usernameID == null) {
            return -1;
        }
        return usernameID;
    }

    public static void setUsernameID(int newUsernameID) {
        usernameID = newUsernameID;
    }

    public static void removeUsernameID() {
        try {
            usernameID = null;
        } catch (ConcurrentModificationException e) {
            removeUsernameID();
        }
    }

    public static void removeAllUsernames() {
        try {
//            for (Object[] o : usernameList) {
//                usernameList.remove(o);
//            }
            usernameList.clear();
        } catch (ConcurrentModificationException e) {
            removeAllUsernames();
        }
    }

    public static ArrayList<Object[]> getActivitiesList() {
        return ProjectUsername.activitiesList;
    }

    public static ArrayList<String> getActivitiesListProject() {
        ArrayList<String> activitiesListProject = new ArrayList<String>();
        for (Object[] o : activitiesList) {
            activitiesListProject.add(o[1].toString());
        }
        return activitiesListProject;
    }

    public static int getProjectID(String ProjectName) {
        for (Object[] a : activitiesList) {
            if (a[1].toString() == ProjectName) {
                return Integer.parseInt(a[0].toString());
            }
        }
        return -1;
    }

    public static String getProjectName(int id) {
        for (Object[] a : activitiesList) {
            if (Integer.parseInt(a[0].toString()) == id) {
                return a[1].toString();
            }
        }
        return null;
    }

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
//            for (int i = 0; i < activitiesList.size(); i++) {
//                activitiesList.remove(i);
//            }
            activitiesList.clear();
        } catch (ConcurrentModificationException e) {
            removeAllProjects();
        }
    }


    public static int getNewUsernameID(String newUsername) {
        for (Object[] o : usernameList) {
            if (o[0].toString() == newUsername) {
                return Integer.parseInt(o[1].toString());
            }
        }
        return -1;
    }
}
