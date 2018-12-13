package edu.calvin.cs262.teama.timetracker;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class handles get, put, post, and delete requests for the app
 * It works for projects, times, and usernames
 * It Works by reading data from a bundle it is passed decides
 * what to do with that bundle via the methods and querysting it gets
 *
 * @author Querntin Barnes
 */

public class NetworkUtils {

    private static final String EmplyeesUrl = "https://calvincs262-fall2018-teama.appspot.com/teama/v1/employees";
    private static final String EmplyeePostUrl = "https://calvincs262-fall2018-teama.appspot.com/teama/v1/employee";
    private static final String TimesUrl = "https://calvincs262-fall2018-teama.appspot.com/teama/v1/times";
    private static final String TimesPostUrl = "https://calvincs262-fall2018-teama.appspot.com/teama/v1/time";
    private static final String ProjectsUrl = "https://calvincs262-fall2018-teama.appspot.com/teama/v1/projects";
    private static final String ProjectsPostUrl = "https://calvincs262-fall2018-teama.appspot.com/teama/v1/project";


    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    static String getPlayerInfo(String queryString, String method, Bundle data) {
        String result = "";
        // Log.d("Quentins Log", queryString + " HELP ME PLEASSSSSSSSSSSSSE");
        // Log.d("Quentins Log", method);

        if (method == "getData") {
            if (Integer.parseInt(queryString) == 0) {
                result += "AllGetData" + "#@!BREAK!@#";
                result += getFunction(EmplyeesUrl);
                result += "#@!BREAK!@#" + getFunction(ProjectsUrl);
                result += "#@!BREAK!@#" + getFunction(TimesUrl);
                // Log.d("Quentins Log", result);
                return result;
            } else if (Integer.parseInt(queryString) == 1) {
                result += "TimesGetData" + "#@!BREAK!@#";
                result += getFunction(TimesUrl);
                // Log.d("Quentins Log", result);
                return result;
            } else if (Integer.parseInt(queryString) == 2) {
                result += "ProjectGetData" + "#@!BREAK!@#";
                result += getFunction(ProjectsUrl);
                // Log.d("Quentins Log", result);
                return result;
            } else if (Integer.parseInt(queryString) == 3) {
                result += "UserGetData" + "#@!BREAK!@#";
                result += getFunction(EmplyeesUrl);
                // Log.d("Quentins Log", result);
                return result;
            } else {
                // Log.d("Quentins Log", "returned nothing");
                return "GetFailed";
            }

        } else if (method == "postData") {
            if (Integer.parseInt(queryString) == 1) {
                boolean status = postFunction(data, TimesPostUrl);
                if (status) {
                    return "TimePostSucsessful" + "#@!BREAK!@#" + "help";
                } else {
                    return "TimePostFail";
                }

            } else if (Integer.parseInt(queryString) == 2) {
                boolean status = postFunction(data, ProjectsPostUrl);
                if (status) {
                    return "ProjPostSucsessful" + "#@!BREAK!@#" + "help";
                } else {
                    return "ProjPostFail";
                }

            } else if (Integer.parseInt(queryString) == 3) {
                boolean status = postFunction(data, EmplyeePostUrl);
                if (status) {
                    return "UserPostSucsessful" + "#@!BREAK!@#" + "help";
                } else {
                    return "UserPostFail";
                }

            } else {
                return "PostFailed";
            }
        } else if (method == "putData") {
            if (Integer.parseInt(queryString) == 1) {
                boolean status = putFunction(data, TimesPostUrl);
                if (status) {
                    return "TimePutSucsessful" + "#@!BREAK!@#" + "help";
                } else {
                    return "TimePutFail";
                }

            } else if (Integer.parseInt(queryString) == 2) {
                boolean status = putFunction(data, ProjectsPostUrl);
                if (status) {
                    return "ProjPutSucsessful" + "#@!BREAK!@#" + "help";
                } else {
                    return "ProjPutFail";
                }

            } else if (Integer.parseInt(queryString) == 3) {
                boolean status = putFunction(data, EmplyeePostUrl);
                if (status) {
                    return "UserPutSucsessful" + "#@!BREAK!@#" + "help";
                } else {
                    return "UserPutFail";
                }

            } else {
                return "PutFailed";
            }
        } else if (method == "deleteData") {
            if (Integer.parseInt(queryString) == 1) {
                boolean status = deleteFunction(data, TimesPostUrl);
                if (status) {
                    return "TimeDelSucsessful" + "#@!BREAK!@#" + "help";
                } else {
                    return "TimeDelFail";
                }

            } else if (Integer.parseInt(queryString) == 2) {
                boolean status = deleteFunction(data, ProjectsPostUrl);
                if (status) {
                    return "ProjDelSucsessful" + "#@!BREAK!@#" + "help";
                } else {
                    return "ProjDelFail";
                }

            } else if (Integer.parseInt(queryString) == 3) {
                boolean status = deleteFunction(data, EmplyeePostUrl);
                if (status) {
                    return "UserDelSucsessful" + "#@!BREAK!@#" + "help";
                } else {
                    return "UserDelFail";
                }

            } else {
                return "DeleteFailed";
            }
        } else {
            return "BIGFAIL";
        }
    }

