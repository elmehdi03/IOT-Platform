# 🔧 Guide de développement - Plateforme IoT

## 📁 Structure du projet

```
iotplatform/
├── 🐳 Configuration Docker
│   ├── Dockerfile                    # Build multi-stage Maven + TomEE
│   ├── docker-compose.yml            # Configuration production
│   ├── docker-compose.dev.yml        # Configuration développement
│   ├── .dockerignore                 # Exclusions pour le build
│   └── .env.example                  # Variables d'environnement
│
├── 🚀 Scripts Windows (.bat)
│   ├── start.bat                     # Démarrage automatique
│   ├── stop.bat                      # Arrêt propre
│   ├── rebuild.bat                   # Rebuild complet
│   ├── logs.bat                      # Logs en temps réel
│   ├── test-env.bat                  # Vérification environnement
│   └── clean.bat                     # Nettoyage Docker
│
├── 📚 Documentation
│   ├── QUICK_START.md                # Guide rapide
│   ├── DOCKER_README.md              # Documentation Docker
│   ├── INSTALLATION_COMPLETE.txt     # Récapitulatif installation
│   └── DEV_GUIDE.md                  # Ce fichier
│
├── 💻 Code source
│   └── src/
│       ├── main/
│       │   ├── java/com/example/iotplatform/
│       │   │   ├── SensorReading.java              # Modèle de données
│       │   │   ├── controller/                     # Servlets
│       │   │   │   ├── IoTSensorControllerServlet.java
│       │   │   │   └── IoTApiControllerServlet.java
│       │   │   ├── dao/                            # Couche d'accès aux données
│       │   │   │   ├── SensorReadingDAO.java
│       │   │   │   └── InMemorySensorReadingDAO.java
│       │   │   ├── service/                        # Logique métier
│       │   │   │   ├── IoTSensorManagerService.java
│       │   │   │   ├── IoTDataCollector.java       # ⚠️ Modifié pour Docker
│       │   │   │   └── IoTExternalApiService.java
│       │   │   └── web/
│       │   │       └── CdiChainTestServlet.java
│       │   ├── resources/
│       │   └── webapp/
│       │       ├── index.jsp
│       │       └── WEB-INF/
│       │           ├── beans.xml
│       │           └── views/
│       │               └── iot-dashboard.jsp
│       └── test/
│
├── 💾 Données
│   ├── sensor_data.json              # Données persistantes
│   └── logs/                         # Logs TomEE (créé auto)
│
└── 🔨 Build
    ├── pom.xml                       # Configuration Maven
    ├── mvnw                          # Maven Wrapper (Linux/Mac)
    ├── mvnw.cmd                      # Maven Wrapper (Windows)
    └── target/                       # Artefacts compilés
```

## 🛠️ Workflow de développement

### 1️⃣ Développement local (sans Docker)

```powershell
# Compiler
.\mvnw clean package

# Déployer sur TomEE local (si installé)
copy target\iotplatform.war %TOMEE_HOME%\webapps\
```

### 2️⃣ Développement avec Docker

```powershell
# Démarrer en mode normal
.\start.bat

# OU démarrer en mode développement (avec debug)
docker-compose -f docker-compose.dev.yml up -d
```

### 3️⃣ Après modification du code

```powershell
# Option 1 : Rebuild complet (recommandé)
.\rebuild.bat

# Option 2 : Manuel
docker-compose down
docker-compose build --no-cache
docker-compose up -d
```

## 🔍 Debug

### Mode debug activé (docker-compose.dev.yml)

1. Démarrer en mode dev :
   ```powershell
   docker-compose -f docker-compose.dev.yml up -d
   ```

2. Configurer IntelliJ IDEA :
   - Run → Edit Configurations
   - Add → Remote JVM Debug
   - Host : `localhost`
   - Port : `8000`
   - Debugger mode : Attach

3. Mettre des breakpoints et lancer le debugger

### Logs en temps réel

```powershell
# Tous les logs
.\logs.bat

# Ou manuellement
docker-compose logs -f

# Logs spécifiques
docker-compose logs -f iotplatform
```

## 📦 Architecture de l'application

### Couches

```
┌─────────────────────────────────────┐
│         JSP / Frontend              │  ← Vues (index.jsp, dashboard.jsp)
├─────────────────────────────────────┤
│         Servlets (Controllers)      │  ← Contrôleurs HTTP
├─────────────────────────────────────┤
│         Services (CDI)              │  ← Logique métier + EJB
├─────────────────────────────────────┤
│         DAO                         │  ← Accès aux données (In-Memory)
├─────────────────────────────────────┤
│         Models (DTOs)               │  ← SensorReading
└─────────────────────────────────────┘
```

### Technologies utilisées

- **Jakarta EE 10** (fourni par TomEE)
- **CDI** (Contexts and Dependency Injection)
- **EJB** (Enterprise JavaBeans) pour les Singleton
- **Servlets** pour les contrôleurs HTTP
- **JSP + JSTL** pour les vues
- **JSON-Java** pour la sérialisation
- **Maven** pour le build
- **Docker + TomEE 10** pour le déploiement

## 🔧 Modification importante pour Docker

### Fichier modifié : `IoTDataCollector.java`

**Avant :**
```java
private static final String JSON_FILE_PATH = "C:/Users/ROG STRIX/IdeaProjects/iotplatform/sensor_data.json";
```

