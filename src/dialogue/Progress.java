/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialogue;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author mana
 */
public class Progress{
    ProgressIndicator pi=new ProgressIndicator();
    ProgressBar pb=new ProgressBar();
    Stage stage=new Stage();
    Thread th;
    HBox pane=new HBox();
    Label msgLabel=new Label();
    Task<Integer> task;
    
    public  void ShowProgress(Task<Integer> task)
    {   this.task=task;
        pi.setStyle("-fx-background-color:transparent;-fx-font-size:8;");
        pb.setStyle("-fx-background-color:transparent;");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        pb.setPrefSize(120, 20);
        pi.setPrefSize(50, 50);
        //pi.setMaxSize(20, 20);
        
        pane.setPrefSize(250, 40);
        pane.setSpacing(10);
        pane.setStyle("-fx-background-color:transparent;");
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(pi,pb);
        msgLabel.setStyle("-fx-font-size :12;-fx-font-family:times;-fx-text-fill:red");
        
        task.progressProperty().addListener(new ChangeListener(){

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            int val=Integer.parseInt(newValue.toString().trim());
            System.out.println("Value :"+val);
            if(val>40)
            {
                msgLabel.setTextFill((Paint)Color.GREEN);
            }
        }
            
        });
        
        if(task!=null)
        {
            task.setOnSucceeded((event)->{
                stage.close();
                Dialogue.displayDialog("Task Status".toUpperCase(), "Task Completion Message", "Your task was completed successfully", Type.INFO);
            });
            task.setOnCancelled((event)->{
                Dialogue.displayDialog("Task Status".toUpperCase(), "Task Cancelled", "Your task was cancelled before completion!", Type.WARNING);
            });
            task.setOnRunning((event)->{
                msgLabel.textProperty().bind(task.messageProperty());
                
            });
            pb.progressProperty().bind(task.progressProperty());
            //pi.progressProperty().bind(task.progressProperty());
            
            
            
            
            
        }
        VBox vb=new VBox();
        vb.setStyle("-fx-background-color:transparent;");
        vb.setSpacing(20);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(pane,msgLabel);
        Scene s=new Scene(vb);
        s.setFill(Color.TRANSPARENT);
        stage.setScene(s);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
        th = new Thread(task);
        th.setDaemon(true);
        th.start();
        
    }
    
    
    
    
}
