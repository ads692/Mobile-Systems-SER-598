package edu.asu.msse.anmurth1.lab5;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.net.URL;
import java.util.LinkedHashMap;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {


    public String selectedStuff;
    public ExpandableListView elview;
    public LinkedHashMap<String, String[]> model;
    public ExpandableStuffAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh();
    }
    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    private void refresh() {
        model=new LinkedHashMap<String,String[]>();
        elview = (ExpandableListView)findViewById(R.id.lvExp);
        myListAdapter = new ExpandableStuffAdapter(this, model);
        try {
            AsyncCalculator asyncCalculator =(AsyncCalculator) new AsyncCalculator().execute(new URL("http://192.168.0.21:8080/"));

        }catch(Throwable e){
            Log.e(this.getClass().getSimpleName(), "Error occurred while executing Async Task");

        }
    }

    WaypointServerStub cjc;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }
    private void populateModel(LinkedHashMap<String, String[]> model) {
        try {
            String[] result = cjc.getCategoryNames();
            String cat = null;
            for (int i = 0; i < result.length; i++) {
                cat = result[i];
                Log.e("--------------------------------------->>",cat);
                String[] result1 = cjc.getNamesInCategory(cat);
                model.put(cat, result1);
            }
        }catch(Throwable e){
            e.printStackTrace();
            Log.e("--------------------------------------->>",e.toString() );
            throw e;
        }
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
    public void clickadd (View v) {
        ExpandableListView ex;
        ex = (ExpandableListView)findViewById(R.id.lvExp);
//        String st = ex.getSelectedItem().toString();
        Intent intent = new Intent(MainActivity.this, AddWPActivity.class);
        intent.putExtra("AAAA", selectedStuff);
        startActivity(intent);
    }
    public void clickview (View v){
        Intent intent = new Intent(MainActivity.this, ViewWaypoint.class);
        intent.putExtra("AAAA", selectedStuff);
        startActivity(intent);
    }
    public void clickmodify (View v){
        Intent intent = new Intent(MainActivity.this, ModifyWaypoint.class);
        intent.putExtra("AAAA", selectedStuff);
        startActivity(intent);
    }
    public void clickdelete (View v){
        Intent intent = new Intent(MainActivity.this, DeleteWaypoint.class);
        intent.putExtra("AAAA", selectedStuff);
        startActivity(intent);
    }


    public void setSelectedStuff(String selectedStuff) {
        this.selectedStuff = selectedStuff;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedStuff=(String)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class AsyncCalculator extends AsyncTask<URL, Integer, Double> {

        private void populateModel(LinkedHashMap<String, String[]> model) {
            try {
                String[] result = cjc.getCategoryNames();
                String cat = null;
                for (int i = 0; i < result.length; i++) {
                    cat = result[i];
                    Log.e("--------------------------------------->>",cat);
                    String[] result1 = cjc.getNamesInCategory(cat);
                    model.put(cat, result1);
                }
            }catch(Throwable e){
                e.printStackTrace();
                Log.e("--------------------------------------->>",e.toString() );
                throw e;
            }
        }
        @Override
        protected Double doInBackground(URL... params) {
            Double res=0.0;
            try{

                cjc = new WaypointServerStub("http://192.168.0.21:8080/");

                populateModel(model);
            }catch(Throwable e){

                Log.e(this.getClass().getSimpleName(),e.getMessage());

            }finally{
                //  s=new StringBuffer();
            }
            return res;
        }
        @Override
        protected void onPostExecute(Double d){
            elview.setAdapter(myListAdapter);
        }
    }
}
