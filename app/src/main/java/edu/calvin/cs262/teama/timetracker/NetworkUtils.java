package edu.calvin.cs262.teama.timetracker;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
        Log.d("Quentins Log", queryString);
        if (method == "getData") {
            if (Integer.parseInt(queryString) == 0) {
                result += "AllGetData" + "#@!BREAK!@#";
                result += getFunction(EmplyeesUrl);
                result += "#@!BREAK!@#" + getFunction(ProjectsUrl);
                result += "#@!BREAK!@#" + getFunction(TimesUrl);
                Log.d("Quentins Log", result);
                return result;
            } else if (Integer.parseInt(queryString) == 1) {
                result += "TimesGetData" + "#@!BREAK!@#";
                result += getFunction(TimesUrl);
                Log.d("Quentins Log", result);
                return result;
            } else if (Integer.parseInt(queryString) == 2) {
                result += "ProjectGetData" + "#@!BREAK!@#";
                result += getFunction(ProjectsUrl);
                Log.d("Quentins Log", result);
                return result;
            } else if (Integer.parseInt(queryString) == 3) {
                result += "UserGetData" + "#@!BREAK!@#";
                result += getFunction(EmplyeesUrl);
                Log.d("Quentins Log", result);
                return result;
            } else {
                Log.d("Quentins Log", "returned nothing");
                return "GetFailed";
            }

        } else if (method == "postData") {
            if (Integer.parseInt(queryString) == 1) {
                boolean status = postFunction(data, TimesPostUrl);
                if (status) {
                    return "TimePostSucsessful";
                } else {
                    return "TimePostFail";
                }

            } else if (Integer.parseInt(queryString) == 2) {
                boolean status = postFunction(data, ProjectsPostUrl);
                if (status) {
                    return "ProjPostSucsessful";
                } else {
                    return "ProjPostFail";
                }

            } else if (Integer.parseInt(queryString) == 3) {
                boolean status = postFunction(data, EmplyeePostUrl);
                if (status) {
                    return "UserPostSucsessful";
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
                    return "TimePutSucsessful";
                } else {
                    return "TimePutFail";
                }

            } else if (Integer.parseInt(queryString) == 2) {
                boolean status = putFunction(data, ProjectsPostUrl);
                if (status) {
                    return "ProjPutSucsessful";
                } else {
                    return "ProjPutFail";
                }

            } else if (Integer.parseInt(queryString) == 3) {
                boolean status = putFunction(data, EmplyeePostUrl);
                if (status) {
                    return "UserPutSucsessful";
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
                    return "TimeDelSucsessful";
                } else {
                    return "TimeDelFail";
                }

            } else if (Integer.parseInt(queryString) == 2) {
                boolean status = deleteFunction(data, ProjectsPostUrl);
                if (status) {
                    return "ProjDelSucsessful";
                } else {
                    return "ProjDelFail";
                }

            } else if (Integer.parseInt(queryString) == 3) {
                boolean status = deleteFunction(data, EmplyeePostUrl);
                if (status) {
                    return "UserDelSucsessful";
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
        HttpURLConnection urlConnection = null;
        try {
            Log.d("Quentins Log", "U1");
            URL requestURL = new URL(website);
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Host", "calvincs262-fall2018-teama.appspot.com");
            Log.d("Quentins Log", "U2");
            urlConnection.connect();
            Log.d("Quentins Log", "U3");

            JSONObject jsonParam;
            if (website == EmplyeePostUrl) {
                jsonParam = new JSONObject();
                jsonParam.put("username", data.get("username").toString());
                jsonParam.put("password", data.get("password").toString());
                Log.d("Quentins Log", "U4");
            } else if (website == TimesPostUrl) {
                jsonParam = new JSONObject();
                jsonParam.put("startTime", data.get("startTime").toString());
                jsonParam.put("endTime", data.get("endTime").toString());
//                jsonParam.put("employeeID", Integer.parseInt(data.get("employeeID").toString()));
                jsonParam.put("employeeID", Integer.parseInt(data.get("employeeID").toString()));
                jsonParam.put("projectID", Integer.parseInt(data.get("projectID").toString()));
                jsonParam.put("uuid", data.get("UUID").toString());
                Log.d("Quentins Log", data.get("UUID").toString());
                Log.d("Quentins Log", "U4");
            } else if (website == ProjectsPostUrl) {
                jsonParam = new JSONObject();
                jsonParam.put("name", data.get("projectName").toString());
                jsonParam.put("managerID", Integer.parseInt(data.get("managerID").toString()));
                Log.d("Quentins Log", "U4");
            } else {
                return false;
            }

            Log.d("Quentins Log", jsonParam.toString());

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonParam.toString());
            Log.d("Quentins Log", "U5");
            out.close();
            Log.d("Quentins Log", "U6");

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
            Log.d("Quentins Log", "U7");


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        Log.d("Quentins Log", "U8");
        return true;
    }

    private static boolean putFunction(Bundle data, String website) {
        HttpURLConnection urlConnection = null;
        try {
            Log.d("Quentins Log", "1");
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
            Log.d("Quentins Log", "2");
            urlConnection.connect();
            Log.d("Quentins Log", "3");

            JSONObject jsonParam;
            if (website == EmplyeePostUrl) {
                jsonParam = new JSONObject();
                jsonParam.put("name", data.get("newUsername").toString());
                jsonParam.put("password", data.get("newPassword").toString());
                Log.d("Quentins Log", "4");
            } else if (website == TimesPostUrl) {
                jsonParam = new JSONObject();
                jsonParam.put("startTime", data.get("newStartTime").toString());
                jsonParam.put("endTime", data.get("newEndTime").toString());
                jsonParam.put("employeeID", Integer.parseInt(data.get("newEmployeeID").toString()));
                jsonParam.put("projectID", Integer.parseInt(data.get("newProjectID").toString()));
                jsonParam.put("uuid", data.get("newUUID").toString());
                Log.d("Quentins Log", "4");
            } else if (website == ProjectsPostUrl) {
                jsonParam = new JSONObject();
                jsonParam.put("name", data.get("newProjectName").toString());
                jsonParam.put("managerID", Integer.parseInt(data.get("newManagerID").toString()));
                Log.d("Quentins Log", "4");
            } else {
                return false;
            }

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonParam.toString());
            Log.d("Quentins Log", "5");
            out.close();
            Log.d("Quentins Log", "6");

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
            Log.d("Quentins Log", "7");

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
        Log.d("Quentins Log", "8");
        return true;
    }

    private static boolean deleteFunction(Bundle data, String website) {
        HttpURLConnection urlConnection = null;
        try {
            Log.d("Quentins Log", "1");
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
            Log.d("Quentins Log", "7");

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
        Log.d("Quentins Log", "8");
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


