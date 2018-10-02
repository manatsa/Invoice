/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

import java.net.URL;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author mana
 */
public class Invoice extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest((WindowEvent e)->{
            System.exit(0);
        });
        Pane root;

                        try{
                            URL url = new URL(this.getClass().getResource("main.fxml").toExternalForm());

                                        FXMLLoader fxmlLoader = new FXMLLoader();
                                        fxmlLoader.setLocation(url);
                                        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                                        root = (Pane)fxmlLoader.load(url.openStream());
                                        Scene scene=new Scene(root);
                                        
                                        primaryStage.setResizable(false);
                                        primaryStage.setTitle("HAM INVOICING APP");
                                        primaryStage.setScene(scene);
                                        primaryStage.setOnShowing((event)->{
                                            primaryStage.getIcons().add(new Image(MainController.class.getResource("h1.png").toExternalForm()));
                                        });
                                        primaryStage.show();
                                        
                        }catch(Exception e){
                            e.printStackTrace();
                            
                        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
