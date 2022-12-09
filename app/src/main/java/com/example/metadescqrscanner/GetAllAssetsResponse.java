package com.example.metadescqrscanner;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllAssetsResponse {

    @SerializedName("result")
    private List<Asset> result;
    @SerializedName("error")
    private String error;
    @SerializedName("errorData")
    private String errorData;

    public GetAllAssetsResponse(List<Asset> result, String error, String errorData) {
        this.result = result;
        this.error = error;
        this.errorData = errorData;
    }

    public List<Asset> getResult() { return result; }

    public void setResult(List<Asset> result) {
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
