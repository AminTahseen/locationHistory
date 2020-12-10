package com.masterandroid.backgroundservice.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.masterandroid.backgroundservice.R;
import com.masterandroid.backgroundservice.place;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<place> historyList;
    private Activity activity;

    public MainAdapter(List<place> historyList, Activity activity) {
        this.historyList = historyList;
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
        holder.name.setText(data.getPlaceName());
        holder.address.setText(data.getPlaceAddress());
        if(data.getPlaceType()==null)
        {

        }else
            {
                holder.type.setText(data.getPlaceType().toString());
            }

        holder.yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Yes for "+data.getPlaceName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "No for "+data.getPlaceName(), Toast.LENGTH_SHORT).show();
                holder.editBtn.setVisibility(View.VISIBLE);
                holder.noBtn.setVisibility(View.GONE);
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Edit for "+data.getPlaceName(), Toast.LENGTH_SHORT).show();
                holder.editBtn.setVisibility(View.GONE);
                holder.noBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView name,address,type;
        Button yesBtn,noBtn,editBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
            type=itemView.findViewById(R.id.type);
            yesBtn=itemView.findViewById(R.id.yesBtn);
            noBtn=itemView.findViewById(R.id.noBtn);
            editBtn=itemView.findViewById(R.id.editBtn);
        }
    }
}
