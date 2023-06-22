package com.example.cslab_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NewPage extends AppCompatActivity {
    TextView example_V,lstm1_V,lstm2_V,dnn1_V,dnn2_V,bert1_V,bert2_V;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);

        DecimalFormat df = new DecimalFormat("##.#####");
        Intent intent=getIntent();
        example_V=findViewById(R.id.cuss_textView);
        lstm1_V=findViewById(R.id.LSTMresult_textView);
        lstm2_V=findViewById(R.id.LSTMtime_textView);
        dnn1_V=findViewById(R.id.DNNresult_textView);
        dnn2_V=findViewById(R.id.DNNtime_textView);
        bert1_V=findViewById(R.id.BERTresult_textView);
        bert2_V=findViewById(R.id.BERTtime_textView);

        BigDecimal lstm1= new BigDecimal(intent.getDoubleExtra("lstm1",0.0)*100);
        BigDecimal lstm2= new BigDecimal(intent.getDoubleExtra("lstm2",0.0));
        BigDecimal dnn1= new BigDecimal(intent.getDoubleExtra("rnn1",0.0)*100);
        BigDecimal dnn2= new BigDecimal(intent.getDoubleExtra("rnn2",0.0));
        BigDecimal bert1= new BigDecimal(intent.getDoubleExtra("bert1",0.0)*100);
        BigDecimal bert2= new BigDecimal(intent.getDoubleExtra("bert2",0.0));


        example_V.setText(intent.getStringExtra("cuss")+" 에 관한 결과입니다.");
        lstm1_V.setText("비속어일 확률(%):"+df.format(lstm1));
        lstm2_V.setText("초(ms):"+df.format(lstm2));
        dnn1_V.setText("비속어일 확률(%):"+df.format(dnn1));
        dnn2_V.setText("초(ms):"+df.format(dnn2));
        bert1_V.setText("비속어일 확률(%):"+df.format(bert1));
        bert2_V.setText("초(ms):"+df.format(bert2));



/*        Button back_page = (Button)findViewById(R.id.back_button);
        back_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(NewPage.this,MainActivity.class);
                startActivity(myintent);
                finish();
            }
        });*/




        Button AppDescBtn = findViewById(R.id.back1_button);
        AppDescBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}