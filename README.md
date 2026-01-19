# 🌐 Plateforme IoT avec Docker

> Application Java EE de gestion de capteurs IoT, entièrement dockerisée et prête à l'emploi !

## 🚀 Démarrage ultra-rapide

**3 secondes pour lancer l'application :**

1. Double-cliquez sur **`start.bat`**
2. Attendez que le navigateur s'ouvre
3. Profitez ! 🎉

👉 **C'est tout !** L'application se lance automatiquement sur http://localhost:8080

---

## 📋 Prérequis

- ✅ **Docker Desktop** installé et démarré
- ✅ Port **8080** disponible
- ✅ Connexion Internet (première fois seulement)

> 💡 Pour vérifier votre environnement : double-cliquez sur **`test-env.bat`**

---

## 📦 Ce qui est inclus

### 🐳 Configuration Docker complète
- Build automatique avec Maven
- Serveur TomEE 10 (Jakarta EE 10)
- Java 17
- Persistance des données

### 🚀 6 Scripts Windows (.bat)
| Script | Description |
|--------|-------------|
| **start.bat** ⭐ | Lance toute l'application automatiquement |
| **stop.bat** | Arrête l'application proprement |
| **rebuild.bat** | Rebuild après modification du code |
| **logs.bat** | Affiche les logs en temps réel |
| **test-env.bat** | Vérifie que Docker est prêt |
| **clean.bat** | Nettoie Docker complètement |

### 📚 Documentation complète
- **QUICK_START.md** - Guide de démarrage rapide (recommandé)
- **DOCKER_README.md** - Documentation Docker complète
- **DEV_GUIDE.md** - Guide de développement
- **CHECKLIST.txt** - Checklist d'installation

---

## 🌐 URLs de l'application

| Page | URL |
|------|-----|
| **Accueil** | http://localhost:8080 |
| **Dashboard IoT** | http://localhost:8080/iot-dashboard |
| **API REST** | http://localhost:8080/api/sensors |

---

## 💡 Utilisation quotidienne

### Démarrer l'application
```
Double-clic sur start.bat
```

### Arrêter l'application
```
Double-clic sur stop.bat
```

### Voir les logs
```
Double-clic sur logs.bat
```

### Après avoir modifié le code
```
Double-clic sur rebuild.bat
```

---

## 🛠️ Workflow de développement

```
1. Modifier le code dans src/
   ↓
2. Lancer rebuild.bat
   ↓
3. Tester sur http://localhost:8080
   ↓
4. Répéter 🔄
```

---

## 🏗️ Architecture technique

- **Backend** : Java 17 + Jakarta EE 10
- **Serveur** : Apache TomEE 10 (WebProfile)
- **Build** : Maven 3.9
- **CDI** : Injection de dépendances
- **EJB** : Singleton pour les services
- **Persistance** : InMemoryDAO + JSON
- **Frontend** : JSP + JSTL
- **Conteneurisation** : Docker + docker-compose

---

## 📊 Fonctionnalités de l'application

### ✨ Gestion des capteurs
- Ajout manuel de lectures de capteurs
- Collecte automatique depuis API externe (ThingSpeak)
- Dashboard de visualisation
- API REST pour intégration

### 💾 Persistance
- Données sauvegardées dans `sensor_data.json`
- Conservation entre les redémarrages
- Restauration automatique au démarrage

### 🔄 Collecte automatique
- Récupération automatique toutes les 30 secondes
- Intégration avec ThingSpeak
- Sauvegarde automatique

---

## 🐛 Résolution de problèmes

### L'application ne démarre pas
```powershell
# Vérifier l'environnement
.\test-env.bat

# Voir les logs
.\logs.bat

# Rebuild complet
.\rebuild.bat
```

### Port 8080 déjà utilisé
```powershell
# Trouver le processus
netstat -ano | findstr :8080

# Tuer le processus
taskkill /PID <PID> /F
```

### Docker ne répond pas
1. Ouvrir Docker Desktop
2. Attendre qu'il soit complètement démarré
3. Relancer `start.bat`

---

## 🎓 Mode développement avancé

### Avec debug Java activé
```powershell
docker-compose -f docker-compose.dev.yml up -d
```
- Application : port **8080**
- Debug : port **8000**

### Commandes Docker utiles
```powershell
# Voir l'état
docker-compose ps

# Voir les ressources
docker stats iotplatform-app

# Accéder au conteneur
docker exec -it iotplatform-app bash

# Redémarrer
docker-compose restart
```

---

## 📖 Documentation

Pour en savoir plus, consultez :

- **QUICK_START.md** - Guide rapide (⭐ recommandé)
- **DOCKER_README.md** - Documentation Docker détaillée
- **DEV_GUIDE.md** - Guide de développement complet
- **CHECKLIST.txt** - Checklist avant démarrage

---

## 🔧 Structure du projet

```
iotplatform/
├── 🐳 Docker
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── docker-compose.dev.yml
│
├── 🚀 Scripts (.bat)
│   ├── start.bat           ⭐ Principal
│   ├── stop.bat
│   ├── rebuild.bat
│   ├── logs.bat
│   ├── test-env.bat
│   └── clean.bat
│
├── 📚 Documentation
│   ├── QUICK_START.md
│   ├── DOCKER_README.md
│   ├── DEV_GUIDE.md
│   └── CHECKLIST.txt
│
├── 💾 Données
│   ├── sensor_data.json    (créé automatiquement)
│   └── logs/               (créé automatiquement)
│
├── 💻 Code source
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   ├── resources/
│       │   └── webapp/
│       └── test/
│
└── 🔨 Build
    ├── pom.xml
    └── target/
```

---

## 🎯 Prochaines étapes

Après avoir lancé l'application :

1. ✅ Tester l'accueil : http://localhost:8080
2. ✅ Accéder au dashboard : http://localhost:8080/iot-dashboard
3. ✅ Ajouter une lecture de capteur via le formulaire
4. ✅ Vérifier que les données sont sauvegardées
5. ✅ Consulter les logs : `logs.bat`

---

## 📝 Notes importantes

- **Premier démarrage** : 1-2 minutes (téléchargement des images)
- **Démarrages suivants** : ~10 secondes
- **Données persistantes** : conservées dans `sensor_data.json`
- **Logs** : disponibles dans le dossier `logs/`

---

## 🤝 Contribution

Pour contribuire au projet :

1. Modifier le code dans `src/`
2. Tester avec `rebuild.bat`
3. Consulter `DEV_GUIDE.md` pour plus d'infos

---

## 📞 Support

En cas de problème :

1. **Vérifier l'environnement** : `test-env.bat`
2. **Consulter les logs** : `logs.bat`
3. **Lire la doc** : `QUICK_START.md` ou `DOCKER_README.md`

---

## ✨ Fonctionnalités Docker

✅ Build multi-stage (Maven + TomEE)  
✅ Health check automatique  
✅ Logs persistants  
✅ Données persistantes  
✅ Hot-reload en mode dev  
✅ Support du debug Java  
✅ Configuration optimisée  

---

## 🎉 C'est parti !

**Vous êtes prêt à utiliser votre plateforme IoT !**

👉 Double-cliquez sur **`start.bat`** pour commencer ! 🚀

---

<div align="center">

**Développé avec ❤️ pour la plateforme IoT**

*Docker • Java 17 • Jakarta EE 10 • TomEE 10*

</div>

