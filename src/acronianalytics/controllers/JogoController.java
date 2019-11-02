package acronianalytics.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
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
    private Label first;
    @FXML
    private Label second;
    @FXML
    private Label third;
    @FXML
    private Label firstr;
    @FXML
    private Label secondr;
    @FXML
    private Label thirdr;
    @FXML
    private BarChart<String, Number> bar;
    
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    
    private static ScrollPane sp;
    int aerea, distorcida, similar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String firebase_baseUrl = "https://analytics-7777.firebaseio.com/";
        String firebase_apiKey = "AIzaSyCmE5kK8pdR1oyD3EOcU4zsnxYq2XSylIE";
        Firebase firebase;
        yAxis.setLabel("N.º pessoas");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>(); 
        XYChart.Series<String, Number> series2 = new XYChart.Series<>(); 
        XYChart.Series<String, Number> series3 = new XYChart.Series<>(); 
        XYChart.Series<String, Number> series4 = new XYChart.Series<>(); 
        XYChart.Series<String, Number> series5 = new XYChart.Series<>(); 
        XYChart.Series<String, Number> series6 = new XYChart.Series<>(); 
        XYChart.Series<String, Number> series7 = new XYChart.Series<>(); 
        series1.setName("5"); 
        series1.getData().add(new XYChart.Data<>("", 240)); 
        series2.setName("7"); 
        series2.getData().add(new XYChart.Data<>("", 490)); 
        series3.setName("10"); 
        series3.getData().add(new XYChart.Data<>("", 330)); 
        series4.setName("15"); 
        series4.getData().add(new XYChart.Data<>("", 100)); 
        series5.setName("20"); 
        series5.getData().add(new XYChart.Data<>("", 75)); 
        series6.setName("25"); 
        series6.getData().add(new XYChart.Data<>("", 55)); 
        series7.setName("30"); 
        series7.getData().add(new XYChart.Data<>("", 20)); 
        bar.getData().addAll(series1, series2, series3, series4, series5, series6, series7);
        bar.setLegendVisible(true);
        bar.setBarGap(40);
        bar.setLegendVisible(false);
        bar.lookupAll(".default-color0.chart-bar").forEach((n) -> {
            n.setStyle("-fx-bar-fill: #0093ff80;");
        });
        bar.lookupAll(".default-color6.chart-bar").forEach((n) -> {
            n.setStyle("-fx-bar-fill: #0093ff80;");
        });
        bar.lookupAll(".default-color1.chart-bar").forEach((n) -> {
            n.setStyle("-fx-bar-fill: #0093ff80;");
        });
        bar.lookupAll(".default-color2.chart-bar").forEach((n) -> {
            n.setStyle("-fx-bar-fill: #0093ff80;");
        });
        bar.lookupAll(".default-color3.chart-bar").forEach((n) -> {
            n.setStyle("-fx-bar-fill: #0093ff80;");
        });
        bar.lookupAll(".default-color4.chart-bar").forEach((n) -> {
            n.setStyle("-fx-bar-fill: #0093ff80;");
        });
        bar.lookupAll(".default-color5.chart-bar").forEach((n) -> {
            n.setStyle("-fx-bar-fill: #0093ff80;");
        });
        
        
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
            pie.setData(list);
            pie.setLegendVisible(false);
            pie.setLabelsVisible(false);
            ArrayList<String> a = new ArrayList<String>();
            a.add(paerea + ",Fase Aérea");
            a.add(pdistorcida + ",Fase Distorcida");
            a.add(psimilar + ",Mundo Similar");
            Collections.sort(a, Collections.reverseOrder());
            first.getText();
            first.setText("1. "+(a.get(0)).split(",")[1] +" | ");
            firstr.setText(setRightTextFases((a.get(0)).split(",")[1]) + " vezes: " + (a.get(0)).split(",")[0] + "%");
            second.setText("2. "+(a.get(1)).split(",")[1]+" | ");
            secondr.setText(setRightTextFases((a.get(1)).split(",")[1]) + " vezes: " + (a.get(1)).split(",")[0] + "%");
            third.setText("3. "+(a.get(2)).split(",")[1]+" | ");
            thirdr.setText(setRightTextFases((a.get(2)).split(",")[1]) + " vezes: " + (a.get(2)).split(",")[0] + "%");
            
        } catch (FirebaseException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    public int setRightTextFases(String marca) {
        if (marca.equals("Fase Aérea"))
            return aerea;
        else if (marca.equals("Fase Distorcida"))
            return distorcida;
        else 
            return similar;
    }
    @FXML
    public void sair() {
        Platform.exit();
    }

    @FXML
    private void entered() {
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.seconds(0.4));
        t.setToX(150);
        t.setNode(gplabels);
        t.play(); 
        t.setOnFinished((ActionEvent actionEvent) -> {
            gplabels.setEffect(new DropShadow(5, Color.BLACK));
        });
    }
    
    @FXML
    private void exited() {
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.seconds(0.4));
        t.setToX(0);
        t.setNode(gplabels);
        t.play();  
        t.setOnFinished((ActionEvent actionEvent) -> {
            gplabels.setEffect(null);
        });
    }
    
    @FXML
    void entrarApp(MouseEvent event) throws IOException {
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.seconds(0.4));
        if (aux[0]) {
            t.setToY(150);
            aux[0] = false;
            aux[1] = true;
        }
        else if (aux[2]) {
            t.setToY(150);
            aux[2] = false;
            aux[1] = true;
        }
        t.setNode(active);
        t.play();  
        Node app = FXMLLoader.load(getClass().getResource("/acronianalytics/views/aplicativo.fxml"));
        sp.setContent(app);
    }

    @FXML
    void entrarJogo(MouseEvent event) throws IOException {
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.seconds(0.4));
        if (aux[1]) {
            aux[1] = false;
        } else if (aux[2]) {
            aux[2] = false;
        }
        aux[0] = true;
        t.setToY(0);
        t.setNode(active);
        t.play(); 
        Node app = FXMLLoader.load(getClass().getResource("/acronianalytics/views/jogo.fxml"));
        sp.setContent(app);
    }

    @FXML
    void entrarWebsite(MouseEvent event) throws IOException {
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.seconds(0.4));
        if (aux[0]) {
            t.setToY(300);
            aux[0] = false;
            aux[2] = true;
        }
        else if (aux[1]) {
            t.setToY(300);
            aux[1] = false;
            aux[2] = true;
        }
        t.setNode(active);
        t.play();
        Node app = FXMLLoader.load(getClass().getResource("/acronianalytics/views/website.fxml"));
        sp.setContent(app);sp.vbarPolicyProperty();
    }
    
    public void t(ScrollPane sp) {
        this.sp = sp;
    }
    
}
