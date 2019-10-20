/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acronianalytics;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

/**
 *
 * @author iwakura
 */
public class Launcher extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/acronianalytics/views/login.fxml"));
        try {
        
            primaryStage.setTitle("Shit is crazy shit is yaeji");
            primaryStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FirebaseException, IOException 
    {
        // get the base-url (ie: 'http://gamma.firebase.com/username')
		String firebase_baseUrl = "https://analytics-7777.firebaseio.com/";

		// get the api-key (ie: 'tR7u9Sqt39qQauLzXmRycXag18Z2')
		String firebase_apiKey = "AIzaSyCmE5kK8pdR1oyD3EOcU4zsnxYq2XSylIE";

		for( String s : args ) {
			if( s == null || s.trim().isEmpty() ) continue;
			String[] split = s.trim().split( "=" );

			if( split[0].equals("baseUrl") ) {
				firebase_baseUrl = split[1];
			}
			else if( split[0].equals("apiKey") ) {
				firebase_apiKey = split[1];
			}
		}
                
		if(firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty() ) {
			throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );
		}

		// create the firebase
		Firebase firebase = new Firebase(firebase_baseUrl);

                // "DELETE" (the fb4jDemo-root)
		FirebaseResponse response;
                
                // "GET" (the fb4jDemo-root)
		response = firebase.get();
		System.out.println( "\n\nResult of GET:\n" + response );
		System.out.println("\n");
                
        launch(args);
        
        
    }
    
}
