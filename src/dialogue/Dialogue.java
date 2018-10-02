package dialogue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.geometry.Pos;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

public class Dialogue {
    
    static Dialog<Response> dialog = new Dialog<>();
    static ImageView iconView=new ImageView();
    static Thread t;
    static double a=1.0;
    static ButtonType closeBtn = new ButtonType("Close", ButtonData.CANCEL_CLOSE);

        public void setStyle(StageStyle style)
        {
            dialog.initStyle(style);
        }

        
        
        
        private static void doScale()
        {
            ScaleTransition scale=new ScaleTransition(Duration.seconds(1), iconView);
                     scale.setAutoReverse(true);
                     scale.setByX(1.0);
                     scale.setByY(1.0);
                     scale.setByZ(0.1);
                     scale.setCycleCount(2);
                     scale.play();
                     
        }
        
        
        
        
 
  
           
            
        
	public static Response displayDialog(String title,String header,Object message,int type) {
	
            Dialog<Response> dialog = new Dialog<>();
                final Result result=new Result();
		ArrayList<Hyperlink> links=new ArrayList<>();
                
		
                Bloom bloom = new Bloom();
                bloom.setThreshold(1.0);
                
                MotionBlur mb = new MotionBlur();
                mb.setRadius(10.0f);
                mb.setAngle(25.0f);
                
                BoxBlur blur = new BoxBlur();
                blur.setWidth(2);
                blur.setHeight(2);
                blur.setIterations(1);
                
                DropShadow ds = new DropShadow();
                ds.setOffsetY(3.0);
                ds.setOffsetX(3.0);
                ds.setColor(Color.BLUEVIOLET);
                
                InnerShadow is = new InnerShadow();
                is.setOffsetX(2.0f);
                is.setOffsetY(2.0f);
                is.setColor(Color.BLUEVIOLET);
                
                Reflection r = new Reflection();
                r.setFraction(0.3);
                
                Distant light = new Distant();
                light.setAzimuth(135.0f);
                Lighting l = new Lighting();
                l.setLight(light);
                l.setSurfaceScale(7.0f);
                
                PerspectiveTransform pt = new PerspectiveTransform();
                pt.setUlx(10.0f);
                pt.setUly(10.0f);
                pt.setUrx(210.0f);
                pt.setUry(40.0f);
                pt.setLrx(210.0f);
                pt.setLry(60.0f);
                pt.setLlx(10.0f);
                pt.setLly(90.0f);
                
                
		dialog.setTitle(title);
		dialog.setHeaderText(header);
                dialog.initStyle(StageStyle.TRANSPARENT);
		dialog.setResizable(false);
                
                dialog.getDialogPane().setStyle("-fx-background-color:lightgrey;-fx-alignment:center;-fx-text-fill:blue;-fx-border-color:#000060;-fx-border-width:5;-fx-background-radius:8;-fx-border-radius:8;");
                

                Label label = new Label("Please find error details below :");

                TextArea textArea = new TextArea();
                textArea.setEditable(false);
                textArea.setWrapText(true);
                
                textArea.setStyle("-fx-background-color:white;-fx-padding:0 20 0 20;-fx-text-fill:#800000;-fx-font-weight:bold;-fx-background-radius:5");
                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);

                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.setMaxHeight(Double.MAX_VALUE);
                expContent.setStyle("-fx-background-color:lightgrey;-fx-padding:0 0 0 0;-fx-text-fill:blue;");
                expContent.add(textArea, 0, 1);

                // Set expandable Exception into the dialog pane.
                
                
                Label msgLabel=new Label("");
                msgLabel.setWrapText(true);
                msgLabel.setPrefSize(400, 20);
                msgLabel.setAlignment(Pos.CENTER);
                msgLabel.setEffect(r);
                msgLabel.setMaxWidth(Double.MAX_VALUE);
                
