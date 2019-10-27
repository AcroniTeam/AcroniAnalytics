package acronianalytics.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;

public class WebsiteController implements Initializable {

    @FXML
    private PieChart pie;
    
    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    boolean[] aux = new boolean[3];
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aux[0] = true;
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        list.add(new PieChart.Data("HyperX", 20));
        list.add(new PieChart.Data("Logitech", 20));
        list.add(new PieChart.Data("Razer", 40));
        list.add(new PieChart.Data("Redragon", 20));
        
        pie.setData(list);
        pie.setLegendVisible(false);
        pie.setLabelsVisible(false);
        
    }
    
}
