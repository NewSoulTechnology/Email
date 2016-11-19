package com.example.administrator.email;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/13.
 */

public class Message extends Activity {
    //发件人、主题、时间
    TextView comefrom,subject,time,content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allmessage_layout);
        comefrom=(TextView)findViewById(R.id.comefrom);
        subject=(TextView)findViewById(R.id.subject);
        time=(TextView)findViewById(R.id.time);
        content=(TextView)findViewById(R.id.content);
        Intent intent=getIntent();
        subject.setText(intent.getStringExtra("subject").toString());
        comefrom.setText(intent.getStringExtra("comefrom").toString());
        time.setText(intent.getStringExtra("time").toString());
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        content.setText(intent.getStringExtra("content").toString());
    }
}
