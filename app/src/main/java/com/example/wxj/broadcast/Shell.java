package com.example.wxj.broadcast;

import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by wxj on 2020/10/27.
 */

public class Shell {


    public static void shell(){

        try {

            JSch jsch=new JSch();
            String host = "192.168.123.1";
            String user = "admin";

            Session session = jsch.getSession(user, host, 22);
            String password = "admin";
            String command = "ls";
            session.setPassword(password);
            UserInfo userInfo = new UserInfo() {
                @Override
                public String getPassphrase() {
                    System.out.println("getPassphrase");
                    return null;
                }
                @Override
                public String getPassword() {
                    System.out.println("getPassword");
                    return null;
                }
                @Override
                public boolean promptPassword(String s) {
                    System.out.println("promptPassword:"+s);
                    return false;
                }
                @Override
                public boolean promptPassphrase(String s) {
                    System.out.println("promptPassphrase:"+s);
                    return false;
                }
                @Override
                public boolean promptYesNo(String s) {
                    System.out.println("promptYesNo:"+s);
                    return true;//notice here!
                }
                @Override
                public void showMessage(String s) {
                    System.out.println("showMessage:"+s);
                }
            };

            session.setUserInfo(userInfo);
            session.connect(30000);
            Channel channel = session.openChannel("shell");

//            ((ChannelExec)channel).setCommand(command);
//            ((ChannelExec)channel).setCommand(command);
            byte[] bytes = command.getBytes();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            channel.setInputStream(byteArrayInputStream);

            channel.setOutputStream(System.out);

//            ((ChannelExec)channel).setErrStream(System.err);

//            InputStream in=channel.getInputStream();

            channel.connect(3000);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
