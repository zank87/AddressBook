package com.johnzank.addressbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<Contact> contactList = new ArrayList<>();
    private String mFilename = "contacts.json";
    public static final String EXTRA_CONTACT_OBJ = "default";
    public static final String TAG = MainActivity.class.getSimpleName();


    private void saveContactList() {
        JSONArray array = new JSONArray(contactList);
        Writer writer;
        try {
            OutputStream out = openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
            writer.close();
        } catch(IOException e ) {
            Log.d("Writer", "IO exception: ", e);
        }
    }

    private void readContactList() {
        BufferedReader reader;
        try {
            InputStream in = openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));

            // Will probably have to replace this since file contains an object, not just strings
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {

            }
            reader.close();
        } catch (IOException e) {
            Log.d("Reader", "IO exception: ", e);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "RETURNED");
        ContactAdapter mContactAdapter = new ContactAdapter(this, contactList);
        if (data == null)
            return;

        // ADD created contact to contactList
        Log.d(TAG,"RETURNED SUCCESSFULLY");
        Intent i = getIntent();
        Contact contact = (Contact)i.getSerializableExtra(EXTRA_CONTACT_OBJ);
        contactList.add(contact);
        mContactAdapter.notifyDataSetChanged();
        ListView listView = (ListView) findViewById(R.id.contactListView);
        listView.setAdapter(mContactAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContactAdapter mContactAdapter = new ContactAdapter(this, contactList);
        ListView listView = (ListView) findViewById(R.id.contactListView);
        listView.setAdapter(mContactAdapter);

        readContactList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveContactList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contact_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ContactAdapter mContactAdapter = new ContactAdapter(this, contactList);
        int position = info.position;
        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                contactList.remove(position);
                mContactAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_contact) {
            Intent i = new Intent(MainActivity.this, AddContact.class);
            startActivityForResult(i, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
