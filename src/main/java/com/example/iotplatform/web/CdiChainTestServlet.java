package com.example.iotplatform.web;

import com.example.iotplatform.SensorReading;
import com.example.iotplatform.service.IoTSensorManagerService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/cdi-test")
public class CdiChainTestServlet extends HttpServlet {

    @Inject
    private IoTSensorManagerService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        // Vérifier l’injection
        if (service == null) {
            out.println("❌ Échec : service non injecté");
            return;
        }

        // Créer une lecture de capteur
        SensorReading reading = new SensorReading(
                null, "S1", "TEMP", 25.4, "°C", System.currentTimeMillis(), "Salle Serveur");
        service.addReading(reading);

        // Récupérer toutes les lectures stockées
        List<SensorReading> all = service.getAllReadings();

        out.println("✅ Injection CDI OK");
        out.println("Nombre de lectures stockées : " + all.size());
        all.forEach(r -> out.println(" → " + r));
    }
}
