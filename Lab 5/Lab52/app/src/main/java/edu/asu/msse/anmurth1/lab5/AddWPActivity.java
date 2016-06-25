package edu.asu.msse.anmurth1.lab5;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.net.URL;
import java.util.LinkedHashMap;

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
public class AddWPActivity extends Activity {
    String s;
    WaypointServerStub cjc ;//= new WaypointServerStub(url);
    Waypoint wp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwp);

    }

    public void clickadd (View v) {
        wp=new Waypoint();
        EditText ex=(EditText)findViewById(R.id.edit1);
        wp.lat=Double.parseDouble(ex.getText().toString());


        ex=(EditText)findViewById(R.id.edit2);

        wp.lon=Double.parseDouble(ex.getText().toString());
        ex=(EditText)findViewById(R.id.edit3);
        wp.name=ex.getText().toString();

        ex=(EditText)findViewById(R.id.edit4);
        wp.address=ex.getText().toString();

        ex=(EditText)findViewById(R.id.edit5);
        wp.category=ex.getText().toString();
        wp.ele=500;
        try {
            AsyncCalculator asyncCalculator =(AsyncCalculator) new AsyncCalculator().execute(new URL("http://192.168.0.21:8080/"));

        }catch(Throwable e){
            Log.e(this.getClass().getSimpleName(), "Error occurred while executing Async Task");

        }

    }

    public void clickback (View v){
        Intent intent = new Intent(AddWPActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private class AsyncCalculator extends AsyncTask<URL, Integer, Double> {


        @Override
        protected Double doInBackground(URL... params) {
            Double res=0.0;
            try{

                cjc = new WaypointServerStub("http://192.168.0.21:8080/");
                 cjc.add(wp);

            }catch(Throwable e){

                Log.e(this.getClass().getSimpleName(),e.getMessage());

            }finally{
                //  s=new StringBuffer();
            }
            return res;
        }
        @Override
        protected void onPostExecute(Double d){
finish();;
        }
    }
}
