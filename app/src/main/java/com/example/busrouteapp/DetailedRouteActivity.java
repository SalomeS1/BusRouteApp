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

import com.example.busrouteapp.services.BusStop;
import com.example.busrouteapp.services.GetBusRouteStopsAsyncTask;

import java.util.ArrayList;

public class DetailedRouteActivity extends Activity {

    private ListView busStops;
    private BusStopsArrayAdapter busStopsArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_stops);
        busStops = findViewById(R.id.stops);

        String routeId = "1";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                routeId = "1";
            } else {
                routeId= extras.getString("routeId");
            }
        }

        busStopsArrayAdapter = new BusStopsArrayAdapter(this, 0, new ArrayList<BusStop>());
        GetBusRouteStopsAsyncTask getBusRouteStopsAsyncTask = new GetBusRouteStopsAsyncTask();
        GetBusRouteStopsAsyncTask.CallBack callback = new GetBusRouteStopsAsyncTask.CallBack() {
            @Override
            public void onDataReceived(ArrayList<BusStop> stops) {
                busStops.setAdapter(busStopsArrayAdapter);
                busStopsArrayAdapter.addAll(stops);
            }
        };

        getBusRouteStopsAsyncTask.setCallback(callback);
        getBusRouteStopsAsyncTask.execute(routeId);
    }



    public class BusStopsArrayAdapter extends ArrayAdapter<BusStop> {

        private Context context;

        public BusStopsArrayAdapter(Context context, int resource, ArrayList<BusStop> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @Override
        public View getView(int position, @Nullable View convertView,  ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_busstop, parent, false);
            final BusStop busStop = getItem(position);
            TextView stopName = view.findViewById(R.id.busStopName);
            TextView stopNumber = view.findViewById(R.id.busStopNumber);
            stopName.setText(busStop.getName());
            stopNumber.setText(busStop.getBusStopId());

            view.findViewById(R.id.show_more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailedRouteActivity.this, DetailedBusStopActivity.class);
                    intent.putExtra("stopId",busStop.getBusStopId());
                    startActivity(intent);
                    finish();
                }
            });

            return view;
        }
    }
}
