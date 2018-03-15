package com.example.ralapchi.myproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;




/**
 * Created by ralapchi on 2017/5/9.
 */

public class Activity_welcome extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        Handler handler = new Handler();
        //当计时结束,跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Activity_welcome.this, MainActivity.class);
                startActivity(intent);
                Activity_welcome.this.finish();
            }
        }, 2000);
    }
}
