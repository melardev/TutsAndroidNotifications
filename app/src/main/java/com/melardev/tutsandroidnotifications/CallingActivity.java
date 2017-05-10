package com.melardev.tutsandroidnotifications;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by melardev on 5/16/2016.
 */
public class CallingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calling_layout);
        TextView txtContact = (TextView) findViewById(R.id.txt_call);

        Bundle extras = getIntent().getExtras();
        String contactName = extras.getString("contactName");
        txtContact.setText("Calling to " + contactName);
    }
}
