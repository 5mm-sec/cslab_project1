package com.example.cslab_android;

import com.google.gson.annotations.SerializedName;

public class registerDATA {
    @SerializedName("EXAMPLE")

    private String EXAMPLE;

    @SerializedName("LSTM-probability")
    private double LSTM_PRO;

    @SerializedName("LSTM-time")
    private double LSTM_TIME;


    @SerializedName("RNN-probability")
    private double RNN_PRO;
    @SerializedName("RNN-time")
    private double RNN_TIME;

    @SerializedName("BERT-probability")
    private double BERT_PRO;

    @SerializedName("BERT-time")
    private double BERT_TIME;


    public String getEXAMPLE() {
        return EXAMPLE;
    }

    public double getLSTM_PRO() {
        return LSTM_PRO;
    }

    public double getLSTM_TIME() {
        return LSTM_TIME;
    }

    public double getRNN_PRO() {
        return RNN_PRO;
    }

    public double getRNN_TIME() {
        return RNN_TIME;
    }

    public double getBERT_PRO() {
        return BERT_PRO;
    }

    public double getBERT_TIME() {
        return BERT_TIME;
    }

}