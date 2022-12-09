package com.example.metadescqrscanner;

import com.google.gson.annotations.SerializedName;

public class Attribute {

    @SerializedName("key")
    private String key;
    @SerializedName("value")
    private String value;
    @SerializedName("instruction")
    private String instruction;

    public Attribute(String key, String value, String instruction) {
        this.key = key;
        this.value = value;
        this.instruction = instruction;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

}
