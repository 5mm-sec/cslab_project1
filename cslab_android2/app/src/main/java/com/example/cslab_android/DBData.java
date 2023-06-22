package com.example.cslab_android;

public class DBData {

    private String example;
    private double LSTM_PRO;
    private double LSTM_TIME;
    private double RNN_PRO;
    private double RNN_TIME;
    private double BERT_PRO;
    private double BERT_TIME;

    public String getExample() {
        return example;
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

    public DBData(String example, double LSTM_PRO, double LSTM_TIME,
                  double RNN_PRO, double RNN_TIME,
                  double BERT_PRO, double BERT_TIME){
        this.example = example;

        this.LSTM_PRO = LSTM_PRO;
        this.LSTM_TIME = LSTM_TIME;
        this.RNN_PRO = RNN_PRO;
        this.RNN_TIME = RNN_TIME;
        this.BERT_PRO = BERT_PRO;
        this.BERT_TIME = BERT_TIME;
    }

    @Override
    public String toString(){
        return  this.example +" "+ this.LSTM_PRO +" "+ this.LSTM_TIME +" "+ this.RNN_PRO +" "+ this.RNN_TIME +" "+ this.BERT_PRO +" "+ this.BERT_TIME;

    }


}
