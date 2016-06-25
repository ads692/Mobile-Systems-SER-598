package edu.asu.msse.anmurth1.lab4;
/**
 * Copyright 2015 Aditya Narasimhamurthy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author: Aditya Narasimhamurthy  aditya.murthy@asu.edu
 * @version: February 3, 2015
 */
import android.util.Log;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;


public class CalculatorStub   {

    public String serviceURL;
    public JsonRpcRequestViaHttp server;
    public static int id = 0;

    public CalculatorStub(String serviceURL) {
        this.serviceURL = serviceURL;
        try {
            this.server = new JsonRpcRequestViaHttp(new URL(serviceURL));
        } catch (Exception ex) {
            Log.e("Incorrect URL===== ", ex.getMessage());
        }
    }

    private String packageCalcCall(String oper, double left, double right)  {
        try {
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("jsonrpc", "2.0");
            jsonObj.put("method", oper);
            jsonObj.put("id", ++id);
            String almost = jsonObj.toString();
            String toInsert = ",\"params\":[" + String.format("%.2f", left)
                    + "," + String.format("%.2f", right) + "]";
            String begin = almost.substring(0, almost.length() - 1);
            String end = almost.substring(almost.length() - 1);
            String ret = begin + toInsert + end;
            return ret;
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Add two numbers
     *
     * @return The sum
     */
    public double add(double left, double right) {
        double result = 0;
        try {
            String jsonStr = this.packageCalcCall("add", left, right);
            String resString = server.call(jsonStr);
            JSONObject res = new JSONObject(resString);
            result = res.optDouble("result");
        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex != null)
                Log.e("exception in rpc call to plus: ", ex.toString());
        }
        return result;
    }

    /**
     * Subtract two numbers
     *
     * @return The difference
     */
    public double subtract(double left, double right) {
        double result = 0;
        try {
            String jsonStr = this.packageCalcCall("subtract", left, right);
            String resString = server.call(jsonStr);
            JSONObject res = new JSONObject(resString);
            result = res.optDouble("result");
        } catch (Exception ex) {
            System.out.println("exception in rpc call to plus: " + ex.getMessage());
        }
        return result;
    }

    /**
     * Multiply two numbers
     *
     * @return The product
     */
    public double multiply(double left, double right) {
        double result = 0;
        try {
            String jsonStr = this.packageCalcCall("multiply", left, right);
            String resString = server.call(jsonStr);
            JSONObject res = new JSONObject(resString);
            result = res.optDouble("result");
        } catch (Exception ex) {
            System.out.println("exception in rpc call to plus: " + ex.getMessage());
        }
        return result;
    }

    /**
     * Divide two numbers
     *
     * @return left / right
     */
    public double divide(double left, double right) {
        double result = 0;
        try {
            String jsonStr = this.packageCalcCall("divide", left, right);
            String resString = server.call(jsonStr);
            JSONObject res = new JSONObject(resString);
            result = res.optDouble("result");
        } catch (Exception ex) {
            System.out.println("exception in rpc call to plus: " + ex.getMessage());
        }
        return result;
    }

    /**
     * Get the service information.
     *
     * @return The service information
     */
    public String serviceInfo() {
        return "Service information";
    }


}