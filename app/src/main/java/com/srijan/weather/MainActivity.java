package com.srijan.weather;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import Api.ApiClient;
import Api.ApiInstance;
import Model.Main_Weather;
import Model.Suncondition;
import Model.Temperature;
import Model.Weather;
import Model.Wind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    private static final int REQUEST_LOCATION = 101;
    private static final String TAG = "This";
    TextView condition, Min_Temp, Max_Temp, main_temp;
    TextView Pressusre, Humidity, Creator, date;
    TextView Sunrise_time, Sunset_time, Windspeed, cityName;
    String apiKey = "8f4f469b9839fe58b4adfdd7a6aa6179";
    double lats, lon;
    String latitude,longitude;
    LocationManager locationManager;
    SwipeRefreshLayout swipeRefreshLayout;

    ApiInstance apiInstance = ApiClient.getRetrofit().create(ApiInstance.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);

        condition = findViewById(R.id.weather_condition);
        Min_Temp = findViewById(R.id.min_Temp);
        Max_Temp = findViewById(R.id.max_Temp);
        Pressusre = findViewById(R.id.text_Pressure_number);
        Humidity = findViewById(R.id.text_Humidity_number);
        Creator = findViewById(R.id.text_creator);
        Windspeed = findViewById(R.id.text_Wind_Number);
        cityName = findViewById(R.id.cityName);
        main_temp = findViewById(R.id.temp);
        date = findViewById(R.id.date);
        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        Sunrise_time = findViewById(R.id.sunrise_time);
        Sunset_time = findViewById(R.id.sunsetTime);

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast toast = Toast.makeText(MainActivity.this, "Turn on Location and Refresh", Toast.LENGTH_SHORT);
            toast.show();
            OnGPS();

        }else{
            getLocation();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
                getLocation();
                apiInstance.getWeather(lats, lon, apiKey).enqueue(new Callback<Main_Weather>() {
                    @SuppressLint({"ResourceType", "SetTextI18n"})
                    @Override
                    public void onResponse(@NonNull Call<Main_Weather> call, @NonNull Response<Main_Weather> response) {
                        if (response.isSuccessful()) {
                            Main_Weather main_weather = response.body();

                            assert main_weather != null;
                            Temperature temperature = main_weather.getTemperature();
                            Suncondition suncondition = main_weather.getSuncondition();
                            Wind Wind_Speed = main_weather.getWind();
                            List<Weather> weatherList = main_weather.getWeatherList();

                            double normal_temp = temperature.getFeels_like() - 273.15;
                            int normal_temp2 = (int) normal_temp;
                            main_temp.setText(Integer.toString(normal_temp2) + " \u2103");

                            double temp = temperature.getTemp_min() - 273.15;
                            int min_temp = (int) temp;
                            Min_Temp.setText("Min Temp : " + Integer.toString(min_temp) + " \u2103");

                            double temp2 = temperature.getTemp_max() - 273.15;
                            int max_temp = (int) temp2;
                            Max_Temp.setText("Max Temp : " + Integer.toString(max_temp) + " \u2103");

                            double humidity1 = temperature.getHumidity();
                            int humidity = (int) humidity1;
                            Humidity.setText(Integer.toString(humidity) + "%");

                            double pressure1 = temperature.getPressure();
                            int pressure = (int) pressure1;
                            Pressusre.setText(Integer.toString(pressure) + "hpa");

                            Creator.setText("Created by Srijan Khadka");

                            double wind = Wind_Speed.getSpeed();
                            int wind2 = (int) wind;
                            Windspeed.setText(Integer.toString(wind2) + " Km/hr");

                            String name = main_weather.getName();
                            cityName.setText(name);

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat("EEE,yyyy-MM-dd HH: mm a");
                            Date Date = new Date();
                            date.setText(fmt.format(Date));

                            condition.setText(weatherList.get(0).getMain());

                            java.util.Date date1 = new Date(suncondition.getSunrise() * 1000);
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
                            Sunrise_time.setText(simpleDateFormat.format(date1));

                            java.util.Date date2 = new Date(suncondition.getSunset() * 1000);
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("h:mm a");
                            Sunset_time.setText(simpleDateFormat2.format(date2));





                        }

                    }

                    @Override
                    public void onFailure(Call<Main_Weather> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Turn On Internet", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        

            apiInstance.getWeather(lats, lon, apiKey).enqueue(new Callback<Main_Weather>() {
                @SuppressLint({"ResourceType", "SetTextI18n", "SimpleDateFormat"})
                @Override
                public void onResponse(@NonNull Call<Main_Weather> call, @NonNull Response<Main_Weather> response) {
                    if (response.isSuccessful()) {
                        Main_Weather main_weather = response.body();

                        assert main_weather != null;
                        Temperature temperature = main_weather.getTemperature();
                        Suncondition suncondition = main_weather.getSuncondition();
                        Wind Wind_Speed = main_weather.getWind();
                        List<Weather> weatherList = main_weather.getWeatherList();

                        double normal_temp = temperature.getFeels_like() - 273.15;
                        int normal_temp2 = (int) normal_temp;
                        main_temp.setText(Integer.toString(normal_temp2) + " \u2103");

                        double temp = temperature.getTemp_min() - 273.15;
                        int min_temp = (int) temp;
                        Min_Temp.setText("Min Temp : " + Integer.toString(min_temp) + " \u2103");

                        double temp2 = temperature.getTemp_max() - 273.15;
                        int max_temp = (int) temp2;
                        Max_Temp.setText("Max Temp : " + Integer.toString(max_temp) + " \u2103");

                        double humidity1 = temperature.getHumidity();
                        int humidity = (int) humidity1;
                        Humidity.setText(Integer.toString(humidity) + "%");

                        double pressure1 = temperature.getPressure();
                        int pressure = (int) pressure1;
                        Pressusre.setText(Integer.toString(pressure) + "hpa");

                        Creator.setText("Created by Srijan Khadka");

                        double wind = Wind_Speed.getSpeed();
                        int wind2 = (int) wind;
                        Windspeed.setText(Integer.toString(wind2) + " Km/hr");

                        String name = main_weather.getName();
                        cityName.setText(name);

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat("EEE,yyyy-MM-dd HH: mm a");
                        Date Date = new Date();

                        date.setText(fmt.format(Date));

                        java.util.Date date1 = new Date(suncondition.getSunrise() * 1000);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
                        Sunrise_time.setText(simpleDateFormat.format(date1));

                        java.util.Date date2 = new Date(suncondition.getSunset() * 1000);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("h:mm a");
                        Sunset_time.setText(simpleDateFormat2.format(date2));

                        condition.setText(weatherList.get(0).getMain());

                    }

                }


                @Override
                public void onFailure(Call<Main_Weather> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Turn On Internet", Toast.LENGTH_SHORT).show();
                }
            });


    }



    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                lats = Double.parseDouble(latitude);
                lon = Double.parseDouble(longitude);

            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();


                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                lats = Double.parseDouble(latitude);
                lon = Double.parseDouble(longitude);

            }
            else if (LocationPassive !=null)
            {
                double lat =LocationPassive.getLatitude();
               double  longi =LocationPassive.getLongitude();

                latitude=String.valueOf(lats);
                longitude=String.valueOf(longi);

                lats = Double.parseDouble(latitude);
                lon = Double.parseDouble(longitude);
            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }

    }


    private void OnGPS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable Location").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }



}