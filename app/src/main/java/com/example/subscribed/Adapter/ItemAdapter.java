package com.example.subscribed.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.subscribed.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private String itemName;
    private Context context;
    int renewalDay;
    int renewalMonth;
    int renewalPeriod;
    int cancellationPeriod;
    int startDay;
    int startMonth;
    int startYear;

    public ItemAdapter(Context context, String itemName, int renewalDay, int renewalMonth,
                       int renewalPeriod,
                       int cancellationPeriod,
                       int startDay,
                       int startMonth,
                       int startYear){
        this.context = context;
        this.itemName = itemName;
        this.renewalDay = renewalDay;
        this.renewalMonth = renewalMonth;
        this.renewalPeriod = renewalPeriod;
        this.cancellationPeriod = cancellationPeriod;
        this.startDay = startDay;
        this.startMonth = startMonth;
        this.startYear = startYear;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        private TextView item_name_tv;
        private TextView days_remained_tv;
        //private ImageView item_image;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            item_name_tv = mView.findViewById(R.id.item_name_tv);
            days_remained_tv = mView.findViewById(R.id.days_remained_tv);
            //item_image = mView.findViewById(R.id.item_image);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.item_name_tv.setText(itemName);
        String text = "" + renewalDay;
        holder.days_remained_tv.setText(text);
    }

    @Override
    public int getItemCount() {

        return 20;
    }
}
