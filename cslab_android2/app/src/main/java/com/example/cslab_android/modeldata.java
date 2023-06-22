package com.example.cslab_android;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class modeldata {
    @SerializedName("probability")
    @Expose
    private double ACC;

    @SerializedName("time")
    @Expose
    private double SEC;

    public double getACC(){
        return this.ACC;
    }
    public double getSEC(){
        return this.SEC;
    }
}