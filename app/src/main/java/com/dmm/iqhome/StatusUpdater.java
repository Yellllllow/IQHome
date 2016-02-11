package com.dmm.iqhome;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dmm.iqhome.com.dmm.iqhome.interfaces.IReturnValueFromStatusUpdater;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
IQ_HARDWARE
-----------
IQ_H_DEVICE
IQ_H_VALUE
IQ_H_ACTIVE
IQ_H_VOICE_COMMAND_ENABLE
IQ_H_VOICE_COMMAND_DISABLE
*/

/**
 * Created by waldekd on 1/24/16.
 */
public class StatusUpdater extends AsyncTask<Device, Void, String> {
    private Context context;
    private IReturnValueFromStatusUpdater returnValueFromStatusUpdater;

    public StatusUpdater(Context context, IReturnValueFromStatusUpdater ret) {
        this.context = context;
        this.returnValueFromStatusUpdater = ret;
    }

    @Override
    protected void onPostExecute(String s) {
        returnValueFromStatusUpdater.GetValueReturnedByStatusUpdater(s);
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(Device... params) {
        String ret = "NO_PARAMS";

        List<NameValuePair> parameters = new ArrayList<>();
        String nameString = "";
        String enableString = "";
        String activeString = "";
        String voiceEnableString = "";
        String voiceDisableString = "";

        if(params != null){
            for(Device par : params){
                if(!par.isEmpty()){
                    if (nameString.isEmpty()) {
                        nameString = par.Name;
                        enableString = par.Value;
                        activeString = par.Active;
                        voiceEnableString = par.VoiceEnable;
                        voiceDisableString = par.VoiceDisable;
                    } else {
                        nameString = par.Name + "," + nameString;
                        enableString = par.Value + "," + enableString;
                        activeString = par.Active + "," + activeString;
                        voiceEnableString = par.VoiceEnable + "," + voiceEnableString;
                        voiceDisableString = par.VoiceDisable + "," + voiceDisableString;
                    }
                }
            }
            parameters.add(new BasicNameValuePair("name", nameString));
            parameters.add(new BasicNameValuePair("enable", enableString));
            parameters.add(new BasicNameValuePair("active", activeString));
            parameters.add(new BasicNameValuePair("voice_enable", voiceEnableString));
            parameters.add(new BasicNameValuePair("voice_disable", voiceDisableString));
        }

        if(!parameters.isEmpty()) {

            InputStream is = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://arduino.890m.com/update.php");
                httppost.setEntity(new UrlEncodedFormEntity(parameters));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.e("pass 1", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
                Toast.makeText(context, "Invalid IP Address", Toast.LENGTH_LONG).show();
            }


            try {
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                while ((line = reader.readLine()) != null) {
                    Log.i(MainActivity.TAG, line);
                    if(line.contains("FAIL")){
                        return "FAIL";
                    }
                }
                is.close();
                Log.e(MainActivity.TAG, "connection success ");
            } catch (Exception e) {
                Log.e(MainActivity.TAG, e.toString());
            }

        }else{
            return ret;
        }
        return "OK";
    }
}
/*
            List<Device> listOfUpdatedDevices = new ArrayList<>();
            for (String singleResult : resultList) {
                try {
                    JSONObject json_data = new JSONObject(singleResult);
                    Log.d(MainActivity.TAG, "Parsing JSON \n");
                   // listOfUpdatedDevices.add(new Device(json_data.getString("IQ_H_DEVICE"), json_data.getString("IQ_H_VALUE")));
                } catch (Exception e) {
                    Log.d(MainActivity.TAG, "Parsing JSON \n" + e.getMessage());
                }
            }

/*
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                ret = sb.toString();
                Log.e("pass 2", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 2", e.toString());
            }
            /*
            int code = -1;
            try {
                JSONObject json_data = new JSONObject(result);
                code = (json_data.getInt("code"));

                if (code == 1) {
                    Toast.makeText(context, "Update Successfully",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Sorry, Try Again",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }*/
