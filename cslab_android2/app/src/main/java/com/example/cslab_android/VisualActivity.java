package com.example.cslab_android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VisualActivity extends AppCompatActivity {

    MyValueFormatter myValueFormatter = new MyValueFormatter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual);

        Button AppDescBtn = findViewById(R.id.button5);
        AppDescBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });
        setTimeChart();
        setLossChart();
        setAccChart();

        setRecentchart();






    }

    public void setTimeChart(){
        BarChart timeChart = (BarChart) findViewById(R.id.timeChart);
        ArrayList<BarEntry> timeEntryChart1 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<BarEntry> timeEntryChart2 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<BarEntry> timeEntryChart3 = new ArrayList<>(); // 데이터를 담을 Arraylist

        BarData timeBarData = new BarData(); // 차트에 담길 데이터
        timeEntryChart1.add(new BarEntry(1.0f, 229.816f)); //entry_chart1에 좌표 데이터를 담는다.
        timeEntryChart2.add(new BarEntry(2.0f, 47.924f));
        timeEntryChart3.add(new BarEntry(3.0f, 10370.251f));
        BarDataSet rnnTime = new BarDataSet(timeEntryChart1, "RNN"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        BarDataSet lstmTime = new BarDataSet(timeEntryChart2, "LSTM"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        BarDataSet bertTime = new BarDataSet(timeEntryChart3, "BERT"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        rnnTime.setValueFormatter(myValueFormatter);
        lstmTime.setValueFormatter(myValueFormatter);
        bertTime.setValueFormatter(myValueFormatter);
        rnnTime.setColor(Color.parseColor("#1E90FF")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        lstmTime.setColor(Color.parseColor("#2CA02C")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        bertTime.setColor(Color.parseColor("#E35F62")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        timeBarData.addDataSet(rnnTime);
        timeBarData.addDataSet(lstmTime);
        timeBarData.addDataSet(bertTime);
        timeChart.setData(timeBarData); // 차트에 위의 DataSet 을 넣는다.
        timeChart.getXAxis().setDrawGridLines(false);
        timeChart.getXAxis().setDrawLabels(false);
        timeChart.animateY(2000);
        timeChart.getDescription().setText("에측시간");
        timeChart.getDescription().setTextSize(10);
        timeChart.invalidate(); // 차트 업데이트
        timeChart.setTouchEnabled(false); // 차트 터치 불가능하게
    }

    public void  setLossChart(){
        IValueFormatter myFormatter = new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat mFormat = new DecimalFormat("0.000");
                return mFormat.format(value);
            }
        };
        BarChart lossChart = (BarChart) findViewById(R.id.lossChart);
        ArrayList<BarEntry> lossEntryChart1 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<BarEntry> lossEntryChart2 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<BarEntry> lossEntryChart3 = new ArrayList<>(); // 데이터를 담을 Arraylist

        BarData lossBarData = new BarData(); // 차트에 담길 데이터
        lossEntryChart1.add(new BarEntry(1.0f, 0.513f)); //entry_chart1에 좌표 데이터를 담는다.
        lossEntryChart2.add(new BarEntry(2.0f, 0.0218f));
        lossEntryChart3.add(new BarEntry(3.0f, 0.0287f));
        BarDataSet rnnloss = new BarDataSet(lossEntryChart1, "RNN"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        BarDataSet lstmloss = new BarDataSet(lossEntryChart2, "LSTM"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        BarDataSet bertloss = new BarDataSet(lossEntryChart3, "BERT"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        rnnloss.setValueFormatter(myFormatter);
        lstmloss.setValueFormatter(myFormatter);
        bertloss.setValueFormatter(myFormatter);
        rnnloss.setColor(Color.parseColor("#1E90FF")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        lstmloss.setColor(Color.parseColor("#2CA02C")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        bertloss.setColor(Color.parseColor("#E35F62")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        lossBarData.addDataSet(rnnloss);
        lossBarData.addDataSet(lstmloss);
        lossBarData.addDataSet(bertloss);
        lossChart.setData(lossBarData); // 차트에 위의 DataSet 을 넣는다.
        lossChart.getXAxis().setDrawGridLines(false);
        lossChart.getXAxis().setDrawLabels(false);
        lossChart.animateY(2000);
        lossChart.getDescription().setText("loss");
        lossChart.getDescription().setTextSize(10);
        lossChart.invalidate(); // 차트 업데이트
        lossChart.setTouchEnabled(false); // 차트 터치 불가능하게
    }

    public  void setAccChart(){


        BarChart AccChart = (BarChart) findViewById(R.id.accChart);
        ArrayList<BarEntry> AccEntryChart1 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<BarEntry> AccEntryChart2 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<BarEntry> AccEntryChart3 = new ArrayList<>(); // 데이터를 담을 Arraylist

        IValueFormatter myFormatter = new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat mFormat = new DecimalFormat("0.0");
                return mFormat.format(value) + "%";
            }
        };

        BarData lossBarData = new BarData(); // 차트에 담길 데이터
        AccEntryChart1.add(new BarEntry(1.0f, 73.3f)); //entry_chart1에 좌표 데이터를 담는다.
        AccEntryChart2.add(new BarEntry(2.0f, 99.4f));
        AccEntryChart3.add(new BarEntry(3.0f, 99.0f));
        BarDataSet rnnAcc = new BarDataSet(AccEntryChart1, "RNN"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        BarDataSet lstmAcc = new BarDataSet(AccEntryChart2, "LSTM"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        BarDataSet bertAcc = new BarDataSet(AccEntryChart3, "BERT"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        rnnAcc.setValueFormatter(myFormatter);
        lstmAcc.setValueFormatter(myFormatter);
        bertAcc.setValueFormatter(myFormatter);
        rnnAcc.setColor(Color.parseColor("#1E90FF")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        lstmAcc.setColor(Color.parseColor("#2CA02C")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        bertAcc.setColor(Color.parseColor("#E35F62")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        lossBarData.addDataSet(rnnAcc);
        lossBarData.addDataSet(lstmAcc);
        lossBarData.addDataSet(bertAcc);
        AccChart.setData(lossBarData); // 차트에 위의 DataSet 을 넣는다.
        AccChart.getXAxis().setDrawGridLines(false);
        AccChart.getXAxis().setDrawLabels(false);
        AccChart.animateY(2000);
        AccChart.getDescription().setText("Acc");
        AccChart.getDescription().setTextSize(10);
        AccChart.invalidate(); // 차트 업데이트
        AccChart.setTouchEnabled(false); // 차트 터치 불가능하게
        //AccChart.getXAxis().setAxisMinimum(0.0f);
        AccChart.getAxisLeft().setStartAtZero(true);
        AccChart.getAxisRight().setStartAtZero(true);
      //
    }

    public void setRecentchart(){
        BarChart recentChart = (BarChart) findViewById(R.id.recentChart);
        ArrayList<BarEntry> timeEntryChart1 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<BarEntry> timeEntryChart2 = new ArrayList<>(); // 데이터를 담을 Arraylist
        ArrayList<BarEntry> timeEntryChart3 = new ArrayList<>(); // 데이터를 담을 Arraylist

        BarData timeBarData = new BarData(); // 차트에 담길 데이터

        DBHelper myDB = new DBHelper(VisualActivity.this);
        ArrayList <DBData> data = myDB.select();
        double rnn = 0;
        double lstm = 0;
        double bert = 0;
        for(DBData mData : data){

                rnn += mData.getRNN_TIME();
                lstm += mData.getLSTM_TIME();
                bert += mData.getBERT_TIME();

        }

        timeEntryChart1.add(new BarEntry(1.0f, (float) rnn)); //entry_chart1에 좌표 데이터를 담는다.
        timeEntryChart2.add(new BarEntry(2.0f, (float) lstm));
        timeEntryChart3.add(new BarEntry(3.0f, (float) bert));
        BarDataSet rnnTime = new BarDataSet(timeEntryChart1, "RNN"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        BarDataSet lstmTime = new BarDataSet(timeEntryChart2, "LSTM"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        BarDataSet bertTime = new BarDataSet(timeEntryChart3, "BERT"); // 데이터가 담긴 Arraylist 를 BarDataSet 으로 변환한다.
        rnnTime.setValueFormatter(myValueFormatter);
        lstmTime.setValueFormatter(myValueFormatter);
        bertTime.setValueFormatter(myValueFormatter);
        rnnTime.setColor(Color.parseColor("#1E90FF")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        lstmTime.setColor(Color.parseColor("#2CA02C")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        bertTime.setColor(Color.parseColor("#E35F62")); // 해당 BarDataSet 색 설정 :: 각 막대 과 관련된 세팅은 여기서 설정한다.
        timeBarData.addDataSet(rnnTime);
        timeBarData.addDataSet(lstmTime);
        timeBarData.addDataSet(bertTime);
        recentChart.setData(timeBarData); // 차트에 위의 DataSet 을 넣는다.
        recentChart.getXAxis().setDrawGridLines(false);
        recentChart.getXAxis().setDrawLabels(false);
        recentChart.animateY(2000);
        recentChart.getDescription().setText("예측시간");
        recentChart.getDescription().setTextSize(10);
        recentChart.invalidate(); // 차트 업데이트
        recentChart.setTouchEnabled(false); // 차트 터치 불가능하게

        recentChart.getAxisLeft().setStartAtZero(true);
        recentChart.getAxisRight().setStartAtZero(true);
    }



    class MyValueFormatter  implements IValueFormatter{

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            DecimalFormat mFormat = new DecimalFormat("0.000");
            return mFormat.format(value);
        }
    }
}