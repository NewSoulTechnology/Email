package com.example.administrator.email;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2016/11/6.
 */

public class SendActivity extends AppCompatActivity {
    SocketThread_write socketThread=null;
    String user_name,password,sendto,subject,content;
    AutoCompleteTextView autoCompleteTextView;
    EditText edittext_type,edittext_subject,edittext_content;
    ImageButton icon_addition;
    Button button_back,button_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        //返回按钮
//        button_back=(Button)findViewById(R.id.back);
//        button_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent_2=new Intent(SendActivity.this,MainActivity.class);
//                startActivity(intent_2);
//            }
//        });
        //发送按钮
        button_send=(Button)findViewById(R.id.send);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                //获取发件人地址
                user_name = intent.getStringExtra("user_name");
                Log.i("HAHA","__________________"+user_name+"___________________");
                //获取密码
                password = intent.getStringExtra("password");
                Log.i("HAHA","__________________"+password+"___________________");
                //获取收件人地址
                autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
                sendto=autoCompleteTextView.getText().toString();
                Log.i("HAHA","__________________"+sendto+"___________________");
                //获取主题
                edittext_subject = (EditText) findViewById(R.id.edit_subject);
                subject=edittext_subject.getText().toString();
                Log.i("HAHA","__________________"+subject+"___________________");
                //获取内容
                edittext_content = (EditText) findViewById(R.id.edittext_content);
                content=edittext_content.getText().toString();
                Log.i("HAHA","__________________"+content+"___________________");
                new Thread() {
                    @Override
                    public void run() {
                            socketThread = new SocketThread_write(user_name, password);
                            //发送邮件
                            socketThread.connecttoserver();
                            socketThread.login();
                        String []temp=socketThread.sendemail(sendto,subject,content).split(" ");
                    }
                }.start();
            }
        });
    }
}
