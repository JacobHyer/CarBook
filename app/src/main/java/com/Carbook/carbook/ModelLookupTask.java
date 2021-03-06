package com.Carbook.carbook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Background task to retrieve and display all models for a
 * specified make/year combination
 */
public class ModelLookupTask implements Runnable{

    private AddCarActivity activity;
    private List<String> params;
    final String baseURL = "https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMakeYear/make/";
    private String url = "";

    public ModelLookupTask(AddCarActivity activity, List<String> params) {
        this.activity = activity;
        this.params = params;
    }

    /**
     * Calls the API that provides all models. Populates the results
     * in the Model selector on the AddCarActivity
     */
    @Override
    public void run() {
        params.add(1, "/modelyear/");
        JSONArray results = nhtsaCall(baseURL, params);
        List<String> models = new ArrayList<>();

        //Iterate through results and add all Model Names to the list
        for (int i = 0; i < results.length(); i++) {
            try {
                JSONObject obj = results.getJSONObject(i);
                String m = obj.getString("Model_Name");
                models.add(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Sort models by alphabetical, then add Model as placeholder in the beginning
        Collections.sort(models);
        models.add(0, "Model");

        //Send list of models to the AddCarActivity for Spinner display
        populateModelSpinner(models);
    }

    /**
     * Helper function that makes the call to the API
     * @param baseURL
     * @param params
     * @return
     */
    private JSONArray nhtsaCall(String baseURL, List<String> params) {
        url += baseURL;
        for (int i = 0; i < params.size(); i++) {
            url += params.get(i);
        }
        url += "?format=json";
        APIHelper api = new APIHelper();
        String data = api.makeCall(url);
        //Create JSON object to strip away unneeded info and leave only "Results" values
        JSONArray results = new JSONArray();
        try {
            if(data == "404") {
                return results;
            }
            JSONObject json = new JSONObject(data);
            results = json.getJSONArray("Results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Notifies the AddCarActivity and supplies all models to the spinner
     * @param list
     */
    private void populateModelSpinner(List<String> list) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.populateModels(list);
            }
        });
    }
}