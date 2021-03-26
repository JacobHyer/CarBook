package com.Carbook.carbook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageLookupTask implements Runnable{

    private AddCarActivity activity;
    final String key = "api_key=daefd14b-9f2b-4968-9e4d-9d4bb4af01d1";
    final String baseURL = "https://api.fuelapi.com/v1/json/vehicle/";
    final String idURL = "https://api.fuelapi.com/v1/json/vehicles/?year=";
    final String productID = "1";
    private String carID = "";
    private String shotCode = "046";
    private String imgURL = "";
    private Car car;

    public ImageLookupTask(AddCarActivity activity, Car c) {
        this.activity = activity;
        this.car = c;
    }

    @Override
    public void run() {
        carID = getID(car);
        imgURL = getImgURL(productID, shotCode);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showImg(imgURL);
            }
        });
    }

    private String getID(Car c) {
        String url = "";
        url += idURL + c.getYear() + "&make=" + c.getMake() + "&model=" + c.getModel() + "&" + key;
        APIHelper api = new APIHelper();
        String data = api.makeCall(url);
        try {
            JSONArray json = new JSONArray(data);
            //Strip away layers of JSON and return the id
            return json.getJSONObject(0).getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "NotFound";
    }

    private String getImgURL(String id, String sc) {
        String url = "";
        url += baseURL + carID + "/?" + key + "&productID=" + id + "&shotCode=" + sc;
        APIHelper api = new APIHelper();
        String data = api.makeCall(url);
        try {
            JSONObject json = new JSONObject(data);
            //Strip away layers of the JSON and return the url of the image
            return json.getJSONArray("products").getJSONObject(0).getJSONArray("productFormats")
                    .getJSONObject(0).getJSONArray("assets").getJSONObject(0).getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "NotFound";
    }
}
