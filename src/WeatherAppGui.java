import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class  WeatherAppGui extends JFrame {
    private JSONObject[] weatherData;
    public WeatherAppGui() {
        super("Weather");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.darkGray);
        setSize(950,650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\hp\\Desktop\\WeatherApp\\src\\assets\\weatherIcon.png");
        setIconImage(icon);
        addGuiComponents();
    }

    public void addGuiComponents() {

        //Search Bar
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15,15,850,45);
        searchTextField.setFont(new Font("Arial", Font.BOLD, 24));
        searchTextField.setBackground(new Color(100, 100, 100));
        searchTextField.setForeground(Color.white);
        searchTextField.setCaretColor(Color.white);
        searchTextField.setBorder(new LineBorder(Color.darkGray , 4));
        add(searchTextField);

        //Weather Images
        JLabel weatherConditionImage1 = weatherConditionImage(30);
        JLabel weatherConditionImage2 = weatherConditionImage(160);
        JLabel weatherConditionImage3 = weatherConditionImage(290);
        JLabel weatherConditionImage4 = weatherConditionImage(420);
        JLabel weatherConditionImage5 = weatherConditionImage(550);
        JLabel weatherConditionImage6 = weatherConditionImage(680);
        JLabel weatherConditionImage7 = weatherConditionImage(810);

        //Temperature Text
        JLabel temperatureText1 = temperatureText(5);
        JLabel temperatureText2 = temperatureText(135);
        JLabel temperatureText3 = temperatureText(270);
        JLabel temperatureText4 = temperatureText(400);
        JLabel temperatureText5 = temperatureText(530);
        JLabel temperatureText6 = temperatureText(660);
        JLabel temperatureText7 = temperatureText(790);

        //Weather Condition Description
        JLabel weatherCondDesc1 = weatherConditionDesc(0);
        JLabel weatherCondDesc2 = weatherConditionDesc(130);
        JLabel weatherCondDesc3 = weatherConditionDesc(265);
        JLabel weatherCondDesc4 = weatherConditionDesc(395);
        JLabel weatherCondDesc5 = weatherConditionDesc(525);
        JLabel weatherCondDesc6 = weatherConditionDesc(655);
        JLabel weatherCondDesc7 = weatherConditionDesc(785);

//        Humidity Images
        humidityImage(35);
        humidityImage(165);
        humidityImage(305);
        humidityImage(435);
        humidityImage(565);
        humidityImage(695);
        humidityImage(825);

//        Humidity Texts
        JLabel humidityText1 = humidityText(30);
        JLabel humidityText2 = humidityText(160);
        JLabel humidityText3 = humidityText(300);
        JLabel humidityText4 = humidityText(430);
        JLabel humidityText5 = humidityText(560);
        JLabel humidityText6 = humidityText(690);
        JLabel humidityText7 = humidityText(820);


        //WindSpeed Images
        windSpeedImage(25);
        windSpeedImage(160);
        windSpeedImage(300);
        windSpeedImage(430);
        windSpeedImage(560);
        windSpeedImage(690);
        windSpeedImage(820);

        //WindSpeed Texts
        JLabel windSpeedText1 = windSpeedText(30);
        JLabel windSpeedText2 = windSpeedText(160);
        JLabel windSpeedText3 = windSpeedText(300);
        JLabel windSpeedText4 = windSpeedText(430);
        JLabel windSpeedText5 = windSpeedText(560);
        JLabel windSpeedText6 = windSpeedText(690);
        JLabel windSpeedText7 = windSpeedText(820);



        //Search Button
        JButton button = new JButton(loadImage("src/assets/search.png"));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(new Color(74, 91, 182));
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setBounds(870,17,45,40);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from the user
                String userInput = searchTextField.getText();

                // validate input -Removing all the whitespaces to ensure non-empty text
                if(userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                //retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                //update GUI
                updateGUI(0,
                        "weather_condition_day_1", weatherConditionImage1,
                         temperatureText1,"temperature_day_1",
                        weatherCondDesc1, "humidity_day_1", humidityText1,
                        "windspeed_day_1", windSpeedText1);

                updateGUI(1,
                        "weather_condition_day_2", weatherConditionImage2,
                        temperatureText2,"temperature_day_2",
                        weatherCondDesc2, "humidity_day_2", humidityText2,
                        "windspeed_day_2", windSpeedText2);

                updateGUI(2,
                        "weather_condition_day_3", weatherConditionImage3,
                        temperatureText3,"temperature_day_3",
                        weatherCondDesc3, "humidity_day_3", humidityText3,
                        "windspeed_day_3", windSpeedText3);

                updateGUI(3,
                        "weather_condition_day_4", weatherConditionImage4,
                        temperatureText4,"temperature_day_4",
                        weatherCondDesc4, "humidity_day_4", humidityText4,
                        "windspeed_day_4", windSpeedText4);

                updateGUI(4,
                        "weather_condition_day_5", weatherConditionImage5,
                        temperatureText5,"temperature_day_5",
                        weatherCondDesc5, "humidity_day_5", humidityText5,
                        "windspeed_day_5", windSpeedText5);

                updateGUI(5,
                        "weather_condition_day_6", weatherConditionImage6,
                        temperatureText6,"temperature_day_6",
                        weatherCondDesc6, "humidity_day_6", humidityText6,
                        "windspeed_day_6", windSpeedText6);

                updateGUI(6,
                        "weather_condition_day_7", weatherConditionImage7,
                        temperatureText7,"temperature_day_7",
                        weatherCondDesc7, "humidity_day_7", humidityText7,
                        "windspeed_day_7", windSpeedText7);
            }
        });
        add(button);
    }

    //used to create images to our gui components
    private ImageIcon loadImage(String resourcePath) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Could not load resources");
        return null;
    }
    private JLabel temperatureText(int xAxis) {
        JLabel temperatureText = new JLabel("0.0");
        temperatureText.setBounds(xAxis,195,130,54);
        temperatureText.setFont(new Font("Arial", Font.BOLD, 14));
        temperatureText.setForeground(Color.WHITE);
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);
        return temperatureText;
    }
    private JLabel weatherConditionDesc(int xAxis) {
        JLabel weatherConditionDesc = new JLabel("Clear");
        weatherConditionDesc.setBounds(xAxis,225,140,36);
        weatherConditionDesc.setFont(new Font("Arial",Font.PLAIN,14));
        weatherConditionDesc.setForeground(Color.WHITE);
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);
        return weatherConditionDesc;
    }
    private JLabel weatherConditionImage(int xAxis) {
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(xAxis,125,100,89);
        add(weatherConditionImage);
        return weatherConditionImage;
    }
    private JLabel humidityImage(int xAxis) {
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(xAxis,255,75,66);
        add(humidityImage);
        return humidityImage;
    }
    private JLabel humidityText(int xAxis) {
        JLabel humidityText = new JLabel("Humidity: 0%");
        humidityText.setBounds(xAxis,305,100,45);
        humidityText.setFont(new Font("Arial", Font.BOLD, 14));
        humidityText.setForeground(Color.WHITE);
        humidityText.setBackground(Color.PINK);
        add(humidityText);
        return humidityText;
    }
    private JLabel windSpeedImage(int xAxis) {
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(xAxis,350,74,66);
        add(windSpeedImage);
        return windSpeedImage;
    }
    private JLabel windSpeedText(int xAxis) {
        JLabel windSpeedText = new JLabel("Wind: 0km/h");
        windSpeedText.setBounds(xAxis,400,100,55);
        windSpeedText.setFont(new Font("Arial", Font.BOLD, 14));
        windSpeedText.setForeground(Color.WHITE);
        add(windSpeedText);
        return windSpeedText;
    }
    private void updateGUI(int index, String weatherConditionDay,
                           JLabel weatherConditionImage, JLabel temperatureText, String temperatureDay,
                            JLabel weatherCondDesc, String humidity_day,
                            JLabel humidityText, String windspeed_day, JLabel windspeedText) {
        String weatherCondition = (String) weatherData[index].get(weatherConditionDay);

        //depending on the condition, we'll update the weather image
        switch (weatherCondition) {
            case "Clear":
                weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                break;
            case "Cloudy":
                weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                break;
            case "Rain":
                weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                break;
            case "Snow":
                weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                break;
        }

        // update temperature text
        double temperature = (double) weatherData[index].get(temperatureDay);
        temperatureText.setText(temperature + " C");

        // update weatherCondition text
        weatherCondDesc.setText(weatherCondition);

        //update humidity text
        long humidity = (long) weatherData[index].get(humidity_day);
        humidityText.setText("Humidity: " + humidity + "%");

        //update windspeed text
        double windspeed = (double) weatherData[index].get(windspeed_day);
        windspeedText.setText("Wind: " + windspeed + "km/h");
    }
}