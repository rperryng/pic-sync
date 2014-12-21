package com.rperryng.picsync.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rperryng.picsync.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan PerryNguyen on 2014-12-21.
 */
public class ContactsListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> mContactNames;

    public ContactsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContactNames = new ArrayList<>();
    }

    public void addContacts(List<String> contacts) {
        mContactNames.addAll(contacts);
    }

    @Override
    public int getCount() {
        return mContactNames.size();
    }

    @Override
    public String getItem(int position) {
        return mContactNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        // No implementation.  Not used by framework.
        return 0;
    }

    private static class ViewHolder {
        public TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.contacts_list_item, null, false);
            viewHolder.text = (TextView) convertView.findViewById(R.id.contacts_listItemText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String contactName = getItem(position);
        viewHolder.text.setText(contactName);

        return convertView;
    }
}
