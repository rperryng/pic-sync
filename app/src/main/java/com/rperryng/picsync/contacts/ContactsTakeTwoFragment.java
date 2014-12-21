package com.rperryng.picsync.contacts;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rperryng.picsync.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan PerryNguyen on 2014-12-15.
 */
public class ContactsTakeTwoFragment extends Fragment {

    public static final String TAG = ContactsTakeTwoFragment.class.getSimpleName();

    public interface ContactsLoadedListener {
        public void onContactsLoaded(List<String> contactNames);
    }

    private ContactsLoadedListener mListener;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contacts_fragment, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof ContactsLoadedListener)) {
            throw new ClassCastException(activity.toString() +
                    " must implement ContactsLoadedListener interface");
        }

        mListener = (ContactsLoadedListener) activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Cursor cursor = getActivity().getContentResolver().query(
                ContactsQuery.CONTENT_URI,
                null,
                ContactsQuery.SELECTION,
                null,
                null
        );

        List<String> phoneContacts = new ArrayList<>();
        while (cursor.moveToNext()) {
            String contactName = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            );
            phoneContacts.add(contactName);
        }
        cursor.close();

        mListener.onContactsLoaded(phoneContacts);

        ListView contactsList = (ListView) getActivity().findViewById(R.id.contacts_list);
        ContactsListAdapter adapter = new ContactsListAdapter(getActivity());
        adapter.addContacts(phoneContacts);
        contactsList.setAdapter(adapter);
    }

    private static class ContactsQuery {
        public static final Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        public static final String SELECTION = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY +
                "<>'' AND  " + ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1";
    }
}
