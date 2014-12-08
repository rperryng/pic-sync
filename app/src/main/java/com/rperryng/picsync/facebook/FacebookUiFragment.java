package com.rperryng.picsync.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.facebook.Session;
import com.facebook.UiLifecycleHelper;

/**
 * Created by Ryan PerryNguyen on 2014-12-07.
 */
public abstract class FacebookUiFragment extends Fragment implements Session.StatusCallback {

    private UiLifecycleHelper mLifecycleHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleHelper = new UiLifecycleHelper(getActivity(), this);
        mLifecycleHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        Session session = Session.getActiveSession();
        if (session != null && (session.isOpened() || session.isClosed())) {

            // on SessionStateChanged callback method
            call(session, session.getState(), null);
        }
        mLifecycleHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLifecycleHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLifecycleHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mLifecycleHelper.onSaveInstanceState(outState);
    }
}
