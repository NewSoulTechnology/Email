package com.example.administrator.email;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SocketThread_read socketThread = null;
    Handler handler = null;
    final List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handler=new Handler();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = getIntent();
                Intent intent_1 = new Intent(MainActivity.this, SendActivity.class);
                intent_1.putExtra("user_name", intent.getStringExtra("user_name"));
                intent_1.putExtra("password", intent.getStringExtra("password"));
                startActivity(intent_1);
            }
        });

        Log.i("YYYYY", "----------------------");
        listView = (ListView) findViewById(R.id.listview);
        new Thread() {
            @Override
            public void run() {
                Intent intent = getIntent();
                socketThread = new SocketThread_read((intent.getStringExtra("user_name") + "@163.com"), intent.getStringExtra("password"));
                socketThread.connettoserver();
                socketThread.login();
                int i = socketThread.read_all_email();
                Log.i("FF", "____________________" + i);
                for (int n = 1; n <= i; n++) {
                    socketThread.rea_one_email(n);
                    String subject = socketThread.get_subject();
                    Log.i("FF", "____________________" + subject);
                    String comefrom = socketThread.get_comefrom();
                    Log.i("FF", "____________________" + comefrom);
                    String time = socketThread.get_time();
                    Log.i("FF", "____________________" + time);
                    String content=socketThread.get_content();
                    Map<String, Object> show = new HashMap<String, Object>();
                    show.put("subject", subject);
                    Log.i("YY", "-------------------");
                    show.put("comefrom", comefrom);
                    Log.i("YY", "-------------------");
                    show.put("time", time);
                    Log.i("YY", "-------------------");
                    show.put("content",content);
                    mapList.add(show);
                }
                handler.post(runnableUi);
            }
        }.start();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击一个listview
                HashMap<String, String> map = (HashMap<String, String>) adapterView.getItemAtPosition(i);
                //将数据加载到详情页面
                //跳转到详情页面
                Intent intent=new Intent(MainActivity.this,Message.class);
                intent.putExtra("subject",map.get("subject").toString());
                intent.putExtra("comefrom",map.get("comefrom").toString());
                intent.putExtra("time",map.get("time").toString());
                intent.putExtra("content",map.get("content").toString());
                new Thread(){
                    @Override
                    public void run() {

                    }
                }.start();
                startActivity(intent);
            }
        });
    }
        Runnable runnableUi = new Runnable() {
            @Override
            public void run() {
                //更新界面
                //定义一个SimpleAdapter
                adapter = new SimpleAdapter(getBaseContext(), mapList, R.layout.simple_message_layout,
                        new String[]{"subject", "comefrom", "time","content"},
                        new int[]{R.id.subject, R.id.comefrom, R.id.time,R.id.content});
                //设置mListView的适配器为adapter
                listView.setAdapter(adapter);
            }
        };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.chaogao: {
            }
            ;
            break;
            case R.id.sended: {
            }
            ;
            break;
            case R.id.deleted: {
            }
            ;
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    TextView text_subject, text_comefrom, text_time;
    ListView listView;

    //设置listview适配器
    public void setAdpater() {

    }

}
