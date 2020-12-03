package com.masterandroid.backgroundservice;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service {

    static CountDownTimer countDownTimer = null;
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null && locationResult.getLastLocation() != null) {
                double longitude = locationResult.getLastLocation().getLongitude();
                double latitude = locationResult.getLastLocation().getLatitude();
                Log.d ("LOCATION_UPDATE",latitude+","+longitude);
            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet Implemented");
    }

    private void startLocation()
    {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        // 60*1*1000 = 1 min
        // 50000 = 50 seconds
        // 10000 = 10 seconds;
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished)
            {
                String channelId = "location notification channel";
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Intent resultIntent = new Intent();
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        getApplicationContext(), 0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        getApplicationContext(),
                        channelId
                );

                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("Location Service");
                builder.setDefaults(NotificationCompat.DEFAULT_ALL);
                builder.setContentText("running");
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(false);
                builder.setPriority(NotificationCompat.PRIORITY_MAX);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (notificationManager != null && notificationManager.getNotificationChannel(channelId) == null) {
                        NotificationChannel notificationChannel = new NotificationChannel(
                                channelId, "Location Service",
                                NotificationManager.IMPORTANCE_HIGH
                        );
                        notificationChannel.setDescription("This channel is used by location service");
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                }
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(5000);
                locationRequest.setFastestInterval(60*100);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                if (ActivityCompat.checkSelfPermission(LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationService.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                LocationServices.getFusedLocationProviderClient(LocationService.this)
                        .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                startForeground(Constants.LOCATION_SERVICE_ID,builder.build());

            }
            public void onFinish()
            {
                Log.d("done!", "done!");
                stopLocation();
            }
        };
        countDownTimer.start();


    }

    private void stopLocation(){
        LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();
        countDownTimer.cancel();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            String action= intent.getAction();
            if(action!=null){
                if(action.equals(Constants.ACTION_START_LOCATION_SERVICE)){
                    startLocation();
                }
                else if(action.equals(Constants.ACTION_STOP_LOCATION_SERVICE)){
                    stopLocation();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
