# Étape 1 : Construction de l'application avec Maven
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Télécharger les dépendances (optimisation du cache Docker)
RUN mvn dependency:go-offline -B

# Copier le code source
COPY src ./src

# Construire le WAR
RUN mvn clean package -DskipTests

# Étape 2 : Image runtime avec TomEE
FROM tomee:10-jre17-webprofile

# Définir le répertoire de travail
WORKDIR /app

# Supprimer les applications par défaut
RUN rm -rf /usr/local/tomee/webapps/*

# Copier le WAR construit depuis l'étape précédente
COPY --from=builder /app/target/iotplatform.war /usr/local/tomee/webapps/ROOT.war

# Créer le répertoire pour les données et donner les permissions
RUN mkdir -p /app/data && chmod -R 777 /app

# Exposer le port 8080
EXPOSE 8080

# Variables d'environnement
ENV JAVA_OPTS="-Xmx512m -Xms256m -Duser.dir=/app"

# Démarrer TomEE
CMD ["catalina.sh", "run"]

