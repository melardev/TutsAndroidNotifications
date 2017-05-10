package com.melardev.tutsandroidnotifications;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void demoToastBasic(View view) {
        Toast.makeText(MainActivity.this, "Default text LENGTH_SHORT", Toast.LENGTH_SHORT).show();
    }

    public void demoToastCustomLayout(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View customToast = inflater.inflate(R.layout.custom_toast, null);
        TextView txt = (TextView) customToast.findViewById(R.id.txt_custom_toast);
        txt.setText("My Custom text");
        Toast toast = new Toast(MainActivity.this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customToast);
        toast.show();
    }

    public void demoAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("my Title").setMessage("my Message").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeToast("Ok Button pressed");
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeToast("Cancel Button Pressed");
            }
        });
        builder.create().show();
    }

    public void demoDialogFragment(View view) {
        makeToast("This demo is under my other project in Github https://github.com/melardev/TutsAndroidUI");
    }

    public void demoPlaySoundNotification(View view) {
        Uri uriRing = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(MainActivity.this, uriRing);
        ringtone.play();
    }

    public void demoNotifWithActions(View view) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intentToMain = new Intent(MainActivity.this, MainActivity.class);
        Intent intentToCall = new Intent(MainActivity.this, CallingActivity.class);
        intentToCall.putExtra("contactName", "girlfriend");
        intentToCall.setAction("com.tutorial.android.melardev.tutorials.action.CALL");
        //intentToCall.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent piToMain = PendingIntent.getActivity(MainActivity.this, 0, intentToMain, 0);
        PendingIntent piToCall = PendingIntent.getActivity(MainActivity.this, 0, intentToCall, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("new contact connected")
                .setContentText("girlfriend is connected")
                .setSound(soundUri) //specifies the sound to be played
                .setLights(Color.RED, 1000, 1000) //sets the color of the LED , ON-state duration , OFF-state duration
                .setVibrate(new long[]{250, 250, 250, 250}) //vibrates the phone with the given pattern
                //make sure to request the android.permission.VIBRATE permission in the manifest
                .setContentIntent(piToMain)
                .addAction(android.R.drawable.ic_menu_call, "call now", piToCall)
                .setVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void demoHeadsUp(View view) {
        //To be heads up , the process is the same but setPriority should be called with at leas
        //PRIORITY_HIGHT , and the notification should use either sound or vibration
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentTitle("Title")
                .setContentText("the text")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
                .setPriority(Notification.PRIORITY_MAX); //requires API 16

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    public void demoProgressDialog(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading , please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 4000L);
    }

    public void demoProgressDlgWithBar(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        progressDialog.setMessage("Loading , please wait ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);

        progressDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.setProgress(progress++);
                if (progress >= 100) {
                    progress = 0;
                    progressDialog.dismiss();
                    return;
                }
                handler.postDelayed(this, 10L);
            }
        }, 10L);
    }

    public void demoSnackBar(View view) {
        makeToast("This demo is under my other project in Github https://github.com/melardev/TutsAndroidUI");
    }

    public void demoFirebase(View view) {
        makeToast("This demo is under my other project in Github   https://github.com/melardev/TutsAndroidFirebase");

    }

    private void makeToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public void demoBasicNotification(View view) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setColor(Color.BLACK)
                .setContentTitle("my title")
                .setContentText("my text")
                .setSound(soundUri) //specifies the sound to be played
                .setLights(Color.RED, 1000, 1000) //sets the color of the LED , ON-state duration , OFF-state duration
                .setVibrate(new long[]{250, 250, 250, 250}); //vibrates the phone with the given pattern
        //make sure to request the android.permission.VIBRATE permission in the manifest

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
        //builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
    }
}
