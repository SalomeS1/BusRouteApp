package com.example.busrouteapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.busrouteapp.services.Bus;
import com.example.busrouteapp.services.GetBusStopAsyncTask;

import java.util.ArrayList;

public class DetailedBusStopActivity extends Activity {

    private ListView buses;
    private BusArrayAdapter busArrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stop);
        buses = findViewById(R.id.bus_list);
        String stopId = "1";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                stopId = "1";
            } else {
                stopId= extras.getString("stopId");
            }
        }

        busArrayAdapter = new BusArrayAdapter(this, 0, new ArrayList<Bus>());
        GetBusStopAsyncTask getBusRouteStopsAsyncTask = new GetBusStopAsyncTask();
        GetBusStopAsyncTask.Callback callback = new GetBusStopAsyncTask.Callback() {
            @Override
            public void onDataReceived(ArrayList<Bus> stops) {
                buses.setAdapter(busArrayAdapter);
                busArrayAdapter.addAll(stops);
            }
        };

        getBusRouteStopsAsyncTask.setCallback(callback);
        getBusRouteStopsAsyncTask.execute(stopId);
    }


    public class BusArrayAdapter extends ArrayAdapter<Bus> {

        private Context context;

        public BusArrayAdapter(Context context, int resource, ArrayList<Bus> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @Override
        public View getView(int position, @Nullable View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_current_stop, parent, false);
            final Bus bus = getItem(position);
            TextView busNumber = view.findViewById(R.id.bus_number);
            TextView timeLeft = view.findViewById(R.id.time_left);
            busNumber.setText(bus.getBusNumber());
            timeLeft.setText(bus.getArrivalTimeLeft());
            return view;
        }
    }


}
