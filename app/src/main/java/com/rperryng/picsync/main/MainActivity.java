package com.rperryng.picsync.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.rperryng.picsync.R;
import com.rperryng.picsync.common.Constants;
import com.rperryng.picsync.common.Utils;
import com.rperryng.picsync.contacts.ContactsTakeTwoFragment;
import com.rperryng.picsync.splash.SplashActivity;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(
                            R.id.main_fragmentContainer,
                            new ContactsTakeTwoFragment(),
                            ContactsTakeTwoFragment.TAG
                    )
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mainMenu_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                Constants.SP.LOGIN.NAME,
                Context.MODE_PRIVATE
        );
        sharedPreferences
                .edit()
                .putBoolean(Constants.SP.LOGIN.KEYS.LOGGED_IN, false)
                .apply();

        Session facebookSession = Session.getActiveSession();

        if (facebookSession == null) {
            facebookSession = new Session(this);
            facebookSession.closeAndClearTokenInformation();
        } else if (facebookSession.isOpened() || !facebookSession.isClosed()) {
            facebookSession.closeAndClearTokenInformation();
        }

        Utils.startActivity(this, SplashActivity.class);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }
}