                dialog.getDialogPane().setEffect(l);
                //ialog.initStyle(StageStyle.UTILITY);
		Response response=new Response(title,header,message);
		 dialog.getDialogPane().setContentText("");
                 dialog.getDialogPane().setContent(null);
                 dialog.getDialogPane().setExpandableContent(null);
                 dialog.setOnShown((event)->{
                     new Thread(){
                         public void run()
                         {
                            doScale();
                            
                            
                            
                         }
                     }.start();
                 });
                 switch(type)
                 {
                     case Type.ERROR:
                         msgLabel.setText("");
                         iconView.setImage(new Image(Dialogue.class.getResourceAsStream("error.png")));
                         dialog.setGraphic(iconView);
                         
                         StringWriter sw = new StringWriter();
                            PrintWriter pw = new PrintWriter(sw);                            
                                if(Exception.class.isAssignableFrom(message.getClass()))
                                {
                                    dialog.getDialogPane().setExpandableContent(expContent);
                                    dialog.getDialogPane().setExpanded(true);
                                    dialog.getDialogPane().setContent(label);
                                    ((Exception)message).printStackTrace(pw);
                                    String exceptionText = sw.toString();
                                    textArea.setText(exceptionText);
                                }else if(message.getClass()==String.class)
                                {
                                    String exceptionText = message.toString();
                                    msgLabel.setStyle("-fx-background-color:lightgrey;-fx-padding:20 0 20 0;-fx-text-fill:#800000;-fx-font-weight:bold;");
                                    msgLabel.setText(exceptionText);
                                    dialog.getDialogPane().setContent(msgLabel);
                                    
                                }
                                ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
                                    
                                    dialog.getDialogPane().getButtonTypes().clear();
                                    dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk ,closeBtn);
                                    
