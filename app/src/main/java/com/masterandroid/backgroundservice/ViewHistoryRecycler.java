package com.masterandroid.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.masterandroid.backgroundservice.adapter.MainAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ViewHistoryRecycler extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    RecyclerView historyRecycler;
    Button refresh_history;
    List<place> historyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser= mAuth.getCurrentUser();
        setContentView(R.layout.activity_view_history_recycler);
        historyRecycler=findViewById(R.id.historyRecycler);
        historyRecycler.setLayoutManager(new LinearLayoutManager(this));
        refresh_history=findViewById(R.id.refresh_history);
        historyList=new ArrayList<>();

        refresh_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readHistory("GuzFS0EjtBSwuRXBuRfhFN8ZSfm1");
            }
        });
    }
    private void readHistory(String userId) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_LIST+userId, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshHistoryList(JSONArray heroes) throws JSONException {
        //clearing previous heroes
       // historyList.clear();

        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < heroes.length(); i++) {
            //getting each hero object
            JSONObject obj = heroes.getJSONObject(i);

            //adding the hero to the list
            // push
            String type= obj.getString("placeType");
            String str[] = type.split(",");
            ArrayList<String> placeType = new ArrayList<String>(Arrays.asList(str));
            place p=new place(
                    obj.getInt("placeId"),
                    obj.getString("userId"),
                    obj.getDouble("placeLongitude"),
                    obj.getDouble("placeLatitude"),
                    obj.getString("placeAddress"),
                    obj.getString("placeName"),
                    placeType,
                    obj.getString("visitStatus"),
                    obj.getString("placeTime")
                    );
            Log.d("place",p.toString());
            if(historyList.contains(p.getPlaceName()))
            {

            }else
                {
                    historyList.add(p);
                }
            //creating the adapter and setting it to the recyclerview
        }

        Log.d("List Size ",Integer.toString(historyList.size()));
        MainAdapter adapter = new MainAdapter(historyList,ViewHistoryRecycler.this);
        historyRecycler.setAdapter(adapter);

    }


    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;
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
                    refreshHistoryList(object.getJSONArray("lists"));
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