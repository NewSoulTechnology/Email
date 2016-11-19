package com.example.administrator.email;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/11/6.
 */

public class LoginActivity extends AppCompatActivity {
    //用户名
    AutoCompleteTextView autoCompleteTextView;
    //密码
    EditText editText_password;
    String[] content=new String[]{"POP3(推荐)","IMAP","Exchange"};
    Spinner spinner;
    Button button_withdraw,button_sure;
    SocketThread_write socketThread=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        //设置协议选项
        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,content);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        editText_password=(EditText)findViewById(R.id.editText_password);
//        socketThread=new SocketThread(autoCompleteTextView.getText().toString(),editText_password.getText().toString());
        button_withdraw=(Button)findViewById(R.id.button_withdraw);
        //取消，返回选择页面
        button_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, chioceActivity.class);
                    startActivity(intent);
                }
        });
        //登录验证
        button_sure=(Button)findViewById(R.id.button_sure);
        button_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run()
                    {
                        socketThread=new SocketThread_write(autoCompleteTextView.getText().toString(),editText_password.getText().toString());
                        //先进行链接验证，验证通过就进入邮箱页面
                        socketThread.connecttoserver();
                        String temp[] = socketThread.login().split(" ");
                        if (temp[0].equals("235")) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            //数据传递
                            intent.putExtra("user_name",autoCompleteTextView.getText().toString());
                            intent.putExtra("password",editText_password.getText().toString());
                        startActivity(intent);
                            socketThread.close();
                        }
//                        else {
//                            Toast.makeText(LoginActivity.this,"用户名不存在或密码错误！",Toast.LENGTH_SHORT).show();
//                               }
                         }
                }.start();
            }
        });
    }
}
