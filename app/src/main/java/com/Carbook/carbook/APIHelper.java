package com.Carbook.carbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class APIHelper {

    final String vinBaseURL = "https://vpic.nhtsa.dot.gov/api/vehicles/decodevinvalues/";
    private String url = "";

    public String makeCall(String method, List<String> params) {
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
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder data = new StringBuilder();
            String line;

            do {
                line = reader.readLine();
                if (line != null) {
                    data.append(line);
                }
            }
            while (line != null);

            return data.toString();
        } catch (IOException ioe) {
            System.out.println("Error reading HTTP Response: " + ioe);
            return null;
        }
    }
}
