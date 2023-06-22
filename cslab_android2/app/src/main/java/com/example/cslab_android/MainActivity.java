package com.example.cslab_android;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    EditText SentenceText; //문장 변수
    ProgressDialog customProgressDialog;
    DBHelper dbHelper; //메인 추가


    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;
    private MediaPlayer mediaPlayer = null;
    private Boolean isPlaying = false;
    private MediaRecorder mediaRecorder;
    private String audioFileName; // 오디오 녹음 생성 파일 이름
    private boolean isRecording = false;    // 현재 녹음 상태를 확인하기 위함.
    private Uri audioUri = null; // 오디오 파일 uri



   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SentenceText = findViewById(R.id.editTextTextPersonName); //입력한 텍스트 저장#####

        ImageButton SendServerBtn = findViewById(R.id.SendServerBtn);
        Button historyBtn = findViewById(R.id.Pos_Neg_Btn);
        Button desc = findViewById(R.id.AppDescBtn); //앱설명 페이지
        Button visualBtn = findViewById(R.id.pos_Neg_Btn); //앱설명 페이지
        ImageButton MicBtn = findViewById(R.id.MicBtn); //마이크 버튼





        dbHelper = new DBHelper(MainActivity.this); //메인 추가
        SendServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Sentence = SentenceText.getText().toString(); //입력받은 텍스트 String 저장###

                JsonObject input = new JsonObject();

                try{
                    input.addProperty("EXAMPLE",Sentence);
                    System.out.println(input);
                }catch(Exception e){

                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://220.69.209.123:8000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitAPI r = retrofit.create(RetrofitAPI.class);
                Log.d("넣은거",input.toString());

                customProgressDialog = new ProgressDialog(MainActivity.this);
                customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customProgressDialog.show();
                customProgressDialog.setCancelable(false);

                Call<registerDATA> call = r.register(input);
                call.enqueue(new Callback<registerDATA>() {
                    @Override
                    public void onResponse(Call<registerDATA> call, Response<registerDATA> response) {
                        registerDATA r = response.body(); //객체 생성 기억하셈
                        Log.d("LSTM-acc:",r.getLSTM_PRO()+"");
                        Log.d("LSTM-sec:",r.getLSTM_PRO()+"");
                        Log.d("rnn-acc:",r.getRNN_PRO()+"");
                        Log.d("rnn-sec:",r.getRNN_PRO()+"");
                        Log.d("bert-acc:",r.getBERT_PRO()+"");
                        Log.d("bert-sec:",r.getBERT_PRO()+"");

                        Intent myIntent = new Intent(MainActivity.this, NewPage.class);

                        myIntent.putExtra("cuss",r.getEXAMPLE());
                        myIntent.putExtra("lstm1",r.getLSTM_PRO());
                        myIntent.putExtra("lstm2",r.getLSTM_TIME());
                        myIntent.putExtra("bert1",r.getBERT_PRO());
                        myIntent.putExtra("bert2",r.getBERT_TIME());
                        myIntent.putExtra("rnn1",r.getRNN_PRO());
                        myIntent.putExtra("rnn2",r.getRNN_TIME());
                        customProgressDialog.dismiss();
                        startActivityForResult(myIntent, 101);

                        dbHelper.insert(r.getEXAMPLE(),r.getLSTM_PRO(),r.getLSTM_TIME(),r.getRNN_PRO(),r.getRNN_TIME(),r.getBERT_PRO(),r.getBERT_TIME()); //메인추가


                        //Log.d("rr",r.getBERT_time()+"");

                    }
                    @Override
                    public void onFailure(Call<registerDATA> call, Throwable t) {
                        Log.d("rt",t.getMessage()+"실패실패");
                        customProgressDialog.dismiss();
                    }
                });
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent myIntent2 = new Intent(MainActivity.this, HistoryActivity.class);
               startActivity(myIntent2);
           }
       });


       desc.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               Intent descIntent = new Intent(MainActivity.this,descPage.class);
               startActivity(descIntent);
           }
       });


       visualBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               Intent visIntent = new Intent(MainActivity.this,VisualActivity.class);
               startActivity(visIntent);
           }
       });


       MicBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){

               if(isRecording){
                   isRecording = false;
                   MicBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_record, null));
                   //audioRecordText.setText("녹음 시작"); // 녹음 상태 텍스트 변경
                   Toast.makeText(MainActivity.this,"녹음을 종료 합니다",Toast.LENGTH_SHORT).show();
                   stopRecording();
               }
               else{
                   if(checkAudioPermission()) {
                       // 녹음 상태에 따른 변수 아이콘 & 텍스트 변경
                       isRecording = true; // 녹음 상태 값
                       MicBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_recording_red, null)); // 녹음 상태 아이콘 변경
                       //audioRecordText.setText("녹음 중"); // 녹음 상태 텍스트 변경
                       startRecording();

                       Toast.makeText(MainActivity.this,"녹음을 시작 합니다",Toast.LENGTH_SHORT).show();
                   }
               }



           }
       });

    }

    private boolean checkAudioPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{recordPermission}, PERMISSION_CODE);
            return false;
        }
    }

    private void startRecording() {
        //파일의 외부 경로 확인
        String recordPath = getExternalFilesDir("/").getAbsolutePath();
        // 파일 이름 변수를 현재 날짜가 들어가도록 초기화. 그 이유는 중복된 이름으로 기존에 있던 파일이 덮어 쓰여지는 것을 방지하고자 함.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        audioFileName = recordPath + "/" +"RecordExample_" + timeStamp + "_"+"audio.mp4";

        //Media Recorder 생성 및 설정
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(audioFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //녹음 시작
        mediaRecorder.start();
    }

    // 녹음 종료
    private void stopRecording() {
        // 녹음 종료 종료
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        // 파일 경로(String) 값을 Uri로 변환해서 저장
        //      - Why? : 리사이클러뷰에 들어가는 ArrayList가 Uri를 가지기 때문
        //      - File Path를 알면 File을  인스턴스를 만들어 사용할 수 있기 때문
        audioUri = Uri.parse(audioFileName);
        Log.d("오디오",audioUri + "");
        File file = new File(String.valueOf(audioUri));
        playAudio(file);



        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("녹음 확인")
                .setMessage("해당 음성 녹음으로 비속어 판별을 해볼까요?")
                .setPositiveButton("네",null)
                .setNegativeButton("아니오",null)
                .setNeutralButton("녹음 파일 다시 듣기",null)
                .create();


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                Button negativeButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
                Button naturalButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEUTRAL);



                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        customProgressDialog = new ProgressDialog(MainActivity.this);
                        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        customProgressDialog.show();
                        customProgressDialog.setCancelable(false);


                        try {
                            FileInputStream fin = new FileInputStream(String.valueOf(audioUri));
                            ArrayList<Byte> b = new ArrayList<>();
                            int c;
                            StringBuilder sb = new StringBuilder();
                            while((c=fin.read()) != -1 ) {
                                sb.append(String.format("%02x", c&0xff));
                            }
                            String data = sb.toString();
                            JsonObject input = new JsonObject();

                            input.addProperty("file",data);
                            input.addProperty("type","wav");
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://220.69.209.123:8000")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            RetrofitAPI r = retrofit.create(RetrofitAPI.class);
                            //Log.d("넣은거",input.toString());
                            Call<registerDATA> call = r.audioSend(input);

                            call.enqueue(new Callback<registerDATA>() {
                                @Override
                                public void onResponse(Call<registerDATA> call, Response<registerDATA> response) {
                                    registerDATA r = response.body(); //객체 생성 기억하셈
                                    Log.d("LSTM-acc:",r.getLSTM_PRO()+"");
                                    Log.d("LSTM-sec:",r.getLSTM_PRO()+"");
                                    Log.d("rnn-acc:",r.getRNN_PRO()+"");
                                    Log.d("rnn-sec:",r.getRNN_PRO()+"");
                                    Log.d("bert-acc:",r.getBERT_PRO()+"");
                                    Log.d("bert-sec:",r.getBERT_PRO()+"");

                                    Intent myIntent = new Intent(MainActivity.this, NewPage.class);

                                    myIntent.putExtra("cuss",r.getEXAMPLE());
                                    myIntent.putExtra("lstm1",r.getLSTM_PRO());
                                    myIntent.putExtra("lstm2",r.getLSTM_TIME());
                                    myIntent.putExtra("bert1",r.getBERT_PRO());
                                    myIntent.putExtra("bert2",r.getBERT_TIME());
                                    myIntent.putExtra("rnn1",r.getRNN_PRO());
                                    myIntent.putExtra("rnn2",r.getRNN_TIME());
                                    customProgressDialog.dismiss();
                                    startActivityForResult(myIntent, 101);

                                    dbHelper.insert(r.getEXAMPLE(),r.getLSTM_PRO(),r.getLSTM_TIME(),r.getRNN_PRO(),r.getRNN_TIME(),r.getBERT_PRO(),r.getBERT_TIME()); //메인추가


                                    //Log.d("rr",r.getBERT_time()+"");

                                }
                                @Override
                                public void onFailure(Call<registerDATA> call, Throwable t) {
                                    Log.d("rt",t.getMessage()+"실패실패");
                                    customProgressDialog.dismiss();
                                }

                            });

                            dialog.dismiss();




                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                    }


                });
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                naturalButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        File file = new File(String.valueOf(audioUri));
                        if(isPlaying){
                            stopAudio();
                        }
                        else{
                            playAudio(file);
                        }

                    }
                });



            }
        });

        dialog.show();


        /*AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("녹음 확인")
                .setMessage("해당 음성 녹음으로 비속어 판별을 해볼까요?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                            FileInputStream fin = new FileInputStream(String.valueOf(audioUri));
                            ArrayList<Byte> b = new ArrayList<>();
                            int c;
                            StringBuilder sb = new StringBuilder();
                            while((c=fin.read()) != -1 ) {
                                sb.append(String.format("%02x", c&0xff));
                            }
                            String data = sb.toString();
                            JsonObject input = new JsonObject();

                            input.addProperty("file",data);
                            input.addProperty("type","wav");
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://220.69.209.123:8000")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            RetrofitAPI r = retrofit.create(RetrofitAPI.class);
                            //Log.d("넣은거",input.toString());
                            Call<registerDATA> call = r.audioSend(input);

                            call.enqueue(new Callback<registerDATA>() {
                                @Override
                                public void onResponse(Call<registerDATA> call, Response<registerDATA> response) {
                                    registerDATA r = response.body();
                                    Log.d("record", r.getLSTM_PRO() + "");
                                }

                                @Override
                                public void onFailure(Call<registerDATA> call, Throwable t) {
                                    t.printStackTrace();
                                }

                            });




                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                    }
                })
                .setNeutralButton("녹음 파일 다시듣기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File file = new File(String.valueOf(audioUri));
                        playAudio(file);
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();*/

        //해당 녹음을 전송 할까요?





    }

    private void playAudio(File file) {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        isPlaying = true;

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopAudio();
            }
        });

    }

    private void stopAudio() {
        //playIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_audio_play, null));
        isPlaying = false;
        mediaPlayer.stop();
    }









    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 101) {
            EditText txt = findViewById(R.id.editTextTextPersonName);
            txt.setText("");
        }
    }




}