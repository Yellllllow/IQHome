package com.dmm.iqhome;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static String TAG = "IQHOMETag";

    Button btnActivateVoiceControl;
    Button btnSelectDB;
    TextView tvStatus;

    private static final int SPEECH_REQUEST_CODE = 0;

    private CommandManager commandManager = new CommandManager();
    private Speech2CommandTranslator speech2CommandTranslator = new Speech2CommandTranslator(commandManager);
    private MessageSender messageSender = new MessageSender();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        btnActivateVoiceControl = (Button)findViewById(R.id.btnActivateVoiceControl);
        btnActivateVoiceControl.setOnClickListener(btnActivateVoiceControlListener);

        btnSelectDB = (Button)findViewById(R.id.btnSelectDB);
        btnSelectDB.setOnClickListener(btnSelectDBListener);

        tvStatus = (TextView)findViewById(R.id.tvStatus);
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

    //listeners
    View.OnClickListener btnActivateVoiceControlListener = new View.OnClickListener(){
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
            List<Device> devices = commandManager.DeviceList;
            new MessageReceiver(getApplicationContext()).execute(devices.toArray(new Device[devices.size()]));
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
            if(devices.isEmpty()){
                Toast.makeText(getApplicationContext(),"Command '" + spokenText + "' not recognized!",Toast.LENGTH_SHORT).show();
            }else{

                for (Device dev : devices) {
                    s+= dev.Name + " " + dev.Value + " \n";
                }
                messageSender.propagateMessagesToDevices(devices);
            }

            tvStatus.setText(s);

            // Do something with spokenText
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}
