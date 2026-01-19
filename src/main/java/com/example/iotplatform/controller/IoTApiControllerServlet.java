package com.example.iotplatform.controller;

import com.example.iotplatform.SensorReading;
import com.example.iotplatform.service.IoTSensorManagerService;
import com.example.iotplatform.service.IoTExternalApiService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Contrôleur pour l'ajout automatique de données à partir d'une API externe.
 */
@WebServlet("/iot-dashboard/api")
public class IoTApiControllerServlet extends HttpServlet {

    @Inject
    private IoTSensorManagerService service;

    @Inject
    private IoTExternalApiService apiService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Exemple : on récupère la lecture depuis une API externe
        SensorReading fromApi = apiService.fetchReadingFromApi("S1");

        if (fromApi != null) {
            service.addReading(fromApi);
            // Redirige vers la page principale après ajout
            resp.sendRedirect(req.getContextPath() + "/iot-dashboard");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_GATEWAY,
                    "Impossible de récupérer la lecture depuis l’API externe.");
        }
    }
}
