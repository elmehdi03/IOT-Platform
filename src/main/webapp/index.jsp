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
            color: white;
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

</body>
</html>
