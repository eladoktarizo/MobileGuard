package arisryan.mobileeg.activity;

/**
 * Created by Aris Riyanto on 5/12/2017.
 */


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import arisryan.mobileeg.R;

public class SplashScreen extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager = new SessionManager(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sessionManager.checkLogin();
                finish();
            }
        },5000);
    }
}