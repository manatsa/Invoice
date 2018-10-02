/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialogue;

/**
 *
 * @author mana
 */
public class Response {
    private String title,header;
    private Object message;
    private char response;
    private int index;
    private String input;

    public Response(String title, String header, Object message) {
        this.title = title;
        this.header = header;
        this.message = message;
    }
    
    

    public Response() {
    }

    @Override
    public String toString() {
        return "Title :"+title+"\nHeader :"+header+"\nMessage :"+message.toString()
                +"\nSelect Index :"+index+"\nInputText :"+input+"\nResponse Char :"+response;
    }

    
    
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public char getResponse() {
        return response;
    }

    public void setResponse(char response) {
        this.response = response;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
    
    
}
