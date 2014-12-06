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

    /*
     * Defines an array that contains column names to move from
     * the Cursor to the ListView.
     */
    private static final String[] FROM_COLUMNS = {
            Contacts.DISPLAY_NAME_PRIMARY
    };

    /*
     * Define which item to bind to when binding the Cursor to
     * the ListView
     */
    private static final int[] TO_IDS = {
            R.id.contacts_list_item_text
    };

    private static final String[] PROJECTION = {
            Contacts._ID,
            Contacts.LOOKUP_KEY,
            Contacts.DISPLAY_NAME_PRIMARY
    };

    private static final String SELECTION = Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?";
    private static final int INDEX_CONTACT_ID = 0;
    private static final int INDEX_LOOKUP_KEY = 1;

    private String mSearchString = "Ryan";
    private String[] mSelectionArgs = {mSearchString};
    private long mContactId;
    private String mContactKey;
    private Uri mContactUri;

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

        getLoaderManager().initLoader(0, null, this);

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
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        mSelectionArgs[0] = "%" + mSearchString + "%";
        // Starts the query
        return new CursorLoader(
                getActivity(),
                Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
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
        mContactId = cursor.getLong(INDEX_CONTACT_ID);
        mContactKey = cursor.getString(INDEX_LOOKUP_KEY);
        mContactUri = Contacts.getLookupUri(mContactId, mContactKey);
    }
}
