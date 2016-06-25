package edu.asu.msse.anmurth1.completemediaplayer;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Copyright 2015 Aditya Narasimhamurthy.
 * <p/>
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
 * @author: Aditya Narasimhamurthy mailto:aditya.murthy@asu.edu
 * @version: April 25, 2015
 */


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"f3.ttf");
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setTypeface(myTypeface);

        Typeface myTypeface3 = Typeface.createFromAsset(getAssets(),"f4.ttf");
        TextView textView3 = (TextView)findViewById(R.id.textView3);
        textView3.setTypeface(myTypeface3);

        Typeface myTypeface2 = Typeface.createFromAsset(getAssets(),"f6.ttf");
        Button button = (Button)findViewById(R.id.button);
        button.setTypeface(myTypeface2);

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setTypeface(myTypeface2);

        Button button3 = (Button)findViewById(R.id.button3);
        button3.setTypeface(myTypeface2);

        Button button4 = (Button)findViewById(R.id.button4);
        button4.setTypeface(myTypeface2);
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

    public void audioplayer(View view) {
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
    }

    public void videoplayer(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }

    public void goOnline(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
        startActivity(intent);
    }

    public void streamOnline(View view){
        Intent intent = new Intent(this, VideoStream.class);
        startActivity(intent);
    }

    public void exitapp(View view){
        System.exit(0);
    }
}
