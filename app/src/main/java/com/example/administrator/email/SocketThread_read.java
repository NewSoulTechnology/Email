package com.example.administrator.email;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SocketThread_read {
    //用户名
    String name=null;
    //密码
    String password=null;
    //发件人
    String comefrom=null;
    //主题
    String subject=null;
    //内容
    String content=null;
    //时间
    String time=null;
    //缓存
    String temp_content=null;
    //socket输出流
    OutputStream outputStream=null;
    //socket输入流
    BufferedReader bufferedReader=null;
    //数据缓存区
    String temp=null;
    //数据信息
    String message=null;
    //构造函数
    SocketThread_read(String user_name,String password)
    {
        name=user_name;
        this.password=password;
    }
    //链接服务器
    public void connettoserver()
    {
        try {
            Socket socket=new Socket();
            socket.connect(new InetSocketAddress("pop3.163.com",110),3000);
            outputStream=socket.getOutputStream();
            bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //获取服务器数据
            temp=bufferedReader.readLine();
            Log.i("TT","____________________"+temp);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    //身份验证
    public String login(){
        try{
            //向服务器发送用户名
            outputStream.write(("USER "+name+"\r\n").getBytes("UTF-8"));
            //获取服务器数据
            temp=bufferedReader.readLine();
            Log.i("TT","____________________"+temp);
            //向服务器发送密码
            outputStream.write(("PASS "+password+"\r\n").getBytes("UTF-8"));
            //获取服务器数据
            temp=bufferedReader.readLine();
            Log.i("TT","____________________"+temp);
        } catch (IOException e){
            e.printStackTrace();
        }
        return temp;
    }
    //获取邮件总数
    public int read_all_email()
    {
        String []array=null;
        try {
            //获取总的邮件数和总邮件大小
            outputStream.write("STAT\r\n".getBytes("UTF-8"));
            //获取服务器数据
            temp=bufferedReader.readLine();
            Log.i("TT","____________________"+temp);
            array=temp.split(" ");
        } catch (IOException e){
            e.printStackTrace();
        }
        Log.i("TT","____________________"+Integer.parseInt(array[1].toString()));
        return  Integer.parseInt(array[1].toString());
    }
    //获取某一个邮件的详情
    public void rea_one_email(int i)
    {
        try {
            //获取第i封邮件的详情
            outputStream.write(("RETR"+" "+i+"\r\n").getBytes("UTF-8"));
            //获取服务器数据
            temp = bufferedReader.readLine();
            while (!temp.equals(".")) {
                Log.i("TT","____________________"+temp);
                //message = bufferedReader.readLine();
                String [] array=temp.split(" ");
                if(array[0].equals("From:"))
                {
                    //发件人
                    comefrom=temp;
                    Log.i("KK","____________________"+comefrom);
                }
                if(array[0].equals("Subject:"))
                {
                    //主题
                    //subject=array[1].toString();
                    subject=array[1].toString();
                    Log.i("KK","____________________"+subject);
                }
                if(array[0].equals("Date:"))
                {
                    //时间
                    //time=message.substring(4);
                    time=temp.substring(6);
                    Log.i("KK","____________________"+time);
                }
                temp_content+=temp;
                array=null;
                //获取服务器数据
                temp = bufferedReader.readLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        content = temp_content;
        temp_content=null;
    }
    //获取主题
    public String get_subject()
    {
        return subject;
    }
    //获取时间
    public String get_time()
    {
        return time;
    }
    //获取发件人
    public String get_comefrom()
    {
        return  comefrom;
    }
    //获取内容
    public String get_content()
    {
        return content;
    }
    //关闭邮箱连接
    public void close()
    {
        try {
            outputStream.write("QUIT\r\n".getBytes("UTF-8"));
            //获取服务器数据
            temp=bufferedReader.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
