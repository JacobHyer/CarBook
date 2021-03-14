package com.Carbook.carbook;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

public class VINLookupTask extends AsyncTask<Void, Void, Void> {

    private WeakReference<AddCarActivity> addCarReference;
    private String method;
    private List<String> params;
    final String vinBaseURL = "https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues/";
    private String url = "";

    public VINLookupTask(AddCarActivity addCarReference, String method, List<String> params) {
        this.addCarReference = new WeakReference<>(addCarReference);
        this.method = method;
        this.params = params;
    }

    @Override
    protected Void doInBackground(Void... voids) {
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
        System.out.println(data);
        return null;
    }
}