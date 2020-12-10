package com.masterandroid.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.masterandroid.backgroundservice.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewHistoryRecycler extends AppCompatActivity {

    RecyclerView historyRecycler;
    Button refresh_history;
    List<place> historyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history_recycler);
        historyRecycler=findViewById(R.id.historyRecycler);
        historyRecycler.setLayoutManager(new LinearLayoutManager(this));
        refresh_history=findViewById(R.id.refresh_history);
        historyList=new ArrayList<>();
        /*
        * Los Angeles
        * 1174 E 59th Pl, Los Angeles, CA 90001, USA
        * 33.985805, -118.2541117
        * */
        refresh_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  place visit=new place(33.985805,-118.2541117,
                        "1174 E 59th Pl, Los Angeles, CA 90001, USA","Los Angeles");
                place visit2=new place(32.455695,-118.2534223,
                        "1174 E 59th Pl, Amsterdam, CA 90001, USA","Amsterdam");
                historyList.add(visit);
                historyList.add(visit2);

                MainAdapter adapter=new MainAdapter(historyList,ViewHistoryRecycler.this);
                historyRecycler.setAdapter(adapter);
*/
            }
        });
    }
}