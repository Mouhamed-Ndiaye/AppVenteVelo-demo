package sn.edu.ugb.ipsl.appventevelo.resources;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "message")
public class SuccessMessage implements Serializable {

    private String successMessage;
    private int statusCode;

//    private String documentation;

    public SuccessMessage(){
    }

    public SuccessMessage(String successMessage, int statusCode) {
        this.successMessage = successMessage;
        this.statusCode = statusCode;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
