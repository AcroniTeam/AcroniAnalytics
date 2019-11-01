package acronianalytics.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import jdk.nashorn.internal.parser.JSONParser;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import org.codehaus.jackson.JsonParser;
import com.google.gson.*;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.control.Label;

public class WebsiteController implements Initializable {

    @FXML
    private PieChart pie;
    @FXML
    private Label first;
    @FXML
    private Label second;
    @FXML
    private Label third;
    @FXML
    private Label fourth;
    @FXML
    private Label firstr;
    @FXML
    private Label secondr;
    @FXML
    private Label thirdr;
    @FXML
    private Label fourthr;
    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    boolean[] aux = new boolean[3];
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aux[0] = true;
        String firebase_baseUrl = "https://analytics-7777.firebaseio.com/";
        String firebase_apiKey = "AIzaSyCmE5kK8pdR1oyD3EOcU4zsnxYq2XSylIE";
        Firebase firebase;
        try {
            firebase = new Firebase(firebase_baseUrl);
            String response;
            response = firebase.get("/relatoriosGlobais/site/marcas").getRawBody();
                       
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (response, JsonElement.class);
            JsonObject json = element.getAsJsonObject(); 
            int razer = Integer.parseInt(json.get("Razer").toString());
            int logitech = Integer.parseInt(json.get("Logitech").toString());
            int redragon = Integer.parseInt(json.get("Redragon").toString());
            int hyperx = Integer.parseInt(json.get("HyperX").toString());
            int total = razer + hyperx + logitech + redragon;
            ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
            int prazer = (razer*100)/total;
            int plogitech = (logitech*100)/total;
            int predragon = (redragon*100)/total;
            int phyperx = (hyperx*100)/total;        
            list.add(new PieChart.Data("HyperX", phyperx));
            list.add(new PieChart.Data("Logitech", plogitech));
            list.add(new PieChart.Data("Razer", prazer));
            list.add(new PieChart.Data("Redragon", predragon));
            pie.setData(list);
            pie.setLegendVisible(false);
            pie.setLabelsVisible(false);
            ArrayList<String> a = new ArrayList<String>();
            a.add(prazer + ",Razer");
            a.add(plogitech + ",Logitech");
            a.add(predragon + ",Redragon");
            a.add(phyperx + ",Hyperx");
            Collections.sort(a, Collections.reverseOrder());
            first.getText();
            first.setText("1. "+(a.get(0)).split(",")[1] +" | ");
            firstr.setText("Comprado " +(Integer.parseInt((a.get(0)).split(",")[0]))/100*total + " vezes: " + (a.get(0)).split(",")[0] + "%");
            second.setText("2. "+(a.get(1)).split(",")[1]+" | ");
            third.setText("3. "+(a.get(2)).split(",")[1]+" | ");
            fourth.setText("4. "+(a.get(3)).split(",")[1]+" | ");
        } catch (FirebaseException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
    }
    
}
