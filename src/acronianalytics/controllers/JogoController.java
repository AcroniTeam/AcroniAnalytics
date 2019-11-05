package acronianalytics.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.service.Firebase;


public class JogoController implements Initializable {

    boolean[] aux = new boolean[3];
    
    @FXML
    private GridPane gplabels;
    
    @FXML
    private Pane active;
    @FXML
    private PieChart pie;
    @FXML
    private PieChart pie2;
    
    @FXML
    private Label first;
    
    @FXML
    private Label second;
    
    @FXML
    private Label third;
    
    @FXML
    private Label fourth1;
    
    @FXML
    private Label firstr;
    
    @FXML
    private Label secondr;
    
    @FXML
    private Label thirdr;
    
    @FXML
    private Label first1;
    
    @FXML
    private Label second1;
    
    @FXML
    private Label third1;
    
    @FXML
    private Label firstr1;
    
    @FXML
    private Label secondr1;
    
    @FXML
    private Label thirdr1;
    
    @FXML
    private Label fourthr1;
    
    @FXML
    private BarChart<String, Number> bar;
    
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    
    private static ScrollPane sp;
    int aerea, distorcida, similar, bloco, bomba, controlador, trampolim;
    String firebase_baseUrl = "https://analytics-7777.firebaseio.com/";
        String firebase_apiKey = "AIzaSyCmE5kK8pdR1oyD3EOcU4zsnxYq2XSylIE";
        Firebase firebase;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        new Thread(() -> {
            
        
        
        try {
            firebase = new Firebase(firebase_baseUrl);
            String response;
            response = firebase.get("/relatoriosGlobais/game/desconto").getRawBody();
                       
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (response, JsonElement.class);
            JsonObject json = element.getAsJsonObject(); 
            
            
            XYChart.Series<String, Number> series1 = new XYChart.Series<>(); 
            for (int i = 1; i < 21; i++) {
                series1.setName(i+"");
                series1.getData().add(new XYChart.Data<>(i+"", Integer.parseInt(json.get(""+i).toString())));   
            }
            
            Platform.runLater(() -> {
                xAxis.setAnimated(false);
                yAxis.setLabel("N.º pessoas");
                
                bar.getData().add(series1);
                bar.setLegendVisible(false);
                bar.setBarGap(0);
                bar.lookupAll(".default-color0.chart-bar").forEach((n) -> {
                    n.setStyle("-fx-bar-fill: #0093ff80;");
                });
            });
            
            
        } catch(Exception e) {} catch (FirebaseException ex) {
            Logger.getLogger(JogoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Gráfico das fases
        try {
            firebase = new Firebase(firebase_baseUrl);
            String response;
            response = firebase.get("/relatoriosGlobais/game/fases").getRawBody();
                       
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (response, JsonElement.class);
            JsonObject json = element.getAsJsonObject(); 
            aerea = Integer.parseInt(json.get("Fase Aérea").toString());
            distorcida = Integer.parseInt(json.get("Fase Distorcida").toString());
            similar = Integer.parseInt(json.get("Mundo Similar").toString());
            
            int total = aerea + distorcida + similar;
            ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
            int paerea = (aerea*100)/total;
            int pdistorcida = (distorcida*100)/total;
            int psimilar = (similar*100)/total;
            list.add(new PieChart.Data("Mundo Similar", psimilar));
            list.add(new PieChart.Data("Fase Aérea", paerea));
            list.add(new PieChart.Data("Fase Distorcida", pdistorcida));
            Platform.runLater(() -> {
                pie.setData(list);
                pie.setLegendVisible(false);
                pie.setLabelsVisible(false);
            });
            ArrayList<String> a = new ArrayList<String>();
            a.add(paerea + ",Fase Aérea");
            a.add(pdistorcida + ",Fase Distorcida");
            a.add(psimilar + ",Mundo Similar");
            Collections.sort(a, Collections.reverseOrder());
            Platform.runLater(() -> {
                first.setText("1. "+(a.get(0)).split(",")[1] +" | ");
                firstr.setText(setRightTextFases((a.get(0)).split(",")[1]) + " vezes: " + (a.get(0)).split(",")[0] + "%");
                second.setText("2. "+(a.get(1)).split(",")[1]+" | ");
                secondr.setText(setRightTextFases((a.get(1)).split(",")[1]) + " vezes: " + (a.get(1)).split(",")[0] + "%");
                third.setText("3. "+(a.get(2)).split(",")[1]+" | ");
                thirdr.setText(setRightTextFases((a.get(2)).split(",")[1]) + " vezes: " + (a.get(2)).split(",")[0] + "%");
            });
        } catch (FirebaseException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Gráfico dos itens
        try {
            firebase = new Firebase(firebase_baseUrl);
            String response;
            response = firebase.get("/relatoriosGlobais/game/itens").getRawBody();
                       
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (response, JsonElement.class);
            JsonObject json = element.getAsJsonObject(); 
            bloco = Integer.parseInt(json.get("Bloco Especial").toString());
            bomba = Integer.parseInt(json.get("Bomba").toString());
            controlador = Integer.parseInt(json.get("Controlador Temporal").toString());
            trampolim = Integer.parseInt(json.get("Trampolim").toString());
            int total = bloco + bomba + controlador + trampolim;
            ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
            int pbloco = (bloco*100)/total;
            int pbomba = (bomba*100)/total;
            int pcontrolador = (controlador*100)/total;
            int ptrampolim = (trampolim*100)/total;
            list.add(new PieChart.Data("Bloco Especial", pbloco));
            list.add(new PieChart.Data("Bomba", pbomba));
            list.add(new PieChart.Data("Controlador Temporal", pcontrolador));
            list.add(new PieChart.Data("Trampolim", ptrampolim));
            Platform.runLater(() -> {
                pie2.setData(list);
                pie2.setLegendVisible(false);
                pie2.setLabelsVisible(false);
            });
            ArrayList<String> a = new ArrayList<String>();
            a.add(pbomba + ",Bomba");
            a.add(ptrampolim + ",Trampolim");
            a.add(pcontrolador + ",Controlador Temporal");
            a.add(pbloco + ",Bloco Especial");
            Collections.sort(a, Collections.reverseOrder());
            Platform.runLater(() -> {
                first1.setText("1. "+(a.get(0)).split(",")[1] +" | ");
                firstr1.setText("Comprado "+setRightTextItens((a.get(0)).split(",")[1]) + " vezes: " + (a.get(0)).split(",")[0] + "%");
                second1.setText("2. "+(a.get(1)).split(",")[1]+" | ");
                secondr1.setText("Comprado "+setRightTextItens((a.get(1)).split(",")[1]) + " vezes: " + (a.get(1)).split(",")[0] + "%");
                third1.setText("3. "+(a.get(2)).split(",")[1]+" | ");
                thirdr1.setText("Comprado "+setRightTextItens((a.get(2)).split(",")[1]) + " vezes: " + (a.get(2)).split(",")[0] + "%");
                fourth1.setText("4. "+(a.get(3)).split(",")[1]+" | ");
                fourthr1.setText("Comprado "+setRightTextItens((a.get(3)).split(",")[1]) + " vezes: " + (a.get(3)).split(",")[0] + "%");
            });
        } catch (FirebaseException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }).start();
    }    
    public int setRightTextFases(String marca) {
        if (marca.equals("Fase Aérea"))
            return aerea;
        else if (marca.equals("Fase Distorcida"))
            return distorcida;
        else 
            return similar;
    }
    public int setRightTextItens(String marca) {
        if (marca.equals("Bomba"))
            return bomba;
        else if (marca.equals("Trampolim"))
            return trampolim;
        else if (marca.equals("Controlador Temporal"))
            return controlador;
        else 
            return bloco;
    }

}

