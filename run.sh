./mvnw clean package -DskipTests
docker-compose build
docker-compose up -d
