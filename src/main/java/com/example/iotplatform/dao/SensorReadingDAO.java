package com.example.iotplatform.dao;

import com.example.iotplatform.SensorReading;
import java.util.List;

/**
 * Interface DAO pour gérer les lectures de capteurs IoT.
 */
public interface SensorReadingDAO {

    /** Ajoute une nouvelle lecture. */
    void addReading(SensorReading reading);

    /** Retourne toutes les lectures stockées. */
    List<SensorReading> getAllReadings();

    /** Cherche une lecture précise par ID unique. */
    SensorReading getReadingById(String readingId);

    /** Cherche toutes les lectures d’un même capteur. */
    List<SensorReading> getReadingsBySensorId(String sensorId);
}
