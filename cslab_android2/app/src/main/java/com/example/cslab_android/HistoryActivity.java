package com.example.cslab_android;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {


    DBHelper dbHelper;

    ArrayList<DBData> list;
    RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);


        TextView LSTM_PRO = findViewById(R.id.LSTM_PRO);
        TextView LSTM_TIME = findViewById(R.id.LSTM_TIME);
        TextView Example = findViewById(R.id.example);
        TextView RNN_PRO = findViewById(R.id.RNN_PRO);
        TextView RNN_TIME = findViewById(R.id.RNN_TIME);
        TextView BERT_PRO = findViewById(R.id.BERT_PRO);
        TextView BERT_TIME = findViewById(R.id.BERT_TIME);


        Button back_page = (Button)findViewById(R.id.BackBtn1);
        back_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        dbHelper = new DBHelper(HistoryActivity.this);
        list = dbHelper.select();
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(HistoryActivity.this,list);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, View view, int position) {
                Log.d(TAG, "onItemClick: " + position);
                DBData data = list.get(position);
                LSTM_PRO.setText(String.format("%.3f%%",data.getLSTM_PRO()*100));
                LSTM_TIME.setText(String.format("%.3f",data.getLSTM_TIME()));
                RNN_PRO.setText(String.format("%.3f%%",data.getRNN_PRO()*100));
                RNN_TIME.setText(String.format("%.3f",data.getRNN_TIME()));
                BERT_PRO.setText(String.format("%.3f%%",data.getBERT_PRO()*100));
                BERT_TIME.setText(String.format("%.3f",data.getBERT_TIME()));
                Example.setText(data.getExample());

            }
        });
        recyclerView.setAdapter(adapter);

        if(list.size() != 0){
            DBData data = list.get(0);
            LSTM_PRO.setText(String.format("%.3f%%",data.getLSTM_PRO()*100));
            LSTM_TIME.setText(String.format("%.3fms",data.getLSTM_TIME()));
            RNN_PRO.setText(String.format("%.3f%%",data.getRNN_PRO()*100));
            RNN_TIME.setText(String.format("%.3fms",data.getRNN_TIME()));
            BERT_PRO.setText(String.format("%.3f%%",data.getBERT_PRO()*100));
            BERT_TIME.setText(String.format("%.3fms",data.getBERT_TIME()));
            Example.setText(data.getExample());
        }
        else{
            LSTM_PRO.setText("");
            LSTM_TIME.setText("");
            RNN_PRO.setText("");
            RNN_TIME.setText("");
            BERT_PRO.setText("");
            BERT_TIME.setText("");
            Example.setText("");
        }

        Button resetBtn = (Button)findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(HistoryActivity.this).setTitle("데이터 초기화")
                                .setMessage("정말로 데이터를 초기화 할까요?")
                                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dbHelper.reset();
                                                list.clear();
                                                adapter.notifyDataSetChanged();
                                                Toast.makeText(HistoryActivity.this,"데이터가 초기화 되었습니다",Toast.LENGTH_SHORT).show();
                                                LSTM_PRO.setText("");
                                                LSTM_TIME.setText("");
                                                RNN_PRO.setText("");
                                                RNN_TIME.setText("");
                                                BERT_PRO.setText("");
                                                BERT_TIME.setText("");
                                                Example.setText("");
                                            }
                                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();



                //reset 버튼
            }
        });


    }
}