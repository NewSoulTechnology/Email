package com.example.administrator.email;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Administrator on 2016/11/13.
 */

public class chioceActivity extends Activity {
    private Button other;
    private ImageButton icon_1,icon_2,icon_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chioce_layout);
        icon_1=(ImageButton)findViewById(R.id.icon_1);
        icon_2=(ImageButton)findViewById(R.id.icon_2);
        icon_3=(ImageButton)findViewById(R.id.icon_3);
        icon_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加按钮事件
            }
        });
        icon_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chioceActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        icon_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加按钮事件
            }
        });
    }
}
