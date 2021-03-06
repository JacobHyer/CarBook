package com.Carbook.carbook;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Background task to retrieve make/model/year for a car
 * with the specified VIN
 */
public class VinLookupTask implements Runnable {

    private AddCarActivity activity;
    private List<String> params;
    final String vinBaseURL = "https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues/";
    private String url = "";

    public VinLookupTask(AddCarActivity activity, List<String> params) {
        this.activity = activity;
        this.params = params;
    }

    /**
     * Makes asks for call to the API, then creates a new Car
     * object from the results. Car is sent back to AddCarActivity
     */
    @Override
    public void run() {
        JSONObject vinResults = nhtsaCall(vinBaseURL, params);
        //Create new car object and send back to AddCarActivity
        Gson g = new Gson();
        Car c = g.fromJson(vinResults.toString(), Car.class);
        sendCar(c);
        }

    /**
     * Helper function to make the call to the API. Returns "stripped"
     * away results
     * @param baseURL
     * @param params
     * @return
     */
    private JSONObject nhtsaCall(String baseURL, List<String> params) {
        //Make API call and return JSONObject of car details
        url += baseURL;
        for (int i = 0; i < params.size(); i++) {
            url += params.get(i);
        }
        url += "?format=json";
        APIHelper api = new APIHelper();
        String data = api.makeCall(url);
        //Create JSON object to strip away unneeded info and leave only "Results" values
        JSONObject values = new JSONObject();
        try {
            //If API call fails, return empty array
            if(data == "404") {
                return values;
            }
            JSONObject json = new JSONObject(data);
            values = json.getJSONArray("Results").getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * Sends car back to AddCarActivity
     * @param car
     */
    private void sendCar(Car car) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showCar(car.getMake(), car.getModel(), car.getYear());
            }
        });
    }
}