package com.Carbook.carbook;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class VINLookupTask implements Runnable {

    //private WeakReference<AddCarActivity> addCarReference;
    private AddCarActivity activity;
    private String method;
    private List<String> params;
    final String vinBaseURL = "https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues/";
    private String url = "";

    public VINLookupTask(AddCarActivity activity, String method, List<String> params) {
        //this.addCarReference = new WeakReference<>(addCarReference);
        this.activity = activity;
        this.method = method;
        this.params = params;
    }

    @Override
    public void run() {
        switch (method) {
            case "vin":
                url += vinBaseURL;
                for (int i = 0; i < params.size(); i++) {
                    url += params.get(i);
                }
                url += "?format=json";
                break;
            case "make/model":
                break;
        }
        APIHelper api = new APIHelper();
        String data = api.makeCall(url);
        //Create JSON object to strip away unneeded info and leave only "Results" values
        JSONObject values = null;
        try {
            JSONObject json = new JSONObject(data);
            JSONArray results = json.getJSONArray("Results");
            values = results.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Return object to string to be used by GSON
        String toGson = values.toString();
        System.out.println(values);
        Gson g = new Gson();
        Car c = g.fromJson(toGson, Car.class);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showCar(c.getMake(), c.getModel(), c.getYear());
            }
        });
    }
}