package com.dmm.iqhome;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dmm.iqhome.com.dmm.iqhome.interfaces.IReturnValueFromStatusProvider;

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
public class StatusProvider extends AsyncTask<Device, Void, List<Device>> {
    Context context;
    private IReturnValueFromStatusProvider returnValueFromStatusProvider;

    public StatusProvider(Context context, IReturnValueFromStatusProvider ret) {
        this.context = context;
        this.returnValueFromStatusProvider = ret;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Device> devices) {
        returnValueFromStatusProvider.GetValueReturnedByStatusProvider(devices);
        super.onPostExecute(devices);
    }

    @Override
    protected List<Device> doInBackground(Device... params) {
        List<Device> ret = new ArrayList<>();

        List<NameValuePair> parameters = new ArrayList<>();
        String paramString = "";
        if (params != null) {
            for (Device par : params) {
                if (paramString.isEmpty()) {
                    paramString = par.Name;
                } else {
                    paramString = par.Name + "," + paramString;
                }

            }
            parameters.add(new BasicNameValuePair("devices", paramString));
        }

        InputStream is = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://arduino.890m.com/select.php");
            if (!parameters.isEmpty()) {
                httppost.setEntity(new UrlEncodedFormEntity(parameters));
            }
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e(MainActivity.TAG, "connection success ");
        } catch (Exception e) {
            Log.e(MainActivity.TAG, e.toString());
            Toast.makeText(context, "Invalid IP Address", Toast.LENGTH_LONG).show();
        }


        List<String> resultList = new ArrayList<>();
        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            while ((line = reader.readLine()) != null) {
                resultList.add(line);
            }
            is.close();
            Log.e(MainActivity.TAG, "connection success ");
        } catch (Exception e) {
            Log.e(MainActivity.TAG, e.toString());
        }


        for (String singleResult : resultList) {
            try {
                JSONObject json_data = new JSONObject(singleResult);
                ret.add(new Device(json_data.getString("IQ_H_DEVICE"), json_data.getString("IQ_H_VALUE"), json_data.getString("IQ_H_ACTIVE"), json_data.getString("IQ_H_VOICE_COMMAND_ENABLE"),json_data.getString("IQ_H_VOICE_COMMAND_DISABLE")));
            } catch (Exception e) {
                Log.d(MainActivity.TAG, "Parsing JSON \n" + e.getMessage());
            }
        }

        return ret;

    }
}
