
package Clinica.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Administrator
 */
public class ClinicaBemEstar extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
          
        Parent root = FXMLLoader.load(getClass().getResource("LoginClinica.fxml"));
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);     
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Clinica Bem Estar");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
