import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.json.simple.JSONObject;

//retrieve weather data from API -
public class WeatherApp {
    public static JSONObject[] getWeatherData(String locationName) {
        JSONArray locationData = getLocationData(locationName);

        // extract latitude and longitude data
        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        //build API request URL with location coordinates
        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relativehumidity_2m,weathercode,windspeed_10m&timezone=auto";

        try {
            HttpURLConnection conn = fetchApiResponse(urlString);

            if (conn.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return null;
            }

            StringBuilder resultJson = new StringBuilder();
            Scanner scan = new Scanner(conn.getInputStream());
            while (scan.hasNext()) {
                resultJson.append(scan.nextLine());
            }

            scan.close();
            conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

            JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");
            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);
            int previousIndex = index;

            int[] indexes = new int[6];
            for(int i = 0; i < 6; i++) {
                indexes[i] = (index += 24);
                System.out.println(indexes[i]);
                if(i == 5) {
                    index = previousIndex;
                }
            }

            
            //get temperature
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);
            double[] temperatures = new double[6];
            for(int i = 0; i < 6; i++) {
                temperatures[i] = (double) temperatureData.get(indexes[i]);
                System.out.println(temperatures[i]);
            }

            //get weather code
            JSONArray weathercode = (JSONArray) hourly.get("weathercode");
            String weatherCondition = convertWeatherCode((long) weathercode.get(index));
            String[] weatherconditions = new String[6];
            for(int i = 0; i < 6; i++) {
                weatherconditions[i] = convertWeatherCode((long) weathercode.get(indexes[i]));
            }

            //get Humidity
            JSONArray relativeHumidity = (JSONArray) hourly.get("relativehumidity_2m");
            long humidity = (long) relativeHumidity.get(index);
            long[] humidities = new long[6];
            for(int i = 0; i < 6; i++) {
                humidities[i] = (long) relativeHumidity.get(indexes[i]);
            }

            //get WindSpeed
            JSONArray windspeedData = (JSONArray) hourly.get("windspeed_10m");
            double windspeed = (double) windspeedData.get(index);
            double[] windspeeds = new double[6];
            for(int i = 0; i < 6; i++) {
                windspeeds[i] = (double) windspeedData.get(indexes[i]);
            }

            //build the weather JSON data object that we are going to access in our frontend
            JSONObject weatherDataDay1 = new JSONObject();
            weatherDataDay1.put("temperature_day_1", temperature);
            weatherDataDay1.put("weather_condition_day_1", weatherCondition);
            weatherDataDay1.put("humidity_day_1", humidity);
            weatherDataDay1.put("windspeed_day_1", windspeed);

            JSONObject weatherDataDay2 = new JSONObject();
            weatherDataDay2.put("temperature_day_2", temperatures[0]);
            weatherDataDay2.put("weather_condition_day_2", weatherconditions[0]);
            weatherDataDay2.put("humidity_day_2", humidities[0]);
            weatherDataDay2.put("windspeed_day_2", windspeeds[0]);

            JSONObject weatherDataDay3 = new JSONObject();
            weatherDataDay3.put("temperature_day_3", temperatures[1]);
            weatherDataDay3.put("weather_condition_day_3", weatherconditions[1]);
            weatherDataDay3.put("humidity_day_3", humidities[1]);
            weatherDataDay3.put("windspeed_day_3", windspeeds[1]);

            JSONObject weatherDataDay4 = new JSONObject();
            weatherDataDay4.put("temperature_day_4", temperatures[2]);
            weatherDataDay4.put("weather_condition_day_4", weatherconditions[2]);
            weatherDataDay4.put("humidity_day_4", humidities[2]);
            weatherDataDay4.put("windspeed_day_4", windspeeds[2]);

            JSONObject weatherDataDay5 = new JSONObject();
            weatherDataDay5.put("temperature_day_5", temperatures[3]);
            weatherDataDay5.put("weather_condition_day_5", weatherconditions[3]);
            weatherDataDay5.put("humidity_day_5", humidities[3]);
            weatherDataDay5.put("windspeed_day_5", windspeeds[3]);

            JSONObject weatherDataDay6 = new JSONObject();
            weatherDataDay6.put("temperature_day_6", temperatures[4]);
            weatherDataDay6.put("weather_condition_day_6", weatherconditions[4]);
            weatherDataDay6.put("humidity_day_6", humidities[4]);
            weatherDataDay6.put("windspeed_day_6", windspeeds[4]);

            JSONObject weatherDataDay7 = new JSONObject();
            weatherDataDay7.put("temperature_day_7", temperatures[5]);
            weatherDataDay7.put("weather_condition_day_7", weatherconditions[5]);
            weatherDataDay7.put("humidity_day_7", humidities[5]);
            weatherDataDay7.put("windspeed_day_7", windspeeds[5]);

            JSONObject[] weatherData = {weatherDataDay1, weatherDataDay2, weatherDataDay3,
                                        weatherDataDay4, weatherDataDay5, weatherDataDay6,
                                        weatherDataDay7};

            return weatherData;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getLocationData(String locationName) {
        locationName = locationName.replaceAll(" ", "+");
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                locationName +"&count=10&language=en&format=json";

        try {
            HttpURLConnection conn = fetchApiResponse(urlString);
            //Check Connection Response
            if(conn.getResponseCode() != 200) {
                System.out.println("Error: Could no connect to API");
            } else {
                //store Api Results
                StringBuilder resultJson  = new StringBuilder();
                Scanner scan = new Scanner(conn.getInputStream());

                //Read and Store the resulting data into our string builder
                while(scan.hasNext()) {
                    resultJson.append(scan.nextLine());
                }

                scan.close();
                conn.disconnect();

                //parse the JSON string into JSON Obj
                JSONParser parser = new JSONParser();
                JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                JSONArray locationData = (JSONArray) resultJsonObj.get("results");
                return locationData;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static int findIndexOfCurrentTime(JSONArray timeList) {
        String currentTime = getCurrentTime();

        //iterate through the time list and see which one matches our current time
        for(int i = 0; i < timeList.size(); i++) {
            String time = (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)) {
                //return index
                return i;
            }
        }
        return 0;
    }

    public static String getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        //Format according to the API Format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        //Format and print current Date and Time
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    private static String convertWeatherCode(long weatherCode) {
        String weatherCondition = "";
        if(weatherCode == 0L) {
            weatherCondition = "Clear";
        } else if (weatherCode > 0L && weatherCode <= 3L) {
            weatherCondition = "Cloudy";
        } else if ((weatherCode >= 51L && weatherCode <= 67L) || (weatherCode >= 80L && weatherCode <= 99L)) {
            weatherCondition = "Rain";
        } else if (weatherCode >= 71L && weatherCode <= 77L) {
            weatherCondition = "Snow";
        }

        return weatherCondition;
    }
}
