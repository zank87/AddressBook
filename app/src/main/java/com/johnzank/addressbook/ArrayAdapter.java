/*package com.johnzank.addressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by johnzank on 9/23/15.
 */

/*public class ArrayAdapter extends android.widget.ArrayAdapter<Contact> {
    public ArrayAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact, parent, false);
        }

        TextView contactFName = (TextView) convertView.findViewById(R.id.contactFName);

        contactFName.setText(contact.contactName);

        return convertView;
    }
}
*/