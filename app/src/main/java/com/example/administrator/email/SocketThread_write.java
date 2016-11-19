package com.example.administrator.email;

import android.util.Base64;
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

public class SocketThread_write {
    /**
     * Created by Administrator on 2016/11/14.
     */
        //用户名
        String name=null;
        //密码
        String password=null;
        //数据缓存区
        String temp=null;
        //输出到服务器
         OutputStream outputStream=null;
        //从服务器接收
         BufferedReader bufferedReaderff=null;
        //构造函数
        SocketThread_write(String user_name,String password){
            name=user_name;
            this.password=password;
        }
        //连接smtp服务器
        public void connecttoserver()
        {
            try {
                Socket socket=new Socket();
                //连接服务器，这里用的是网易邮箱，超时时间为3000ms
                socket.connect(new InetSocketAddress("smtp.163.com", 25), 3000);
                outputStream = socket.getOutputStream();
                bufferedReaderff = new BufferedReader(new InputStreamReader
                        (socket.getInputStream()));
                //获取服务器数据
                temp=bufferedReaderff.readLine();
                System.out.println(temp);
                //与服务器握手
                outputStream.write("HELO smtp.163.com\r\n".getBytes("UTF-8"));
                //获取服务器数据
                temp=bufferedReaderff.readLine();
                System.out.println(temp);
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        //登录账户
        public  String login()
        {
            try {
                Log.i("MESSAGE","__________________"+name+"___________________");
                Log.i("MESSAGE","__________________"+password+"___________________");
                //发送登录命令
                outputStream.write("AUTH LOGIN\r\n".getBytes("UTF-8"));
                //获取服务器数据
                temp=bufferedReaderff.readLine();
                Log.i("MESSAGE","__________________"+temp+"___________________");
                //账户加密
                temp= Base64.encodeToString(name.getBytes("UTF-8"),Base64.NO_WRAP);
                //发送登录账户
                outputStream.write((temp+"\r\n").getBytes("UTF-8"));
                //获取服务器数据
                temp=bufferedReaderff.readLine();
                Log.i("MESSAGE","__________________"+temp+"___________________");
                //密码加密
                password=Base64.encodeToString(password.getBytes("UTF-8"),Base64.NO_WRAP);
                //发送登录密码
                outputStream.write((password+"\r\n").getBytes("UTF-8"));
                //获取服务器数据
                temp=bufferedReaderff.readLine();
                Log.i("MESSAGE","__________________"+temp+"___________________");
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return temp;
        }
        //发送邮件
        public String sendemail(String sendto,String subject,String content)
        {
            try {
                outputStream.write(("MAIL FROM:<"+name+"@163.com>\r\n").getBytes("UTF-8"));
                //获取服务器数据
                temp=bufferedReaderff.readLine();
                Log.i("HAHA","__________________"+temp+"___________________");
                //发送收件人邮箱
                outputStream.write(("RCPT TO:<"+sendto+">\r\n").getBytes("UTF-8"));
                //获取服务器数据
                temp=bufferedReaderff.readLine();
                Log.i("HAHA","__________________"+temp+"___________________");
                //发送邮件内容开始命令
                outputStream.write("DATA\r\n".getBytes("UTF-8"));
                //获取服务器数据
                temp=bufferedReaderff.readLine();
                Log.i("HAHA","__________________"+temp+"___________________");
                //发送邮件格式
                outputStream.write(("TO:"+sendto+"\r\n"+"FROM:"+name+"@163.com\r\n"+"SUBJECT:"+subject+"\r\n\r\n").getBytes("UTF-8"));
                //发送邮件内容
                outputStream.write(("\r\t"+content).getBytes("UTF-8"));
                //发送邮件内容结束标志
                outputStream.write("\r\n.\r\n".getBytes("UTF-8"));
                //获取服务器数据
                temp=bufferedReaderff.readLine();
                Log.i("HAHA","__________________"+temp+"___________________");
//            //发送延迟空操作（用于与保持与服务器的联系）
//            outputStream.write("noop\r\n".getBytes("UTF-8"));
//            //获取服务器数据
//            temp=bufferedReaderff.readLine();
//            System.out.println(temp);
            } catch (IOException e){
                e.printStackTrace();
            }
            return temp;
        }
        //关闭链接
        public void close(){
            try {
                //退出SMTP服务
                outputStream.write("quit\r\n".getBytes("UTF-8"));
                //获取服务器数据
                temp=bufferedReaderff.readLine();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
}
