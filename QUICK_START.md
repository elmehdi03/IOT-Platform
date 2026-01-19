# 🚀 Guide de démarrage rapide - Plateforme IoT

## ⚡ Démarrage en 3 secondes

1. **Double-cliquez** sur `start.bat`
2. **Attendez** que le navigateur s'ouvre automatiquement
3. **Profitez !** 🎉

---

## 📝 Scripts disponibles

| Fichier | Description |
|---------|-------------|
| **start.bat** | ▶️ Démarre l'application complète |
| **stop.bat** | ⏹️ Arrête l'application |
| **logs.bat** | 📋 Affiche les logs en temps réel |
| **rebuild.bat** | 🔄 Rebuild après modification du code |
| **test-env.bat** | ✅ Vérifie que l'environnement est correct |

---

## 🌐 URLs importantes

| Page | URL |
|------|-----|
| Accueil | http://localhost:8080 |
| Dashboard | http://localhost:8080/iot-dashboard |
| API Sensors | http://localhost:8080/api/sensors |
| Test CDI | http://localhost:8080/cdi-test |

---

## 🔧 Commandes manuelles

### Démarrer
```powershell
docker-compose up --build -d
```

### Arrêter
```powershell
docker-compose down
```

### Voir les logs
```powershell
docker-compose logs -f
```

### Rebuild complet
```powershell
docker-compose down
docker-compose build --no-cache
docker-compose up -d
```

---

## 📊 Vérifier l'état

```powershell
# Voir les conteneurs actifs
docker-compose ps

# Vérifier la santé du conteneur
docker inspect iotplatform-app | findstr Health

# Voir l'utilisation des ressources
docker stats iotplatform-app
```

---

## 🐛 Problèmes courants

### ❌ "Port 8080 already in use"

**Solution :**
```powershell
# Trouver le processus qui utilise le port
netstat -ano | findstr :8080

# Tuer le processus (remplacer PID par le numéro trouvé)
taskkill /PID <PID> /F
```

### ❌ Docker ne démarre pas

**Solution :**
1. Ouvrir Docker Desktop
2. Attendre qu'il soit complètement démarré
3. Relancer `start.bat`

### ❌ L'application ne répond pas

**Solution :**
```powershell
# Redémarrer le conteneur
docker-compose restart

# Si ça ne marche pas, rebuild complet
.\rebuild.bat
```

---

## 📦 Fichiers persistants

- **sensor_data.json** : Données des capteurs (créé automatiquement)
- **logs/** : Logs de TomEE (créé automatiquement)

⚠️ Ne supprimez pas ces fichiers si vous voulez conserver vos données !

---

## 🎯 Workflow de développement

1. **Modifier le code** dans `src/`
2. **Lancer** `rebuild.bat`
3. **Tester** sur http://localhost:8080
4. **Répéter** 🔄

---

## 💡 Astuces

### Voir les logs en temps réel
```powershell
.\logs.bat
```

### Accéder au conteneur
```powershell
docker exec -it iotplatform-app bash
```

### Nettoyer Docker complètement
```powershell
docker-compose down -v
docker system prune -a
```

### Mode développement (avec debug)
```powershell
docker-compose -f docker-compose.dev.yml up -d
# Port debug : 8000
```

---

## 📞 Besoin d'aide ?

- Consultez `DOCKER_README.md` pour la documentation complète
- Exécutez `test-env.bat` pour vérifier votre environnement
- Regardez les logs avec `logs.bat`

---

**Bon développement ! 🚀**

