package com.rperryng.picsync.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;
import com.rperryng.picsync.R;
import com.rperryng.picsync.main.MainActivity;

import java.util.Arrays;

/**
 * Created by Ryan PerryNguyen on 2014-12-07.
 */
public class SplashActivity extends Activity implements Session.StatusCallback {

    public static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        LoginButton loginButton = (LoginButton) findViewById(R.id.splash_loginButton);
        loginButton.setReadPermissions(Arrays.asList("user_friends"));
        loginButton.setSessionStatusCallback(this);
    }

    @Override
    public void call(Session session, SessionState sessionState, Exception e) {
        if (!sessionState.isOpened()) {
            Log.e(TAG, "Not logged in");
            return;
        }

        Log.e(TAG, "Starting main activity");
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
}
