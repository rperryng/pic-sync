package com.rperryng.picsync.contacts;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.rperryng.picsync.R;

/**
 * Created by Ryan PerryNguyen on 2014-12-06.
 */
public class ContactsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    public static final String TAG = ContactsFragment.class.getSimpleName();

    private static final int LOADER_ID = 0;
    private static final String[] FROM_COLUMNS = {
            Contacts.DISPLAY_NAME_PRIMARY
    };
    private static final int[] TO_IDS = {
            R.id.contacts_list_item_text
    };

    private ListView mListContacts;
    private SimpleCursorAdapter mCursorAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.contacts_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        Activity activity = getActivity();

        mListContacts = (ListView) activity.findViewById(R.id.contacts_list);
        mCursorAdapter = new SimpleCursorAdapter(
                activity,
                R.layout.contacts_list_item,
                null,
                FROM_COLUMNS,
                TO_IDS,
                0
        );

        mListContacts.setAdapter(mCursorAdapter);
        mListContacts.setOnItemClickListener(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        if (id != LOADER_ID) {
            Log.e(TAG, "onCreateLoader - incorrect id provided (" + id + ")");
            return null;
        }

        return new CursorLoader(
                getActivity(),
                ContactsQuery.CONTENT_URI,
                ContactsQuery.PROJECTION,
                null,
                null,
                ContactsQuery.SORT_ORDER
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
        Cursor cursor = mCursorAdapter.getCursor();
        cursor.moveToPosition(position);
    }

    private static final class ContactsQuery {
        public static final Uri CONTENT_URI = Contacts.CONTENT_URI;
        public static final String SORT_ORDER = Contacts.SORT_KEY_PRIMARY;
        public static final String SELECTION = Contacts.DISPLAY_NAME_PRIMARY +
                "<>''" + " AND " + Contacts.IN_VISIBLE_GROUP + "=1";

        public static final String[] PROJECTION = {
                Contacts._ID,
                Contacts.LOOKUP_KEY,
                Contacts.DISPLAY_NAME_PRIMARY,
                Contacts.PHOTO_THUMBNAIL_URI,
                SORT_ORDER
        };
    }
}
