# Utilisez une image de base avec Java 17 préinstallé
FROM openjdk:17 AS builder

# Définir le répertoire de travail
WORKDIR /app

# Copiez les fichiers de configuration du projet (pom.xml, wrapper.properties, etc.)
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copiez le reste des fichiers du projet
COPY src/ ./src/

# Exécutez la commande de build pour générer le fichier JAR
RUN ./mvnw package -DskipTests

# Créez une nouvelle image à partir d'une image plus légère avec seulement JRE
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copiez le fichier JAR résultant depuis le premier stade de construction
COPY --from=builder /app/target/consumer-0.0.1-SNAPSHOT.jar .

# Exposing port 8080
EXPOSE 8080

# Commande par défaut pour exécuter l'application lorsque le conteneur démarre
CMD ["java", "-jar", "consumer-0.0.1-SNAPSHOT.jar"]
