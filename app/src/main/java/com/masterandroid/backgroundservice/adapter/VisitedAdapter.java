package com.masterandroid.backgroundservice.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.masterandroid.backgroundservice.Api;
import com.masterandroid.backgroundservice.R;
import com.masterandroid.backgroundservice.RequestHandler;
import com.masterandroid.backgroundservice.place;
import com.masterandroid.backgroundservice.showPlaceNearby;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class VisitedAdapter extends RecyclerView.Adapter<VisitedAdapter.ViewHolder> {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private List<place> historyList;
    private Activity activity;

    public VisitedAdapter(List<place> historyList, Activity activity) {
        this.historyList = historyList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.visited_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        place data=historyList.get(position);
        holder.name.setText(data.getPlaceName());
        holder.address.setText(data.getPlaceAddress());
        if(data.getPlaceType()==null)
        {

        }else
            {
                holder.type.setText(data.getPlaceType().toString());
            }

    }
    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView name,address,type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
            type=itemView.findViewById(R.id.type);
        }
    }

}
