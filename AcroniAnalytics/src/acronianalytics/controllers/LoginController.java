/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acronianalytics.controllers;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author iwakura
 */
public class LoginController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    Stage stage;

    @FXML
    private TextField txt1;
    
    @FXML
    private GridPane gridpane;
    @FXML
    private TextField txt2;

    @FXML
    private Pane sup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Mudando o caret dos TextFields
        txt1.setSkin(new CaretColor(txt1));
        txt2.setSkin(new CaretColor(txt2));
        //Evento para pegar a posição da janela
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent event) { 
                stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            } 
        };  
        sup.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
    }    

    //Evento para deslocar a janela
    @FXML
    public void topBarDragged(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    //Classe para mudar a cor do caret
    public class CaretColor extends TextFieldSkin {
        public CaretColor(TextField tf)
        {
            super(tf);
            caretPath.strokeProperty().unbind();
            caretPath.fillProperty().unbind();
            caretPath.scaleXProperty().unbind();
            caretPath.setScaleX(2);
            caretPath.setStroke(Color.rgb(0, 147, 155));
            caretPath.setFill(Color.rgb(0, 147, 155));

        }
    }

    @FXML
    public void entrar() throws IOException {
        ((Stage) txt1.getScene().getWindow()).close();
    
        Node root = FXMLLoader.load(getClass().getResource("/acronianalytics/views/masterpage.fxml"));
        Node app = FXMLLoader.load(getClass().getResource("/acronianalytics/views/aplicativo.fxml"));
        ScrollPane sp = new ScrollPane();
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setContent(app);
        AnchorPane ap = new AnchorPane();
        ap.getChildren().add(sp);
        ap.getChildren().add(root);
                
        AnchorPane.setRightAnchor(sp, 0.0);
        
               
        Stage st = new Stage();
        st.setScene(new Scene(ap)); 
        
        st.initStyle(StageStyle.UNDECORATED);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        st.setX(bounds.getMinX());
        st.setY(bounds.getMinY());
        st.setWidth(bounds.getWidth());
        st.setHeight(bounds.getHeight());
        st.setResizable(false);
        
        st.show();
        
    }
    
}
