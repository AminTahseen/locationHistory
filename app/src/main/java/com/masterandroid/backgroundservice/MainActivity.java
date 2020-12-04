package com.masterandroid.backgroundservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static CountDownTimer countDownTimer = null;

    Button start;
    Button stop;
    TextView Details;
    Button show;
    final int REQUEST_CODE_LOCATION_PERMISSION=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start= findViewById(R.id.startService);
        stop= findViewById(R.id.stopService);
        Details= findViewById(R.id.details);
        show= findViewById(R.id.show);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION
                    );
                }
                else {
                    startLocation();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocation();

                //for distance between two latlngs
//                double earthRadius = 6371; //kilometers
//                double dLat = Math.toRadians(24.343432-24.769594);
//                double dLng = Math.toRadians(79.478738-79.573627);
//                double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
//                        Math.cos(Math.toRadians(24.769594)) * Math.cos(Math.toRadians(24.343432)) *
//                                Math.sin(dLng/2) * Math.sin(dLng/2);
//                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
//                float dist = (float) (earthRadius * c);
//
//                stop.setText(String.valueOf(dist));
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences= getSharedPreferences("Details",Context.MODE_PRIVATE);
                String value= sharedPreferences.getString("AddressList","abc");
                Details.setText(value);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startLocation();
            }
            else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean isLocationServiceRunning(){
        ActivityManager activityManager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager!=null){
            for (ActivityManager.RunningServiceInfo service:
                    activityManager.getRunningServices(Integer.MAX_VALUE)){
                if(LocationService.class.getName().equals(service.service.getClassName())){
                    if(service.foreground){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void startLocation(){
        if(!isLocationServiceRunning()){
            Intent intent= new Intent(getApplicationContext(),LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this,"Started",Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLocation(){
        if(isLocationServiceRunning()){
            Intent intent= new Intent(getApplicationContext(),LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this,"Stoped",Toast.LENGTH_SHORT).show();
        }
    }

    private void Notification(){
        String channelId = "location notification channel";
        NotificationCompat.Builder builder;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder = new NotificationCompat.Builder(
                getApplicationContext(),
                channelId
        );

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Location Service");
        builder.setDefaults(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentText("running");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelId, "Location Service",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                notificationChannel.setDescription("This channel is used by location service");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

    }
}