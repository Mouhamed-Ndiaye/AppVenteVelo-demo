package sn.edu.ugb.ipsl.appventevelo.resources.exceptions;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement(name = "message")
public class ErrorMessage implements Serializable {

    private String errorMessage;
    private int errorCode;
   private String DOCUMENTATION = "localhost:8080/AppVenteVelo-1.0-SNAPSHOT/documentations/index.html";

    public ErrorMessage(){
    }

    public ErrorMessage(String errorMessage, int errorCode, String DOCUMENTATION) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDOCUMENTATION() {
        return DOCUMENTATION;
    }

    public void setDOCUMENTATION(String DOCUMENTATION) {
        this.DOCUMENTATION = DOCUMENTATION;
    }
}
