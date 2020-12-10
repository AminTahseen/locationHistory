package com.masterandroid.backgroundservice.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.masterandroid.backgroundservice.R;
import com.masterandroid.backgroundservice.place;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<place> historyList;
    private Activity activity;
    public MainAdapter(List<place> List, Activity activity) {
        this.historyList = List;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        place data=historyList.get(position);
        holder.address.setText(data.getPlaceAddress());
        holder.latlng.setText(Double.toString(data.getPlaceLatitude())+", "+Double.toString(data.getPlaceLongitude()));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView city;
        TextView address;
        TextView latlng;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            city=itemView.findViewById(R.id.city);
            address=itemView.findViewById(R.id.address);
            latlng=itemView.findViewById(R.id.latlng);

        }
    }
}
