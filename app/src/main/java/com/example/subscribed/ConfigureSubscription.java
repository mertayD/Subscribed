package com.example.subscribed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.Month;
import java.util.Date;

public class ConfigureSubscription extends AppCompatActivity {

    //Pop Up screen elements
    EditText popup_name_et;
    EditText popup_desc_et;
    EditText price_et;
    TextView popup_color_et;
    TextView popup_cycle_tv;
    TextView popup_first_bill;
    String[] day_identifier_values;
    String[] months;
    String[] months_to_write;
    Button done_button;
    Subscription to_add;
    Boolean is_edit = false; //Paramter to check if the subs is in edit mode
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_subscription);
        popup_name_et = findViewById(R.id.name_enter_et);
        popup_desc_et = findViewById(R.id.enter_description_et);
        popup_desc_et.addTextChangedListener(new ConfigureSubscription.MyTextWatcher(1));
        popup_name_et.addTextChangedListener(new ConfigureSubscription.MyTextWatcher(0));
        popup_cycle_tv = findViewById(R.id.enter_cycle_tv);
        popup_first_bill = findViewById(R.id.enter_firstbill_tv);
        done_button = findViewById(R.id.done_button);
        price_et = findViewById(R.id.price_tag_et);
        price_et. setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        to_add = new Subscription();
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_add.setName(getName());
                to_add.setDescription(getDescribtion());
                to_add.setColor(getColor());
                to_add.setCycle_identified(getCycleIdentifier());
                to_add.setCycle_time(getCycleInt()); // Just to test actual calculation has to be done in adapter
                to_add.setSubscriptionCeated(getFirstBillDate());
                to_add.setPrice(get_price());
                // add a little more to make sure everything is passed.
                // pass the subscribtion
                Intent i = new Intent(ConfigureSubscription.this, MainActivity.class);
                if(!is_edit)
                {
                    i.putExtra("sampleObject", to_add);
                }
                else{
                    i.putExtra("editedObject", to_add);
                }
                startActivity(i);
            }
        });

        popup_first_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickShowPopupWindowFirstBill();
            }
        });
        popup_cycle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickShowPopupWindowCycle();
            }
        });

        price_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    String text = price_et.getText().toString();
                    if(text.length() == 1) {
                        text = "$0.00";
                    }
                    else if(!text.contains("."))
                    {
                        text += ".00";
                    }
                    price_et.setText(text);
                    price_et.setTextColor(getResources().getColor(R.color.customGray));
                }
                else{
                    String text = price_et.getText().toString();

                    if(text.equals("$0.00"))
                    {
                        text = "$";
                    }
                    price_et.setText(text);

                    price_et.setTextColor(getResources().getColor(R.color.colorWhite));
                }
            }
        });

        price_et.addTextChangedListener(new TextWatcher() {
            String previous_text = "";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                previous_text = s.toString();
                Log.e("BEFORE", s.toString() + " START " + start + " COUNT " + count + " AFTER" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.e("AFTER", s.toString() + " START " + start + " COUNT " + count + "BEFORE " + before);
                String text = s.toString();

                if(start == 0 && ((count == 0 && before == 1) || count == 1 && before == 0))
                {
                    price_et.setText(previous_text);
                }
                int first_occurence = text.indexOf(".");
                int last_occurence = text.lastIndexOf(".");
                if(first_occurence != last_occurence)
                {
                    price_et.setText(previous_text);
                }


                /*

                if(!(text.length() > 0))
                {
                    //if for some reason text has length 0
                    price_et.setText("$");
                }

                else if (price_et.getText().charAt(0) != '$') {
                    price_et.setText("$"+text);
                }
                 */
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        day_identifier_values = new String[] {"Day(s)", "Week(s)", "Month(s)", "Year(s)"};
        months = new String[] {"Januray", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        months_to_write = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

        //WHEN USER GETS HEAR BY CLICKING SUBSCRIPTION
        Intent i = getIntent();
        if(i.hasExtra("currentSubscription"))
        {
            Subscription current = (Subscription) i.getSerializableExtra("currentSubscription");
            if(current == null)
            {
                Log.e("NULL SUBS", " NULL ");
            }
            else
            {
                //WORK ON THIS ONE, GET CYLE IDENTIFIED SHOULD BE GETTING FROM DAY_IDENTIFIER_VALUES
                current.to_string();
                popup_name_et.setText(current.getName());
                popup_desc_et.setText(current.getDescription());
                popup_cycle_tv.setText("Every " + current.getCycle_time() + " " + day_identifier_values[current.getCycle_identified().ordinal()] );
                popup_first_bill.setText("" + current.getSubscriptionCeated().getDate() + " " +  months_to_write[current.getSubscriptionCeated().getMonth()] + " " + (current.getSubscriptionCeated().getYear()) );
                price_et.setText("$" + current.getPrice());
                is_edit = true;
                Log.e("NOT NULL SUBS", "NOT NULL ");
            }
            //subscriptions.add(to_add);
        }

        else{
            Log.e("SAFAB SUBS", " NULL ");
        }

        price_et.clearFocus();

    }

    public class MyTextWatcher implements TextWatcher {
        boolean check = true;
        int editText = 0;
        MyTextWatcher(int editText){
            this.editText = editText;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(check){
                check = false;
                if(editText == 0)
                    popup_name_et.setText("");
                else
                    popup_desc_et.setText("");
            }
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    public void onButtonClickShowPopupWindowCycle() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.renewal_picker, null);

        //Initialize elements
        final NumberPicker number_picker = popupView.findViewById(R.id.number_picker_cycle_np);
        number_picker.setMinValue(1);
        number_picker.setMaxValue(31);
        number_picker.setValue(getCycleInt());


        final NumberPicker identifier_picker = popupView.findViewById(R.id.identifier_picker_cycle_np);
        identifier_picker.setMinValue(0);
        identifier_picker.setMaxValue(3);
        identifier_picker.setDisplayedValues(day_identifier_values);
        identifier_picker.setValue(getCycleIdentifier());

        number_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                 int value = number_picker.getValue();
                 setCycleText(value, true);
            }
        });


        identifier_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int value = identifier_picker.getValue();
                setCycleText(value, false);
            }
        });
        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(popup_cycle_tv, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    void setCycleText(int value, boolean number_picker)
    {
        String cycle_tv_text = popup_cycle_tv.getText().toString();
        String cycl_substr = "";

        if(number_picker)
        {

            if(cycle_tv_text.charAt(7) ==  ' ')
            {

                cycl_substr = "Every " + value + " " + cycle_tv_text.substring(8);
            }
            else
            {

                cycl_substr = "Every " + value + " " + cycle_tv_text.substring(9);

            }

        }
        else
        {

            if(getCycleInt() > 9)
            {
                cycl_substr = cycle_tv_text.substring(0, 9);
            }
            else
            {
                cycl_substr = cycle_tv_text.substring(0, 8);
            }

            cycl_substr = cycl_substr + day_identifier_values[value];
        }

        popup_cycle_tv.setText(cycl_substr);

    }

    void setFirstBillText(int switched_identifier, int value)
    {
        String first_bill_tv_text = popup_first_bill.getText().toString();
        String first_bill_substr = "";
        String holder;
        //Day Changed
        if(switched_identifier == 0)
        {
            if(getFirstBillDay() > 9)
            {
                first_bill_substr = first_bill_tv_text.substring(3);
            }
            else{
                first_bill_substr = first_bill_tv_text.substring(2);
            }

            popup_first_bill.setText(value + " " + first_bill_substr);
        }
        else if(switched_identifier == 1)
        {
            first_bill_substr = "" + getFirstBillDay() + " " + months_to_write[value] + " " + getFirstBillYear();
            popup_first_bill.setText(first_bill_substr);
        }
        else if( switched_identifier == 2)
        {
            first_bill_substr = "" + getFirstBillDay() + " " + months_to_write[getFirstBillMonth()] + " " + value;
            popup_first_bill.setText(first_bill_substr);
        }
    }

    int getCycleInt()
    {
        String cycle_tv_text = popup_cycle_tv.getText().toString();
        String to_return = "";
        if(cycle_tv_text.charAt(7) ==  ' ')
        {
            to_return = "" + cycle_tv_text.charAt(6);
        }
        else
        {
            to_return = cycle_tv_text.substring(6,8);
        }
        return Integer.parseInt(to_return);
    }

    int getCycleIdentifier(){

        String cycle_tv_text = popup_cycle_tv.getText().toString();
        int to_return = 0;
        String to_return_string = "";
        if(cycle_tv_text.charAt(7) ==  ' ')
        {
            to_return_string = cycle_tv_text.substring(8);
        }
        else
        {
            to_return_string = cycle_tv_text.substring(9);
        }
        Log.e("IDENTIFIER", to_return_string);
        if(to_return_string.equals("Day(s)"))
        {
            to_return = 0;
        }
        else if(to_return_string.equals("Week(s)"))
        {
            to_return = 1;
        }
        else if(to_return_string.equals("Month(s)"))
        {
            to_return = 2;
        }
        else
        {
            to_return = 3;
        }
        return to_return;
    }

    int getFirstBillDay()
    {
        String first_bill_tv_text = popup_first_bill.getText().toString();
        int to_return = 0;
        String holder = "";
        if(first_bill_tv_text.charAt(1) == ' ')
        {
            holder = "" + first_bill_tv_text.charAt(0);
            to_return = Integer.parseInt(holder);

        }
        else{
            holder = first_bill_tv_text.substring(0,2);
            to_return = Integer.parseInt(holder);
        }
        return to_return;
    }

    int getFirstBillMonth()
    {
        String first_bill_tv_text = popup_first_bill.getText().toString();
        int to_return = 0;
        String holder = "";
        int day_digit = getFirstBillDay();
        int index = 0;
        if(day_digit > 9)
        {
            index = 3;
        }
        else
        {
            index = 2;
        }

        while( first_bill_tv_text.charAt(index) != ' ' )
        {
            Log.e("CHAR ", holder);
            holder += first_bill_tv_text.charAt(index);
            index++;
        }


        switch (holder)
        {
            case "Jan":
                to_return = 0;
                break;
            case "Feb":
                to_return = 1;
                break;
            case "Mar":
                to_return = 2;
                break;
            case "Apr":
                to_return = 3;
                break;
            case "May":
                to_return = 4;
                break;
            case "June":
                to_return = 5;
                break;
            case "July":
                to_return = 6;
                break;
            case "Aug":
                to_return = 7;
                break;
            case "Sept":
                to_return = 8;
                break;
            case "Oct":
                to_return = 9;
                break;
            case "Nov":
                to_return = 10;
                break;
            case "Dec":
                to_return = 11;
                break;
             default:
                 to_return = 0;
                 break;
        }


        return to_return;
    }

    String getName()
    {
        String name = popup_name_et.getText().toString();
        return name;
    }

    String getDescribtion(){
        String desc = popup_desc_et.getText().toString();
        return desc;
    }

    int getColor(){
        //JUST FOR NOW IT RETURNS 0

        return 0;
    }

    Date getFirstBillDate(){

        int day = getFirstBillDay();
        int month = getFirstBillMonth();
        int year = getFirstBillYear();

        Date created = new Date(year, month, day);
        return created;
    }


    int getFirstBillYear()
    {
        String first_bill_tv_text = popup_first_bill.getText().toString();
        int to_return = 0;
        String holder = "";
        int day_digit = getFirstBillDay();
        int index = 0;
        if(day_digit > 9)
        {
            index = 3;
        }
        else
        {
            index = 2;
        }

        while( !isNumeric(first_bill_tv_text.charAt(index)) )
        {
            index++;
        }
        int i = 0;
        while (i < 4)
        {
            holder += first_bill_tv_text.charAt(index);
            i++;
            index++;
        }

        to_return = Integer.parseInt(holder);
        return to_return;
    }


    public void onButtonClickShowPopupWindowFirstBill() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.day_picker, null);

        //Initialize elements
        final NumberPicker month_picker = popupView.findViewById(R.id.month_picker_np);
        month_picker.setMinValue(0);
        month_picker.setMaxValue(11);
        month_picker.setDisplayedValues(months);
        month_picker.setValue(getFirstBillMonth());
        //month_picker.setValue(getCycleInt());
        //write a function to start the picker from the value that is already written


        final NumberPicker day_picker = popupView.findViewById(R.id.day_picker_np);
        day_picker.setMinValue(1);
        day_picker.setMaxValue(31);
        day_picker.setValue(getFirstBillDay());
        //identifier_picker.setValue(getCycleIdentifier());
        //Write a function to get days from tv

        final NumberPicker year_picker = popupView.findViewById(R.id.year_picker_np);
        year_picker.setMinValue(2000);
        year_picker.setMaxValue(2020);
        year_picker.setValue(getFirstBillYear());
        year_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int value = year_picker.getValue();
                setFirstBillText(2, value);

            }
        });


        month_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int value = month_picker.getValue();
                setFirstBillText(1, value);
            }
        });

        day_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int value = day_picker.getValue();
                setFirstBillText(0, value);

            }
        });

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(popup_cycle_tv, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    double get_price()
    {
        double price =0;
        double remainder=0;
        String price_str = price_et.getText().toString();
        int index_dot = price_str.indexOf(".");
        if(index_dot >=0 )
        {
            price = Integer.parseInt(price_str.substring(1,index_dot));
            remainder = Integer.parseInt(price_str.substring(index_dot+1));
            price += remainder/100;
        }
        else{
            price = Integer.parseInt(price_str.substring(1));
        }
        return price;
    }


    boolean isNumeric(char ch){
        char numeric[] = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for(int i = 0; i < numeric.length; i++)
        {
            if(ch == numeric[i])
                return true;
        }
        return false;
    }

}
