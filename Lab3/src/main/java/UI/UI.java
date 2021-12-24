package UI;

import API.GraphhopperGeocode.GHPlace;
import API.OpenTripMap.OTMPlace;
import API.OpenTripMap.OTMPlaceInfo;
import API.OpenWeatherMap.WeatherInfo;
import API.Requests;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UI {

    private static UI UI;
    private JFrame frame;
    private JLabel label;
    private JTextField textField;
    private JButton findByPlaceButton;
    private JButton selectPlaceButton;
    private List<GHPlace> places;
    private DefaultListModel<String> listModel;
    private JList<String> list;

    private JLabel temperature;
    private JLabel tempFeelsLike;
    private JLabel wind;
    private JLabel humidity;
    private JLabel pressure;
    private JLabel precipitation;

    private List<OTMPlace> OTMPlaces;
    private List<JLabel> placeName;
    private List<JButton> wikiButtons;

    private Mesh mesh;

    private class Mesh extends JComponent{
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawLine(500, 0, 500, 240);
            for (int i = 0; i < 5; i++) {
                g.drawLine(0, 240+150*i, 1000, 240+150*i);
            }
        }
    }

    public static UI getInstance(){
        if (UI == null) {
            UI = new UI();
        }

        return UI;
    }

    private UI(){
        frame = new JFrame("Place info");
        frame.setSize(1400, 1010);

        label = new JLabel("Введите место для поиска: ");
        label.setBounds(10, 10, 170, 30);

        textField = new JTextField();
        textField.setBounds(175, 10, 200, 30);
        textField.addActionListener(e -> getLocation());

        findByPlaceButton = new JButton("Найти");
        findByPlaceButton.setBounds(380, 10, 70, 30);
        findByPlaceButton.addActionListener(e -> getLocation());

        frame.add(label);
        frame.add(textField);
        frame.add(findByPlaceButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void getLocation(){
        if(textField.getText() != null) {
            this.places = new ArrayList<>();
            this.listModel = new DefaultListModel<>();
            Thread modelThread = new Thread(() -> Requests.getLocation(textField.getText().replace(' ', '-')));
            modelThread.start();
            getPlace();
        }
    }

    public void addPlaces(GHPlace[] places){
        this.places.addAll(Arrays.asList(places));
        listModel.setSize(this.places.size());
        for (int i = 0; i < places.length; i++) {
            listModel.setElementAt("<html>" + places[i].getInfo(), i);
        }
    }

    public void addPlaceWeather(int i, WeatherInfo weatherInfo){
        double weather = weatherInfo.main.temp;
        StringBuilder tmp = new StringBuilder();
        if(weather > 30){
            tmp.append("<font color=#FF0000");
        } else if(weather > 20){
            tmp.append("<font color=#FF8C00>");
        } else if(weather > 10){
            tmp.append("<font color=#FFFF00>");
        } else if(weather > -5){
            tmp.append("<font color=#00CED1>");
        } else if(weather > -20){
            tmp.append("<font color=#0000FF>");
        } else {
            tmp.append("<font color=#000080>");
        }
        tmp.append("  ").append(weather).append("°C").append("</font></html>");
        listModel.setElementAt(listModel.getElementAt(i).concat(tmp.toString()), i);
    }

    private void getPlace(){

        if (list != null) {
            frame.remove(list);
        }
        if (selectPlaceButton != null) {
            frame.remove(selectPlaceButton);
        }

        list = new JList<>(listModel);
        list.setBounds(10, 70, 480, 100);
        list.setBackground(frame.getBackground());
        selectPlaceButton = new JButton("Выбрать");
        selectPlaceButton.setBounds(10, 170, 100, 30);
        selectPlaceButton.setEnabled(false);
        selectPlaceButton.addActionListener((e) -> getDescriptions());
        list.addListSelectionListener((e) -> selectPlaceButton.setEnabled(true));
        frame.add(selectPlaceButton);
        frame.add(list);
    }

    private void getDescriptions(){
        int index = list.getSelectedIndex();
        Requests.getRadius(places.get(index));
        //placeDescription = new ArrayList<>(5);
        mesh = new Mesh();
        mesh.setVisible(true);
        frame.getContentPane().add(mesh);
    }

    private void repaint(){
        frame.repaint(0);
    }

    public void setWeather(WeatherInfo weather){
        if (temperature != null) {
            frame.remove(temperature);
            frame.remove(tempFeelsLike);
            frame.remove(wind);
            frame.remove(humidity);
            frame.remove(pressure);
            frame.remove(precipitation);
        }
        temperature = new JLabel(weather.main.temp + "°C");
        temperature.setBounds(510, 10, 300, 20);
        frame.add(temperature);
        tempFeelsLike = new JLabel("Ощущается как: " + weather.main.feelsLike + "°C");
        tempFeelsLike.setBounds(510, 35, 300, 20);
        frame.add(tempFeelsLike);
        wind = new JLabel("Ветер: " + weather.wind.speed + " м/c. Направление: " + weather.wind.deg);
        wind.setBounds(510, 60, 400, 20);
        frame.add(wind);
        humidity = new JLabel("Влажность: " + weather.main.humidity + " %");
        humidity.setBounds(510, 85, 400, 20);
        frame.add(humidity);
        pressure = new JLabel("Давление: " + ((Double)(weather.main.pressure * 0.75)).intValue() + " мм.рт.ст");
        pressure.setBounds(510, 110, 400, 20);
        frame.add(pressure);
        precipitation = new JLabel("Осадки: " + weather.weather[0].description);
        precipitation.setBounds(510, 135, 400, 20);
        frame.add(precipitation);
        repaint();
    }

    public void setPlaces(List<OTMPlace> places){
        OTMPlaces = places;
        placeName = new ArrayList<>(OTMPlaces.size());
        for (int i = 0; i < OTMPlaces.size(); i++) {
            placeName.add(i, new JLabel(places.get(i).name));
            placeName.get(i).setBounds(10, 230 + i * 150, 500, 15);
            frame.add(placeName.get(i));
        }
        repaint();
    }

    private void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void setPlaceDescription(int index, OTMPlaceInfo placeInfo){
        JTextArea textArea = new JTextArea(placeInfo.wikipediaExtracts.text);
        textArea.setBounds(10, 270 + index * 150, 900, 80);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(frame.getBackground());
        frame.add(textArea);
        if(placeInfo.wikipedia != null){
            JButton button = new JButton("Wikipedia");
            button.addActionListener((e) -> {
                try {
                    open(new URI(placeInfo.wikipedia));
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
            });
            button.setBounds(10, 350 + index * 150, 100, 20);
            frame.add(button);
        }

        if(placeInfo.url != null){
            JButton button = new JButton("Web-site");
            button.addActionListener((e) -> {
                try {
                    open(new URI(placeInfo.url));
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
            });
            button.setBounds(120, 350 + index * 150, 100, 20);
            frame.add(button);
        }

        repaint();
    }

}
