import org.json.simple.JSONObject;
import java.awt.Component;
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
    JPanel panel;
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel currentWeatherPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel hoursPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel newPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


    public WeatherAppGui() {
        super("Weather");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.PINK);
        setSize(500,650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\hp\\Desktop\\WeatherApp\\src\\assets\\weatherIcon.png");
        setIconImage(icon);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.RED);

        addGuiComponents();

        JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(pane, BorderLayout.CENTER);
    }

    public void addGuiComponents() {

        searchPanel.setBackground(new Color(66, 65, 65));
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));  // Set layout to BorderLayout
        searchPanel.setSize(100, 50);

        //Search Bar
        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(380, 50));
        searchTextField.setFont(new Font("Arial", Font.BOLD, 24));
        searchTextField.setBackground(new Color(100, 100, 100));
        searchTextField.setForeground(Color.white);
        searchTextField.setCaretColor(Color.white);
        searchTextField.setBorder(new LineBorder(Color.darkGray , 4));
        searchPanel.add(searchTextField, BorderLayout.CENTER);
        panel.add(searchPanel);

        //Search Button
        JButton button = new JButton(loadImage("src/assets/search.png"));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(new Color(74, 91, 182));
        button.setFocusPainted(false);
        button.setFocusable(false);
        searchPanel.add(button, BorderLayout.CENTER);

        currentWeatherPanel.setBackground(new Color(100, 100, 100));
        currentWeatherPanel.setPreferredSize(new Dimension(100,300));
        currentWeatherPanel.setLayout(new GridBagLayout());
        currentWeatherPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(currentWeatherPanel);

        hoursPanel.setBackground(Color.YELLOW);
        JLabel label = new JLabel("HoursPanel");
        JLabel label1 = new JLabel("HoursPanel");
        JLabel label2 = new JLabel("HoursPanel");
        JLabel label3 = new JLabel("HoursPanel");
        JLabel label4 = new JLabel("HoursPanel");
        JLabel label5 = new JLabel("HoursPanel");
        JLabel label6 = new JLabel("HoursPanel");
        JLabel label7 = new JLabel("HoursPanel");
        JLabel label8 = new JLabel("HoursPanel");
        JLabel label9 = new JLabel("HoursPanel");

        hoursPanel.setPreferredSize(new Dimension(900,70));
        hoursPanel.setLayout(new GridLayout(1,24,2,0));
        hoursPanel.add(label);
        hoursPanel.add(label1);
        hoursPanel.add(label2);
        hoursPanel.add(label3);
        hoursPanel.add(label4);
        hoursPanel.add(label5);
        hoursPanel.add(label6);
        hoursPanel.add(label7);
        hoursPanel.add(label8);
        hoursPanel.add(label9);
        JScrollPane hoursScrollPane = new JScrollPane(hoursPanel);
        hoursScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        hoursScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        hoursScrollPane.setPreferredSize(new Dimension(400, 90));
        panel.add(hoursScrollPane);

        newPanel.setBackground(Color.BLUE);
        JLabel labelll = new JLabel("BluePanel");
        newPanel.setPreferredSize(new Dimension(100,300));
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        newPanel.add(labelll);
        panel.add(newPanel);

//        newPanel.setBackground(Color.BLACK);
//        JLabel labelll = new JLabel("OrangePanel");
//        newPanel.setPreferredSize(new Dimension(100,100));
//        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
//        newPanel.add(labell);
//        panel.add(newPanel);

        //Weather Images
        JLabel weatherConditionImage1 = weatherConditionImage(currentWeatherPanel);
//        JLabel weatherConditionImage2 = weatherConditionImage(30);
//        JLabel weatherConditionImage3 = weatherConditionImage(30);
//        JLabel weatherConditionImage4 = weatherConditionImage(30);
//        JLabel weatherConditionImage5 = weatherConditionImage(30);
//        JLabel weatherConditionImage6 = weatherConditionImage(30);
//        JLabel weatherConditionImage7 = weatherConditionImage(30);

        //Temperature Text
        JLabel temperatureText1 = temperatureText(currentWeatherPanel);
//        JLabel temperatureText2 = temperatureText(135);
//        JLabel temperatureText3 = temperatureText(270);
//        JLabel temperatureText4 = temperatureText(400);
//        JLabel temperatureText5 = temperatureText(530);
//        JLabel temperatureText6 = temperatureText(660);
//        JLabel temperatureText7 = temperatureText(790);

        //Weather Condition Description
        JLabel weatherCondDesc1 = weatherConditionDesc(currentWeatherPanel);
//        JLabel weatherCondDesc2 = weatherConditionDesc(130);
//        JLabel weatherCondDesc3 = weatherConditionDesc(265);
//        JLabel weatherCondDesc4 = weatherConditionDesc(395);
//        JLabel weatherCondDesc5 = weatherConditionDesc(525);
//        JLabel weatherCondDesc6 = weatherConditionDesc(655);
//        JLabel weatherCondDesc7 = weatherConditionDesc(785);

//        Humidity Images
//        humidityImage(35, currentWeatherPanel);
//        humidityImage(165);
//        humidityImage(305);
//        humidityImage(435);
//        humidityImage(565);
//        humidityImage(695);
//        humidityImage(825);

