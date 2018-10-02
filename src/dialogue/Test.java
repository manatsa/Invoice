/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialogue;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.stage.Stage;

/**
 *
 * @author mana
 */
public class Test extends Application{

    Progress pr=new Progress();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Task<Integer> task=new Task() {

            @Override
            protected Integer call() throws Exception {
                System.out.println("now starting");
                for(int a=0;a<=1000000;a++)
                {
                    System.out.println(a);
                    this.updateProgress(a, 1000000);
                    this.updateMessage((a/1000000)+" complete");
                    this.updateValue(a);
                    this.updateTitle("Almost done");
                    if(a==1000000)
                        break;
                }
                return 0;
            }

          
        };
        
        
        pr.ShowProgress(task);
        
        
        
        
        
    }
    
    
    
    public static void main(String[]args)
    {
        launch(args);
    }
    
}
