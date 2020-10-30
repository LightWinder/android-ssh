package com.example.wxj.broadcast;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import ch.ethz.ssh2.Connection;

public class MainActivity extends AppCompatActivity {
    private static final String action ="android.provider.Telephony.SMS_RECEIVED";
    private static final int SEND_SMS = 100;
    private Button btn1;
    private WebView webView;
    private String ip,username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersonTask task = new PersonTask();
        task.execute();

        //注册广播
        permission();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(1000);
        filter.addAction(action);
        BatteryBroadcat broadcat = new BatteryBroadcat();
        this.registerReceiver(broadcat,filter);
        //按钮单击发送短信
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSS();
            }
        });
    }
    public void permission(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED||
            ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.RECEIVE_SMS) !=PackageManager.PERMISSION_GRANTED||
            ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS,
                            Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS},
                    1);
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMSS();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "CALL_PHONE Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void sendSMSS() {
        String content = "mm";
        String phone="106593005";
        if (!content.equals("") && !content.equals("")) {
            SmsManager manager = SmsManager.getDefault();
            ArrayList<String> strings = manager.divideMessage(content);
            for (int i = 0; i < strings.size(); i++) {
                manager.sendTextMessage(phone, null, content, null, null);
            }
            Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "手机号或内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}

class BatteryBroadcat extends BroadcastReceiver {

    private static final String action ="android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(action)) {
            StringBuffer SMSAddress = new StringBuffer();
            StringBuffer SMSContent = new StringBuffer();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdusObjects = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdusObjects.length];
                for (int i = 0; i < pdusObjects.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdusObjects[i],
                            intent.getStringExtra("format"));
                }
                for (SmsMessage message : messages) {
                    SMSAddress.append(message.getDisplayOriginatingAddress());
                    SMSContent.append(message.getDisplayMessageBody());
                }
            }
            System.out.println("发送号码：" + SMSAddress + "\n" + "短信内容："
                    + SMSContent);
            if ("106593005".equals(String.valueOf(SMSAddress))){
                Toast.makeText(context,SMSContent,Toast.LENGTH_LONG).show();
            }
        }
    }

}
