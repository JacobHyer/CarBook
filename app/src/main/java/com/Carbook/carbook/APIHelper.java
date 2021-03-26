package com.Carbook.carbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class APIHelper {

    public String makeCall(String url) {

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
            return "404";
        }

    }
}
