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
import com.example.subscribed.Subscription;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    private List<Subscription> list;
    private Context context;
    private OnItemClickListener listener;


    public ItemAdapter(Context context, List<Subscription> list, OnItemClickListener listener)
    {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final View mView;

        private TextView item_name_tv;
        private TextView days_remained_tv;
        private ImageView item_image;
        private TextView price_tv;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            item_name_tv = mView.findViewById(R.id.item_name_tv);
            days_remained_tv = mView.findViewById(R.id.days_remained_tv);
            item_image = mView.findViewById(R.id.item_image);
            price_tv = mView.findViewById(R.id.subscription_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
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

        Subscription current = list.get(position);
        holder.item_name_tv.setText(current.getName());
        int cycle_time = current.getCycle_time();
        String indicate_multiple = "";
        if( cycle_time != 1)
        {
            indicate_multiple = "s";
        }
        String text = "Every " + cycle_time + " " + current.getCycle_identified() + indicate_multiple + " DATE " + current.getSubscriptionCeated().getMonth() +  "/" + current.getSubscriptionCeated().getDay() + "/" + current.getSubscriptionCeated().getYear();
        holder.days_remained_tv.setText(text);
        holder.item_image.setImageResource(R.drawable.netflix_icon);
        holder.price_tv.setText("$" + current.getPrice());
        //list.get(position).to_string();
        Log.e("CYCLE IDENTIFIER = ", "" + list.get(position).getCycle_identified());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}
