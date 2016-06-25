package edu.asu.msse.anmurth1.lab6;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedHashMap;

import edu.asu.msse.anmurth1.lab6.MainActivity;
import edu.asu.msse.anmurth1.lab6.R;

/**
 * Copyright 2015 Tim Lindquist,
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
 * <p/>
 * Purpose:  Demonstrate an adapter for an expandable list view
 *
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, Arizona State University Polytechnic
 * @version February 07, 2015
 */
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.LinkedHashMap;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

public class ExpandableStuffAdapter extends BaseExpandableListAdapter
        implements View.OnTouchListener,
        ExpandableListView.OnGroupExpandListener,
        ExpandableListView.OnGroupCollapseListener{
    private TextView currentSelectedTV = null;
    private MainActivity parent;
    private LinkedHashMap<String,String[]> model;

    public ExpandableStuffAdapter(MainActivity parent,LinkedHashMap<String,String[]> model) {
        android.util.Log.d(this.getClass().getSimpleName(),"in constructor so creating new model");
        this.model =model;//
        this.parent = parent;
        parent.elview.setOnGroupExpandListener(this);
        parent.elview.setOnGroupCollapseListener(this);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String[] stuffTitles = model.keySet().toArray(new String[] {});
        return model.get(stuffTitles[groupPosition])[childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            android.util.Log.d(this.getClass().getSimpleName(),"in getChildView null so creating new view");
            LayoutInflater inflater = (LayoutInflater) this.parent
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView txtListChild = (TextView)convertView.findViewById(R.id.lblListItem);
        convertView.setOnTouchListener(this);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String[] stuffTitles = model.keySet().toArray(new String[] {});
        android.util.Log.d(this.getClass().getSimpleName(),"in getChildrenCount for: "+groupPosition+" returning: "+  model.get(stuffTitles[groupPosition]).length);
        android.util.Log.e(toString(), stuffTitles[groupPosition]);
        return model.get(stuffTitles[groupPosition]).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        String[] stuffTitles = model.keySet().toArray(new String[] {});
        android.util.Log.d(this.getClass().getSimpleName(),"in getGroup returning: "+stuffTitles[groupPosition]);
        return stuffTitles[groupPosition];
    }

    @Override
    public int getGroupCount() {
        String[] stuffTitles = model.keySet().toArray(new String[] {});
        android.util.Log.d(this.getClass().getSimpleName(),"in getGroupCount returning: "+stuffTitles.length);
        return stuffTitles.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        android.util.Log.d(this.getClass().getSimpleName(),"in getGroupPosition returning: "+groupPosition);
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String)getGroup(groupPosition);
        if (convertView == null) {
            android.util.Log.d(this.getClass().getSimpleName(),"in getGroupView null so creating new view");
            LayoutInflater inflater = (LayoutInflater) this.parent
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        android.util.Log.d(this.getClass().getSimpleName(),"in getGroupView text is: "+lblListHeader.getText());
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
            /*ImageView img = (ImageView)convertView.findViewById(R.id.group_image);
            if("Pets".equals(headerTitle)) {
                img.setImageResource(R.drawable.ic_pet_dog);
            } else if("Hikes".equals(headerTitle)) {
                img.setImageResource(R.drawable.ic_camp);
            } else if("Tools".equals(headerTitle)){
                img.setImageResource(R.drawable.ic_hammer);
            } else {
                img.setImageResource(R.drawable.ic_book);
            }*/
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        android.util.Log.d(this.getClass().getSimpleName(),"in hasStableIds returning false");
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        String[] stuffTitles = model.keySet().toArray(new String[] {});
        android.util.Log.d(this.getClass().getSimpleName(),"in isChildSelectable?  "+
                model.get(stuffTitles[groupPosition])[childPosition]);
        return true;
    }

    public boolean onTouch(View v, MotionEvent event){
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            android.util.Log.d(this.getClass().getSimpleName(),"in onTouch called for view of type: " +
                    v.getClass().toString());
            if(v instanceof android.widget.LinearLayout){
                android.widget.LinearLayout layView = (android.widget.LinearLayout)v;
                for(int i=0; i<=layView.getChildCount(); i++){
                    if(layView.getChildAt(i) instanceof TextView){
                        // keep track of TV stuff was most recently touched to un-highlighted

                        if (currentSelectedTV != null){
                            currentSelectedTV.setBackgroundColor(Color.GREEN);
                        }
                        TextView tmp = ((TextView)layView.getChildAt(i));
                        tmp.setBackgroundColor(Color.GREEN);
                        currentSelectedTV = tmp;
                        parent.setSelectedStuff(tmp.getText().toString());
                        android.util.Log.d(this.getClass().getSimpleName(),"TextView "+
                                ((TextView)layView.getChildAt(i)).getText()+" selected.");
                    }
                }
            }
            // code below never executes. onTouch is called for textview's linearlayout parent
            if(v instanceof TextView){
                android.util.Log.d(this.getClass().getSimpleName(),"in onTouch called for: " +
                        ((TextView)v).getText());
            }
        }
        return true;
    }

    public void onGroupExpand(int groupPosition){
        android.util.Log.d(this.getClass().getSimpleName(),"in onGroupExpand called for: "+
                model.keySet().toArray(new String[] {})[groupPosition]);
        if (currentSelectedTV != null){
            currentSelectedTV.setBackgroundColor(Color.GREEN);
            currentSelectedTV = null;
        }
        for (int i=0; i< this.getGroupCount(); i++) {
            if(i != groupPosition){
                parent.elview.collapseGroup(i);
            }
        }
    }

    public void onGroupCollapse(int groupPosition){
        android.util.Log.d(this.getClass().getSimpleName(),"in onGroupCollapse called for: "+
                model.keySet().toArray(new String[] {})[groupPosition]);
        if (currentSelectedTV != null){
            currentSelectedTV.setBackgroundColor(Color.GREEN);
            currentSelectedTV = null;
        }
    }
}
