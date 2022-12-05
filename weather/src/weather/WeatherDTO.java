package weather;

import java.util.Date;

public class WeatherDTO {
    private String id;
    private String location;
    private String weather;
    private String temperature;
    private Date wDate;

    public WeatherDTO(String id, String location, String weather, String temperature, Date wDate) {
        this.id = id;
        this.location = location;
        this.weather = weather;
        this.temperature = temperature;
        this.wDate = wDate;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeather() {
        return weather;
    }
    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Date getwDate() {
        return wDate;
    }
    public void setwDate(Date wDate) {
        this.wDate = wDate;
    }
}
