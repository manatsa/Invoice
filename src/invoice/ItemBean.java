/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author mana
 */
public class ItemBean {

//    private  SimpleStringProperty same;
//    private  SimpleStringProperty stype;
//    private  SimpleStringProperty sqnty;
//    private  SimpleStringProperty sunit;
//    private  SimpleStringProperty stprice;
//    
//    String name,type,price,qnty,tprice;
//    
//    public ItemBean(String name, String type, String qnty, String price) {
//        same = new SimpleStringProperty(name);
//        stype = new SimpleStringProperty(type);
//        sqnty = new SimpleStringProperty(qnty);
//        sunit = new SimpleStringProperty(price);
//        Double tot=Double.parseDouble(qnty)* Double.parseDouble(price);
//        stprice = new SimpleStringProperty(tot+"");
//    }
//
//    public ItemBean() {
//    }

//    public SimpleStringProperty getSame() {
//        return same;
//    }
//
//    public SimpleStringProperty getStype() {
//        return stype;
//    }
//
//    public SimpleStringProperty getSqnty() {
//        return sqnty;
//    }
//
//    public SimpleStringProperty getStprice() {
//        return stprice;
//    }
//
//    public SimpleStringProperty getSunit() {
//        return sunit;
//    }
//
//    public String getName() {
//        name=this.same.get();
//        return this.same.get();
//    }
//    
//
//    public void setName(String name) {
//        this.same.set(name);
//    }
//
//    public String getType() {
//        type=this.stype.get();
//        return this.stype.get();
//    }
//
//    public void setType(String type) {
//        this.stype.set(type);
//    }
//
//    public String getQnty() {
//        qnty=this.sqnty.get();
//        return this.sqnty.get();
//    }
//
//    public void setQnty(String qnty) {
//        this.sqnty.set(qnty);
//    }
//
//    public String getPrice() {
//        price=this.sunit.get();
//        return this.sunit.get();
//    }
//
//    public void setPrice(String price) {
//        this.sunit.set(price);
//    }
//    
//     public String getTotalPrice() {
//         tprice=this.stprice.get();
//        return this.stprice.get();
//    }
//
//    public void setTotalPrice(String price) {
//        this.stprice.set(price);
//    }
//   
    
    
    
     private String name,type,qnty,price,tprice;

   
    
    public ItemBean(String name, String type, String qnty, String price) {
        this.name = name;
        this.type = type;
        this.qnty = qnty;
        this.price = price;
        this.tprice=(Double.parseDouble(qnty) * Double.parseDouble(price))+"";
    }

    public ItemBean() {
    }

    public String getName() {
        return name;
    }
    

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQnty() {
        return qnty;
    }

    public void setQnty(String qnty) {
        this.qnty = qnty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }
    
    
}
