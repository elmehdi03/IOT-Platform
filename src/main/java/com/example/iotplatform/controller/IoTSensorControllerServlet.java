package com.example.iotplatform.controller;

import com.example.iotplatform.SensorReading;
import com.example.iotplatform.service.IoTSensorManagerService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Contrôleur principal pour la gestion des lectures de capteurs IoT.
 * Reçoit les requêtes du client (GET / POST) et interagit avec la couche Service.
 */
@WebServlet("/iot-dashboard")
public class IoTSensorControllerServlet extends HttpServlet {

    @Inject
    private IoTSensorManagerService service;

    /** Affiche la page JSP avec toutes les lectures existantes */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<SensorReading> readings = service.getAllReadings();
        req.setAttribute("readings", readings);

        req.getRequestDispatcher("/WEB-INF/views/iot-dashboard.jsp")
                .forward(req, resp);
    }

    /** Traite le formulaire d’ajout d’une nouvelle lecture */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String sensorId   = req.getParameter("sensorId");
        String sensorType = req.getParameter("sensorType");
        String valueStr   = req.getParameter("value");
        String unit       = req.getParameter("unit");
        String location   = req.getParameter("location");

        // ✅ Étape 1 : validation côté serveur
        if (sensorId == null || sensorType == null || unit == null || location == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Champs manquants.");
            return;
        }

        // ✅ Étape 2 : liste des unités autorisées
        List<String> allowedUnits = List.of("°C", "%", "Pa", "lx", "m/s");
        if (!allowedUnits.contains(unit)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unité non reconnue : " + unit);
            return;
        }

        // ✅ Étape 3 : conversion sécurisée de la valeur
        double value;
        try {
            value = Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Valeur numérique invalide.");
            return;
        }

        // ✅ Étape 4 : création de la lecture
        SensorReading reading = new SensorReading(
                UUID.randomUUID().toString(),
                sensorId,
                sensorType,
                value,
                unit,
                System.currentTimeMillis(),
                location
        );

        service.addReading(reading);

        // ✅ Étape 5 : redirection propre
        resp.sendRedirect(req.getContextPath() + "/iot-dashboard");
    }
}
