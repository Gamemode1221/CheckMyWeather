package weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Weather {
    private static String location;
    private static String weather;
    private static String temperature;

    public String getCurrentLocation() {
        return location;
    }
    public String getCurrentWeather() {
        return weather;
    }
    public String getCurrentTemperature() {
        return temperature;
    }

    // 대림대학교의 위도 및 경도
    private static String lat = "37.4036262"; // 위도
    private static String lon = "126.9303398"; // 경도
    private static String apiKey = "5c4e0cf0a8fbe52219385b47406bc016";
    private static String urlStr = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey;

    public void checkWeather() {
        try {
            String line;
            String result = "";

            URL url = new URL(urlStr);

            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(url.openStream()));

            while ((line = br.readLine()) != null) {
                result = result.concat(line);
            }

            var jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result);

            //지역 출력
            location = jsonObj.get("name").toString();
//            System.out.println("지역 : " + location);

            //날씨 출력
            JSONArray weatherArray = (JSONArray) jsonObj.get("weather");
            JSONObject obj = (JSONObject) weatherArray.get(0);
            weather = obj.get("main").toString();
//            System.out.println("날씨 : " + weather);

            //온도 출력(절대온도라서 변환 필요)
            JSONObject mainArray = (JSONObject) jsonObj.get("main");
            double kTemp = Double.parseDouble(mainArray.get("temp").toString());
            double temp = kTemp-273.15;
            temperature = (int) Math.round(temp) + "";
//            System.out.printf("온도 : %.2f\n", temperature);

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}