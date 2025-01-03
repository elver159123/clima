package com.sigfred.clima.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.sigfred.clima.Domains.Hourly;
import com.sigfred.clima.R;

import java.util.ArrayList;

public class HourlyAdapters extends RecyclerView.Adapter<HourlyAdapters.ViewHolder> {
    private final ArrayList<Hourly> items;
    private final Context context;

    public HourlyAdapters(ArrayList<Hourly> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hourly, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hourly item = items.get(position);
        holder.hourTxt.setText(item.getHour());
        holder.tempTxt.setText(item.getTemp() + "°");

        int drawableResourceId = holder.itemView.getResources()
                .getIdentifier(items.get(position).getPicPath(), "drawable", holder.itemView.getContext().getPackageName());


        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hourTxt, tempTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hourTxt = itemView.findViewById(R.id.hourtxt);
            tempTxt = itemView.findViewById(R.id.tempTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
