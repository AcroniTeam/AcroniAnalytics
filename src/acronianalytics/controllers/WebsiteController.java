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
import java.util.Calendar;
import java.util.Collections;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
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
    @FXML
    private AreaChart<?,?> line;
    boolean[] aux = new boolean[3];
    int razer, logitech, redragon, hyperx;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aux[0] = true;
        String firebase_baseUrl = "https://analytics-7777.firebaseio.com/";
        String firebase_apiKey = "AIzaSyCmE5kK8pdR1oyD3EOcU4zsnxYq2XSylIE";
        Firebase firebase;
        //Gráfico das visitas
        try {
            firebase = new Firebase(firebase_baseUrl);
            String response;
            response = firebase.get("/relatoriosMensais/site/"+Calendar.getInstance().get(Calendar.YEAR)).getRawBody();
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (response, JsonElement.class);
            JsonObject json = element.getAsJsonObject(); 
            XYChart.Series series = new XYChart.Series();
            for (int i = 1; i <= Calendar.getInstance().get(Calendar.MONTH)+1; i++) {
                series.getData().add(new XYChart.Data<>(mesPTBR(i),Integer.parseInt(json.getAsJsonObject(mesPTBR(i)).get("numVisitasMensais").toString())));
            }
            line.setLegendVisible(false);
            yAxis.setLabel("N.º Visitas");
            yAxis.setTickUnit(100);
            line.getData().add(series);
        } catch (Exception e) {} catch (FirebaseException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Gráfico das marcas
        try {
            firebase = new Firebase(firebase_baseUrl);
            String response;
            response = firebase.get("/relatoriosGlobais/site/marcas").getRawBody();
                       
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (response, JsonElement.class);
            JsonObject json = element.getAsJsonObject(); 
            razer = Integer.parseInt(json.get("Razer").toString());
            logitech = Integer.parseInt(json.get("Logitech").toString());
            redragon = Integer.parseInt(json.get("Redragon").toString());
            hyperx = Integer.parseInt(json.get("HyperX").toString());
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
            firstr.setText("Comprado " + setRightText((a.get(0)).split(",")[1]) + " vezes: " + (a.get(0)).split(",")[0] + "%");
            second.setText("2. "+(a.get(1)).split(",")[1]+" | ");
            secondr.setText("Comprado " + setRightText((a.get(1)).split(",")[1]) + " vezes: " + (a.get(1)).split(",")[0] + "%");
            third.setText("3. "+(a.get(2)).split(",")[1]+" | ");
            thirdr.setText("Comprado " + setRightText((a.get(2)).split(",")[1]) + " vezes: " + (a.get(2)).split(",")[0] + "%");
            fourth.setText("4. "+(a.get(3)).split(",")[1]+" | ");
            fourthr.setText("Comprado " + setRightText((a.get(3)).split(",")[1]) + " vezes: " + (a.get(3)).split(",")[0] + "%");
        } catch (FirebaseException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int setRightText(String marca) {
        if (marca.equals("Razer"))
            return razer;
        else if (marca.equals("Logitech"))
            return logitech;
        else if (marca.equals("Hyperx"))
            return hyperx;
        else
            return redragon;
    }
    public String getActualMonth()
    {
        int nMes = Calendar.getInstance().get(Calendar.MONTH);
        String[] meses = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        return meses[nMes - 1];
    }
    public String mesPTBR(int n) {
        String[] meses = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        return meses[n - 1];
    }
}
