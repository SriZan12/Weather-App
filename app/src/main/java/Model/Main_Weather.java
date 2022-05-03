package Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Main_Weather {

    private float timezone;
    private float id;
    private String name;
    private float cod;

    @SerializedName("main")
    Temperature temperature;

    @SerializedName("sys")
    Suncondition suncondition;

    @SerializedName("wind")
    Wind wind;

    @SerializedName("weather")
    List<Weather> weatherList = new ArrayList<>();

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Suncondition getSuncondition() {
        return suncondition;
    }

    public void setSuncondition(Suncondition suncondition) {
        this.suncondition = suncondition;
    }

    public float getTimezone() {
        return timezone;
    }

    public void setTimezone(float timezone) {
        this.timezone = timezone;
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCod() {
        return cod;
    }

    public void setCod(float cod) {
        this.cod = cod;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

}
