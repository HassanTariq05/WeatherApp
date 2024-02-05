import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import org.json.simple.JSONObject;

//retrieve weather data from API -
public class WeatherApp {
    public static String currentHour;
    public static JSONObject getWeatherData(String locationName) {
        JSONArray locationData = getLocationData(locationName);

        // extract latitude and longitude data
        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        //build API request URL with location coordinates
        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relativehumidity_2m,weathercode,windspeed_10m&timezone=auto";

        System.out.println(urlString);

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
            System.out.println("Current Index: " + index);
            int previousIndex = index;

            //get temperature for upcoming 24 hours
            JSONArray temperatureDataForHours = (JSONArray) hourly.get("temperature_2m");
            double[] temperaturesForHours = new double[24];
            for(int i = 0; i < temperaturesForHours.length; i++) {
                temperaturesForHours[i] = (double) temperatureDataForHours.get(i);
                System.out.println(temperaturesForHours[i]);
            }

            //get weather code for upcoming 24 hours
            JSONArray weatherCodeForHours = (JSONArray) hourly.get("weathercode");
            String[] weatherconditionsForHours = new String[24];
            for(int i = 0; i < weatherconditionsForHours.length; i++) {
                weatherconditionsForHours[i] = convertWeatherCode((long) weatherCodeForHours.get(i));
                System.out.println("Code" + weatherconditionsForHours[i]);
            }

            //build the weather JSON data object that we are going to access in our frontend
            JSONObject weatherDataHours = new JSONObject();
            for (int i = 0; i < temperaturesForHours.length; i++) {
                weatherDataHours.put("temperature_hour_" + (i), temperaturesForHours[i]);
                weatherDataHours.put("weather_condition_hour_" + (i), weatherconditionsForHours[i]);
            }


            //Indexes for upcoming weekdays
            int[] indexForWeek = new int[6];
            for(int i = 0; i < 6; i++) {
                indexForWeek[i] = (index += 24);
//                System.out.println(indexForWeek[i]);
                if(i == 5) {
                    index = previousIndex;
                }
            }

            //get temperature for upcoming weekdays
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);
            double[] temperatures = new double[6];
            for(int i = 0; i < 6; i++) {
                temperatures[i] = (double) temperatureData.get(indexForWeek[i]);
//                System.out.println(temperatures[i]);
            }

            //get weather code
            JSONArray weathercode = (JSONArray) hourly.get("weathercode");
            String weatherCondition = convertWeatherCode((long) weathercode.get(index));
            String[] weatherconditions = new String[6];
            for(int i = 0; i < 6; i++) {
                weatherconditions[i] = convertWeatherCode((long) weathercode.get(indexForWeek[i]));
            }

            //get Humidity
            JSONArray relativeHumidity = (JSONArray) hourly.get("relativehumidity_2m");
            long humidity = (long) relativeHumidity.get(index);
            long[] humidities = new long[6];
            for(int i = 0; i < 6; i++) {
                humidities[i] = (long) relativeHumidity.get(indexForWeek[i]);
            }

            //get WindSpeed
            JSONArray windspeedData = (JSONArray) hourly.get("windspeed_10m");
            double windspeed = (double) windspeedData.get(index);
            double[] windspeeds = new double[6];
            for(int i = 0; i < 6; i++) {
                windspeeds[i] = (double) windspeedData.get(indexForWeek[i]);
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

            JSONObject[] weekDaysData = {weatherDataDay1, weatherDataDay2, weatherDataDay3,
                    weatherDataDay4, weatherDataDay5, weatherDataDay6,
                    weatherDataDay7};
            JSONObject weatherData = new JSONObject();
            weatherData.put("week_data", weekDaysData);
            weatherData.put("hours_data", weatherDataHours);

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
    public static String getHourForIndex(int index) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + index);
        Date date = cal.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh a");
        return simpleDateFormat.format(date);
    }
    public static String getDayNameForIndex(int index) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.get(Calendar.DAY_OF_WEEK) + index);
        Date date = cal.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        return simpleDateFormat.format(date);
    }
    public static String getDayDateForIndex(int index) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.get(Calendar.DAY_OF_WEEK) + index);
        Date date = cal.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
        return simpleDateFormat.format(date);
    }

    private static String convertWeatherCode(long weatherCode) {
        String weatherCondition = "";
        if(weatherCode == 0L) {
            weatherCondition = "Clear";
        } else if (weatherCode > 0L && weatherCode <= 3L) {
            weatherCondition = "Cloudy";
        } else if (weatherCode >= 4L && weatherCode <= 9L) {
            weatherCondition = "Haze";
        } else if (weatherCode >= 10L && weatherCode <= 19L) {
            weatherCondition = "Mist";
        } else if (weatherCode >= 20L && weatherCode <= 29L) {
            weatherCondition = "Rain";
        } else if (weatherCode >= 30L && weatherCode <= 39L) {
            weatherCondition = "Storm";
        } else if (weatherCode >= 40L && weatherCode <= 49L) {
            weatherCondition = "Fog";
        } else if ((weatherCode >= 50L && weatherCode <= 67L) || (weatherCode >= 80L && weatherCode <= 99L)) {
            weatherCondition = "Rain";
        } else if (weatherCode >= 71L && weatherCode <= 77L) {
            weatherCondition = "Snow";
        } else {
            weatherCondition = "Error";
        }

        return weatherCondition;
    }
}
