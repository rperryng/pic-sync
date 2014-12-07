package com.rperryng.picsync.facebook;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.rperryng.picsync.R;

/**
 * Created by Ryan PerryNguyen on 2014-12-06.
 */
public class FacebookLoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = FacebookLoginFragment.class.getSimpleName();
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

        Button button = (Button) getActivity().findViewById(R.id.facebook_login_button);
        mTextTest = (TextView) getActivity().findViewById(R.id.facebook_login_text);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Session.openActiveSession(getActivity(), true, onActiveSessionHandler);
    }

    private Session.StatusCallback onActiveSessionHandler = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception e) {
            if (!state.isOpened()) {
                return;
            }

            Request.newMeRequest(session, onMeRequest).executeAsync();
        }
    };

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
