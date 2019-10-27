package acronianalytics.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MasterpageController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private GridPane gplabels;
    @FXML
    private GridPane gp;
    @FXML
    private ImageView sair;
    @FXML
    private Pane active;
    private static ScrollPane sp;
    
    boolean[] aux = new boolean[3];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aux[0] = true;
    }    

    @FXML
    private void entrarApp(MouseEvent event) throws IOException {
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
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setVvalue(0);
    }

    @FXML
    private void entrarJogo(MouseEvent event) throws IOException {
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
        sp.setVvalue(0);
    }

    @FXML
    private void entrarWebsite(MouseEvent event) throws IOException {
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
        sp.setContent(app);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setVvalue(0);
    }

    @FXML
    private void exited(MouseEvent event) {
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
    private void entered(MouseEvent event) {
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
    private void sair(MouseEvent event) {
        Platform.exit();
    }
    
    public void t(ScrollPane sp) {
        this.sp = sp;
    }
    
}
