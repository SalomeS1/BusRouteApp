package com.example.busrouteapp.services;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetBusRouteStopsAsyncTask extends AsyncTask<String, Void, ArrayList<BusStop>> {

    private CallBack callback;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<BusStop> doInBackground(String... routeNumber) {
        ArrayList<BusStop> busStops = new ArrayList<BusStop>();
        try
        {
            String url = String.format("http://transfer.ttc.com.ge:8080/otp/routers/ttc/routeInfo?routeNumber=%s&type=bus",routeNumber);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            String jsonData = response.body().string();
            JSONArray jsonArray = (new JSONObject(jsonData)).getJSONArray("RouteStops") ;
            busStops = new ArrayList<BusStop>(Arrays.asList(new Gson().fromJson(jsonArray.toString(), BusStop[].class)));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return busStops;
    }

    @Override
    protected void onPostExecute(ArrayList<BusStop> busStops) {
        if (callback != null) {
            callback.onDataReceived(busStops);
        }
    }


    public void setCallback(CallBack callback)
    {
        this.callback = callback;
    }


    public interface CallBack {
        void onDataReceived(ArrayList<BusStop> movies);
    }

}
