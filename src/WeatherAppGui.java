import org.json.simple.JSONObject;
import java.awt.Component;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class  WeatherAppGui extends JFrame {

    String baseAssetPath = "src/assets/";
    private JSONObject[] weatherDataWeekly;
    private JSONObject weatherDataHourly;
    JPanel mainPanel;
    JPanel container;
    JPanel sidePanel;
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel currentWeatherPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel upcomingHoursPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel[] hoursPanel = new JPanel[24];
    JPanel[] weekDayPanel = new JPanel[7];
    JPanel upcomingWeekPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel[] currentHour = new JLabel[24];
    JLabel[] weekDayName = new JLabel[7];
    JLabel[] weekDayDate = new JLabel[7];
    JLabel weatherConditionImage = new JLabel();
    JLabel temperatureText = new JLabel();
    JLabel weatherCondDesc = new JLabel();
    JLabel humidityText = new JLabel();
    JLabel visibilityText = new JLabel();
    JLabel precipText = new JLabel();
    JLabel windSpeedText = new JLabel();
    JLabel highTempMain = new JLabel("H:0");
    JLabel lowTempMain = new JLabel("L:0");
    JPanel topPanel;
    JLabel locationTempLow1 = new JLabel("L:0");
    JLabel locationTempHigh1 = new JLabel("H:0");
    JPanel[] locationPanels = new JPanel[6];
    Boolean locationAdded = false;
    int buttonCounter = 0;
    String[] existingLocation = new String[6];
    Boolean isPresent = false;
    String[] locationLabels = new String[6];


    public WeatherAppGui() {
        super("Weather");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        getContentPane().setBackground(new Color(100,100,100));

        setSize(984,639);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("/Users/shahidmehmood/IdeaProjects/WeatherApp/src/assets/search.png/weatherIcon.png");
        setIconImage(icon);

        container = new JPanel();
        container.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        container.setPreferredSize(new Dimension(984,639));
//        container.setBackground(new Color(100,100,100));

        sidePanel = new JPanel();
        sidePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        sidePanel.setPreferredSize(new Dimension(200,639));
        sidePanel.setBackground(new Color(86, 130, 163));
        container.add(sidePanel);

//        getLocationPanel();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setPreferredSize(new Dimension(784,750));
        mainPanel.setBackground(new Color(79, 107, 133));

        // Create a new panel to hold the search panel at the top
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.setPreferredSize(new Dimension(400,40));
        topPanel.setBackground(new Color(86, 130, 163));
        container.add(topPanel);

        addGuiComponents();
        container.add(mainPanel);

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        mainScrollPane.setBackground(new Color(79, 107, 133));
        mainScrollPane.setBorder(BorderFactory.createLineBorder(new Color(79, 107, 133)));
        getContentPane().add(mainScrollPane, BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(sidePanel, BorderLayout.WEST);
//        try {
//            getContentPane().add(new JPanelWithBackground("/Users/shahidmehmood/IdeaProjects/WeatherApp/src/assets/clear.png"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    public void addGuiComponents() {
        // Add inner borders
        Insets insets = getInsets();
        getContentPane().setBackground(new Color(86, 130, 163));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 80));

        searchPanel.setBackground(new Color(79, 107, 133));
        searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,0,4));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,4));
        searchPanel.setSize(100, 50);
        searchPanel.setBackground(new Color(86, 130, 163));

        JLabel searchIcon = new JLabel(loadImage(baseAssetPath + "search.png", 25,26));
        searchIcon.setBorder(BorderFactory.createMatteBorder(3,3,3,0, new Color(161, 208, 243, 148)));
        searchPanel.add(searchIcon, BorderLayout.WEST);

        //Add Location Button

        JButton addLocationBtn = new JButton("+");
        addLocationBtn.setVisible(true);
        addLocationBtn.setForeground(new Color(86, 130, 163));
        topPanel.add(addLocationBtn, BorderLayout.EAST);

        JPanel getLocationPanel = getLocationPanel();


        //Search Bar
        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(205, 32));
        searchTextField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        searchTextField.setBackground(new Color(86, 130, 163));
        searchTextField.setForeground(Color.white);
        searchTextField.setCaretColor(new Color(161, 208, 243, 148));
        searchTextField.setBorder(BorderFactory.createMatteBorder(3,0,3,3, new Color(161, 208, 243, 148)));
        searchPanel.add(searchTextField, BorderLayout.CENTER);
        topPanel.add(searchPanel);

        //Search Button
        JButton button = new JButton("",loadImage(baseAssetPath + "search.png", 38, 38));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(Color.darkGray);
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setVisible(false);
        searchPanel.add(button, BorderLayout.CENTER);
        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });

        currentWeatherPanel.setBackground(new Color(79, 107, 133));
        currentWeatherPanel.setPreferredSize(new Dimension(100,50));
        currentWeatherPanel.setLayout(new GridBagLayout());
        currentWeatherPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(currentWeatherPanel);

        upcomingHoursPanel.setBackground(new Color(95,129,160));
        upcomingHoursPanel.setPreferredSize(new Dimension(1450,93));
        upcomingHoursPanel.setLayout(new GridLayout(1,24,0,0));


        for (int i = 0; i < hoursPanel.length; i++) {

            hoursPanel[i] = new JPanel();
            hoursPanel[i].setBackground(new Color(95,129,160));
            hoursPanel[i].setLayout(new FlowLayout(FlowLayout.CENTER,5,10));
            currentHour[i] = new JLabel();
            currentHour[i].setFont(new Font("Arial", Font.BOLD, 10));
            currentHour[i].setForeground(Color.WHITE);
            String hour = WeatherApp.getHourForIndex(i).toUpperCase();
            if(i == 0) {
                currentHour[i].setText("Now");
            } else {
                currentHour[i].setText(hour);
            }
            hoursPanel[i].add(currentHour[i]);
            hoursPanel[i].add(weatherConditionImageHours(hoursPanel[i]));
            hoursPanel[i].add(temperatureText(hoursPanel[i]));
            upcomingHoursPanel.add(hoursPanel[i]);
        }

        JScrollPane hoursScrollPane = new JScrollPane(upcomingHoursPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        hoursScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
        hoursScrollPane.setBorder(BorderFactory.createLineBorder(new Color(95,129,160),0,true));
        mainPanel.add(hoursScrollPane);

        JPanel moreInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,17));
        moreInfoPanel.setBackground(new Color(79, 107, 133));
        mainPanel.add(moreInfoPanel);

        upcomingWeekPanel.setBackground(new Color(79, 107, 133));
        upcomingWeekPanel.setPreferredSize(new Dimension(300,360));
        upcomingWeekPanel.setLayout(new BoxLayout(upcomingWeekPanel, BoxLayout.Y_AXIS));
        upcomingWeekPanel.setBorder(BorderFactory.createLineBorder(new Color(95,129,160),12,false));
        moreInfoPanel.add(upcomingWeekPanel);

        for(int i = 0; i < weekDayPanel.length; i++) {
            weekDayPanel[i] = new JPanel();
            weekDayPanel[i].setBackground(new Color(95,129,160));
            weekDayPanel[i].setLayout(new GridLayout(1,4));
            if(i < (weekDayPanel.length-1)) {
                weekDayPanel[i].setBorder(BorderFactory.createMatteBorder(0,0,2,0, new Color(161, 208, 243, 148)));
            }
            else {
                weekDayPanel[i].setBorder(BorderFactory.createMatteBorder(0,0,2,0, new Color(95,129,160)));
            }
            weekDayDate[i] = new JLabel();
            weekDayDate[i].setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
            weekDayDate[i].setFont(new Font("Arial", Font.BOLD, 12));
            weekDayDate[i].setForeground(Color.WHITE);
            String dayDate = WeatherApp.getDayDateForIndex(i).toUpperCase();
            weekDayDate[i].setText(dayDate);
            weekDayPanel[i].add(weekDayDate[i]);
            weekDayName[i] = new JLabel();
            weekDayName[i].setFont(new Font("Arial", Font.BOLD, 12));
            weekDayName[i].setForeground(Color.WHITE);
            String dayName = WeatherApp.getDayNameForIndex(i).toUpperCase();
            if(i == 0) {
                weekDayName[i].setText("Today");
            } else {
                weekDayName[i].setText(dayName);
            }
            weekDayPanel[i].add(weekDayName[i]);
            weekDayPanel[i].add(weatherConditionImageHours(weekDayPanel[i]));
            weekDayPanel[i].add(temperatureText(weekDayPanel[i]));
            upcomingWeekPanel.add(weekDayPanel[i]);
        }

        weatherConditionImage = weatherConditionImage(currentWeatherPanel);
        temperatureText = temperatureText(currentWeatherPanel);
        weatherCondDesc = weatherConditionDesc(currentWeatherPanel);
        humidityText =  humidityText(currentWeatherPanel);
        windSpeedText = windSpeedText(currentWeatherPanel);
        highTempMain = highTempMain(currentWeatherPanel);
        lowTempMain = lowTempMain(currentWeatherPanel);



        JPanel moreInfo = new JPanel(new GridLayout(2,2,17,17));
        moreInfo.setPreferredSize(new Dimension(324,365));
        moreInfo.setBorder(BorderFactory.createEmptyBorder(5,17,0,0));
        moreInfo.setBackground(new Color(79, 107, 133));
        moreInfoPanel.add(moreInfo);

        JPanel windPanel = new JPanel();
        windPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        windPanel.setPreferredSize(new Dimension(33,50));
        windPanel.setBackground(new Color(95,129,160));
        JLabel windLabel = new JLabel("Wind");
        windLabel.setFont(new Font("Arial", Font.BOLD, 14));
        windLabel.setForeground(Color.white);
        windLabel.setPreferredSize(new Dimension(300,14));
        windPanel.add(windLabel);
        windPanel.add(windSpeedText);
        moreInfo.add(windPanel);

        JPanel humidityPanel = new JPanel();
        humidityPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        humidityPanel.setPreferredSize(new Dimension(33,50));
        humidityPanel.setBackground(new Color(95,129,160));
        JLabel humidityLabel = new JLabel("Humidity");
        humidityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        humidityLabel.setForeground(Color.white);
        humidityLabel.setPreferredSize(new Dimension(300,14));
        humidityPanel.add(humidityLabel);
        humidityPanel.add(humidityText);
        moreInfo.add(humidityPanel);

        JPanel visibilityPanel = new JPanel();
        visibilityPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        visibilityPanel.setPreferredSize(new Dimension(33,50));
        visibilityPanel.setBackground(new Color(95,129,160));
        JLabel visibilityLabel = new JLabel("Visibility");
        visibilityLabel.setForeground(Color.WHITE);
        visibilityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        visibilityText.setText("0.00km");
        visibilityText.setFont(new Font("Arial", Font.BOLD, 28));
        visibilityText.setBorder(new EmptyBorder(35,10,0,0));
        visibilityText.setForeground(Color.WHITE);
        visibilityPanel.add(visibilityLabel);
        visibilityPanel.add(visibilityText);
        moreInfo.add(visibilityPanel);

        JPanel precipitationPanel = new JPanel();
        precipitationPanel.setPreferredSize(new Dimension(33,50));
        precipitationPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        precipitationPanel.setBackground(new Color(95,129,160));
        JLabel precipitationLabel = new JLabel("Precipitation");
        precipitationLabel.setForeground(Color.WHITE);
        precipitationLabel.setFont(new Font("Arial", Font.BOLD, 14));
        precipText.setText("00%");
        precipText.setFont(new Font("Arial", Font.BOLD, 28));
        precipText.setBorder(new EmptyBorder(35,30,0,0));
        precipText.setForeground(Color.WHITE);
        precipitationPanel.add(precipitationLabel);
        precipitationPanel.add(precipText);
        moreInfo.add(precipitationPanel);

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

                    //update Current Weather GUI
                updateCurrentWeatherGUI(0,
                        "weather_condition_day_" + (1), weatherConditionImage,
                        temperatureText,"temperature_day_" + (1),
                        weatherCondDesc, "humidity_day_" + (1), humidityText,
                        "windspeed_day_" + (1), windSpeedText,
                        "precipitation_day_" + (1), precipText, "visibility_day_"+ (1), visibilityText);


                for (int i = 0; i < hoursPanel.length; i++) {
                    hoursPanel[i].removeAll();
                    currentHour[i] = new JLabel();
                    currentHour[i].setFont(new Font("Arial", Font.BOLD, 10));
                    currentHour[i].setForeground(Color.WHITE);
                    String hour = WeatherApp.getHourForIndex(i).toUpperCase();
                    if(i == 0) {
                        currentHour[i].setText("Now");
                    } else {
                        currentHour[i].setText(hour);
                    }
                    hoursPanel[i].add(currentHour[i]);
                    updateHourGUI(i, weatherConditionImageHours(hoursPanel[i]), temperatureText(hoursPanel[i]));
                }

                for (int i = 0; i < weekDayPanel.length; i++) {
                    weekDayPanel[i].removeAll();
                    weekDayDate[i] = new JLabel();
                    weekDayDate[i].setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
                    weekDayDate[i].setFont(new Font("Arial", Font.BOLD, 12));
                    weekDayDate[i].setForeground(Color.WHITE);
                    String dayDate = WeatherApp.getDayDateForIndex(i).toUpperCase();
                    weekDayDate[i].setText(dayDate);
                    weekDayPanel[i].add(weekDayDate[i]);
                    weekDayName[i] = new JLabel();
                    weekDayName[i].setFont(new Font("Arial", Font.BOLD, 12));
                    weekDayName[i].setForeground(Color.WHITE);
                    String dayName = WeatherApp.getDayNameForIndex(i).toUpperCase();
                    if(i == 0) {
                        weekDayName[i].setText("Today");
                    } else {
                        weekDayName[i].setText(dayName);
                    }
                    weekDayPanel[i].add(weekDayName[i]);
                    updateWeekGUI((i),i, weatherConditionImageHours(weekDayPanel[i]), temperatureText(weekDayPanel[i]));
                }

                updateLowHighTemp(lowTempMain, highTempMain);
                sidePanel.remove(getLocationPanel);
                sidePanel.revalidate();
                sidePanel.repaint();
                if(!locationAdded) {
                    locationPanels[buttonCounter] = getLocationPanel(searchTextField.getText(), weatherCondDesc.getText(), temperatureText.getText());
                    existingLocation[buttonCounter] = generateFullName(searchTextField.getText());
                    buttonCounter++;
                    for(int i =0 ; i < existingLocation.length; i++) {
                        if(Objects.equals(existingLocation[i], generateFullName(searchTextField.getText()))) {
                            isPresent = true;
                        }
                    }

                    locationAdded = true;
                }
            }
        });

        addLocationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < existingLocation.length; i++) {
                    if(Objects.equals(existingLocation[i], generateFullName(searchTextField.getText()))) {
                        isPresent = true;
                    }
                }
                if(buttonCounter < 6 && !isPresent) {

                    locationPanels[buttonCounter] = getLocationPanel(searchTextField.getText(), weatherCondDesc.getText(), temperatureText.getText());
                    existingLocation[buttonCounter] = generateFullName(searchTextField.getText());
                    buttonCounter++;
                }
                else {
                    System.out.println("Location Limit Reached");
                    System.out.println(Arrays.toString(existingLocation));
                    isPresent = false;
                }
            }
        });

        for (int i = 0; i < locationPanels.length; i++) {
            locationPanels[i] = new JPanel();
            int index = i;
            if (locationPanels[i] == null) {
                System.out.println("locationPanel[" + i + "] is null");
            } else {
                System.out.println("listener Added");
                locationPanels[i].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("mouse clicked");
                        searchTextField.setText(locationLabels[index]);
                        button.doClick();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        System.out.println("mouse clicked");
                        searchTextField.setText(locationLabels[index]);
                        button.doClick();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        System.out.println("mouse clicked");
                        searchTextField.setText(locationLabels[index]);
                        button.doClick();
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        System.out.println("mouse clicked");
                        searchTextField.setText(locationLabels[index]);
                        button.doClick();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            }
        }
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
    private JLabel humidityText(JPanel panelName) {
        JLabel humidityText = new JLabel("0%");
        humidityText.setFont(new Font("Arial", Font.BOLD, 28));
        humidityText.setBorder(new EmptyBorder(37,42,0,0));
        humidityText.setForeground(Color.WHITE);
        panelName.add(humidityText);
        return humidityText;
    }
    private JLabel windSpeedText(JPanel panelName) {
        JLabel windSpeedText = new JLabel("0.0km/h");
        windSpeedText.setFont(new Font("Arial", Font.BOLD, 28));
        windSpeedText.setBorder(new EmptyBorder(37,10,0,0));
        windSpeedText.setForeground(Color.WHITE);
        panelName.add(windSpeedText);
        return windSpeedText;
    }
    private void updateCurrentWeatherGUI(int index, String weatherConditionDay,
                            JLabel weatherConditionImage, JLabel temperatureText, String temperatureDay,
                            JLabel weatherCondDesc, String humidity_day,
                            JLabel humidityText, String windspeed_day, JLabel windspeedText, String precip_day,
                            JLabel precipitationText, String visibility_day, JLabel visibilityText) {

        String weatherCondition = (String) weatherDataWeekly[index].get(weatherConditionDay);
        weatherConditionImage.setIcon(loadImage(baseAssetPath+weatherCondition.toLowerCase()+".png", 98, 98));
        // update temperature text
        double temperature = (double) weatherDataWeekly[index].get(temperatureDay);
        temperatureText.setText(temperature + " C");

        // update weatherCondition text
        weatherCondDesc.setText(weatherCondition);

        //update humidity text
        long humidity = (long) weatherDataWeekly[index].get(humidity_day);
        humidityText.setText(humidity + "%");

        //update windspeed text
        double windspeed = (double) weatherDataWeekly[index].get(windspeed_day);
        windspeedText.setText(windspeed + "km/h");

        //update Visibility text
        double visibility = (double) weatherDataWeekly[index].get(visibility_day);
        visibilityText.setText((visibility/1000) + "km");

        //update Precipitation text
        long precipitation = (long) weatherDataWeekly[index].get(precip_day);
        precipitationText.setText(precipitation + "%");
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
            String weatherCondition = (String) weatherDataWeekly[index].get("weather_condition_day_" + (objectIndex+1));
            weatherConditionImage.setIcon(null);
            weatherConditionImage.setIcon(loadImage(baseAssetPath + weatherCondition.toLowerCase() + ".png", 28, 28));

            // update temperature text
            double temperature = (double) weatherDataWeekly[index].get("temperature_day_" + (objectIndex+1));
            temperatureText.setText(temperature + " C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public JPanel getLocationPanel(String exactLocation,
                                  String weatherDesc, String weatherTemp) {

        System.out.println("Got called");
        JPanel locationPanel = new JPanel();
        locationPanel.setPreferredSize(new Dimension(190,80));
        locationPanel.setLayout(new GridLayout(1,2));
        locationPanel.setBorder(BorderFactory.createMatteBorder(8,10,10,10, new Color(113,142,164)));


        System.out.println("Location Panel created");
        JPanel locationNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        locationNamePanel.setPreferredSize(new Dimension(50,10));
        locationNamePanel.setBackground( new Color(113,142,164));
        locationPanel.add(locationNamePanel);
        String exactLocation1 = generateFullName(exactLocation);
        locationLabels[buttonCounter] = exactLocation1;
        JLabel currentLocation = new JLabel(exactLocation1);
        currentLocation.setFont(new Font("Arial", Font.BOLD, 16));
        currentLocation.setForeground(Color.WHITE);
        locationNamePanel.add(currentLocation);
        JLabel locationWeatherDesc = new JLabel(weatherDesc);
        locationWeatherDesc.setFont(new Font("Arial", Font.BOLD, 14));
        locationWeatherDesc.setForeground(Color.WHITE);
        locationNamePanel.add(locationWeatherDesc);

        JPanel locationDescPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        locationDescPanel.setPreferredSize(new Dimension(50,10));
        locationDescPanel.setBackground( new Color(113,142,164));
        locationPanel.add(locationDescPanel);

        JLabel locationTemp = new JLabel(weatherTemp);
        locationTemp.setFont(new Font("Arial", Font.BOLD, 18));
        locationTemp.setForeground(Color.WHITE);
        locationTemp.setPreferredSize(new Dimension(70,16));
        locationTemp.setBorder(BorderFactory.createEmptyBorder(10,10,10,0));
        locationDescPanel.add(locationTemp);

        // update temperature text
        double highTemperature = (double) weatherDataWeekly[0].get("high_temp_day_1");
        JLabel locationTempHigh = new JLabel("H:" + String.valueOf(highTemperature));
        locationTempHigh.setPreferredSize(new Dimension(70,14));
        locationTempHigh.setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
        locationTempHigh.setForeground(Color.WHITE);
        locationDescPanel.add(locationTempHigh);

        double lowTemperature = (double) weatherDataWeekly[0].get("low_temp_day_1");
        JLabel locationTempLow = new JLabel("L:" + String.valueOf(lowTemperature));
        locationTempLow.setForeground(Color.WHITE);
        locationDescPanel.add(locationTempLow);

        sidePanel.add(locationPanel);
        sidePanel.revalidate();
        sidePanel.repaint();

        return locationPanel;
    }
    private JPanel getLocationPanel() {

        JPanel locationPanel = new JPanel();
        locationPanel.setPreferredSize(new Dimension(190,80));
        locationPanel.setLayout(new GridLayout(1,2));
        locationPanel.setBorder(BorderFactory.createMatteBorder(12,10,10,10, new Color(113,142,164)));
        sidePanel.add(locationPanel);

        JPanel locationNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        locationNamePanel.setPreferredSize(new Dimension(50,10));
        locationNamePanel.setBackground( new Color(113,142,164));
        locationPanel.add(locationNamePanel);
        JLabel currentLocation = new JLabel("Location");
        currentLocation.setFont(new Font("Arial", Font.BOLD, 16));
        currentLocation.setForeground(Color.WHITE);
        locationNamePanel.add(currentLocation);
        JLabel locationWeatherDesc = new JLabel("Weather");
        locationWeatherDesc.setFont(new Font("Arial", Font.BOLD, 14));
        locationWeatherDesc.setForeground(Color.WHITE);
        locationNamePanel.add(locationWeatherDesc);

        JPanel locationDescPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        locationDescPanel.setPreferredSize(new Dimension(50,10));
        locationDescPanel.setBackground( new Color(113,142,164));
        locationPanel.add(locationDescPanel);
        JLabel locationTemp = new JLabel("26");
        locationTemp.setFont(new Font("Arial", Font.BOLD, 40));
        locationTemp.setForeground(Color.WHITE);
        locationTemp.setPreferredSize(new Dimension(70,32));
        locationTemp.setBorder(BorderFactory.createEmptyBorder(25,18,25,0));
        locationDescPanel.add(locationTemp);
        JLabel locationTempHigh = new JLabel("H:0");
        locationTempHigh.setForeground(Color.WHITE);
        locationDescPanel.add(locationTempHigh);
        JLabel locationTempLow = new JLabel("L:0");
        locationTempLow.setForeground(Color.WHITE);
        locationDescPanel.add(locationTempLow);

        return locationPanel;
    }

    private JLabel highTempMain(JPanel panelName) {
        JLabel locationTempHigh = new JLabel("H:0");
        locationTempHigh.setHorizontalTextPosition(SwingConstants.CENTER);
        locationTempHigh.setFont(new Font("Arial", Font.BOLD, 14));
        locationTempHigh.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 5, 0); // Adjust the top margin if needed
        panelName.add(locationTempHigh, gbc);
        return locationTempHigh;
    }
    private JLabel lowTempMain(JPanel panelName) {
        JLabel locationTempLow = new JLabel("L:0");
        locationTempLow.setHorizontalTextPosition(SwingConstants.CENTER);
        locationTempLow.setFont(new Font("Arial", Font.BOLD, 14));
        locationTempLow.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(0, 0, 5, 0); // Adjust the top margin if needed
        panelName.add(locationTempLow, gbc);
        return locationTempLow;
    }
    private void updateLowHighTemp(JLabel lowTempLabel, JLabel highTempLabel) {
        // update temperature text
        double lowTemperature = (double) weatherDataWeekly[0].get("low_temp_day_1");
        lowTempLabel.setText("L:" + String.valueOf(lowTemperature));

        double highTemperature = (double) weatherDataWeekly[0].get("high_temp_day_1");
        highTempLabel.setText("H:" + String.valueOf(highTemperature));
    }
    public static String generateFullName(String fullname){
        String resultFullname="";

        String[] splitted=fullname.split(" ");
        for(int i=0;i<splitted.length;i++) {
            if (!splitted[i].isEmpty()) {
                resultFullname += splitted[i].substring(0, 1).toUpperCase() + splitted[i].substring(1).toLowerCase()+" ";
            }
        }
        return resultFullname.trim();
    }
}