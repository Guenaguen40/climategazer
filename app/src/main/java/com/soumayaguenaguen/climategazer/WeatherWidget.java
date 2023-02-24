package com.soumayaguenaguen.climategazer;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherWidget extends AppWidgetProvider {
    String CITY = "Tunisia,Tunis";
    String API = "75f3399b57fcd26ffff6f5ffa20ae308";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
            new WeatherTask(context, views, appWidgetManager, appWidgetId).execute();
        }
    }

    private class WeatherTask extends AsyncTask<String, Void, String> {
        private Context context;
        private RemoteViews views;
        private AppWidgetManager appWidgetManager;
        private int appWidgetId;

        public WeatherTask(Context context, RemoteViews views, AppWidgetManager appWidgetManager, int appWidgetId) {
            this.context = context;
            this.views = views;
            this.appWidgetManager = appWidgetManager;
            this.appWidgetId = appWidgetId;
        }

        @Override
        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
                String temp = main.getString("temp") + "°C";
                String humidity = main.getString("humidity");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");

                // Update the RemoteViews with the new data
                views.setTextViewText(R.id.address, address);
                views.setTextViewText(R.id.statusTxt, weatherDescription.toUpperCase());
                views.setTextViewText(R.id.temp, temp);
                views.setTextViewText(R.id.wind, windSpeed);
                views.setTextViewText(R.id.humidity, humidity);

                // Update the widget with the new views
                appWidgetManager.updateAppWidget(appWidgetId, views);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
