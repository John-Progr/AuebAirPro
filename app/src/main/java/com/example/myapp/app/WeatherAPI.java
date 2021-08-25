package com.example.myapp.app;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.net.URL;
import java.util.*;
import android.util.Log;
import java.time.LocalDate;
import java.time.Month;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherAPI extends AsyncTask<String,Void,Integer> {
    private final String LOG_TAG=WeatherAPI.class.getSimpleName();


    private int getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {
        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "list";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        JSONObject dayForecast = weatherArray.getJSONObject(0);//


        JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
        double high = temperatureObject.getDouble(OWM_MAX);
        double low = temperatureObject.getDouble(OWM_MIN);
        int mean=(int)(high+low)/2;

        return mean;

    }
    @Override
    protected Integer doInBackground(String... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        // Log.d("Didnt recieve shit","lul");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        if (params.length == 0) {
            Log.d("Didnt recieve shit","no parametrs");
            return 0;
        }
        int numDays = 7;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;
        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            String baseUrl = "https://api.openweathermap.org/data/2.5/forecast/daily?id=264371&mode=json&units=metric&cnt=7";
            // Here insert your unique APPID
            String apiKey = "&APPID=612ce9a4c7726a3a0a00a69b84b9a01a";
            URL url = new URL(baseUrl.concat(apiKey));
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return 0;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return 0;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return 0;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return getWeatherDataFromJson(forecastJsonStr, numDays);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result !=0) {
            AC_Activity.temp=result;
        }
    }
}