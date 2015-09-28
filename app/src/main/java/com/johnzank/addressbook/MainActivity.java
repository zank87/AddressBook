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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
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
import java.util.Arrays;

public class MainActivity extends Activity {

    private ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<String> contactNamesList = new ArrayList<>();
    private ArrayAdapter<String> contactAdapter;
    private String mFilename = "contacts.json";
    public static final String EXTRA_CONTACT_OBJ = "default";
    public static final String TAG = MainActivity.class.getSimpleName();


    // Write the names of contacts into JSON file
    private void saveContactList() {
        JSONArray array = new JSONArray(contactNamesList);
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

    // Read names of contacts from JSON file
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
        if (data == null)
            return;

        // ADD created contact to contactNamesList
        Log.d(TAG,"RETURNED SUCCESSFULLY");
        Bundle contactInfo = data.getExtras();
        String[] contactBuilder = contactInfo.getStringArray(EXTRA_CONTACT_OBJ);
        Log.d(TAG, Arrays.toString(contactBuilder));
        contacts.add(new Contact(contactBuilder[0], contactBuilder[1], contactBuilder[2], contactBuilder[3], contactBuilder[4]));
        contactNamesList.add(contactBuilder[0]);
        ListView listView = (ListView) findViewById(R.id.contactListView);
        listView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactNamesList);
        ListView listView = (ListView) findViewById(R.id.contactListView);
        listView.setAdapter(contactAdapter);
        registerForContextMenu(listView);
        readContactList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveContactList();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                contactNamesList.remove(position);
                contacts.remove(position);
                contactAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_item_edit:
                Intent i = new Intent(MainActivity.this, AddContact.class);
                Bundle contactInfo = new Bundle();
                String[] tempContact = {
                        contacts.get(position).contactName,
                        contacts.get(position).contactPhone,
                        contacts.get(position).contactEmail,
                        contacts.get(position).contactStreet,
                        contacts.get(position).contactCityStZip,
                        position+ ""};
                contactInfo.putStringArray(EXTRA_CONTACT_OBJ, tempContact);
                i.putExtras(contactInfo);
                startActivityForResult(i, 1);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contact_list, menu);
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
