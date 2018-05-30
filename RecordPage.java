package com.example.whlu.tr2000;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RecordPage extends AppCompatActivity {

    private TextView subtitle;
    private TextView question_times;
    private Button btn_logout;
    private ImageButton btn_demo;
    private ImageButton btn_record;
    private ImageButton btn_stop;
    // 錄音器
    MediaRecorder mediaRecorder = null;
    // 音檔儲存檔案
    File recordFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recorderpage);


        subtitle = findViewById(R.id.subtitle);
        question_times = findViewById(R.id.r_times);
        btn_logout = findViewById(R.id.logout);
        btn_demo = findViewById(R.id.demo);
        btn_record = findViewById(R.id.record);
        btn_stop = findViewById(R.id.stop);

        btn_stop.setEnabled(false);


        btn_logout.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setClass(RecordPage.this, MainActivity.class);
                startActivity(intent);
                RecordPage.this.finish();
            }
        });

        btn_demo.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View view){
            }
        });

        btn_record.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View view){
                record_start();
            }
        });

        btn_stop.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View view){
                record_finish();
            }
        });

    }

    private void demo_play(){


    }
    private void record_start() {

        try {
            //設定開始錄音時按鈕不能按
            btn_logout.setEnabled(false);
            btn_demo.setEnabled(false);
            btn_record.setEnabled(false);
            btn_stop.setEnabled(true);
            mediaRecorder = new MediaRecorder();
            //設定音源(一般是麥克風)
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //設定輸出格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            //設定編碼
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            // 設定檔案位置，可以在手機上的檔案管理找到剛剛錄下的聲音
            //mediaRecorder.setAudioEncodingBitRate(96000);
            mediaRecorder.setAudioSamplingRate(44100);
            recordFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.amr");
            mediaRecorder.setOutputFile(recordFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this,recordFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this,"FileIOException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void record_finish() {
        btn_logout.setEnabled(true);
        btn_demo.setEnabled(true);
        btn_record.setEnabled(true);
        btn_stop.setEnabled(false);
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
}