package com.example.whlu.tr2000;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.widget.Toast.*;


public class

MainActivity extends AppCompatActivity {
    private EditText text_user;
    private EditText text_password;
    private Button btn_login;
    Task task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_user = findViewById(R.id.username);
        text_password = findViewById(R.id.password);
        btn_login = findViewById(R.id.login);


        btn_login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                boolean skip_login = false;
                if(skip_login) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, RecordPage.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                else {
                    task = new Task();
                    task.execute();
                }
            }
        });
    }

    public class Task extends AsyncTask<String,Void,Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            String uriPath = "http://140.116.245.148/WebCourse/students/f74036302/Taiwanese/Taiwanese_2000/Login.php";
            RequestBody formBody = new FormBody.Builder()
                    .add("userName", text_user.getText().toString())
                    .add("password", text_password.getText().toString())
                    .build();

            Request request = new Request.Builder()
                    .url(uriPath)
                    .post(formBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String result=response.body().string();
                if (result.equals("fail")) {
                    //Toast.makeText(this, "this", Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, RecordPage.class);
                    intent.putExtra("data",result);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
