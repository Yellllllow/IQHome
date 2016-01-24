package com.dmm.iqhome;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by waldekd on 1/24/16.
 */
public class MessageReceiver extends AsyncTask<Device, Void, List<Device>> {
    Context context;


    public MessageReceiver(Context context) {
        this.context = context;
    }

    @Override
    protected List<Device> doInBackground(Device... params) {
        List<Device> ret = new ArrayList<>();

        List<NameValuePair> parameters = new ArrayList<>();
        String paramString = "";
        if(params != null) {
            for(Device par : params){
                if(paramString.isEmpty()){
                    paramString = par.Name;
                }else{
                    paramString = par.Name + "," + paramString;
                }

            }
            parameters.add(new BasicNameValuePair("devices", paramString));
        }

        InputStream is = null;
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://arduino.890m.com/select.php");
            if(!parameters.isEmpty()) {
                httppost.setEntity(new UrlEncodedFormEntity(parameters));
            }
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e(MainActivity.TAG, "connection success ");
        }
        catch(Exception e)
        {
            Log.e(MainActivity.TAG, e.toString());
            Toast.makeText(context, "Invalid IP Address", Toast.LENGTH_LONG).show();
        }


        List<String> resultList = new ArrayList<>();
        try
        {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            while ((line = reader.readLine()) != null)
            {
                resultList.add(line);
            }
            is.close();
            Log.e(MainActivity.TAG, "connection success ");
        }
        catch(Exception e)
        {
            Log.e(MainActivity.TAG, e.toString());
        }


            for(String singleResult : resultList) {
                try {
                    JSONObject json_data = new JSONObject(singleResult);
                    ret.add(new Device(json_data.getString("IQ_H_DEVICE"), json_data.getString("IQ_H_VALUE")));
                }catch(Exception e){
                    Log.d(MainActivity.TAG, "Parsing JSON \n" + e.getMessage());
                }
            }

            //name=(json_data.getString("name"));



        return ret;

/*
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://arduino.890m.com/select.php");

        List<NameValuePair> parametes = new ArrayList<NameValuePair>();
        if(params != null) {
            for(String par : params){
                parametes.add(new BasicNameValuePair("device", par));
            }
        }

        if(!parametes.isEmpty()) {
            //Encoding POST data
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(parametes));

            } catch (UnsupportedEncodingException e) {
                Log.e("MESSAGE_RECEIVER", "doInBackground: " + e.getMessage() );
                Toast.makeText(context, "UnsupportedEncodingException in MessageRceiver (1)", Toast.LENGTH_SHORT);
            }
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.d("Http Post Response:", response.toString());
            ret = response.toString();
        } catch (IOException e) {
            Log.e("MESSAGE_RECEIVER", "doInBackground: " + e.getMessage());
            Toast.makeText(context, "Exception in MessageRceiver (2)", Toast.LENGTH_SHORT);
        }

        return ret;

        /*
        InputStream is = null;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://arduino.890m.com/select.php");

            List<NameValuePair> parametes = new ArrayList<NameValuePair>();
            if(params != null) {
                for(String par : params){
                    parametes.add(new BasicNameValuePair("device", par));
                }
            }

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            Toast.makeText(context, "Invalid IP Address",
                    Toast.LENGTH_LONG).show();
        }

        String result = null;
        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.e("pass 2", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());
        }

        Integer code;
        try {
            JSONObject json_data = new JSONObject(result);
            code = (json_data.getInt("status"));

        } catch (Exception e) {
            code = 0;
            Log.e("Fail 3", e.toString());
        }
        return code;
        */
    }
}
