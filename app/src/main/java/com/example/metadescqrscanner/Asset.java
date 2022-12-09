package com.example.metadescqrscanner;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Asset {

    @SerializedName("id")
    private String id;
    @SerializedName("SerialNumber")
    private String SerialNumber;
    @SerializedName("type")
    private String type;
    @SerializedName("tag")
    private String tag;
    @SerializedName("status")
    private String status;
    @SerializedName("price")
    private Double price;
    @SerializedName("parent")
    private String parent;
    @SerializedName("owner")
    private String owner;
    @SerializedName("attrs")
    private List<Attribute> attrs;
    @SerializedName("forSale")
    private boolean forSale;
    @SerializedName("txType")
    private String txType;
    @SerializedName("childesCount")
    private Integer childesCount;

    public Asset(
            String id, String serialNumber, String type, String tag, String status,
            Double price, String parent, String owner, List<Attribute> attrs,
            boolean forSale, String txType, Integer childesCount
    ) {
        this.id = id;
        SerialNumber = serialNumber;
        this.type = type;
        this.tag = tag;
        this.status = status;
        this.price = price;
        this.parent = parent;
        this.owner = owner;
        this.attrs = attrs;
        this.forSale = forSale;
        this.txType = txType;
        this.childesCount = childesCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Attribute> getAttrs() { return attrs; }

    public void setAttrs(List<Attribute> attrs) { this.attrs = attrs; }

    public boolean isForSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }

    public String getTxType() {
        return txType;
    }

    public void setTxType(String txType) {
        this.txType = txType;
    }

    public Integer getChildesCount() {
        return childesCount;
    }

    public void setChildesCount(Integer childesCount) {
        this.childesCount = childesCount;
    }

}
