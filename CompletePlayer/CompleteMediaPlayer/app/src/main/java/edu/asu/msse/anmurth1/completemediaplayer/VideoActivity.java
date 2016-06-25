package edu.asu.msse.anmurth1.completemediaplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

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
 * @author: Aditya Narasimhamurthy  mailto:aditya.murthy@asu.edu
 * @version: April 28, 2015
 */
public class VideoActivity extends MainActivity {

    ListView lv;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_list);
        lv = (ListView) findViewById(R.id.list);
        String[] items;

        final ArrayList<File> myVideos = findVideos(Environment.getExternalStorageDirectory());
        items = new String[myVideos.size()];
        for(int i=0; i<myVideos.size(); i++){
            //toast(myVideos.get(i).getName().toString());
            items[i] = myVideos.get(i).getName().toString().replace(".mp4", "");
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_layout,R.id.textView2, items);
        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                startActivity(new Intent(getApplicationContext(),VideoPlayer.class).putExtra("pos", position).putExtra("videolist", myVideos));
            }
        });
    }

    public ArrayList<File> findVideos(File root) {
        ArrayList<File> a1 = new ArrayList<File>();
        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                a1.addAll(findVideos(singleFile));
            }
            else{
                if(singleFile.getName().endsWith(".mp4")){
                    a1.add(singleFile);
                }
            }
        }
        return  a1;

    }

    /*public void toast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }*/

}

