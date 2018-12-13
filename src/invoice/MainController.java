/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

import dialogue.Dialogue;
import dialogue.Type;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author mana
 */
public class MainController implements Initializable{

    double grandTot=0.00;
    double total=0.00;
    double vattotal=0.00;
    double vat=0.00;
    NumberFormat form = new DecimalFormat("#0.00"); 
    Map<String,ItemBean> map=new HashMap<>();
    ArrayList<Double> subs=new ArrayList<>();
    @FXML
    TextField custText,itName,itType,itQnty,itPrice,taskText,invText,contactText,vatText,vatNumText,addText;
    
    @FXML
    TableView<ItemBean> itemsTable;
    
    @FXML
    DatePicker invDate;
    
    @FXML private TableColumn<ItemBean, String> nameCol;
    @FXML private TableColumn<ItemBean, String> typeCol;
    @FXML private TableColumn<ItemBean, String> qntyCol;
    @FXML private TableColumn<ItemBean, String> unitCol;
    @FXML private TableColumn<ItemBean, String> totCol;
    
    
    
    @FXML
    public void cancel()
    {
       ((Stage)custText.getScene().getWindow()).close(); 
       System.exit(0);
    }
    
     @FXML
    public void clear()
    {
       itName.setText("");
       itType.setText("");
       itQnty.setText("");
       itPrice.setText("");
    }
    
    @FXML
    public void preview()
    {
        doPreview();
    }
    
    @FXML
    public void addItem()
    {
       String name=itName.getText();
       String type=itType.getText();
       String qnty=itQnty.getText();
       String price=itPrice.getText();
       
       if(name.length()>0 && qnty.length()>0 && price.length()>0)
       {
           ItemBean item=new ItemBean(name, type, qnty, price);
           map.put(name, item);
           Dialogue.displayDialog("ITEM INSERT", "Your Item was added", "The item was successfully added to the list!", Type.INFO);
           itName.setText("");
           itType.setText("");
           itQnty.setText("");
           itPrice.setText("");
           Double subt=Double.parseDouble(item.getPrice())*Double.parseDouble(item.getQnty());
           subs.add(subt);
           total=Double.parseDouble(calculateTotal());
           vattotal=Double.parseDouble(calculateVat());
           grandTot=Double.parseDouble(calculateGrandTotal());
           ObservableList<ItemBean> items=FXCollections.observableArrayList();
           for(ItemBean it: map.values()){
               items.add(it);
           }
           //itemsTable.getItems().clear();
           itemsTable.setItems(items);
           
        //System.err.println("Total :"+total+"\nVat :"+vattotal+"\nGrandTotal :"+grandTot);
       }else{
           Dialogue.displayDialog("MISSING INFO", "Required info is missing!", "Please fill all required fields", Type.ERROR);
       }
    }
    
    @FXML
    public void RemoveAllItems()
    {
        map.clear();
        subs.clear();
        grandTot=0.00;
        total=0.00;
        vattotal=0.00;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       invDate.setValue(LocalDate.now());
       System.out.println("Value : "+nameCol.getId());
       nameCol.setCellValueFactory(new PropertyValueFactory("name"));
       typeCol.setCellValueFactory(new PropertyValueFactory("type"));
       qntyCol.setCellValueFactory(new PropertyValueFactory("qnty"));
       unitCol.setCellValueFactory(new PropertyValueFactory("price"));
       totCol.setCellValueFactory(new PropertyValueFactory("tprice"));
    }