//        Humidity Texts
        JLabel humidityText1 = humidityText(currentWeatherPanel);
//        JLabel humidityText2 = humidityText(160);
//        JLabel humidityText3 = humidityText(300);
//        JLabel humidityText4 = humidityText(430);
//        JLabel humidityText5 = humidityText(560);
//        JLabel humidityText6 = humidityText(690);
//        JLabel humidityText7 = humidityText(820);


        //WindSpeed Images
//        windSpeedImage(25, currentWeatherPanel);
//        windSpeedImage(160);
//        windSpeedImage(300);
//        windSpeedImage(430);
//        windSpeedImage(560);
//        windSpeedImage(690);
//        windSpeedImage(820);

        //WindSpeed Texts
        JLabel windSpeedText1 = windSpeedText(currentWeatherPanel);
//        JLabel windSpeedText2 = windSpeedText(160);
//        JLabel windSpeedText3 = windSpeedText(300);
//        JLabel windSpeedText4 = windSpeedText(430);
//        JLabel windSpeedText5 = windSpeedText(560);
//        JLabel windSpeedText6 = windSpeedText(690);
//        JLabel windSpeedText7 = windSpeedText(820);




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

//                updateGUI(1,
//                        "weather_condition_day_2", weatherConditionImage2,
//                        temperatureText2,"temperature_day_2",
//                        weatherCondDesc2, "humidity_day_2", humidityText2,
//                        "windspeed_day_2", windSpeedText2);

//                updateGUI(2,
//                        "weather_condition_day_3", weatherConditionImage3,
//                        temperatureText3,"temperature_day_3",
//                        weatherCondDesc3, "humidity_day_3", humidityText3,
//                        "windspeed_day_3", windSpeedText3);

//                updateGUI(3,
//                        "weather_condition_day_4", weatherConditionImage4,
//                        temperatureText4,"temperature_day_4",
//                        weatherCondDesc4, "humidity_day_4", humidityText4,
//                        "windspeed_day_4", windSpeedText4);
//
//                updateGUI(4,
//                        "weather_condition_day_5", weatherConditionImage5,
//                        temperatureText5,"temperature_day_5",
//                        weatherCondDesc5, "humidity_day_5", humidityText5,
//                        "windspeed_day_5", windSpeedText5);
//
//                updateGUI(5,
//                        "weather_condition_day_6", weatherConditionImage6,
//                        temperatureText6,"temperature_day_6",
//                        weatherCondDesc6, "humidity_day_6", humidityText6,
//                        "windspeed_day_6", windSpeedText6);
//
//                updateGUI(6,
//                        "weather_condition_day_7", weatherConditionImage7,
//                        temperatureText7,"temperature_day_7",
//                        weatherCondDesc7, "humidity_day_7", humidityText7,
//                        "windspeed_day_7", windSpeedText7);
            }
        });
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
    private JLabel temperatureText(JPanel panelName) {
        JLabel temperatureText = new JLabel("0.0");
        temperatureText.setHorizontalTextPosition(SwingConstants.CENTER);
        temperatureText.setFont(new Font("Arial", Font.BOLD, 14));
        temperatureText.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 5, 0); // Adjust the top margin if needed
        panelName.add(temperatureText, gbc);
        return temperatureText;
    }
    private JLabel weatherConditionDesc(JPanel panelName) {
        JLabel weatherConditionDesc = new JLabel("Clear");
        weatherConditionDesc.setFont(new Font("Arial",Font.PLAIN,14));
        weatherConditionDesc.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 5, 0); // Adjust the top margin if needed
        panelName.add(weatherConditionDesc, gbc);
        return weatherConditionDesc;
    }
    private JLabel weatherConditionImage(JPanel panelName) {
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        panelName.add(weatherConditionImage);
        return weatherConditionImage;
    }
    private JLabel humidityImage(Panel panelName) {
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        panelName.add(humidityImage);
        return humidityImage;
    }
    private JLabel humidityText(JPanel panelName) {
        JLabel humidityText = new JLabel("Humidity: 0%");
        humidityText.setFont(new Font("Arial", Font.BOLD, 14));
        humidityText.setForeground(Color.WHITE);
        humidityText.setBackground(Color.PINK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 5, 0); // Adjust the top margin if needed
        panelName.add(humidityText, gbc);
        return humidityText;
    }
    private JLabel windSpeedImage(JPanel panelName) {
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        panelName.add(windSpeedImage);
        return windSpeedImage;
    }
    private JLabel windSpeedText(JPanel panelName) {
        JLabel windSpeedText = new JLabel("Wind: 0km/h");
        windSpeedText.setFont(new Font("Arial", Font.BOLD, 14));
        windSpeedText.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 5, 0); // Adjust the top margin if needed
        panelName.add(windSpeedText, gbc);
        return windSpeedText;
    }
    private void updateGUI(int index, String weatherConditionDay,
                           JLabel weatherConditionImage, JLabel temperatureText, String temperatureDay,
                            JLabel weatherCondDesc, String humidity_day,
                            JLabel humidityText, String windspeed_day, JLabel windspeedText) {
        String weatherCondition = (String) weatherData[index].get(weatherConditionDay);
        weatherConditionImage.setIcon(loadImage("src/assets/"+weatherCondition.toLowerCase()+".png"));
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