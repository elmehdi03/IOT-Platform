package com.example.iotplatform.service;

import com.example.iotplatform.SensorReading;
import jakarta.enterprise.context.ApplicationScoped;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Service qui récupère automatiquement les mesures depuis l'API ThingSpeak.
 */
@ApplicationScoped
public class IoTExternalApiService {

    // URL du canal public (exemple : Weather Station)
    private static final String API_URL =
            "https://api.thingspeak.com/channels/9/feeds.json?results=1";

    /**
     * Récupère la dernière lecture IoT depuis ThingSpeak.
     */
    public SensorReading fetchReadingFromApi(String sensorId) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            JSONObject json = new JSONObject(sb.toString());
            JSONArray feeds = json.getJSONArray("feeds");
            JSONObject lastFeed = feeds.getJSONObject(feeds.length() - 1);

            // On lit les champs du flux
            double temperature = Double.parseDouble(lastFeed.getString("field1"));
            double humidity = Double.parseDouble(lastFeed.getString("field2"));
            String createdAt = lastFeed.getString("created_at");

            // Créer une lecture SensorReading pour la température (par exemple)
            SensorReading reading = new SensorReading(
                    UUID.randomUUID().toString(),
                    sensorId,
                    "TEMP",
                    temperature,
                    "°C",
                    System.currentTimeMillis(),
                    "Station ThingSpeak (ID 9)"
            );

            // Tu peux aussi enregistrer l’humidité comme un second capteur
            // ou la combiner si ton modèle le permet

            System.out.println("Lecture API ThingSpeak récupérée à " + createdAt);
            return reading;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
