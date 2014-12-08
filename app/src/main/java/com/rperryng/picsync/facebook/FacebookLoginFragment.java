package com.rperryng.picsync.facebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.rperryng.picsync.R;

import java.util.Arrays;

/**
 * Created by Ryan PerryNguyen on 2014-12-06.
 */
public class FacebookLoginFragment extends FacebookUiFragment {

    public static final String TAG = FacebookLoginFragment.class.getSimpleName();

    private LoginButton mLoginButton;
    private TextView mTextTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.facebook_login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTextTest = (TextView) getActivity().findViewById(R.id.facebookLogin_text);
        mLoginButton = (LoginButton) getActivity().findViewById(R.id.facebookLogin_loginButton);

        mLoginButton.setFragment(this);
        mLoginButton.setReadPermissions(Arrays.asList("user_friends"));
    }

    @Override
    public void call(Session session, SessionState sessionState, Exception e) {
        if (!sessionState.isOpened()) {
            return;
        }

        Request.newMeRequest(session, onMeRequest).executeAsync();
    }

    private Request.GraphUserCallback onMeRequest = new Request.GraphUserCallback() {
        @Override
        public void onCompleted(GraphUser graphUser, Response response) {
            if (graphUser == null) {
                return;
            }

            mTextTest.setText("Welcome " + graphUser.getFirstName() + "!");
        }
    };
}
