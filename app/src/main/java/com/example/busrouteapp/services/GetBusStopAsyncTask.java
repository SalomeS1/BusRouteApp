package com.example.busrouteapp.services;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetBusStopAsyncTask extends AsyncTask<String, Void, ArrayList<Bus>> {

    private GetBusStopAsyncTask.Callback callback;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Bus> doInBackground(String... stopId) {
        ArrayList<Bus> buses = new ArrayList<Bus>();
        String xmlData = "<ArrivalTimes>\n" +
                "    <Bus>\n" +
                "        <BusNumber>1</BusNumber>\n" +
                "        <TimeLeft>7</TimeLeft>\n" +
                "    </Bus>\n" +
                "    <Bus>\n" +
                "        <BusNumber>2</BusNumber>\n" +
                "        <TimeLeft>8</TimeLeft>\n" +
                "    </Bus>\n" +
                "        <Bus>\n" +
                "        <BusNumber>3</BusNumber>\n" +
                "        <TimeLeft>9</TimeLeft>\n" +
                "    </Bus>\n" +
                "</ArrivalTimes>";
        try
        {
            /*
            String url = String.format("http://transfer.ttc.com.ge:8080/otp/routers/ttc/stopArrivalTimes?stopId=%s",stopId);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://transfer.ttc.com.ge:8080/otp/routers/ttc/routes")
                    .build();
            Response response = client.newCall(request).execute();
            xmlData = response.body().string();*/
            Document doc = (Document) Jsoup.parse(xmlData, "", Parser.xmlParser());
            Elements elements = doc.getElementsByTag("Bus");
            for(int i=0;i<elements.size(); i++)
            {
                    Bus bus = new Bus();
                    bus.setBusNumber(elements.get(i).getElementsByTag("BusNumber").get(0).text());
                    bus.setArrivalTimeLeft(elements.get(i).getElementsByTag("TimeLeft").get(0).text());
                    buses.add(bus);

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return buses;
    }

    @Override
    protected void onPostExecute(ArrayList<Bus> buses) {
        if (callback != null) {
            callback.onDataReceived(buses);
        }
    }

    public void setCallback(Callback callback)
    {
        this.callback = callback;
    }


    public interface Callback {
        void onDataReceived(ArrayList<Bus> buses);
    }
}
