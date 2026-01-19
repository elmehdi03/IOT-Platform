package com.example.iotplatform.service;

import com.example.iotplatform.SensorReading;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Collecteur automatique de données IoT à intervalle régulier.
 * Récupère les mesures depuis ThingSpeak et les sauvegarde dans un fichier JSON.
 */
@Singleton
public class IoTDataCollector {

    // 🔧 Chemin du fichier JSON (compatible Docker et local)
    private static final String JSON_FILE_PATH = System.getProperty("user.dir", "/app") + "/sensor_data.json";

    @Inject
    private IoTSensorManagerService service;

    @Inject
    private IoTExternalApiService apiService;

    /** Collecte automatique toutes les 30 secondes */
    @Schedule(hour="*", minute="*", second="*/30", persistent=false)
    public void collectData() {
        SensorReading reading = apiService.fetchReadingFromApi("S1");

        if (reading != null) {
            service.addReading(reading);
            saveToJsonFile(service.getAllReadings());
            System.out.println("✅ Donnée ajoutée automatiquement : " + reading.getValue() + " " + reading.getUnit());
        }
    }

    /** Sauvegarde toutes les lectures actuelles dans un fichier JSON local */
    private void saveToJsonFile(List<SensorReading> readings) {
        JSONArray array = new JSONArray();
        for (SensorReading r : readings) {
            JSONObject obj = new JSONObject();
            obj.put("readingId", r.getReadingId());
            obj.put("sensorId", r.getSensorId());
            obj.put("sensorType", r.getSensorType());
            obj.put("value", r.getValue());
            obj.put("unit", r.getUnit());
            obj.put("timestamp", r.getTimestamp());
            obj.put("location", r.getLocation());
            array.put(obj);
        }

        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            writer.write(array.toString(4)); // indenté
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Charge les données sauvegardées au démarrage (optionnel) */
    public void loadFromJsonFile() {
        try {
            if (Files.exists(Paths.get(JSON_FILE_PATH))) {
                String jsonContent = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
                JSONArray array = new JSONArray(jsonContent);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    SensorReading r = new SensorReading(
                            obj.getString("readingId"),
                            obj.getString("sensorId"),
                            obj.getString("sensorType"),
                            obj.getDouble("value"),
                            obj.getString("unit"),
                            obj.getLong("timestamp"),
                            obj.getString("location")
                    );
                    service.addReading(r);
                }

                System.out.println("💾 Données restaurées depuis le fichier JSON (" + array.length() + " lectures).");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
