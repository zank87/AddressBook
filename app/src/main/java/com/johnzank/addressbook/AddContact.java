package com.johnzank.addressbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends Activity {

    public static final String EXTRA_CONTACT_OBJ = "default";
    public static final String TAG = AddContact.class.getSimpleName();

    private void contactAdded(Contact contact) {
        Intent data = new Intent(this, MainActivity.class);
        data.putExtra(EXTRA_CONTACT_OBJ, contact);
        setResult(RESULT_OK, data);
        Log.d(TAG, "SET RESULT");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        final EditText mName = (EditText) findViewById(R.id.nameEditText);
        final EditText mPhone = (EditText) findViewById(R.id.phoneEditText);
        final EditText mEmail = (EditText) findViewById(R.id.emailEditText);
        final EditText mStreet = (EditText) findViewById(R.id.streetEditText);
        final EditText mCityStZip = (EditText) findViewById(R.id.cityStZipEditText);

        Button addContact = (Button) findViewById(R.id.addContactButton);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"PRESSED ADD CONTACT");
                Contact contact = new Contact(
                        mName.getText().toString(),
                        mPhone.getText().toString(),
                        mEmail.getText().toString(),
                        mStreet.getText().toString(),
                        mCityStZip.getText().toString());
                contactAdded(contact);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