    private static boolean postFunction(Bundle data, String website) {
        if (ProjectUsername.getIsRunningPost()) {
            String dupData;
            Boolean isDuplicate = false;
            // Log.d("Duplicate", "At start: " + Boolean.toString(isDuplicate));

            if (website == EmplyeePostUrl) {
                try {
                    dupData = getFunction(EmplyeesUrl);
                    JSONObject jsonObject = new JSONObject(dupData);
                    JSONArray itemsArray = jsonObject.getJSONArray("items");
                    int i = 0;
                    while (i < itemsArray.length() || i == 0) {
                        // Get the current item information.
                        JSONObject player = itemsArray.getJSONObject(i);
                        // Log.d("Quentins Log", "User3");

                        // Try to get the author and title from the current item,
                        // catch if either field is empty and move on.
                        try {
                            if (player.getString("username").matches(data.get("username").toString())) {
                                isDuplicate = true;
                                // Log.d("Duplicate", "Username Dupe");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            // Log.d("Quentins Log", "username Crash");
                        }
                        i++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (website == TimesPostUrl) {
                try {
                    dupData = getFunction(TimesUrl);
                    JSONObject jsonObject = new JSONObject(dupData);
                    JSONArray itemsArray = jsonObject.getJSONArray("items");
                    int i = 0;
                    while (i < itemsArray.length() || i == 0) {
                        // Get the current item information.
                        JSONObject player = itemsArray.getJSONObject(i);
                        // Log.d("Quentins Log", "User3");

                        // Try to get the author and title from the current item,
                        // catch if either field is empty and move on.
                        try {
                            if (player.getString("uuid").matches(data.get("UUID").toString())) {
                                isDuplicate = true;
                                // Log.d("Duplicate", "Times Dupe");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            // Log.d("Quentins Log", "Times Crash");
                        }
                        i++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (website == ProjectsPostUrl) {
                try {
                    dupData = getFunction(ProjectsUrl);
                    JSONObject jsonObject = new JSONObject(dupData);
                    JSONArray itemsArray = jsonObject.getJSONArray("items");
                    int i = 0;
                    while (i < itemsArray.length() || i == 0) {
                        // Get the current item information.
                        JSONObject player = itemsArray.getJSONObject(i);
                        // Log.d("Quentins Log", "User3");

                        // Try to get the author and title from the current item,
                        // catch if either field is empty and move on.
                        try {
                            if (player.getString("name").matches(data.get("projectName").toString())) {
                                isDuplicate = true;
                                // Log.d("Duplicate", "Project Dupe");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            // Log.d("Quentins Log", "Project Crash");
                        }
                        i++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Log.d("Duplicate", "At end: " + Boolean.toString(isDuplicate));

            if (!isDuplicate) {
                // Log.d("Duplicate", "Started post command");
                HttpURLConnection urlConnection = null;
                try {
                    // Log.d("Quentins Log", "U1");
                    URL requestURL = new URL(website);
                    urlConnection = (HttpURLConnection) requestURL.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Host", "calvincs262-fall2018-teama.appspot.com");
                    // Log.d("Quentins Log", "U2");
                    urlConnection.connect();
                    // Log.d("Quentins Log", "U3");

                    // Log.d("Duplicate", "1Started post command");

                    // Log.d("Duplicate", website);
                    try {
                        // Log.d("Duplicate", data.get("projectName").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Log.d("Duplicate", "Doesnt Exist");
                    }
                    try {
                        // Log.d("Duplicate", data.get("managerID").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Log.d("Duplicate", "Doesnt Exist");
                    }
                    JSONObject jsonParam;
                    if (website == EmplyeePostUrl) {
                        jsonParam = new JSONObject();
                        jsonParam.put("username", data.get("username").toString());
                        jsonParam.put("password", data.get("password").toString());
                        // Log.d("Quentins Log", "U4");
                    } else if (website == TimesPostUrl) {
                        jsonParam = new JSONObject();
                        jsonParam.put("startTime", data.get("startTime").toString());
                        jsonParam.put("endTime", data.get("endTime").toString());
                        jsonParam.put("employeeID", Integer.parseInt(data.get("employeeID").toString()));
                        jsonParam.put("projectID", Integer.parseInt(data.get("projectID").toString()));
                        jsonParam.put("uuid", data.get("UUID").toString());
                        // Log.d("Quentins Log", data.get("UUID").toString());
                        // Log.d("Quentins Log", "U4");
                    } else if (website == ProjectsPostUrl) {
                        jsonParam = new JSONObject();
                        jsonParam.put("name", data.get("projectName").toString());
                        jsonParam.put("managerID", Integer.parseInt(data.get("managerID").toString()));
                        // Log.d("Quentins Log", "U4");
                    } else {
                        return false;
                    }
                    // Log.d("Duplicate", "2Started post command");
                    // Log.d("Quentins Log", jsonParam.toString());

                    OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                    out.write(jsonParam.toString());
                    // Log.d("Quentins Log", "U5");
                    out.close();
                    // Log.d("Quentins Log", "U6");
                    // Log.d("Duplicate", "3Started post command");

                    int HttpResult = urlConnection.getResponseCode();
                    String sb = "D/Quentins Log \n";
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                urlConnection.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb += line + "\n";
                        }
                        br.close();

                        System.out.println("" + sb);

                    } else {
                        System.out.println(urlConnection.getResponseMessage());
                    }
                    // Log.d("Quentins Log", "U7");
                    // Log.d("Duplicate", "4Started post command");

                    jsonParam.remove("username");
                    jsonParam.remove("password");
                    jsonParam.remove("startTime");
                    jsonParam.remove("endTime");
                    jsonParam.remove("employeeID");
                    jsonParam.remove("projectID");
                    jsonParam.remove("uuid");
                    jsonParam.remove("name");
                    jsonParam.remove("managerID");


                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                ProjectUsername.setIsRunningPost(false);
                // Log.d("Quentins Log", "U8");
                // Log.d("Duplicate", "5Started post command");
                return true;
            } else {
                // Log.d("Duplicate", "Skipped Post Command");
                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean putFunction(Bundle data, String website) {
        HttpURLConnection urlConnection = null;
        try {
            // Log.d("Quentins Log", "PUT1");
            URL requestURL;
            if (website == EmplyeePostUrl) {
                requestURL = new URL(website + "/" + data.get("userIdToChange").toString());
            } else if (website == TimesPostUrl) {
                requestURL = new URL(website + "/" + data.get("timeIdToChange").toString());
            } else if (website == ProjectsPostUrl) {
                requestURL = new URL(website + "/" + data.get("projIdToChange").toString());
            } else {
                Exception e = new Exception("Bad Url");
                throw e;
            }
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Host", "calvincs262-fall2018-teama.appspot.com");
            // Log.d("Quentins Log", "PUT2");
            urlConnection.connect();
            // Log.d("Quentins Log", "PUT3");

            JSONObject jsonParam;
            if (website == EmplyeePostUrl) {
                jsonParam = new JSONObject();
                jsonParam.put("name", data.get("newUsername").toString());
                jsonParam.put("password", data.get("newPassword").toString());
                // Log.d("Quentins Log", "PUT4");
            } else if (website == TimesPostUrl) {
                jsonParam = new JSONObject();
                jsonParam.put("endTime", data.get("newEndTime").toString());
                // Log.d("Quentins Log", "PUT4");
            } else if (website == ProjectsPostUrl) {
                jsonParam = new JSONObject();
                jsonParam.put("name", data.get("newProjectName").toString());
                jsonParam.put("managerID", Integer.parseInt(data.get("newManagerID").toString()));
                // Log.d("Quentins Log", "PUT4");
            } else {
                return false;
            }

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonParam.toString());
            // Log.d("Quentins Log", "PUT5");
            out.close();
            // Log.d("Quentins Log", "PUT6");

            int HttpResult = urlConnection.getResponseCode();
            String sb = "D/Quentins Log \n";
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb += line + "\n";
                }
                br.close();

                // Log.d("Quentins Log", HttpResult + sb);
                System.out.println("" + sb);

            } else {
                // Log.d("Quentins Log", HttpResult + " " + urlConnection.getResponseMessage());
                System.out.println(urlConnection.getResponseMessage());
            }
            // Log.d("Quentins Log", "PUT7");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        // Log.d("Quentins Log", "PUT8");
        return true;
    }

    private static boolean deleteFunction(Bundle data, String website) {
        HttpURLConnection urlConnection = null;
        try {
            // Log.d("Quentins Log", "1DELETE");
            URL requestURL;
            if (website == EmplyeePostUrl) {
                requestURL = new URL(website + "/" + data.get("userIdToDelete").toString());
            } else if (website == TimesPostUrl) {
                requestURL = new URL(website + "/" + data.get("timeIdToDelete").toString());
            } else if (website == ProjectsPostUrl) {
                requestURL = new URL(website + "/" + data.get("projIdToDelete").toString());
            } else {
                Exception e = new Exception("Bad Url");
                throw e;
            }
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.connect();


            int HttpResult = urlConnection.getResponseCode();
            String sb = "D/Quentins Log \n";
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb += line + "\n";
                }
                br.close();

                System.out.println("" + sb);

            } else {
                System.out.println(urlConnection.getResponseMessage());
            }
            // Log.d("Quentins Log", "7");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        // Log.d("Quentins Log", "8");
        return true;
    }

    public static String getFunction(String website) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String playerJSONString = null;
        try {
            URL requestURL = new URL(website);
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
   /* Since it's JSON, adding a newline isn't necessary (it won't affect
      parsing) but it does make debugging a *lot* easier if you print out the
      completed buffer for debugging. */
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            playerJSONString = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return playerJSONString;
    }

}


