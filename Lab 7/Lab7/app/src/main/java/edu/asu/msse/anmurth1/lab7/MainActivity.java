/*
 * Copyright 2015 Aditya Narasimhamurthy.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  @author: Aditya Narasimhamurthy  aditya.murthy@asu.edu
 *  @version: February 28, 2015
 */

package edu.asu.msse.anmurth1.lab7;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static edu.asu.msse.anmurth1.lab7.R.*;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    EditText fn, ln, pn, em;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        fn = (EditText)findViewById(id.fname);
        ln = (EditText)findViewById(id.lname);
        pn = (EditText)findViewById(id.pnumber);
        em = (EditText)findViewById(id.email);
        spinner = (Spinner)findViewById(id.spinner);
        String[] displayNames = getContactNames();
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new ArrayList(Arrays.asList(displayNames)));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStart(){
        super.onStart();
        Log.d(getClass().getSimpleName(),"in onStart()");
    }

    public void onRestart(){
        super.onRestart();
        Log.d(getClass().getSimpleName(),"in onRestart()");
    }

    public void onResume(){
        super.onResume();
        Log.d(getClass().getSimpleName(),"in onResume()");
        adapter.clear();
        adapter.addAll(new ArrayList<String>(Arrays.asList(this.getContactNames())));
        adapter.notifyDataSetChanged();
    }

    public void onPause(){
        super.onPause();
        Log.d(getClass().getSimpleName(),"in onPause()");
    }

    public void onStop(){
        super.onStop();
        Log.d(getClass().getSimpleName(),"in onStop()");
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(getClass().getSimpleName(),"in onDestroy()");
    }

    public void add(View v){
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, fn.getText()+" "+ln.getText());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, pn.getText());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, em.getText());

        startActivity(intent);
    }

    private String[] getContactNames(){
        ArrayList<String> ret = new ArrayList<String>();
        String whereName = ContactsContract.Data.MIMETYPE + " =?";
        String[] whereNameParams = new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
        Cursor nameCur = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null,
                whereName, whereNameParams, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
        while(nameCur.moveToNext()){
            ret.add((String)nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME)));
        }
        nameCur.close();
        return (String[])ret.toArray(new String[]{});
    }

    private String getEmailId(String Id){
        String ret = "noEmail";

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                "DISPLAY_NAME = '" + Id + "'", null, null);
        if(cursor.moveToFirst()) {
            String emailID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor mail = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + emailID, null, null);

            while (mail.moveToNext()) {
                String number = mail.getString(mail.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                ret = number;
                break;
            }
            mail.close();
        }
        cursor.close();
        return ret;
    }

    private String getPhone(String aName){
        String ret = "noPhone";

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                "DISPLAY_NAME = '" + aName + "'", null, null);
        if(cursor.moveToFirst()) {
            String contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactID, null, null);

            while (phones.moveToNext()) {
                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                ret = number;
                break;
            }
            phones.close();
        }
        cursor.close();
        return ret;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        Object item = parent.getItemAtPosition(pos);
        android.util.Log.d(this.getClass().getSimpleName(), "item type is: " + item.getClass().toString());
        android.util.Log.d(this.getClass().getSimpleName(), "spinner item: " + item.toString() + "selected");

        String phone = getPhone(item.toString());
        android.util.Log.d(this.getClass().getSimpleName(),"found "+phone);
        pn.setText(phone);
        String email = getEmailId(item.toString());
        android.util.Log.d(this.getClass().getSimpleName(),"found "+email);
        em.setText(email);

        Scanner s = new Scanner(item.toString());
        fn.setText((s.hasNext() ? s.next():"aFirst"));
        ln.setText((s.hasNext() ? s.next():"aLast"));
    }

    public void onNothingSelected(AdapterView<?> parent){
        android.util.Log.w(this.getClass().getSimpleName(), "in onNothingSelected");
    }

}
