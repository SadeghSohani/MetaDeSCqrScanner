package com.example.metadescqrscanner;

import com.google.gson.annotations.SerializedName;

public class ChangeOwnerResult {

    @SerializedName("message")
    private String message;
    @SerializedName("body")
    private Asset body;

    public ChangeOwnerResult(String message, Asset body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Asset getBody() {
        return body;
    }

    public void setBody(Asset body) {
        this.body = body;
    }

}