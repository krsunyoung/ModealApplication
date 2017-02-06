package com.ff.modealapplication.app.ui.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ff.modealapplication.R;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by BIT on 2017-02-06.
 */

public class MessagingActivity extends AppCompatActivity {

    private static final String TAG = "MessagingActivity";
    String title = null;
    String body = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        FirebaseMessaging.getInstance().subscribeToTopic("notice"); // 푸시 알림 전송시 같은 토픽명 그룹 전체에 메세지 전송 가능
//        FirebaseInstanceId.getInstance().getToken(); // 사용자 기기의 고유 토큰 값
        title = ((EditText) findViewById(R.id.titleM)).getText().toString();
        body = ((EditText) findViewById(R.id.bodyM)).getText().toString();


        findViewById(R.id.sendM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(title + "!!", body + "@@");
                // Get the token
//                String token = FirebaseInstanceId.getInstance().getToken();
//                Log.d(TAG, "Token : " + token);
//                Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                new Thread() {
                    public void run() {
                        MessagingService.send(title, body);
                    }
                }.start();
            }
        });
    }
}
