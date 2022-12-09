package com.example.metadescqrscanner;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryReqResult {
    @SerializedName("result")
    private List<HistoryItem> result;
    @SerializedName("error")
    private String error;
    @SerializedName("errorData")
    private String errorData;

    public HistoryReqResult(List<HistoryItem> result, String error, String errorData) {
        this.result = result;
        this.error = error;
        this.errorData = errorData;
    }

    public List<HistoryItem> getResult() {
        return result;
    }

    public void setResult(List<HistoryItem> result) {
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