    private void doPreview() {
     Date date=null;
     
     try{
        date=Date.valueOf(invDate.getValue());
        String customer=custText.getText();
        String contact=contactText.getText();
        String invoicer=invText.getText();
        String task=taskText.getText();
        String vattext=vatText.getText();
        String vatnum=vatNumText.getText();
        vat=Double.parseDouble(vattext.trim());
        System.err.println("Vat Text:"+vat);
        if(customer.length()>0 && task.length()>0 && invoicer.length()>0 && contact.length()>0)
        {
            VBox dummy=new VBox();
            dummy.setSpacing(20);
            VBox main=new VBox();
            main.setSpacing(10);
            main.setAlignment(Pos.CENTER);
            main.setStyle("-fx-background-color:white;-fx-padding:30 20 30 20");
            
            HBox header=new HBox();
            header.setAlignment(Pos.CENTER);
            header.setSpacing(5);
            ImageView imgv=new ImageView(MainController.class.getResource("h1.png").toExternalForm());
            imgv.setFitHeight(60);
            imgv.setFitWidth(75);
            Label coLabel=new Label("HAM Instrumentation  &  Electrical");
            coLabel.setStyle("-fx-font-weight:bold;-fx-text-fill:hotpink;-fx-font-size:14;");
            header.getChildren().addAll(imgv,coLabel);
            main.getChildren().add(header);
            
            GridPane grid=new GridPane();
            grid.setHgap(5);
            grid.setVgap(5);
            
            Label dateLb=new Label("Date");
            dateLb.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label vatNumLb=new Label("VAT Number");
            vatNumLb.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label addLb=new Label("Cust Address");
            addLb.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            
            Label custLb=new Label("Customer");
            custLb.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label tasklb=new Label("Task Involved");
            tasklb.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label dateVal=new Label(date.toString());
            dateVal.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label custVal=new Label(customer);
            custVal.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label taskVal=new Label(task);
            taskVal.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label vatsVal=new Label(vatnum);
            vatsVal.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label addVal=new Label(addText.getText());
            addVal.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            
            grid.add(dateLb, 0, 0);
            grid.add(new Label(" : "), 1, 0);
            grid.add(dateVal, 2, 0);
            grid.add(vatNumLb, 0, 1);
            grid.add(new Label(" : "), 1, 1);
            grid.add(vatsVal, 2, 1);
            
            grid.add(custLb, 0, 2);
            grid.add(new Label(" : "), 1, 2);
            grid.add(custVal, 2, 2);
            grid.add(addLb, 0, 3);
            grid.add(new Label(" : "), 1, 3);
            grid.add(addVal, 2, 3);
            grid.add(tasklb, 0, 5);
            grid.add(new Label(" : "), 1, 5);
            grid.add(taskVal, 2, 5);
            
            Label itemsLb=new Label("ITEMS");
            itemsLb.setStyle("-fx-font-weight:bold;-fx-font-size:12;");
            grid.add(itemsLb, 0, 6);
            int size=5;
            Set<String> keys=map.keySet();
            for(int a=0;a<map.size();a++)
            {
                ItemBean item=map.get(keys.toArray()[a].toString());
                Label qntlb=new Label(item.getQnty()+" x");
                qntlb.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
                Label typlb=new Label(item.getType());
                typlb.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
                Label namlb=new Label(item.getName());
                namlb.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
                Label priclb=new Label("at "+item.getPrice());
                priclb.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
                Double subt=Double.parseDouble(item.getPrice())*Double.parseDouble(item.getQnty());
                //subs.add(subt);
                NumberFormat form = new DecimalFormat("#0.00");
                String sub=form.format(subt);
                Label totlb=new Label("= R"+sub);
                totlb.setStyle("-fx-font-weight:bold;-fx-font-size:12;");
                grid.add(qntlb, 0, a+7);
                grid.add(typlb, 1, a+7);
                grid.add(namlb, 2, a+7);
                grid.add(priclb, 3, a+7);
                grid.add(totlb, 4, a+7);
                size+=1;
            }
            Label totallb=new Label("Sub Total");
            totallb.setStyle("-fx-font-weight:bold;-fx-font-size:12;-fx-text-fill:red;");
            Label totalVal=new Label("R"+form.format(total));
            totalVal.setStyle("-fx-font-weight:bold;-fx-font-size:12;-fx-text-fill:red;-fx-border-color: red,transparent;-fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0, 0 0 1 0 ;-fx-border-style:solid;-fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0, 0 0 4 0;");
            
            grid.add(totallb, 0, size+2);
            grid.add(new Label(" : "), 1, size+2);
            grid.add(totalVal, 2, size+2);
            
            
            
            Label vatlb=new Label("Vat("+form.format(vat)+"%)");
            vatlb.setStyle("-fx-font-weight:bold;-fx-font-size:11;-fx-text-fill:red;");
            Label vatVal=new Label("R"+ form.format(vattotal));
            vatVal.setStyle("-fx-font-weight:bold;-fx-font-size:11;-fx-text-fill:red;-fx-border-color: transparent;-fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0, 0 0 1 0 ;-fx-border-style:solid;-fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0, 0 0 4 0;");
            
            grid.add(vatlb, 0, size+3);
            grid.add(new Label(" : "), 1, size+3);
            grid.add(vatVal, 2, size+3);
            
            
            Label grandlb=new Label("GRAND TOTAL");
             
            grandlb.setStyle("-fx-font-weight:bold;-fx-font-size:14;-fx-text-fill:red;");
            Label grandVal=new Label("R"+form.format(grandTot));
            grandVal.setStyle("-fx-font-weight:bold;-fx-font-size:14;-fx-text-fill:red;-fx-border-color: red, transparent, red,transparent;-fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0, 0 0 1 0 ;-fx-border-style:solid;-fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0, 0 0 4 0;");
            
            grid.add(grandlb, 0, size+4);
            grid.add(new Label(" : "), 1, size+4);
            grid.add(grandVal, 2, size+4);
            
            
            Label contlb=new Label("Contact");
            contlb.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label person=new Label("Invoiced By");
            person.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label contVal=new Label(contact);
            contVal.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            Label personVal=new Label(invoicer);
            personVal.setStyle("-fx-font-weight:bold;-fx-font-size:10;");
            
            grid.add(contlb, 0, size+6);
            grid.add(new Label(" : "), 1, size+6);
            grid.add(contVal, 2, size+6);
            grid.add(person, 0, size+7);
            grid.add(new Label(" : "), 1, size+7);
            grid.add(personVal, 2, size+7);
            
            
            Stage stage=new Stage();
            stage.getIcons().add(new Image(MainController.class.getResource("h1.png").toExternalForm()));
            main.getChildren().add(grid);
            dummy.getChildren().add(main);
            Button printBtn=new Button("Print");
            printBtn.setDefaultButton(true);
            printBtn.setStyle("-fx-border-color:blue;-fx-background-radius:5;-fx-border-radius:5;-fx-border-width:2;-fx-font-weight:bold;-fx-text-fill:blue;");
            printBtn.setOnAction(event->{
                print(main);
                
            });
            Button cancelBtn=new Button("Cancel");
            cancelBtn.setStyle("-fx-border-color:blue;-fx-background-radius:5;-fx-border-radius:5;-fx-border-width:2;-fx-font-weight:bold;-fx-text-fill:blue;");
            cancelBtn.setOnAction(event->{
                stage.close();
            });
            BorderPane border=new BorderPane();
            border.setLeft(cancelBtn);
            border.setRight(printBtn);
            border.setStyle("-fx-background-color:grey;-fx-border-color:lightblue;-fx-background-radius:8;-fx-border-width:3;-fx-border-radius:8;-fx-padding:10 20 10 20");
            dummy.getChildren().add(border);
            stage.setScene(new Scene(dummy));
            stage.show();
            
        }else{
            Dialogue.displayDialog("MISSING INFO", "Need Customer Name, Contact and Name of Invoicer", "Please enter all required details!", Type.ERROR);
        }
     }catch(Exception e)
     {
         Dialogue.displayDialog("ERROR", "Date Error", e, Type.ERROR);
     }
    }
    
  
    
  public void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        double scale=(scaleX<scaleY)?scaleX:scaleY;
        if(scaleX<1.0 || scaleY<1.0){
            node.getTransforms().add(new Scale(scale, scale));
        }
 
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }  

  private String calculateVat() {
        double vattotals=0.00;
        System.err.println("total : "+total+"/t vat :"+vat);
        vattotals=(total*vat)/100;
        
        return form.format(vattotals);
        
    }
  
    private String calculateTotal() {
        double total=0.00;
        for(double sub:subs)
        {
            total+=sub;
        }
        total+=0.001;
       
        return form.format(total);
        
    }
    
    private String calculateGrandTotal() {
        
        double grTotal=total+vattotal;
        
        return form.format(grTotal);
        
    }
    
    
    @FXML
    private double onVatChanged()
    {
        double vt=0.0;
        
        vat=Double.parseDouble(vatText.getText().trim());
        System.err.println("VAT NOW IS :"+vat);
        return vat;
    }
    
}
