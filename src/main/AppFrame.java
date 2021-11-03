package main;

import org.codehaus.jackson.map.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
	
    private JTextField cityField;
	private JButton viewButton;
    private JLabel guideLabel;
    private JLabel weatherInfo1;
    private JLabel weatherInfo2;
    private JLabel weatherInfo3;
    private JLabel weatherInfo4;
	
	public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AppFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AppFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AppFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(AppFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AppFrame().setVisible(true);
            }
        });
    }

    public AppFrame() {
        initComponents();
    }

    private void initComponents() {

    	cityField = new JTextField();
        viewButton = new JButton();
        guideLabel = new JLabel();
        weatherInfo1 = new JLabel();
        weatherInfo2 = new JLabel();
        weatherInfo3 = new JLabel();
        weatherInfo4 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setFont(new Font("Arial", 2, 10));

        viewButton.setFont(new Font("Arial", 3, 14));
        viewButton.setText("View Weather Data");
        viewButton.setToolTipText("");
        viewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	viewButtonActionPerformed(evt);
            }
        });

        guideLabel.setText("Enter Your City Name");

        weatherInfo1.setFont(new Font("Arial", 1, 14));
        weatherInfo1.setHorizontalAlignment(SwingConstants.CENTER);

        weatherInfo2.setFont(new Font("Arial", 1, 14));
        weatherInfo2.setHorizontalAlignment(SwingConstants.CENTER);

        weatherInfo3.setFont(new Font("Arial", 1, 14));
        weatherInfo3.setHorizontalAlignment(SwingConstants.CENTER);

        weatherInfo4.setFont(new Font("Arial", 1, 14));
        weatherInfo4.setHorizontalAlignment(SwingConstants.CENTER);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(guideLabel)
                        .addGap(29, 29, 29)
                        .addComponent(cityField, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(viewButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(weatherInfo1, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
                            .addComponent(weatherInfo2, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
                            .addComponent(weatherInfo3, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
                            .addComponent(weatherInfo4, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cityField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewButton)
                    .addComponent(guideLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(weatherInfo1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(weatherInfo2, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(weatherInfo3, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(weatherInfo4, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }

    
    private void viewButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {                                         
            String city = cityField.getText();
            System.out.println("you are currently located in " + city);
            
            String API_KEY = "0dd59b7fe8d623b93262030067444b3d";
            String UrlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=imperial";
            
            StringBuilder rawResult = new StringBuilder();
            URL url  = new URL(UrlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String resultLine = rd.readLine();
                while (resultLine != null)
                {
                    rawResult.append(resultLine);
                }
                rd.close();
            
            /**
             * Read JSON from a string into a Map
             */
            //using Jackson library
                ObjectMapper mapper = new ObjectMapper();
                String weatherReSult = rawResult.toString();
                Map<String, Object> resultMap = mapper.readValue(weatherReSult, Map.class);
            
            //using gson liabrary
                Map<String,Object> mainResultMap = jsonToMap(resultMap.get("main").toString());
                Map<String,Object> windResultMap = jsonToMap(resultMap.get("wind").toString());
                String humidity = mainResultMap.get("humidity").toString();
                String temperature = mainResultMap.get("temp").toString();
                String speed = windResultMap.get("speed").toString();
                String angle = windResultMap.get("deg").toString();
                weatherInfo1.setText("Humidity : " + humidity);
                weatherInfo2.setText("Temprature : " + temperature);
                weatherInfo3.setText("Wind Speed : "+ speed);
                weatherInfo4.setText("Wind Angle : " + angle);
        } catch (IOException ex) {
            Logger.getLogger(AppFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    }
    
   private Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map= new Gson().fromJson(str, new TypeToken<HashMap<String,Object>>(){}.getType());
        return map; 
   }
   
}
