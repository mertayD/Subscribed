package com.example.subscribed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ConfigureSubscription extends AppCompatActivity {

    //Pop Up screen elements
    EditText popup_name_et;
    EditText popup_desc_et;
    EditText popup_color_et;
    TextView popup_cycle_tv;
    View layout_this;

    String[] day_identifier_values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_subscription);
        popup_name_et = findViewById(R.id.name_enter_et);
        popup_desc_et = findViewById(R.id.enter_description_et);
        popup_desc_et.addTextChangedListener(new ConfigureSubscription.MyTextWatcher(1));
        popup_name_et.addTextChangedListener(new ConfigureSubscription.MyTextWatcher(0));
        popup_cycle_tv = findViewById(R.id.enter_cycle_et);
        layout_this = findViewById(R.id.configure_sub_layout);
        popup_cycle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick();
            }
        });
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
    public void onButtonShowPopupWindowClick() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.renewal_picker, null);

        //Initialize elements
        final NumberPicker number_picker = popupView.findViewById(R.id.number_picker_cycle_np);
        number_picker.setMinValue(1);
        number_picker.setMaxValue(31);

        day_identifier_values = new String[] {"Day(s)", "Week(s)", "Month(s)", "Year(s)"};


        final NumberPicker identifier_picker = popupView.findViewById(R.id.identifier_picker_cycle_np);
        identifier_picker.setMinValue(0);
        identifier_picker.setMaxValue(3);
        identifier_picker.setDisplayedValues(day_identifier_values);


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
                Log.e("CYCLE 10 ", cycle_tv_text.substring(8));

                cycl_substr = "Every " + value + " " + cycle_tv_text.substring(8);
            }
            else
            {
                Log.e("CYCLE 9 ", cycle_tv_text.substring(9));

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
}
