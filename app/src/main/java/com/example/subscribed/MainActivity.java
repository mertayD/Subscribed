package com.example.subscribed;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.example.subscribed.Adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ImageButton addSubscription;
    private TextView editOption;
    static List<Subscription> subscriptions = new ArrayList<Subscription>();;


    Date popup_firstbill_date;
    Date popup_cycle_date;
    int popup_duration;
    Switch popup_notify_switch;

    public static int day_input;
    public static int month_input;
    public static int year_input;
    public static int index_sent_to_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_recycler_view);
        Intent i = getIntent();
        if(i.hasExtra("sampleObject"))
        {
            Subscription to_add = (Subscription) i.getSerializableExtra("sampleObject");
            if(to_add == null)
            {
                Log.e("NULL NEW SUBS", " NULL ");
            }
            else
            {
                subscriptions.add(to_add);

                generateRecylerView(subscriptions);

                Log.e("NOT NULL NEW SUBS", "NOT NULL ");
            }

            //subscriptions.add(to_add);
        }

        else if(i.hasExtra("editedObject")){
            Subscription edited_object = (Subscription) i.getSerializableExtra("editedObject");
            if(edited_object == null)
            {
                Log.e("Err Occured After Edit", " Keeping Old Version ");
            }
            else
            {
                Log.e("INDEX AFTER", "" + index_sent_to_edit);

                subscriptions.remove(index_sent_to_edit);

                subscriptions.add(index_sent_to_edit, edited_object);

                generateRecylerView(subscriptions);

                Log.v("Returned Valid", "NOT NULL");
            }
        }
        else{
            Log.e("SUBS Returned", "NULL");
        }

        addSubscription = findViewById(R.id.add_button);
        addSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*

                Date created = new Date(2016, 10, 22);
                Subscription toBeAdded  = new Subscription("NETFLIX",
                        "Entertainment", created,
                        5, Subscription.durationIdentifier.MONTH,
                        10, Subscription.durationIdentifier.DAY,
                        2,4,true );
                subscriptions.add(toBeAdded);
                //generateRecylerView(subscriptions);

                 */

                Intent to_navigate = new Intent(MainActivity.this, ConfigureSubscription.class);
                startActivity(to_navigate);


                //onButtonShowPopupWindowClick();
            }
        });

        //DialogFragment newFragment = new DatePickerFragment();
        //newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    void generateRecylerView(final List<Subscription> list)
    {
        itemAdapter = new ItemAdapter(this, list, new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent configure = new Intent(MainActivity.this, ConfigureSubscription.class);
                configure.putExtra("currentSubscription", subscriptions.get(position));
                index_sent_to_edit = position;
                Log.e("INDEX BEFORE", "" + index_sent_to_edit);
                Log.e("HEREEEEEE", "HERE");
                startActivity(configure);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);
    }

    /*
    public void onButtonShowPopupWindowClick() {


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.add_configure_sub_popup, null);
        //Initialize elements


        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);

        popup_cycle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater1 = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final View popupView = inflater1.inflate(R.layout.renewal_picker, null);
                final PopupWindow newWindow = new PopupWindow(popupView, width, height, focusable);

                newWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);

            }
        });
        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
*/


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            day_input = day;
            month_input = month;
            year_input = year;
            Log.e("DATE PICKED", day + "." + month + "." + year);
        }
    }
}
