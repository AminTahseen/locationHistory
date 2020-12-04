package com.masterandroid.backgroundservice;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationService extends Service {

    String details;
    List<String> visitAddress;
    static CountDownTimer countDownTimer = null;
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null && locationResult.getLastLocation() != null) {
                double longitude = locationResult.getLastLocation().getLongitude();
                double latitude = locationResult.getLastLocation().getLatitude();
                Log.d ("LOCATION_UPDATE",latitude+","+longitude);
                details= getAddress(longitude,latitude);
                visitAddress.add(details);
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

        // Try Increasing countDownInterval
        countDownTimer = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished)
            {
                String left=Long.toString(millisUntilFinished);
                Log.d("Service Time Interval ",left);

                LocationRequest locationRequest = new LocationRequest();

                // Try adjusting the location.setFastestInterval
                /*
                50*100 = 5,000 = 5 Seconds
                100*100 = 10,000 = 10 Seconds
                 */
                locationRequest.setInterval(5000);
                locationRequest.setFastestInterval(100*100);
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
            }
            public void onFinish()
            {
                Log.d("done!", "done!");
                stopLocation();
                Gson gson= new Gson();
                String jsonText= gson.toJson(visitAddress);
                SharedPreferences sharedPreferences= getSharedPreferences("Details", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("AddressList",jsonText);
                editor.apply();
            }
        };

        countDownTimer.start();
        visitAddress= new ArrayList<>();

    }


    private void stopLocation(){
        LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();
        countDownTimer.cancel();

    }

    private String getAddress(double Longitude, double Latitude){
        Geocoder geocoder;
        String completeDetails="";
        List<Address> addresses= new ArrayList<>();
        geocoder=new Geocoder(this, Locale.getDefault());

        try {
            addresses= geocoder.getFromLocation(Latitude,Longitude,1);
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            completeDetails= address;
            Log.d("LOCATION Push","Push In DB");
            Log.d("LOCATION_DETAILS",Latitude+", "+Longitude+", "+knownName+", "+address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return completeDetails;
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
