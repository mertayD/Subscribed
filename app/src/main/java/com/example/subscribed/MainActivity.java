package com.example.subscribed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.subscribed.Adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ImageButton addSubscription;
    private TextView editOption;
    List<Subscription> subscriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subscriptions = new ArrayList<Subscription>();
        addSubscription = findViewById(R.id.add_button);
        addSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date created = new Date(2016, 10, 22);
                Subscription toBeAdded  = new Subscription("NETFLIX", "Entertainment", created, 5, Subscription.durationIdentifier.MONTH, 10, Subscription.durationIdentifier.DAY,2,4,true );
                subscriptions.add(toBeAdded);
            }
        });
        recyclerView = findViewById(R.id.main_recycler_view);
        itemAdapter = new ItemAdapter(this,subscriptions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);
    }
}
