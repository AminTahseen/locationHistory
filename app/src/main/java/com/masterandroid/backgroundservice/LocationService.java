package com.masterandroid.backgroundservice;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.masterandroid.backgroundservice.retrofit.ApiClient;
import com.masterandroid.backgroundservice.retrofit.ApiInterface;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationService extends Service {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    FirebaseUser currentUser;

    place details;
    static CountDownTimer countDownTimer = null;
    List<place> place_detailsArrayList;
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null && locationResult.getLastLocation() != null) {
                double longitude =  locationResult.getLastLocation().getLongitude();
                double latitude = locationResult.getLastLocation().getLatitude();
                Log.d ("LOCATION_UPDATE",latitude+","+longitude);
                details= getGeocodingDetails(longitude,latitude);
               // getDetailsFromAPI(latitude+","+longitude,"AIzaSyDazjxsJFdohTwZllHdMsacB4P9luVjqyE");
                storeDataInDatabase(details,latitude,longitude);
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
        // 60*30*1000 = 30 min
        // 60*10*1000 = 10 min
        // 50000 = 50 seconds
        // 10000 = 10 seconds
        // 60000 = 60 seconds

        // Try Increasing countDownInterval
        countDownTimer = new CountDownTimer( 60*10*1000, 1000) {
            public void onTick(long millisUntilFinished)
            {
                String left=Long.toString(millisUntilFinished);
                Log.d("Service Time Interval ",left);

                LocationRequest locationRequest = new LocationRequest();

                // Try adjusting the location.setFastestInterval
                /*
                50*100 = 5,000 = 5 Seconds
                100*100 = 10,000 = 10 Seconds
                100*400 = 40,000 = 40 Seconds
                100*600 = 60,000 = 60 Seconds
                100*900 = 90,000 = 90 Seconds

                 */
                locationRequest.setInterval(5000);
                locationRequest.setFastestInterval(100*400);
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
            }
        };
        countDownTimer.start();
    }


    public void stopLocation(){
        LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();
      countDownTimer.cancel();

    }

    public void storeDataInDatabase(place obj, double lat, double lng)
    {
        getPlaceSearchDetails(lat,lng,obj.getPlaceAddress(),
                "textquery",
                "photos,formatted_address,name,opening_hours,rating,types",
                "circle:2000@"+lat+","+lng,
                "AIzaSyDazjxsJFdohTwZllHdMsacB4P9luVjqyE");


    }
    public void storeData(String UserId, String name, String address,String type,double latitude, double longitude, String VisitStatus)
    {

        HashMap<String, String> params = new HashMap<>();

        params.put("userId",UserId);
        params.put("placeLatitude",String.valueOf(latitude));
        params.put("placeLongitude",String.valueOf(longitude));
        params.put("placeAddress",address);
        params.put("placeName",name);
        params.put("placeType",type);
        params.put("visitStatus",VisitStatus);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_LIST, params, CODE_POST_REQUEST);
        request.execute();

    }

//commit

    public void getPlaceSearchDetails(double latitude, double longitude, String input, String inputtime, String fields,String location,String key){
        place_detailsArrayList=new ArrayList<>();
        final ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call <ResponseModel> call=apiInterface.getPlaceSearch(input,inputtime,fields,location,key);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful())
                {
                    //place myPlace= new place();
                    place_detailsArrayList=response.body().getCandidates();
                    for(int i=0;i<place_detailsArrayList.size();i++)
                    {
                        String name=place_detailsArrayList.get(i).getPlaceName();
                        String address=place_detailsArrayList.get(i).getPlaceAddress();
                       // Log.d("API Success",place_detailsArrayList.get(i).getPlaceName());
                        String type= place_detailsArrayList.get(i).getPlaceType().toString();
                        Log.d("Full Details ",name+' '+address+' '+type);
                       // FirebaseAuth mAuth=FirebaseAuth.getInstance();
                     //   currentUser = mAuth.getCurrentUser();

                        storeData("GuzFS0EjtBSwuRXBuRfhFN8ZSfm1",name,address,type,latitude,longitude,"pending");

                    }
                }
                else{

                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d("onFailure Response: " , t.getMessage());
            }
        });
    }

    private place getGeocodingDetails(double Longitude, double Latitude){
        Geocoder geocoder;
        place completeDetails=null;
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

            completeDetails= new place("GuzFS0EjtBSwuRXBuRfhFN8ZSfm1",Latitude,Longitude,address,"pending");
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

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;
        // comment
        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the herolist after every operation
                    //so we get an updated list
                    //we will create this method right now it is commented
                    //because we haven't created it yet
                    //refreshList(object.getJSONArray("myLists"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);

            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
}
