package com.example.metadescqrscanner;

import com.google.gson.annotations.SerializedName;

public class HistoryItem {
    @SerializedName("txId")
    private String txId;
    @SerializedName("asset")
    private Asset asset;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("isDelete")
    private boolean isDelete;

    public HistoryItem(String txId, Asset asset, String timestamp, boolean isDelete) {
        this.txId = txId;
        this.asset = asset;
        this.timestamp = timestamp;
        this.isDelete = isDelete;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
