# 🌐 Plateforme IoT - Guide de démarrage

## 📋 Prérequis

- **Docker Desktop** installé et en cours d'exécution
  - Télécharger : https://www.docker.com/products/docker-desktop
  - Version minimale : 20.10+

## 🚀 Démarrage rapide

### Méthode 1 : Double-clic sur `start.bat`

1. Double-cliquez sur le fichier **`start.bat`**
2. L'application se construira et démarrera automatiquement
3. Le navigateur s'ouvrira automatiquement sur `http://localhost:8080`

**C'est tout !** 🎉

### Méthode 2 : Ligne de commande

```powershell
# Démarrer l'application
.\start.bat

# Ou manuellement avec docker-compose
docker-compose up --build -d
```

## 🌍 Accès à l'application

Une fois démarrée, l'application est accessible sur :

- **Page d'accueil** : http://localhost:8080
- **Dashboard IoT** : http://localhost:8080/iot-dashboard
- **API REST** : http://localhost:8080/api/sensors

## ⏹️ Arrêter l'application

### Méthode 1 : Double-clic sur `stop.bat`

### Méthode 2 : Ligne de commande

```powershell
.\stop.bat

# Ou manuellement
docker-compose down
```

## 📊 Gestion des logs

### Voir les logs en temps réel

```powershell
docker-compose logs -f
```

### Voir les logs de l'application uniquement

```powershell
docker-compose logs -f iotplatform
```

Les logs TomEE sont également disponibles dans le dossier `./logs/`

## 💾 Données persistantes

Les données de capteurs sont stockées dans le fichier **`sensor_data.json`** à la racine du projet. Ce fichier est automatiquement créé au premier démarrage et persiste entre les redémarrages.

## 🔧 Architecture

### Services Docker

- **iotplatform** : Application Java EE (TomEE 10 + JRE 17)
  - Port : 8080
  - Mémoire : 512 Mo max, 256 Mo min

### Structure du projet

```
iotplatform/
├── Dockerfile              # Construction de l'image Docker
├── docker-compose.yml      # Orchestration des services
├── start.bat              # Script de démarrage automatique
├── stop.bat               # Script d'arrêt
├── .dockerignore          # Fichiers exclus du build Docker
├── sensor_data.json       # Données persistantes (créé automatiquement)
├── logs/                  # Logs TomEE
└── src/                   # Code source de l'application
```

## 🛠️ Commandes utiles

### Reconstruire l'application après modifications du code

```powershell
docker-compose up --build -d
```

### Redémarrer l'application

```powershell
docker-compose restart
```

### Voir l'état des conteneurs

```powershell
docker-compose ps
```

### Accéder au conteneur (shell)

```powershell
docker exec -it iotplatform-app bash
```

### Nettoyer complètement

```powershell
docker-compose down -v
docker system prune -a
```

## 🐛 Résolution de problèmes

### L'application ne démarre pas

1. Vérifiez que Docker Desktop est lancé
2. Vérifiez que le port 8080 n'est pas déjà utilisé :
   ```powershell
   netstat -ano | findstr :8080
   ```
3. Consultez les logs :
   ```powershell
   docker-compose logs
   ```

### Erreur "Port already in use"

Un autre service utilise le port 8080. Options :

1. Arrêter le service qui utilise le port 8080
2. Modifier le port dans `docker-compose.yml` :
   ```yaml
   ports:
     - "8081:8080"  # Utiliser le port 8081 à la place
   ```

### L'application ne répond pas

Attendez 30-60 secondes que TomEE démarre complètement. Vérifiez le health check :

```powershell
docker inspect iotplatform-app | findstr Health
```

## 📝 Développement

### Modifier le code

1. Modifiez les fichiers dans `src/`
2. Relancez le build :
   ```powershell
   docker-compose up --build -d
   ```

### Volumes montés

- `./sensor_data.json` → `/app/sensor_data.json` (persistance)
- `./logs` → `/usr/local/tomee/logs` (logs)

## 🔒 Sécurité

En production, pensez à :

- Configurer un reverse proxy (nginx)
- Activer HTTPS
- Limiter les ressources Docker
- Utiliser des secrets pour les configurations sensibles

## 📞 Support

Pour toute question ou problème, consultez :

- Logs de l'application : `./logs/`
- Logs Docker : `docker-compose logs`

---

**Développé avec ❤️ pour la plateforme IoT**