**Après :**
```java
private static final String JSON_FILE_PATH = System.getProperty("user.dir", "/app") + "/sensor_data.json";
```

**Pourquoi ?**
- Compatible avec Docker (user.dir = `/app`)
- Compatible avec l'environnement local
- Fonctionne sur tous les systèmes d'exploitation

## 📝 Ajouter une nouvelle fonctionnalité

### Exemple : Ajouter un nouveau type de capteur

1. **Modifier le modèle** (`SensorReading.java`) si nécessaire

2. **Ajouter la logique métier** dans `IoTSensorManagerService.java`

3. **Créer/Modifier un servlet** dans `controller/`

4. **Mettre à jour la vue** dans `webapp/WEB-INF/views/`

5. **Rebuild et tester** :
   ```powershell
   .\rebuild.bat
   ```

## 🧪 Tests

### Tests manuels

1. **Démarrer l'application** :
   ```powershell
   .\start.bat
   ```

2. **Tester les endpoints** :
   - http://localhost:8080
   - http://localhost:8080/iot-dashboard
   - http://localhost:8080/api/sensors
   - http://localhost:8080/cdi-test

3. **Vérifier les logs** :
   ```powershell
   .\logs.bat
   ```

### Tests avec curl

```powershell
# GET - Liste des capteurs
curl http://localhost:8080/api/sensors

# POST - Ajouter une lecture
curl -X POST http://localhost:8080/api/sensors ^
  -H "Content-Type: application/x-www-form-urlencoded" ^
  -d "sensorId=S1&sensorType=temperature&value=25.5&unit=°C&location=Bureau"
```

## 📊 Monitoring

### État des conteneurs
```powershell
docker-compose ps
```

### Ressources utilisées
```powershell
docker stats iotplatform-app
```

### Health check
```powershell
docker inspect iotplatform-app | findstr Health
```

### Accéder au conteneur
```powershell
docker exec -it iotplatform-app bash
```

### Inspecter les fichiers dans le conteneur
```powershell
# Voir le fichier JSON
docker exec iotplatform-app cat /app/sensor_data.json

# Voir les logs TomEE
docker exec iotplatform-app cat /usr/local/tomee/logs/catalina.out
```

## 🐛 Résolution de problèmes

### Build Maven échoue

```powershell
# Nettoyer le cache Maven local
.\mvnw clean

# Rebuild complet
docker-compose build --no-cache
```

### Le WAR ne se déploie pas

1. Vérifier que le build Maven produit bien un WAR :
   ```powershell
   .\mvnw clean package
   dir target\iotplatform.war
   ```

2. Vérifier les logs TomEE :
   ```powershell
   docker-compose logs iotplatform
   ```

### Problèmes de permissions (sensor_data.json)

```powershell
# Sur Windows, vérifier les permissions du fichier
icacls sensor_data.json

# Recréer le fichier si nécessaire
del sensor_data.json
echo [] > sensor_data.json
```

### Port 8080 déjà utilisé

```powershell
# Trouver le processus
netstat -ano | findstr :8080

# Tuer le processus
taskkill /PID <PID> /F

# OU changer le port dans docker-compose.yml
# ports:
#   - "8081:8080"
```

## 🔒 Bonnes pratiques

### Avant de committer

1. ✅ Tester localement avec Docker
2. ✅ Vérifier que le build Maven passe
3. ✅ Vérifier qu'il n'y a pas d'erreurs dans les logs
4. ✅ Tester tous les endpoints principaux

### Sécurité

- ⚠️ Ne jamais committer de secrets dans le code
- ⚠️ Utiliser des variables d'environnement pour les configs sensibles
- ⚠️ Valider toutes les entrées utilisateur côté serveur

### Performance

- 💡 Utiliser un pool de connexions si ajout d'une vraie DB
- 💡 Mettre en cache les données fréquemment accédées
- 💡 Limiter la taille du sensor_data.json (rotation des logs)

## 📚 Ressources utiles

### Documentation officielle

- [Jakarta EE](https://jakarta.ee/)
- [TomEE](https://tomee.apache.org/)
- [Docker](https://docs.docker.com/)
- [Maven](https://maven.apache.org/)

### Commandes Docker utiles

```powershell
# Voir toutes les images
docker images

# Supprimer une image
docker rmi <image_id>

# Nettoyer tout Docker
docker system prune -a

# Voir les volumes
docker volume ls

# Supprimer les volumes inutilisés
docker volume prune
```

## 🎯 Prochaines étapes

### Améliorations possibles

1. **Base de données réelle**
   - Ajouter PostgreSQL ou MySQL au docker-compose
   - Migrer de InMemoryDAO vers JPA

2. **API REST complète**
   - Ajouter JAX-RS (RESTEasy)
   - Documentation avec Swagger/OpenAPI

3. **Frontend moderne**
   - Remplacer JSP par React/Vue.js
   - API REST + SPA

4. **CI/CD**
   - GitHub Actions pour le build automatique
   - Déploiement automatique

5. **Monitoring**
   - Ajouter Prometheus + Grafana
   - Alertes sur les erreurs

## 💬 Support

Pour toute question ou problème :

1. Consultez d'abord les logs : `.\logs.bat`
2. Vérifiez l'environnement : `.\test-env.bat`
3. Consultez la documentation : `DOCKER_README.md`

---

**Bon développement ! 🚀**

