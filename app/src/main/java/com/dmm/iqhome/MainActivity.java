package com.dmm.iqhome;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dmm.iqhome.com.dmm.iqhome.fragments.DevicesMainFragment;
import com.dmm.iqhome.com.dmm.iqhome.fragments.Settings;
import com.dmm.iqhome.com.dmm.iqhome.interfaces.IReturnValueFromStatusProvider;
import com.dmm.iqhome.com.dmm.iqhome.interfaces.IReturnValueFromStatusUpdater;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements IReturnValueFromStatusUpdater, IReturnValueFromStatusProvider {

    public static String TAG = "IQHOMETag";

    Button btnActivateVoiceControl;
    Button btnSelectDB;
    TextView tvStatus;

    boolean isDeviceFragmentAdded = false;
//    ToggleButton tbLED1;
//    ToggleButton tbLED2;

    private ProgressDialog progressDialog;
    private DevicesMainFragment devicesMainFragment = new DevicesMainFragment();

    private static final int SPEECH_REQUEST_CODE = 0;

    private CommandManager commandManager = new CommandManager();
    private Speech2CommandTranslator speech2CommandTranslator = new Speech2CommandTranslator(commandManager);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isNetworkAvailable()){
            Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        btnSelectDB = (Button) findViewById(R.id.btnSelectDB);
        btnSelectDB.setOnClickListener(btnSelectDBListener);
        btnSelectDB.performClick();

        if (findViewById(R.id.frDevicesMain) != null) {
            if (savedInstanceState != null) {
                return;
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnActivateVoiceControl = (Button) findViewById(R.id.btnActivateVoiceControl);
        btnActivateVoiceControl.setOnClickListener(btnActivateVoiceControlListener);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
            Intent i = new Intent(this, Settings.class);
            i.putExtra(CommandManager.DEVICE_LIST, (Serializable) getDeviceList());
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //listeners
    View.OnClickListener btnActivateVoiceControlListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            // Start the activity, the intent will be populated with the speech text
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }
    };

    View.OnClickListener btnSelectDBListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<Device> devices = getDeviceList();
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            progressDialog = ProgressDialog.show(MainActivity.this, "Getting data...", "Please wait...");
            new StatusProvider(getApplicationContext(), MainActivity.this).execute(devices.toArray(new Device[devices.size()]));
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

            List<Device> devices = speech2CommandTranslator.GetDevicesForSpeech(spokenText);

            String s = "";
            if (devices.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Command '" + spokenText + "' not recognized!", Toast.LENGTH_SHORT).show();
            } else {

                for (Device dev : devices) {
                    s += dev.Name + " " + dev.Value + " \n";
                }
                updateDevices(devices);
                //new StatusUpdater(getApplicationContext(), this).execute(devices.toArray(new Device[devices.size()]));
            }

            tvStatus.setText(s);

            // Do something with spokenText
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void updateDevices(List<Device> devices) {
        if (devices != null) {
            progressDialog = ProgressDialog.show(MainActivity.this, "Updating devices...", "Please wait...");
            new StatusUpdater(getApplicationContext(), this).execute(devices.toArray(new Device[devices.size()]));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnSelectDB.performClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //btnSelectDB.performClick();
    }

    //interfaces
    public void GetValueReturnedByStatusUpdater(String s) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        String msg;
        switch (s) {
            case "OK":
                msg = "Devices updated successfully";
                break;
            case "NO_PARAMS":
                msg = "No devices passed to updater";
                break;
            case "FAIL":
                msg = "Unable to update devices";
                break;
            default:
                msg = "Other problem";
                break;
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void GetValueReturnedByStatusProvider(List<Device> devices) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        setDeviceList(devices);

        if (!isDeviceFragmentAdded && !isFinishing() && findViewById(R.id.frDevicesMain) != null && getFragmentManager().findFragmentByTag("FR_TAG") == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frDevicesMain, devicesMainFragment, "FR_TAG" );
            fragmentTransaction.commitAllowingStateLoss();
            isDeviceFragmentAdded = true;
        }else{
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.detach(devicesMainFragment);
            fragmentTransaction.attach(devicesMainFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    public List<Device> getDeviceList(){
        return commandManager.DeviceList;
    }

    public void setDeviceList(List<Device> list){
        commandManager.setDeviceList(list);
    }
}
