package com.example.iotplatform.dao;

import com.example.iotplatform.SensorReading;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implémentation en mémoire du DAO pour stocker les lectures de capteurs IoT.
 * Utilise une ConcurrentHashMap pour permettre un accès thread-safe.
 */
@ApplicationScoped
public class InMemorySensorReadingDAO implements SensorReadingDAO {

    // Stockage interne des lectures : clé = readingId
    private final Map<String, SensorReading> readings = new ConcurrentHashMap<>();

    /** Ajoute une nouvelle lecture dans la mémoire */
    @Override
    public void addReading(SensorReading reading) {
        if (reading == null || reading.getReadingId() == null) return;
        readings.put(reading.getReadingId(), reading);
    }

    /** Retourne la liste de toutes les lectures */
    @Override
    public List<SensorReading> getAllReadings() {
        return new ArrayList<>(readings.values());
    }

    /** Récupère une lecture précise via son identifiant */
    @Override
    public SensorReading getReadingById(String readingId) {
        if (readingId == null) return null;
        return readings.get(readingId);
    }

    /** Récupère toutes les lectures associées à un capteur spécifique */
    @Override
    public List<SensorReading> getReadingsBySensorId(String sensorId) {
        if (sensorId == null) return List.of();
        return readings.values().stream()
                .filter(r -> sensorId.equals(r.getSensorId()))
                .collect(Collectors.toList());
    }
}
