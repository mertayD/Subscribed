package com.example.subscribed;

import android.util.Log;

import java.util.Date;
import java.io.Serializable;

@SuppressWarnings("serial")

public class Subscription implements Serializable {
    private String name;
    private String description;
    private Date subscriptionCeated;
    private int duration_time; // int range from 1 - 40;

    enum durationIdentifier{
        DAY, WEEK, MONTH, YEAR
    }
    private durationIdentifier duration_identified; // Either day, week, months, years
    private int cycle_time;
    private durationIdentifier cycle_identified;
    private int image_identifier; //Which Icon is used
    private int color;
    private boolean notify_user;
    private double price; //Subs Price

    public Subscription(){
        this.name = "";
        this.description = "";
        this.subscriptionCeated = new Date();
        this.duration_time = 0;
        this.duration_identified = durationIdentifier.DAY;
        this.cycle_time = 0;
        this.cycle_identified = durationIdentifier.DAY;
        this.image_identifier = 0;
        this.color = 0;
        this.price = 0;
        this.notify_user = false;

    }
    public Subscription(String name,
                        String description,
                        Date subscriptionCeated,
                        int duration_time,
                        durationIdentifier duration_identified,
                        int cycle_time,
                        durationIdentifier cycle_identified,
                        int image_identifier,
                        int color,
                        boolean notify_user,
                        double price) {
        this.name = name;
        this.description = description;
        this.subscriptionCeated = subscriptionCeated;
        this.duration_time = duration_time;
        this.duration_identified = duration_identified;
        this.cycle_time = cycle_time;
        this.cycle_identified = cycle_identified;
        this.image_identifier = image_identifier;
        this.color = color;
        this.notify_user = notify_user;
        this.price = price;
    }

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSubscriptionCeated() {
        return subscriptionCeated;
    }

    public void setSubscriptionCeated(Date subscriptionCeated) {
        this.subscriptionCeated = subscriptionCeated;
    }

    public int getDuration_time() {
        return duration_time;
    }

    public void setDuration_time(int duration_time) {
        this.duration_time = duration_time;
    }

    public durationIdentifier getDuration_identified() {
        return duration_identified;
    }

    public void setDuration_identified(durationIdentifier duration_identified) {
        this.duration_identified = duration_identified;
    }

    public int getCycle_time() {
        return cycle_time;
    }

    public void setCycle_time(int cycle_time) {
        this.cycle_time = cycle_time;
    }

    public durationIdentifier getCycle_identified() {
        return cycle_identified;
    }

    public void setCycle_identified(durationIdentifier cycle_identified) {
        this.cycle_identified = cycle_identified;
    }
    public void setCycle_identified(int cycle_identified) {
        switch (cycle_identified){
            case 0:
                this.cycle_identified = durationIdentifier.DAY;
                break;
            case 1:
                this.cycle_identified = durationIdentifier.WEEK;
                break;
            case 2:
                this.cycle_identified = durationIdentifier.MONTH;
                break;
            case 3:
                this.cycle_identified = durationIdentifier.YEAR;
                break;
        }
    }

    public int getImage_identifier() {
        return image_identifier;
    }

    public void setImage_identifier(int image_identifier) {
        this.image_identifier = image_identifier;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isNotify_user() {
        return notify_user;
    }

    public void setNotify_user(boolean notify_user) {
        this.notify_user = notify_user;
    }

    public void to_string(){
        Log.e("SUBSCRIPTION", "NAME = "+ name +  "\n Description = " + description + "\n " + "Duration = " + getDuration_time());
    }
}
