package com.rperryng.picsync.facebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rperryng.picsync.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan PerryNguyen on 2014-12-08.
 */
public class FacebookContactsListAdapter extends BaseAdapter {

    private List<FacebookContactModel> mFacebookContacts;
    private LayoutInflater mInflater;
    private Context mContext;

    public FacebookContactsListAdapter(Context context) {
        mFacebookContacts = new ArrayList<FacebookContactModel>();
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void addContacts (List<FacebookContactModel> newUsers) {
        mFacebookContacts.addAll(newUsers);
    }

    @Override
    public int getCount() {
        return mFacebookContacts.size();
    }

    @Override
    public FacebookContactModel getItem(int position) {
        return mFacebookContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        // No implementation.  Not used by framework/
        return 0;
    }

    private static class ViewHolder {
        public ImageView image;
        public TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.facebook_contacts_list_item, null, false);
            viewHolder.text = (TextView) convertView
                    .findViewById(R.id.facebookContactsListItem_text);
            viewHolder.image = (ImageView) convertView
                    .findViewById(R.id.facebookContactsListItem_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FacebookContactModel facebookContact = getItem(position);
        viewHolder.text.setText(facebookContact.getName());

        Picasso
                .with(mContext)
                .load(facebookContact.getPicture().getUrl())
                .into(viewHolder.image);

        return convertView;
    }
}
