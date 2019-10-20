package acronianalytics.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
    
    private static ScrollPane sp;
    
    boolean[] aux = new boolean[3];
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aux[0] = true;
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        list.add(new PieChart.Data("Comum", 80));
        list.add(new PieChart.Data("Premium", 20));
        pie.setData(list);
        pie.setLegendVisible(false);
        pie.setLabelsVisible(false);
        
        line.setLegendVisible(false);
        yAxis.setLabel("N.º Teclados");
        yAxis.setTickUnit(100);
                
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("Janeiro",100));
        series.getData().add(new XYChart.Data<>("Fevereiro",200));
        series.getData().add(new XYChart.Data<>("Março",300));
        series.getData().add(new XYChart.Data<>("Abril",200));
        series.getData().add(new XYChart.Data<>("Maio",250));
        series.getData().add(new XYChart.Data<>("Junho",150));
        series.getData().add(new XYChart.Data<>("Julho",50));
        line.getData().add(series);
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
