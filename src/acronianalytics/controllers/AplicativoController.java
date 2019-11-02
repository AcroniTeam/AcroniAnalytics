package acronianalytics.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Calendar;
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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.service.Firebase;

public class AplicativoController implements Initializable {

    @FXML
    private GridPane gplabels;
  
    @FXML
    private Pane active;
    
    @FXML
    private PieChart pie;
    
    @FXML
    private AreaChart<?, ?> line;
    
    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    
    @FXML
    private AnchorPane anchorApp;
    
    @FXML
    private Label comum;
    
    @FXML
    private Label premium;
    
    private static ScrollPane sp;
    
    boolean[] aux = new boolean[3];
    int p, np;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aux[0] = true;
        String firebase_baseUrl = "https://analytics-7777.firebaseio.com/";
        String firebase_apiKey = "AIzaSyCmE5kK8pdR1oyD3EOcU4zsnxYq2XSylIE";
        Firebase firebase;
        //Gráfico dos teclados criados
        try {
            firebase = new Firebase(firebase_baseUrl);
            String response;
            response = firebase.get("/relatoriosMensais/desktop/"+Calendar.getInstance().get(Calendar.YEAR)).getRawBody();
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (response, JsonElement.class);
            JsonObject json = element.getAsJsonObject(); 
            XYChart.Series series = new XYChart.Series();
            for (int i = 1; i <= Calendar.getInstance().get(Calendar.MONTH)+1; i++) {
                series.getData().add(new XYChart.Data<>(mesPTBR(i),Integer.parseInt(json.getAsJsonObject(mesPTBR(i)).get("qntTecladosProduzidosPorMes").toString())));
            }
            line.setLegendVisible(false);
            yAxis.setLabel("N.º Visitas");
            yAxis.setTickUnit(100);
            line.getData().add(series);
        } catch (FirebaseException ex) {
            Logger.getLogger(WebsiteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AplicativoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Gráfico dos ususários premium
        try {
            firebase = new Firebase(firebase_baseUrl);
            String response;
            response = firebase.get("/relatoriosGlobais/site/premium").getRawBody();
            Gson gson = new Gson();
            JsonElement element = gson.fromJson (response, JsonElement.class);
            JsonObject json = element.getAsJsonObject(); 
            p = Integer.parseInt(json.get("s").toString());
            np = Integer.parseInt(json.get("n").toString());
            int total = p + np;
            ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
            int pp = (p*100)/total;
            int pnp = (np*100)/total;
            list.add(new PieChart.Data("Comum", pnp));
            list.add(new PieChart.Data("Premium", pp));
            comum.setText(pnp+"% comum");
            premium.setText(pp+"% premium");
            pie.setData(list);
            pie.setLegendVisible(false);
            pie.setLabelsVisible(false);
            
        } catch (FirebaseException ex) {
            Logger.getLogger(AplicativoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AplicativoController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
