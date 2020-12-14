package com.masterandroid.backgroundservice.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.masterandroid.backgroundservice.R;
import com.masterandroid.backgroundservice.nearbyPlace;

import java.util.List;

public class nearbyAdapter  extends RecyclerView.Adapter<nearbyAdapter.ViewHolder> {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private List<nearbyPlace> nearbyList;
    private Activity activity;

    public nearbyAdapter(List<nearbyPlace> nearbyList, Activity activity) {
        this.nearbyList = nearbyList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public nearbyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull nearbyAdapter.ViewHolder holder, int position) {
        nearbyPlace data=nearbyList.get(position);
        holder.name.setText(data.getPlaceName());
        holder.latLng.setText(Double.toString(data.getPlaceLatitude())+","+Double.toString(data.getPlaceLongitude()));
    }

    @Override
    public int getItemCount() {
        return nearbyList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView name,latLng;
        Button yesBtn,noBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            latLng=itemView.findViewById(R.id.latlng);
            yesBtn=itemView.findViewById(R.id.yesBtn);
            noBtn=itemView.findViewById(R.id.noBtn);
        }
    }
}
