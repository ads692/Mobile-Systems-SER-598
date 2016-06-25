package edu.asu.msse.anmurth1.lab4;

import android.content.ClipData;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.net.URI;
import java.net.URL;
import java.text.NumberFormat;
import java.text.StringCharacterIterator;


public class MainActivity extends ActionBarActivity implements  AdapterView.OnItemSelectedListener  {
    Spinner spinner;

    private ClipData.Item leftET;
    private ClipData.Item rightET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner)findViewById(R.id.spinner);

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

    public void callMethodClick(View v){
        android.util.Log.w(this.getClass().getSimpleName(),"button clicked: "+spinner.getSelectedItem());
        try{
            AsyncCalculate ac = (AsyncCalculate) new AsyncCalculate().execute(new URL(this.getString(R.string.url_string)));
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"exception buttonClick: "+ex.getMessage());
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class AsyncCalculate extends AsyncTask<URL, Integer, Double> {

        String retStr = "";

        @Override
        protected void onPreExecute() {
            android.util.Log.d(this.getClass().getSimpleName(), "in onPreExecute on " +
                    (Looper.myLooper() == Looper.getMainLooper() ? "Main Thread" : "Async Thread"));
        }

        @Override
        protected Double doInBackground(URL... aURL) {
            android.util.Log.d(this.getClass().getSimpleName(), "in doInBackground on " +
                    (Looper.myLooper() == Looper.getMainLooper() ? "Main Thread" : "Async Thread"));
            double val = 0;
            try {
                CalculatorStub calc = new CalculatorStub(aURL[0].toString());
                String oper = (String) spinner.getSelectedItem();
                EditText leftET=(EditText)findViewById(R.id.leftET);
                EditText rightET=(EditText)findViewById(R.id.rightET);
                double left = new Double(leftET.getText().toString());
                double right = new Double(rightET.getText().toString());
                if ("add".equalsIgnoreCase(oper)) {
                    val = calc.add(left, right);
                } else if ("subtract".equalsIgnoreCase(oper)) {
                    val = calc.subtract(left, right);
                } else if ("multiply".equalsIgnoreCase(oper)) {
                    val = calc.multiply(left, right);
                } else if ("divide".equalsIgnoreCase(oper)) {
                    val = calc.divide(left, right);
                }
            } catch (Throwable ex) {
                android.util.Log.e(this.getClass().getSimpleName(), "=========================Exception in remote call " +
                        ex.toString());
                ex.printStackTrace();


            }
            android.util.Log.d(this.getClass().getSimpleName(), "Completed JsonRPC call: result "+
                    (new Double(val).toString()));
            return val;
        }

        @Override
        protected void onPostExecute(Double res) {
            android.util.Log.d(this.getClass().getSimpleName(), "in onPostExecute on " +
                    (Looper.myLooper() == Looper.getMainLooper() ? "Main Thread" : "Async Thread"));
            android.util.Log.d(this.getClass().getSimpleName(), "result is " + res.toString());
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            EditText t=(EditText) findViewById(R.id.resultET);
            t.setText(nf.format(res));
        }
    }
}