                                    dialog.getDialogPane().setOnKeyPressed(event->{
                                        if(event.getCode()==KeyCode.ESCAPE)
                                        {
                                            final Button ok=(Button)dialog.getDialogPane().lookupButton(ButtonType.OK);
                                            ok.fire();
                                        }
                                    }); 
                                    
                                    
                                                /*new Timer().schedule(
                                               new TimerTask() {

                                                   @Override
                                                   public void run() {
                                                       final Button ok=(Button)dialog.getDialogPane().lookupButton(ButtonType.OK);
                                                           ok.fire();
                                                   }
                                               }, 0, 5000);*/
                                            
                                    
                                    // Result converter for dialog
                                    dialog.setResultConverter(new Callback<ButtonType, Response>() {
                                            @Override
                                            public Response call(ButtonType b) {
                                                    if (b == buttonTypeOk) {
                                                        
                                                        response.setResponse(RType.OK);
                                                            
                                                    }else{
                                                        response.setResponse(RType.CANCEL);
                                                    }
                                                    return response;
                                            }
                                    });
                         break;
                     case Type.CONFIRM:
                            iconView.setImage(new Image(Dialogue.class.getResourceAsStream("question.png")));
                            dialog.setGraphic(iconView);
                            msgLabel.setText(message.toString());
                            msgLabel.setStyle("-fx-background-color:lightgrey;-fx-padding:20 0 20 0;-fx-text-fill:#800000;-fx-font-weight:bold;");
                            dialog.getDialogPane().setContent(msgLabel);
                            dialog.setGraphic(iconView);
                            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonData.OK_DONE);
                                dialog.getDialogPane().getButtonTypes().clear();
                                dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes , closeBtn);

                                // Result converter for dialog
                                dialog.setResultConverter(new Callback<ButtonType, Response>() {
                                        @Override
                                        public Response call(ButtonType b) {

                                                if (b == buttonTypeYes) {

                                                        response.setResponse(RType.YES);
                                                }else{
                                                    response.setResponse(RType.NO);
                                                }
                                                return response;
                                        }
                                });
                         break;
                     case Type.INFO: 
                        // msgLabel.setEffect(pt);
                         Image img=new Image(Dialogue.class.getResourceAsStream("info.png"));
                            iconView.setImage(img);
                            msgLabel.setText(""+message.toString()+"                   .");
                            msgLabel.setStyle("-fx-background-color:transparent;-fx-padding:20 0 20 0;-fx-text-fill:blue;-fx-font-weight:bold;");
                            dialog.setGraphic(iconView);
                            dialog.getDialogPane().setContent(msgLabel);
                         
                         ButtonType buttonTypeOk0 = new ButtonType("Okay", ButtonData.OK_DONE);
                                dialog.getDialogPane().getButtonTypes().clear();
                                dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk0 , closeBtn);

                                // Result converter for dialog
                                dialog.setResultConverter((ButtonType b) -> {
                                    if (b == buttonTypeOk0) {
                                        
                                        response.setResponse(RType.OK);
                                    }else{
                                        response.setResponse(RType.CANCEL);
                                    }
                                        return response;
                         });
                                Button ok=(Button)dialog.getDialogPane().lookupButton(buttonTypeOk0);
                                Button can=(Button)dialog.getDialogPane().lookupButton(closeBtn);
                                try{
                                    ok.setStyle("-fx-background-color:linear-gradient(to top,#400000,lightgrey);-fx-font-weight:bold;-fx-text-fill:#ffffff;-fx-border-color:white;-fx-border-width:2;-fx-border-radius:5;");
                                    can.setStyle("-fx-background-color:linear-gradient(to top,#400000,lightgrey);-fx-font-weight:bold;-fx-text-fill:#ffffff;-fx-border-color:white;-fx-border-width:2;-fx-border-radius:5;");
                                }catch(Exception e){e.printStackTrace();}
                                
                         break;
                         
                     case Type.WARNING:
                           if(iconView.getImage()==null)
                            iconView.setImage(new Image(Dialogue.class.getResourceAsStream("warning.png")));
                                dialog.setGraphic(iconView);
                                msgLabel.setText(message.toString());
                                dialog.getDialogPane().setContent(msgLabel);
                                msgLabel.setStyle("-fx-background-color:lightgrey;-fx-padding:20 0 20 0;-fx-text-fill:#800000;-fx-font-weight:bold;");
                                ButtonType buttonTypeOk1 = new ButtonType("Okay", ButtonData.OK_DONE);
                                dialog.getDialogPane().getButtonTypes().clear();
                                dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk1 , closeBtn);

                                // Result converter for dialog
                                dialog.setResultConverter((ButtonType b) -> {
                                    if (b == buttonTypeOk1) {
                                        
                                        response.setResponse(RType.OK);
                                    }else{
                                        response.setResponse(RType.CANCEL);
                                    }
                                        return response;
                                });
                                
                                Button okw=(Button)dialog.getDialogPane().lookupButton(buttonTypeOk1);
                                Button canw=(Button)dialog.getDialogPane().lookupButton(closeBtn);
                                try{
                                    okw.setStyle("-fx-background-color:linear-gradient(to top,#400000,lightgrey);-fx-font-weight:bold;-fx-text-fill:#ffffff;-fx-border-color:white;-fx-border-width:2;-fx-border-radius:5;");
                                    canw.setStyle("-fx-background-color:linear-gradient(to top,#400000,lightgrey);-fx-font-weight:bold;-fx-text-fill:#ffffff;-fx-border-color:white;-fx-border-width:2;-fx-border-radius:5;");
                                }catch(Exception e){e.printStackTrace();}
                                
                         break;
                         
                     case Type.CHOICE:
                         ObservableList list=(ObservableList)message;
                         
                         ChoiceBox box=new ChoiceBox(list);
                         box.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                         box.setMinSize(Double.MIN_VALUE, Double.MIN_VALUE);
                         box.setPrefSize(200, 30);
                         box.setStyle("-fx-text-alignment:center;");//box.setPadding(new Insets(5,10,5,15));
                         box.setValue(list.get(0));
                         box.setTooltip(new Tooltip("Expand the drop down and select an option..."));
                         VBox vb=new VBox(box);
                         vb.setPadding(new Insets(10,15,10,15));
                         dialog.getDialogPane().setContent(vb);
                         ButtonType selectBtn  = new ButtonType("Select", ButtonData.OK_DONE);
                            dialog.getDialogPane().getButtonTypes().clear();
                            dialog.getDialogPane().getButtonTypes().addAll(selectBtn , closeBtn);
                            
                         dialog.setResultConverter((ButtonType b) -> {
                                    if (b == selectBtn) {
                                        
                                        response.setIndex(box.getSelectionModel().getSelectedIndex());
                                    }else{
                                        response.setIndex(-1);
                                    }
                                        return response;
                         });
                         
                                Button okc=(Button)dialog.getDialogPane().lookupButton(selectBtn);
                                Button canc=(Button)dialog.getDialogPane().lookupButton(closeBtn);
                                try{
                                    okc.setStyle("-fx-background-color:linear-gradient(to top,#400000,lightgrey);-fx-font-weight:bold;-fx-text-fill:#ffffff;-fx-border-color:white;-fx-border-width:2;-fx-border-radius:5;");
                                    canc.setStyle("-fx-background-color:linear-gradient(to top,#400000,lightgrey);-fx-font-weight:bold;-fx-text-fill:#ffffff;-fx-border-color:white;-fx-border-width:2;-fx-border-radius:5;");
                                }catch(Exception e){e.printStackTrace();}
                         break;
                     case Type.INPUT:
                         TextField tf=new TextField();
                         Label lab=new Label("Your Input:            ");
                         lab.setMaxWidth(Double.MAX_VALUE);
                         lab.setFont(new Font("Monos,Calibri",12));
                         tf.setFont(new Font("Monos,Calibri",10));
                         lab.setStyle("-fx-font-weight:bold;");
                         tf.setStyle("-fx-font-weight:bold;-fx-padding:10 30 10 30;");
                         HBox hbox=new HBox();
                        hbox.setStyle("-fx-background-color:lightgrey;-fx-background-radius:8;"
                                + "-fx-border-width:3;-fx-border-radius:8;-fx-border-color:##bfffad;");
                         hbox.setSpacing(20);
                         hbox.getChildren().addAll(lab,tf);
                         dialog.getDialogPane().setContent(hbox);
                         ButtonType done  = new ButtonType("Done", ButtonData.OK_DONE);
                            dialog.getDialogPane().getButtonTypes().clear();
                            dialog.getDialogPane().getButtonTypes().addAll(done , closeBtn);
                            
                         dialog.setResultConverter((ButtonType b) -> {
                                    if (b == done) {
                                        
                                        response.setInput(tf.getText());
                                    }else{
                                        response.setInput("");
                                    }
                                        return response;
                         });
                         
                         Button oki=(Button)dialog.getDialogPane().lookupButton(done);
                                Button cani=(Button)dialog.getDialogPane().lookupButton(closeBtn);
                                try{
                                    oki.setStyle("-fx-background-color:linear-gradient(to top,#400000,lightgrey);-fx-font-weight:bold;-fx-text-fill:#ffffff;-fx-border-color:white;-fx-border-width:2;-fx-border-radius:5;");
                                    cani.setStyle("-fx-background-color:linear-gradient(to top,#400000,lightgrey);-fx-font-weight:bold;-fx-text-fill:#ffffff;-fx-border-color:white;-fx-border-width:2;-fx-border-radius:5;");
                                }catch(Exception e){e.printStackTrace();}
                         break;
                     case Type.LINK:
                         
                         VBox sp=new VBox();
                         dialog.getDialogPane().setOnKeyPressed(new EventHandler<KeyEvent>(){

                            @Override
                            public void handle(KeyEvent event) {
                                if(event.getCode()==KeyCode.UP)
                                {
                                    if(result.getIndex()>0){
                                        result.setIndex(result.getIndex()-1);
                                    } 
                                    else{
                                        result.setIndex(links.size()-1);
                                    }
                                   for(Hyperlink link:links)
                                   {
                                       link.setStyle("-fx-text-fill:blue;-fx-font-weight:bold;-fx-background-color:transparent;");
                                   }
                                   links.get(result.getIndex()).setStyle("-fx-text-fill:blue;-fx-font-weight:bold;-fx-background-color:lightgrey;"); 
                                }
                                if(event.getCode()==KeyCode.DOWN)
                                {
                                    if(result.getIndex()<links.size()-1){
                                        result.setIndex(result.getIndex()+1);
                                    } else{
                                        result.setIndex(0);
                                    }
                                   for(Hyperlink link:links)
                                   {
                                       link.setStyle("-fx-text-fill:blue;-fx-font-weight:bold;-fx-background-color:transparent;");
                                   }
                                   links.get(result.getIndex()).setStyle("-fx-text-fill:blue;-fx-font-weight:bold;-fx-background-color:lightgrey;"); 
                                }
                            }
                             
                         });
                         sp.setAlignment(Pos.CENTER);
                         ObservableList<String> lists=(ObservableList)message;
                         for(int a=0;a<lists.size();a++)
                         {
                             final int b=a;
                            Hyperlink select  = new Hyperlink(lists.get(a));
                            select.setPadding(new Insets(0,10,0,10));
                            select.setFont(new Font("New Time Roman",12));
                            
                            select.setStyle("-fx-text-fill:blue;-fx-font-weight:bold;-fx-background-color:transparent;");
                            if(a==0)
                            {
                                select.setStyle("-fx-text-fill:blue;-fx-font-weight:bold;-fx-background-color:lightgrey;");
                                select.requestFocus();
                                result.setIndex(a);
                            }
                            links.add(select);
                            
                            select.setOnAction(new EventHandler<ActionEvent>(){
                                @Override
                                public void handle(ActionEvent event) {                                   
                                   result.setIndex(b); 
                                   for(Hyperlink link:links)
                                   {
                                       link.setStyle("-fx-text-fill:blue;-fx-font-weight:bold;-fx-background-color:transparent;");
                                   }
                                   select.setStyle("-fx-text-fill:blue;-fx-font-weight:bold;-fx-background-color:lightgrey;");
                                }
                            });
                            
                            sp.getChildren().add(select);
                            dialog.getDialogPane().setContent(sp);
                         }
                         
                         ButtonType selectedBtn  = new ButtonType("Select", ButtonData.OK_DONE);
                            dialog.getDialogPane().getButtonTypes().clear();
                            dialog.getDialogPane().getButtonTypes().addAll(selectedBtn, closeBtn);
                            
                            
                            Button okl=(Button)dialog.getDialogPane().lookupButton(selectedBtn);
                                Button canl=(Button)dialog.getDialogPane().lookupButton(closeBtn);
                                try{
                                    okl.setStyle("-fx-background-color:linear-gradient(to top,#400000,lightgrey);-fx-font-weight:bold;-fx-text-fill:#ffffff;-fx-border-color:white;-fx-border-width:2;-fx-border-radius:5;");
                                    canl.setStyle("-fx-background-color:linear-gradient(to top,#400000,lightgrey);-fx-font-weight:bold;-fx-text-fill:#ffffff;-fx-border-color:white;-fx-border-width:2;-fx-border-radius:5;");
                                }catch(Exception e){e.printStackTrace();}
                            
                            
                         dialog.setResultConverter((ButtonType b) -> {
                                    if (b == selectedBtn) {
                                        
                                        response.setIndex(result.getIndex());
                                    }else{
                                        response.setIndex(-1);
                                    }
                                        return response;
                         });
                         
                         break;
                         
                         
                 }
                        
		
                 dialog.setWidth(dialog.getWidth()*3/2);
                 
                 
                 Task<Void> task=new Task(){
                     
                    @Override
                    protected Object call() throws Exception {
                        int a=0;
                        while(a<3)
                        {
                            try{
                                Thread.sleep(1000);
                            }catch(Exception e){}
                            a++;
                        }
                        Node img=(Node)dialog.getGraphic();
                        //img.setTranslateX(10);
                        Button b=(Button)dialog.getDialogPane().lookupButton(closeBtn);
                        try{ 
                            Platform.runLater(()->{
                                FadeTransition fd=new FadeTransition(Duration.seconds(3), dialog.getDialogPane());
                                fd.setToValue(0.0);
                                fd.setCycleCount(1);
                                fd.setDelay(Duration.seconds(1));
                                fd.setOnFinished(e->{
                                     b.fire();
                                     Stage stage=(Stage)dialog.getGraphic().getScene().getWindow();
                                     stage.close();
                                     
                                });
                                
                                switch(type)
                                {
                                    case Type.ERROR:
                                    case Type.INFO:
                                    case Type.WARNING:
                                        
                                        fd.play();
                                        break;
                                }
                                //dialog.initStyle(StageStyle.TRANSPARENT);
                               
                            });
                        
                        }catch(Exception e){e.printStackTrace();}
                        return null;
                    }
                     
                 };
                 
                 Thread tr=new Thread(task);
                 tr.start();
                
		// Show dialog and wait for the input from the user
		Optional<Response> results = dialog.showAndWait();
		
		if (results.isPresent()) {

			return results.get();
		}
                
           
                return response;
                
                
	}
	
	
        
        private void hyperClick(Hyperlink control)
        {
            MouseEvent event = new MouseEvent(MouseEvent.MOUSE_CLICKED,  
                    0, 0, // X & Y  
                    0, 0, // Screen X & Y  
  
  
                    // p.getX(), p.getY(), p.getX(), p.getY(),  
                    MouseButton.PRIMARY, // Mouse button  
                    1, // Click count  
                    false  /* shift down */, false /* control down*/, false /* Alt down */, false /* Meta down */,  
                    true, false, false /* primary, middle and secondary button down */,  
                    false /* Synthetized */, false /* popup trigger */, true /* still since press */,  
                    null /* pick result */);  
  
Event.fireEvent(control, event);
        }
        
      
        private static class Result{
            private int index;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
            
        }
}