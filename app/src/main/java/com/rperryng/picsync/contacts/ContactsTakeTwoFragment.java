package com.rperryng.picsync.contacts;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rperryng.picsync.R;

/**
 * Created by Ryan PerryNguyen on 2014-12-15.
 */
public class ContactsTakeTwoFragment extends Fragment {

    public static final String TAG = ContactsTakeTwoFragment.class.getSimpleName();

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

        Cursor cursor = getActivity().getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY +
                        "<>'' AND  " + ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1" ,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String contactName = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            );

            Log.e(TAG, "contactName: " + contactName);
        }

        cursor.close();
    }
}
