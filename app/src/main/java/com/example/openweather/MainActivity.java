package com.example.openweather;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {

    SearchView locationSearchView;

    private static final String API_KEY = "b31f986179635820b5d31e28a4cf9fc2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationSearchView = findViewById(R.id.location);
        locationSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchWeatherData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        fetchWeatherData("jawalakhel,np");
    }

    private void fetchWeatherData(String location) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&units=metric&appid=" + API_KEY;
        String url2 = "https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&units=metric&appid=" + API_KEY;

        ProgressBar loader = findViewById(R.id.loader);
        RelativeLayout mainContainer = findViewById(R.id.mainContainer);
        TextView errorText = findViewById(R.id.errorText);

        loader.setVisibility(View.VISIBLE);
        mainContainer.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject main = response.getJSONObject("main");
                            JSONObject sys = response.getJSONObject("sys");
                            JSONObject wind = response.getJSONObject("wind");
                            JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                            long dt = response.getLong("dt");

                            String date = formatDate(dt);
                            String temp = main.getString("temp") + "°C";
                            String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                            String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                             String pressure = main.getString("pressure");
                            String humidity = main.getString("humidity");

                            String country = sys.getString("country");
                            long sunrise = sys.getLong("sunrise");
                            long sunset = sys.getLong("sunset");
                            String windSpeed = wind.getString("speed");
                            String weatherDescription = weather.getString("description");

                            String address = response.getString("name");

                            TextView dateTextView = findViewById(R.id.date);
                            TextView addressTextView = findViewById(R.id.address);
                            TextView statusTextView = findViewById(R.id.status);
                            TextView tempTextView = findViewById(R.id.temp);
                            TextView tempMinTextView = findViewById(R.id.temp_min);
                            TextView tempMaxTextView = findViewById(R.id.temp_max);
                            TextView sunriseTextView = findViewById(R.id.sunrise);
                            TextView sunsetTextView = findViewById(R.id.sunset);
                            TextView windTextView = findViewById(R.id.wind);
                            TextView pressureTextView = findViewById(R.id.pressure);
                            TextView humidityTextView = findViewById(R.id.humidity);
                            TextView countryTextView = findViewById(R.id.country);

                            dateTextView.setText(date);
                            addressTextView.setText(address);
                            statusTextView.setText(weatherDescription);
                            tempTextView.setText(temp);
                            tempMinTextView.setText(tempMin);
                            tempMaxTextView.setText(tempMax);
                            sunriseTextView.setText(formatTime(sunrise));
                            sunsetTextView.setText(formatTime(sunset));
                            windTextView.setText(windSpeed);
                            pressureTextView.setText(pressure);
                            humidityTextView.setText(humidity);
                            countryTextView.setText(country);

                            String iconCode = weather.getString("icon");
                            String iconUrl = "https://openweathermap.org/img/w/" + iconCode + ".png";
                            ImageView iconImageView = findViewById(R.id.weatherIcon);
                            Picasso.get().load(iconUrl).into(iconImageView);

                            loader.setVisibility(View.GONE);
                            mainContainer.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            showError();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError();
            }
        });

        JsonObjectRequest forecastJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray forecastList = response.getJSONArray("list");
                            JSONObject forecast1 = forecastList.getJSONObject(0);
                            JSONObject forecast2 = forecastList.getJSONObject(1);
                            JSONObject forecast3 = forecastList.getJSONObject(2);
                            JSONObject forecast4 = forecastList.getJSONObject(3);
                            JSONObject forecast5 = forecastList.getJSONObject(4);
                            JSONObject forecast6 = forecastList.getJSONObject(5);

                            String temp1 = forecast1.getJSONObject("main").getString("temp") + "°C";
                            String temp2 = forecast2.getJSONObject("main").getString("temp") + "°C";
                            String temp3 = forecast3.getJSONObject("main").getString("temp") + "°C";
                            String temp4 = forecast4.getJSONObject("main").getString("temp") + "°C";
                            String temp5 = forecast5.getJSONObject("main").getString("temp") + "°C";
                            String temp6 = forecast5.getJSONObject("main").getString("temp") + "°C";

                            String sky1 = forecast1.getJSONArray("weather").getJSONObject(0).getString("description");
                            String sky2 = forecast2.getJSONArray("weather").getJSONObject(0).getString("description");
                            String sky3 = forecast3.getJSONArray("weather").getJSONObject(0).getString("description");
                            String sky4 = forecast4.getJSONArray("weather").getJSONObject(0).getString("description");
                            String sky5 = forecast4.getJSONArray("weather").getJSONObject(0).getString("description");
                            String sky6 = forecast4.getJSONArray("weather").getJSONObject(0).getString("description");

                            long timestamp1 = forecast1.getLong("dt");
                            long timestamp2 = forecast2.getLong("dt");
                            long timestamp3 = forecast3.getLong("dt");
                            long timestamp4 = forecast4.getLong("dt");
                            long timestamp5 = forecast5.getLong("dt");
                            long timestamp6 = forecast6.getLong("dt");

                            String day1 = formatTime(timestamp1);
                            String day2 = formatTime(timestamp2);
                            String day3 = formatTime(timestamp3);
                            String day4 = formatTime(timestamp4);
                            String day5 = formatTime(timestamp5);
                            String day6 = formatTime(timestamp6);

                            TextView temp1TextView = findViewById(R.id.ftemp1);
                            TextView temp2TextView = findViewById(R.id.ftemp2);
                            TextView temp3TextView = findViewById(R.id.ftemp3);
                            TextView temp4TextView = findViewById(R.id.ftemp4);
                            TextView temp5TextView = findViewById(R.id.ftemp5);
                            TextView temp6TextView = findViewById(R.id.ftemp6);
                            TextView sky1TextView = findViewById(R.id.sky1);
                            TextView sky2TextView = findViewById(R.id.sky2);
                            TextView sky3TextView = findViewById(R.id.sky3);
                            TextView sky4TextView = findViewById(R.id.sky4);
                            TextView sky5TextView = findViewById(R.id.sky5);
                            TextView sky6TextView = findViewById(R.id.sky6);
                            TextView day1TextView = findViewById(R.id.day1);
                            TextView day2TextView = findViewById(R.id.day2);
                            TextView day3TextView = findViewById(R.id.day3);
                            TextView day4TextView = findViewById(R.id.day4);
                            TextView day5TextView = findViewById(R.id.day5);
                            TextView day6TextView = findViewById(R.id.day6);

                            temp1TextView.setText(temp1);
                            temp2TextView.setText(temp2);
                            temp3TextView.setText(temp3);
                            temp4TextView.setText(temp4);
                            temp5TextView.setText(temp5);
                            temp6TextView.setText(temp6);
                            sky1TextView.setText(sky1);
                            sky2TextView.setText(sky2);
                            sky3TextView.setText(sky3);
                            sky4TextView.setText(sky4);
                            sky4TextView.setText(sky5);
                            sky4TextView.setText(sky6);
                            day1TextView.setText(day1);
                            day2TextView.setText(day2);
                            day3TextView.setText(day3);
                            day4TextView.setText(day4);
                            day5TextView.setText(day5);
                            day6TextView.setText(day6);

                            loader.setVisibility(View.GONE);
                            mainContainer.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            showError();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
        Volley.newRequestQueue(this).add(forecastJsonObjectRequest);
    }
    private void showError() {
        ProgressBar loader = findViewById(R.id.loader);
        TextView errorText = findViewById(R.id.errorText);

        loader.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);
    }
    private String formatDate(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH);
        return dateFormat.format(new Date(timestamp * 1000));
    }
    private String formatTime(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        return dateFormat.format(new Date(timestamp * 1000));
    }
}
