package com.example.iotplatform.service;

import com.example.iotplatform.dao.SensorReadingDAO;
import com.example.iotplatform.SensorReading;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 * Service métier pour la gestion des lectures de capteurs IoT.
 */
@ApplicationScoped
public class IoTSensorManagerService {

    @Inject
    private SensorReadingDAO dao;

    /** Ajoute une nouvelle lecture avec un ID généré automatiquement. */
    public void addReading(SensorReading reading) {
        if (reading.getReadingId() == null || reading.getReadingId().isEmpty()) {
            reading.setReadingId(UUID.randomUUID().toString());
        }
        dao.addReading(reading);
    }

    /** Retourne toutes les lectures disponibles. */
    public List<SensorReading> getAllReadings() {
        return dao.getAllReadings();
    }

    /** Récupère une lecture par ID. */
    public SensorReading getReadingById(String id) {
        return dao.getReadingById(id);
    }

    /** Récupère toutes les lectures d’un capteur spécifique. */
    public List<SensorReading> getReadingsBySensorId(String sensorId) {
        return dao.getReadingsBySensorId(sensorId);
    }
}
