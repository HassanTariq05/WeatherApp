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

    String baseAssetPath = "src/assets/";
    private JSONObject[] weatherDataWeekly;
    private JSONObject weatherDataHourly;
    JPanel panel;
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel currentWeatherPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel upcomingHoursPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel[] hoursPanel = new JPanel[24];
    JPanel[] weekDayPanel = new JPanel[6];
    JPanel newPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel[] currentHour = new JLabel[24];
    JLabel[] weekDayName = new JLabel[6];
    JLabel[] weekDayDate = new JLabel[6];
    JLabel[] weatherConditionImages = new JLabel[7];
    JLabel[] temperatureTexts = new JLabel[7];
    JLabel[] weatherCondDescs = new JLabel[7];
    JLabel[] humidityTexts = new JLabel[7];
    JLabel[] windSpeedTexts = new JLabel[7];



    public WeatherAppGui() {
        super("Weather");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.PINK);
        setSize(500,650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("/Users/shahidmehmood/IdeaProjects/WeatherApp/src/assets/search.png/weatherIcon.png");
        setIconImage(icon);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.DARK_GRAY);

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
        searchTextField.setPreferredSize(new Dimension(405, 50));
        searchTextField.setFont(new Font("Arial", Font.BOLD, 24));
        searchTextField.setBackground(new Color(100, 100, 100));
        searchTextField.setForeground(Color.white);
        searchTextField.setCaretColor(Color.white);
        searchTextField.setBorder(new LineBorder(Color.darkGray , 4));
        searchPanel.add(searchTextField, BorderLayout.CENTER);
        panel.add(searchPanel);

        //Search Button
        JButton button = new JButton("",loadImage(baseAssetPath+"search.png", 38, 38));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(Color.darkGray);
        button.setFocusPainted(false);
        button.setFocusable(false);
        searchPanel.add(button, BorderLayout.CENTER);
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });

        currentWeatherPanel.setBackground(new Color(100, 100, 100));
        currentWeatherPanel.setPreferredSize(new Dimension(100,300));
        currentWeatherPanel.setLayout(new GridBagLayout());
        currentWeatherPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(currentWeatherPanel);

        upcomingHoursPanel.setBackground(new Color(100,100,100));
        upcomingHoursPanel.setPreferredSize(new Dimension(1450,70));
        upcomingHoursPanel.setLayout(new GridLayout(1,24,2,0));

        int j = 0;
