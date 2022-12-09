package com.example.metadescqrscanner;

import com.google.gson.annotations.SerializedName;

public class ChangeOwnerReqResponse {

    @SerializedName("result")
    private ChangeOwnerResult result;
    @SerializedName("error")
    private String error;
    @SerializedName("errorData")
    private String errorData;

    public ChangeOwnerReqResponse(ChangeOwnerResult result, String error, String errorData) {
        this.result = result;
        this.error = error;
        this.errorData = errorData;
    }

    public ChangeOwnerResult getResult() {
        return result;
    }

    public void setResult(ChangeOwnerResult result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorData() {
        return errorData;
    }

    public void setErrorData(String errorData) {
        this.errorData = errorData;
    }

}
