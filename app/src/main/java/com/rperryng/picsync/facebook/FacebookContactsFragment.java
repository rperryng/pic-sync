package com.rperryng.picsync.facebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.rperryng.picsync.R;

import java.util.List;

/**
 * Created by Ryan PerryNguyen on 2014-12-08.
 */
public class FacebookContactsFragment extends FacebookUiFragment {

    public static final String TAG = FacebookContactsFragment.class.getSimpleName();

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.facebook_contacts_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle parameters = new Bundle();
        parameters.putString("fields", "picture.width(500).height(500),first_name,last_name");

        new Request(
                Session.getActiveSession(),
                "me/taggable_friends",
                parameters,
                HttpMethod.GET,
                onFbFriendsRequest
        ).executeAsync();

    }

    private Request.Callback onFbFriendsRequest = new Request.Callback() {
        @Override
        public void onCompleted(Response response) {
            if (response == null) {
                Log.e(TAG, "Response was null");
                return;
            }

            if (response.getError() != null) {
                Log.e(TAG, "Facebook friends request error with "
                        + response.getError().getErrorMessage());
                return;
            }

            List<FacebookContactModel> users = FacebookContactModel
                    .parseFacebookResponse(response.getRawResponse());

            ListView listView = (ListView) getActivity().findViewById(R.id.facebookContacts_list);
            FacebookContactsListAdapter adapter = new FacebookContactsListAdapter(getActivity());
            adapter.addContacts(users);
            listView.setAdapter(adapter);
        }
    };

    @Override
    public void call(Session session, SessionState sessionState, Exception e) {

    }
}