//        JLabel[] labels = new JLabel[23];
        for (int i = 0; i < hoursPanel.length; i++) {

            hoursPanel[i] = new JPanel();
            hoursPanel[i].setBackground(new Color(66, 65, 65));
            currentHour[i] = new JLabel();
            currentHour[i].setFont(new Font("Arial", Font.PLAIN, 10));
            currentHour[i].setForeground(Color.WHITE);
            String hour = WeatherApp.getHourForIndex(i).toUpperCase();
            currentHour[i].setText(hour);
            hoursPanel[i].add(currentHour[i]);
            hoursPanel[i].add(weatherConditionImageHours(hoursPanel[i]));
            hoursPanel[i].add(temperatureText(hoursPanel[i]));
            upcomingHoursPanel.add(hoursPanel[i]);
        }

        JScrollPane hoursScrollPane = new JScrollPane(upcomingHoursPanel);
        hoursScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        hoursScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        hoursScrollPane.setPreferredSize(new Dimension(400, 90));
        panel.add(hoursScrollPane);

        newPanel.setBackground(new Color(100, 100, 100));
        newPanel.setPreferredSize(new Dimension(200,300));
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        panel.add(newPanel);

        for(int i = 0; i < weekDayPanel.length; i++) {
            weekDayPanel[i] = new JPanel();
            weekDayPanel[i].setBackground(new Color(66, 65, 65));
            weekDayPanel[i].setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 4, true));
            weekDayPanel[i].setLayout(new FlowLayout(FlowLayout.CENTER, 80,8));
            weekDayDate[i] = new JLabel();
            weekDayDate[i].setFont(new Font("Arial", Font.BOLD, 12));
            weekDayDate[i].setForeground(Color.WHITE);
            String dayDate = WeatherApp.getDayDateForIndex(i).toUpperCase();
            weekDayDate[i].setText(dayDate);
            weekDayPanel[i].add(weekDayDate[i]);
            weekDayName[i] = new JLabel();
            weekDayName[i].setFont(new Font("Arial", Font.BOLD, 12));
            weekDayName[i].setForeground(Color.WHITE);
            String dayName = WeatherApp.getDayNameForIndex(i).toUpperCase();
            weekDayName[i].setText(dayName);
            weekDayPanel[i].add(weekDayName[i]);
            weekDayPanel[i].add(weatherConditionImageHours(weekDayPanel[i]));
            weekDayPanel[i].add(temperatureText(weekDayPanel[i]));
            newPanel.add(weekDayPanel[i]);
        }

        for(int i = 0; i < 7; i++) {
            if(i == 0) {
                weatherConditionImages[i] = weatherConditionImage(currentWeatherPanel);
                temperatureTexts[i] = temperatureText(currentWeatherPanel);
                weatherCondDescs[i] = weatherConditionDesc(currentWeatherPanel);
                humidityTexts[i] =  humidityText(currentWeatherPanel);
                windSpeedTexts[i] = windSpeedText(currentWeatherPanel);
            }
        }

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
                JSONObject data = WeatherApp.getWeatherData(userInput);
                weatherDataWeekly = (JSONObject[]) data.get("week_data");
                weatherDataHourly = (JSONObject) data.get("hours_data");

                for(int i = 0; i < 1; i++) {
                    //update GUI
                    updateGUI(i,
                            "weather_condition_day_" + (i+1), weatherConditionImages[i],
                            temperatureTexts[i],"temperature_day_" + (i+1),
                            weatherCondDescs[i], "humidity_day_" + (i+1), humidityTexts[i],
                            "windspeed_day_" + (i+1), windSpeedTexts[i]);
                }

                for (int i = 0; i < 24; i++) {
                    hoursPanel[i].removeAll();
                    currentHour[i] = new JLabel();
                    currentHour[i].setFont(new Font("Arial", Font.PLAIN, 10));
                    currentHour[i].setForeground(Color.WHITE);
                    String hour = WeatherApp.getHourForIndex(i).toUpperCase();
                    currentHour[i].setText(hour);
                    hoursPanel[i].add(currentHour[i]);
                    updateHourGUI(i, weatherConditionImageHours(hoursPanel[i]), temperatureText(hoursPanel[i]));
                }

                for (int i = 0; i < weekDayPanel.length; i++) {
                    weekDayPanel[i].removeAll();
                    weekDayDate[i] = new JLabel();
                    weekDayDate[i].setFont(new Font("Arial", Font.BOLD, 12));
                    weekDayDate[i].setForeground(Color.WHITE);
                    String dayDate = WeatherApp.getDayDateForIndex(i).toUpperCase();
                    weekDayDate[i].setText(dayDate);
                    weekDayPanel[i].add(weekDayDate[i]);
                    weekDayName[i] = new JLabel();
                    weekDayName[i].setFont(new Font("Arial", Font.BOLD, 12));
                    weekDayName[i].setForeground(Color.WHITE);
                    String dayName = WeatherApp.getDayNameForIndex(i).toUpperCase();
                    weekDayName[i].setText(dayName);
                    weekDayPanel[i].add(weekDayName[i]);
                    updateWeekGUI((i+1),i, weatherConditionImageHours(weekDayPanel[i]), temperatureText(weekDayPanel[i]));
                }

            }
        });
    }

    //used to create images to our gui components
    private ImageIcon loadImage(String resourcePath, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            Image newing = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newing);
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading image: " + resourcePath);
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("src/assets/error.png"));
            } catch (IOException ex) {
                System.out.println("Error loading default error image");
            }
            Image newing = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newing);
        }
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
        JLabel weatherConditionDesc = new JLabel("Cloudy");
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
        JLabel weatherConditionImage = new JLabel(loadImage(baseAssetPath+"cloudy.png", 98, 98));
        panelName.add(weatherConditionImage);
        return weatherConditionImage;
    }
    private JLabel weatherConditionImageHours(JPanel panelName) {
        JLabel weatherConditionImage = new JLabel(loadImage(baseAssetPath+"cloudy.png", 24, 24));
        weatherConditionImage.setPreferredSize(new Dimension(35,24));
        panelName.add(weatherConditionImage);
        return weatherConditionImage;
    }
    private JLabel humidityImage(Panel panelName) {
        JLabel humidityImage = new JLabel(loadImage(baseAssetPath+"humidity.png", 28, 28));
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
        JLabel windSpeedImage = new JLabel(loadImage(baseAssetPath+"windspeed.png", 28, 28));
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
        String weatherCondition = (String) weatherDataWeekly[index].get(weatherConditionDay);
        weatherConditionImage.setIcon(loadImage(baseAssetPath+weatherCondition.toLowerCase()+".png", 98, 98));
        // update temperature text
        double temperature = (double) weatherDataWeekly[index].get(temperatureDay);
        temperatureText.setText(temperature + " C");

        // update weatherCondition text
        weatherCondDesc.setText(weatherCondition);

        //update humidity text
        long humidity = (long) weatherDataWeekly[index].get(humidity_day);
        humidityText.setText("Humidity: " + humidity + "%");

        //update windspeed text
        double windspeed = (double) weatherDataWeekly[index].get(windspeed_day);
        windspeedText.setText("Wind: " + windspeed + "km/h");
    }
    private void updateHourGUI(int objectIndex, JLabel weatherConditionImage,  JLabel temperatureText) {

        System.out.println("updateHourGUI:" + objectIndex);

        try {
            String weatherCondition = (String) weatherDataHourly.get("weather_condition_hour_" + (objectIndex));
            weatherConditionImage.setIcon(loadImage(baseAssetPath + weatherCondition.toLowerCase() + ".png", 28, 28));

            // update temperature text
            double temperature = (double) weatherDataHourly.get("temperature_hour_" + (objectIndex));
            temperatureText.setText(temperature + " C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateWeekGUI(int objectIndex, int index, JLabel weatherConditionImage,  JLabel temperatureText) {

        System.out.println("updateWeekGUI:" + objectIndex);

        try {
            String weatherCondition = (String) weatherDataWeekly[index].get("weather_condition_day_" + (objectIndex));
            weatherConditionImage.setIcon(loadImage(baseAssetPath + weatherCondition.toLowerCase() + ".png", 28, 28));

            // update temperature text
            double temperature = (double) weatherDataWeekly[index].get("temperature_day_" + (objectIndex));
            temperatureText.setText(temperature + " C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}