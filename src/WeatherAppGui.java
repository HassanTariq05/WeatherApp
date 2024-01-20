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

public class WeatherAppGui extends JFrame {
    private WeatherApp weatherApp;
    private JSONObject weatherData;
    public WeatherAppGui() {
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.darkGray);
        setSize(450,650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\hp\\Desktop\\WeatherApp\\src\\assets\\weatherIcon.png");
        setIconImage(icon);

        weatherApp = new WeatherApp();
        addGuiComponents();
    }

    public void addGuiComponents() {

        //Search Bar
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15,15,351,45);
        searchTextField.setFont(new Font("Arial", Font.BOLD, 24));
        searchTextField.setBackground(new Color(100, 100, 100));
        searchTextField.setForeground(Color.white);
        searchTextField.setCaretColor(Color.white);
        searchTextField.setBorder(new LineBorder(Color.darkGray , 4));
        add(searchTextField);

        //Weather Image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0,125,450,217);
        add(weatherConditionImage);

        //Temperature Text
        JLabel temperatureText = new JLabel("Temperature: N/A");
        temperatureText.setBounds(0,350,450,54);
        temperatureText.setFont(new Font("Arial", Font.BOLD, 48));
        temperatureText.setForeground(Color.WHITE);
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        //Weather Condition Description
        JLabel weatherConditionDesc = new JLabel("Weather Condition: N/A");
        weatherConditionDesc.setBounds(0,405,450,36);
        weatherConditionDesc.setFont(new Font("Arial",Font.PLAIN,32));
        weatherConditionDesc.setForeground(Color.WHITE);
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

//        Humidity Image
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        //Humidity Text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> N/A</html>");
        humidityText.setBounds(100,500,85,55);
        humidityText.setFont(new Font("Arial", Font.PLAIN, 16));
        humidityText.setForeground(Color.WHITE);
        add(humidityText);

        //WindSpeed Image
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(200,500,74,66);
        add(windSpeedImage);

        //WindSpeed Text
        JLabel windSpeedText = new JLabel("<html><b>Wind Speed</b> N/A</html>");
        windSpeedText.setBounds(300,500,85,55);
        windSpeedText.setFont(new Font("Arial", Font.PLAIN, 16));
        windSpeedText.setForeground(Color.WHITE);
        add(windSpeedText);

        //Search Button
        JButton button = new JButton(loadImage("src/assets/search.png"));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(new Color(74, 91, 182));
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setBounds(375,17,45,40);
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

                //update weather Image
                String weatherCondition = (String) weatherData.get("weather_condition");

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
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.pngImage"));
                        break;
                }

                // update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");

                // update weatherCondition text
                weatherConditionDesc.setText(weatherCondition);

                //update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                //update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
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
}