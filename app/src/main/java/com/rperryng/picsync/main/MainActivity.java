package com.rperryng.picsync.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.rperryng.picsync.R;
import com.rperryng.picsync.common.Constants;
import com.rperryng.picsync.common.Utils;
import com.rperryng.picsync.contacts.ContactsTakeTwoFragment;
import com.rperryng.picsync.facebook.FacebookContactModel;
import com.rperryng.picsync.facebook.FacebookContactsFragment;
import com.rperryng.picsync.splash.SplashActivity;

import java.util.List;


public class MainActivity extends ActionBarActivity implements
        ContactsTakeTwoFragment.ContactsLoadedListener,
        FacebookContactsFragment.FacebookContactsLoadedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewPager = (ViewPager) findViewById(R.id.main_viewPager);
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(
//                            R.id.main_fragmentContainer,
//                            new FacebookContactsFragment(),
//                            FacebookContactsFragment.TAG
//                    )
//                    .commit();
//        }
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

    @Override
    public void onContactsLoaded(List<String> contactNames) {
        for (String contactName : contactNames) {
            Log.e(TAG, "contact: " + contactName);
        }
    }

    @Override
    public void onFacebookContactsLoaded(List<FacebookContactModel> facebookContacts) {
        for (FacebookContactModel facebookContact : facebookContacts) {
            Log.e(TAG, "facebook: " + facebookContact.getFullName());
        }
    }

    private class MainPagerAdapter extends FragmentStatePagerAdapter {

        private static final int NUM_PAGES = 2;
        private static final int PAGE_NUM_CONTACTS = 0;
        private static final int PAGE_NUM_FACEBOOK = 1;

        private MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e(TAG, "position " + position);
            if (position == PAGE_NUM_CONTACTS) {
                return new ContactsTakeTwoFragment();
            } else {
                return new FacebookContactsFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
