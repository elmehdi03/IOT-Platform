<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de Bord IoT</title>
    <style>
        body { font-family: "Segoe UI", sans-serif; margin: 40px; background: #fdfdfd; }
        h1 { color: #2c3e50; margin-bottom: 20px; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background: #3498db; color: white; }
        tr:nth-child(even) { background: #f2f2f2; }
        form { margin-top: 30px; display: flex; flex-wrap: wrap; gap: 10px; }
        input { padding: 5px; border: 1px solid #aaa; border-radius: 4px; }
        button { background: #27ae60; color: white; border: none; border-radius: 4px; padding: 6px 12px; cursor: pointer; }
        button:hover { background: #2ecc71; }
    </style>
</head>
<body>

<h1>📊 Tableau de Bord IoT</h1>

<!-- Tableau d’affichage -->
<table>
    <tr>
        <th>ID Lecture</th>
        <th>Capteur</th>
        <th>Type</th>
        <th>Valeur</th>
        <th>Unité</th>
        <th>Lieu</th>
        <th>Horodatage</th>
    </tr>

    <c:forEach var="reading" items="${readings}">
        <tr>
            <td>${reading.readingId}</td>
            <td>${reading.sensorId}</td>
            <td>${reading.sensorType}</td>
            <td>${reading.value}</td>
            <td>${reading.unit}</td>
            <td>${reading.location}</td>
            <td>${reading.timestamp}</td>
        </tr>
    </c:forEach>
</table>

<!-- Formulaire d’ajout -->
<h3>➕ Ajouter une nouvelle lecture</h3>
<form action="${pageContext.request.contextPath}/iot-dashboard" method="post">
    <input type="text" name="sensorId" placeholder="ID Capteur" required>
    <input type="text" name="sensorType" placeholder="Type (ex: TEMP)" required>
    <input type="number" step="any" name="value" placeholder="Valeur" required>
    <select name="unit" required>
        <option value="" disabled selected>-- Choisir une unité --</option>
        <option value="°C">°C (Température)</option>
        <option value="%">%(Humidité)</option>
        <option value="Pa">Pa (Pression)</option>
        <option value="lx">lx (Luminosité)</option>
        <option value="m/s">m/s (Vitesse du vent)</option>
    </select>
    <input type="text" name="location" placeholder="Lieu" required>
    <button type="submit">Enregistrer</button>
</form>
<h3>Ou</h3>
<form action="${pageContext.request.contextPath}/iot-dashboard/api" method="get">
    <button type="submit" style="background:#2980b9;">Charger depuis l’API</button>
</form>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil - IoT Platform</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background: linear-gradient(135deg, #3498db, #2ecc71);
            color: black;
        }
        a {
            background: white;
            color: #2c3e50;
            padding: 10px 20px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
            transition: 0.3s;
        }
        a:hover {
            background: #ecf0f1;
        }
    </style>
</head>
<body>
<h1>Bienvenue sur la Plateforme IoT 🌐</h1>
<p>Accédez au tableau de bord pour consulter les lectures de capteurs.</p>
<a href="${pageContext.request.contextPath}/iot-dashboard">Aller au tableau de bord</a>
<script>
    // Recharge automatiquement la page toutes les 30 secondes
    setInterval(() => {
        window.location.reload();
    }, 30000); // 30 000 ms = 30 s
</script>

</body>
</html>

</body>
</html>
