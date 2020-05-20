package com.example.busrouteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.busrouteapp.services.BusRoute;
import com.example.busrouteapp.services.GetBusRoutesAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView busRouteList;
    private BusRouteArrayAdapter busRouteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        busRouteList = findViewById(R.id.bus_route_list);

        busRouteAdapter = new BusRouteArrayAdapter(this, 0, new ArrayList<BusRoute>());
        GetBusRoutesAsyncTask getBusRoutesAsyncTask = new GetBusRoutesAsyncTask();
        GetBusRoutesAsyncTask.Callback callback = new GetBusRoutesAsyncTask.Callback() {
            @Override
            public void onDataReceived(ArrayList<BusRoute> routes) {
                busRouteList.setAdapter(busRouteAdapter);
                busRouteAdapter.addAll(routes);
            }
        };

        getBusRoutesAsyncTask.setCallback(callback);
        getBusRoutesAsyncTask.execute();
    }



    class BusRouteArrayAdapter extends ArrayAdapter<BusRoute> {

        private Context context;

        public BusRouteArrayAdapter(Context context, int resource, ArrayList<BusRoute> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_busroute, parent, false);
            final BusRoute busRoute = getItem(position);
            TextView stopA = view.findViewById(R.id.stopA);
            TextView stopB = view.findViewById(R.id.stopB);
            stopA.setText(busRoute.getFirstStop());
            stopB.setText(busRoute.getSecondStop());

            view.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DetailedRouteActivity.class);
                    intent.putExtra("routeId", busRoute.getRouteNumber());
                    startActivity(intent);
                    finish();
                }
            });

            return view;
        }
    }
}
