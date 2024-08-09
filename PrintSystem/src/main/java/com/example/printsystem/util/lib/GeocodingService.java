package com.example.printsystem.util.lib;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GeocodingService {
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";

    public static Double[] getCoordinates(String address) throws Exception {
        String url = String.format(GEOCODING_URL, address.replace(" ", "+"), API_KEY);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());

                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonObject location = jsonObject
                        .getAsJsonArray("results")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("geometry")
                        .getAsJsonObject("location");

                double lat = location.get("lat").getAsDouble();
                double lng = location.get("lng").getAsDouble();

                return new Double[]{lat, lng};
            }
        }
    }
}
