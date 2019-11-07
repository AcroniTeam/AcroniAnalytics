/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acronianalytics;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.ImageIcon;

public class Launcher extends Application {
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
            
        Parent root = FXMLLoader.load(getClass().getResource("/acronianalytics/views/login.fxml"));
        try {
            primaryStage.setTitle("Acroni");
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.getIcons().add(new Image(new FileInputStream("src/res/img/logo.png"))); 
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            
        }
    }

    public static void main(String[] args)
    {           
        launch(args);   
    }
    
}
