package com.rperryng.picsync.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;
import com.rperryng.picsync.R;
import com.rperryng.picsync.common.Constants;
import com.rperryng.picsync.common.Utils;
import com.rperryng.picsync.main.MainActivity;

import java.util.Arrays;

/**
 * Created by Ryan PerryNguyen on 2014-12-07.
 */
public class SplashActivity extends Activity implements Session.StatusCallback {

    public static final String TAG = SplashActivity.class.getSimpleName();

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        mSharedPreferences = getSharedPreferences(Constants.SP.LOGIN.NAME, Context.MODE_PRIVATE);
        boolean isLoggedIn = mSharedPreferences.getBoolean(
                Constants.SP.LOGIN.KEYS.LOGGED_IN,
                false
        );

        if (isLoggedIn) {
            startMainActivity();
            return;
        }

        LoginButton loginButton = (LoginButton) findViewById(R.id.splash_loginButton);
        loginButton.setReadPermissions(Arrays.asList("user_friends"));
        loginButton.setSessionStatusCallback(this);
    }

    @Override
    public void call(Session session, SessionState sessionState, Exception e) {
        if (!sessionState.isOpened()) {
            return;
        }

        mSharedPreferences
                .edit()
                .putBoolean(Constants.SP.LOGIN.KEYS.LOGGED_IN, true)
                .apply();

        startMainActivity();
    }

    private void startMainActivity() {
        Utils.startActivity(this, MainActivity.class);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
}
