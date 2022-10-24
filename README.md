   ```sh
   mvn clean package -DskipTests=true
   mvn docker:build
   docker-compose up
   ```