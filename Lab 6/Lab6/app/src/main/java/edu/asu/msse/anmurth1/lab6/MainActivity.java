package edu.asu.msse.anmurth1.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {


    public static String selectedStuff;
    public ExpandableListView elview;
    public static LinkedHashMap<String, String[]> model;
    public ExpandableStuffAdapter myListAdapter;
    static MainActivity mn;
    static Context c;
    CourseDB db ;
    SQLiteDatabase crsDB;
    public  MainActivity(Context cn){
        c=cn;
        db = new CourseDB(c);
        try {
            db.copyDB();
            crsDB = db.openDB();
        }catch(Exception e) {
        }
    }
    public  MainActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        c=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mn = new MainActivity(this);
        model=new LinkedHashMap<String,String[]>();

        refresh();
    }
    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    private void refresh() {

        mn.setModelFromDB();

        elview = (ExpandableListView)findViewById(R.id.lvExp);
        myListAdapter = new ExpandableStuffAdapter(this, model);

        elview.setAdapter(myListAdapter);
    }

    WaypointServerStub cjc;
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
    public void clickadd (View v) {
        ExpandableListView ex;
        ex = (ExpandableListView)findViewById(R.id.lvExp);
//        String st = ex.getSelectedItem().toString();
        Intent intent = new Intent(MainActivity.this, AddWPActivity.class);
        intent.putExtra("*****************", selectedStuff);
        startActivity(intent);
    }
    public void clickview (View v){
        Intent intent = new Intent(MainActivity.this, ViewWaypoint.class);
        intent.putExtra("*****************", selectedStuff);
        startActivity(intent);
    }
    public void clickmodify (View v){
        Intent intent = new Intent(MainActivity.this, ModifyWaypoint.class);
        intent.putExtra("*****************", selectedStuff);
        startActivity(intent);
    }
    public void clickdelete (View v){
        Intent intent = new Intent(MainActivity.this, DeleteWaypoint.class);
        intent.putExtra("*****************", selectedStuff);
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
public SQLiteDatabase initDB(){

    crsDB.beginTransaction();
    return crsDB;
}
    public void setModelFromDB(){
        try{
            model.clear();
            crsDB=initDB();
            Map<String,List<String>> map=new LinkedHashMap<String,List<String>>();
            Cursor cur = crsDB.rawQuery("select name , category from waypointsdata;",new String[]{});
            while (cur.moveToNext()) {
                String name = cur.getString(0);
                String cat = cur.getString(1);
                List<String> list = map.get(cat);
                if (list == null) {
                    list = new ArrayList<String>();
                    map.put(cat, list);
                }
                list.add(name);
            }
            for(String k:map.keySet()){
                String[] str=new String[map.get(k).size()];int i=0;
                for(String s:map.get(k)){
                    str[i++]=s;
                }

                 model.put(k, str);

            }

            closeDB(cur);
        } catch (Exception ex){ex.printStackTrace();
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }
    }

    private void closeDB(Cursor cur) {
        if (cur!=null)
        cur.close();
        crsDB.endTransaction();
        //crsDB.close();
       // db.close();
    }

    public void FetchViewFromDB(Waypoint wp){

        try{

            crsDB=initDB();
            Log.d("---selected---",selectedStuff);
            Cursor cur = crsDB.rawQuery("select longitude, latitude, name, address, category from " +
                    "waypointsdata where name = '"+selectedStuff+"'",new String[]{});
            while (cur.moveToNext()) {
                wp.lon = cur.getDouble(0);
                wp.lat = cur.getDouble(1);
                wp.name = cur.getString(2);
                wp.address = cur.getString(3);
                wp.category = cur.getString(4);

            }
           closeDB(cur);
        } catch (Exception ex){ex.printStackTrace();
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }
    }
    public void DeleteViewFromDB( ){

        try{
            crsDB=initDB();
            Log.d("---selected---",selectedStuff);
            int  cur = crsDB.delete("waypointsdata", "name = '" + selectedStuff + "'", null);
            //Log.d("****code",cur+"");
            refresh();
            closeDB(null);
        } catch (Exception ex){ex.printStackTrace();
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }

    }

    public void AddtoDB(Waypoint wp){

        try{
            crsDB=initDB();
            //Log.d("---selected---",selectedStuff);
            ContentValues values = new ContentValues();

            values.put("latitude", wp.lat);
            values.put("longitude", wp.lon);
            values.put("name", wp.name);
            values.put("address", wp.address);
            values.put("category", wp.category);
            crsDB.insert("waypointsdata", null, values);

            //Log.d(">>>>>>return code",cur+"");
            closeDB(null);
            refresh();

        } catch (Exception ex){ex.printStackTrace();
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }

    }
    public void export (View v){
        mn.ExportFromDB();
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private void writeToFile(String s) {
        FileOutputStream outputStream;
        String filename = "waypoint.json";
        try {
            if (!isExternalStorageWritable() || isExternalStorageReadable()) {
                Log.i(this.getClass().getSimpleName(),"Unable to find the external storage device");return ;
            }

            File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), filename);
            if (!myFile.exists())
                myFile.createNewFile();
            FileOutputStream fos;
            byte[] data = s.getBytes();
            try {
                fos = new FileOutputStream(myFile);
                fos.write(data);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

        public void ExportFromDB(){

        try{

            StringBuffer sb=new StringBuffer();
            crsDB=initDB();
            //Log.d("---selected---",selectedStuff);
            Cursor cur = crsDB.rawQuery("select longitude, latitude, name, address, category from " +
                    "waypointsdata", new String[]{});
            while (cur.moveToNext()) {
                Waypoint wp = new Waypoint();
                wp.lon = cur.getDouble(0);
                wp.lat = cur.getDouble(1);
                wp.name = cur.getString(2);
                wp.address = cur.getString(3);
                wp.category = cur.getString(4);
                sb.append(wp.toJsonString()+"\n");
            }
            writeToFile(sb.toString());
            closeDB(cur);
        } catch (Exception ex){ex.printStackTrace();
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }
    }
}
