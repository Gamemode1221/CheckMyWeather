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


    public void checkWeather() {
        // 포스팅 참고 (https://walkinpcm.blogspot.com/2016/03/java-open-api.html)
        try {
            // 대림대학교의 위도 및 경도
            String lat = "37.4036262"; // 위도
            String lon = "126.9303398"; // 경도

            String apiKey = "5c4e0cf0a8fbe52219385b47406bc016"; // openweathermap 사이트의 개인 API key
            String urlStr = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey; // 위도, 경도, API key를 사용하여 날씨정보 획득

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

            // 지역 삽입
            location = jsonObj.get("name").toString();

            // 날씨 삽입
            JSONArray weatherArray = (JSONArray) jsonObj.get("weather");
            JSONObject obj = (JSONObject) weatherArray.get(0);
            weather = obj.get("main").toString();

            // 온도 삽입 (절대온도에서 섭씨로 변환)
            JSONObject mainArray = (JSONObject) jsonObj.get("main");
            double kTemp = Double.parseDouble(mainArray.get("temp").toString());
            double temp = kTemp-273.15;
            temperature = (int) Math.round(temp) + "";

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
