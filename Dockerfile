# Utilisez l'image de base Payara Server
FROM payara/server-full

# Copiez votre fichier WAR (ou EAR) dans le conteneur Docker
COPY /target/AppVenteVelo-1.0-SNAPSHOT.war /opt/payara/deployments/
