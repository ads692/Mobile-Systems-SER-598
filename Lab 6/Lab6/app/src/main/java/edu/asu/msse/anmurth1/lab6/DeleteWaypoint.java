package edu.asu.msse.anmurth1.lab6;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2015 Aditya Narasimhamurthy.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author: Aditya Narasimhamurthy  mailto:aditya.murthy@asu.edu.
 * @version: February 16, 2015
 */
public class DeleteWaypoint extends Activity {

    public LinkedHashMap<String, String[]> model;

    String s;
    WaypointServerStub cjc;
    Waypoint wp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        s = getIntent().getStringExtra("*****************");
        wp=new Waypoint();
        MainActivity.mn.FetchViewFromDB(wp);
        onPostExecute();

    }
    protected void onPostExecute(){
        EditText ex=(EditText)findViewById(R.id.edit1);
        ex.setText(wp.lat+"");
        ex=(EditText)findViewById(R.id.edit2);
        ex.setText(wp.lon+"");
        ex=(EditText)findViewById(R.id.edit3);
        ex.setText(wp.name+"");
        ex=(EditText)findViewById(R.id.edit4);
        ex.setText(wp.address+"");
        ex=(EditText)findViewById(R.id.edit5);
        ex.setText(wp.category+"");
    }

    public void clickdelete (View v) {

        MainActivity.mn.DeleteViewFromDB();
        finish();

    }

    public void clickback (View v){
        finish();
    }


}



