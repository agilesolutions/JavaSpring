# ShowCase project for Spring Boot with GitLab CI/CD 
Demonstrating how to build a Spring Boot application with GitLab CI/CD and deploy it to a Kubernetes cluster using FluxCD.
## Features
- Spring Boot application with JPA and MongoDB
- Kafka producer and consumer
- REST API with controller and controller advice
- Problem+JSON error handling
- Declarative REST client
- Spring Security for authentication and authorization
- Spring Scheduler for scheduled tasks
- Spring Retry for retrying operations
- Helm for packaging and deploying applications
- Kustomize for customizing Kubernetes manifests
- DevOps pipeline for CI/CD

## Prerequisites
- Java 17 or higher
- Gradle 7.0 or higher
- Docker
- Kubernetes cluster (public or private)
- GitLab account with a project

## Project Structure
```
kotlinspring/
├── build.gradle.kts
├── settings.gradle.kts
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/agilesolutions/
│   │   │       ├── AllInOneApplication.java     
│   │   │       ├── config/
│   │   │       │   ├── KafkaConfig.java
│   │   │       │   ├── MongoConfig.java  
│   │   │       │   └── RestConfig.java        
│   │   │       ├── controller/
│   │   │       │   └── StockController.kt
│   │   │       ├── model/
│   │   │       │   └── StockData.kt
│   │   │       ├── dto/
│   │   │       │   ├── ShareDto.kt
│   │   │       │   └── StockResponse.kt
│   │   │       ├── exception/
│   │   │       │   ├── BusinessException.java
│   │   │       │   ├── CustomontrollerAdvice.kt
│   │   │       │   └── Problem.kt
│   │   │       ├── kafka/
│   │   │       │   ├── KafkaProducer.kt
│   │   │       │   └── KafkaConsumer.kt
│   │   │       ├── repository/
│   │   │       │   ├── AssetRepository.kt
│   │   │       │   ├── ShareRepository.kt
│   │   │       │   └── StockPriceRepository.kt
│   │   │       ├── service/
│   │   │       │   ├── AssetService.kt
│   │   │       │   ├── ShareService.kt
│   │   │       │   └── StockPriceService.kt
│   │   │       └── util/
│   │   │           └── AssetMapper.kt
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── static/
│   │       └── templates/
├── docker/
│   └── Dockerfile
├── helm/
│   └── kotlinspring/
│       ├── Chart.yaml
│       ├── values.yaml
│       ├── templates/
│       │   ├── deployment.yaml
│       │   ├── service.yaml
│       │   └── ingress.yaml
└── kustomize/
    └── base/
        ├── kustomization.yaml
        ├── deployment.yaml
        ├── service.yaml
        └── ingress.yaml
```
## Build and Run the Application
### Build the application
```gradle build
```
This will compile the code, run tests, and create a JAR file in the `build/libs` directory.

### Run the application
You can run the application using the following command:
```bash
java -jar build/libs/application-1.0.0.jar
```
## Docker
### Build the Docker image
You can build the Docker image using the following command:
```bash
docker build -t allinone:latest -f docker/Dockerfile .
``` 
### Run the Docker container
You can run the Docker container using the following command:
```bash
docker run -p 8080:8080 allinone:latest
```
## Gradle Tasks
- `generateAvro`: Generates Avro classes from the Avro schema files.
- `bootRun`: Runs the Spring Boot application.
- `build`: Builds the application and creates a JAR file.
- `test`: Runs the tests.
- `docker`: Builds the Docker image.
- `helm`: Packages the application using Helm.
- `kustomize`: Customizes the Kubernetes manifests using Kustomize.
- `deploy`: Deploys the application to a Kubernetes cluster using FluxCD.
- `ci`: Runs the CI/CD pipeline.
- `lint`: Runs the code linter.
- `format`: Formats the code using the code formatter.
- `check`: Runs the code checks.
- `publish`: Publishes the application to a remote repository.
- `release`: Creates a release of the application.
- `clean`: Cleans the build directory.

## GitLab CI/CD Pipeline
The GitLab CI/CD pipeline is defined in the `.gitlab-ci.yml` file. It includes the following stages:
- `build`: Builds the application and creates a JAR file.
- `test`: Runs the tests.
- `docker`: Builds the Docker image.
- `helm`: Packages the application using Helm.
- `kustomize`: Customizes the Kubernetes manifests using Kustomize.
- `deploy`: Deploys the application to a Kubernetes cluster using FluxCD.

## Running the Application Locally
To run the application locally, you can use the following command:
```bash
./gradlew bootRun
```
This will start the Spring Boot application on port 8080.
## Accessing the Application
You can access the application using the following URLs:
- JPA Shares: [http://localhost:8080/jpa/shares](http://localhost:8080/jpa/shares)
- Mongo Shares: [http://localhost:8080/mongo/shares](http://localhost:8080/mongo/shares)
- Kafka Shares: [http://localhost:8080/kafka/shares](http://localhost:8080/kafka/shares)
- Stock Prices API: [http://localhost:8080/api/assets/stockPrices/AAPL](http://localhost:8080/api/assets/stockPrices/AAPL)
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Actuator: [http://localhost:8080/actuator](http://localhost:8080/actuator)
- Health Check: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
- Metrics: [http://localhost:8080/actuator/metrics](http://localhost:8080/actuator/metrics)
- Prometheus: [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)
- Grafana: [http://localhost:3000](http://localhost:3000) (default username: admin, password: admin)
- Kibana: [http://localhost:5601](http://localhost:5601) (default username: elastic, password: changeme)
- Elasticsearch: [http://localhost:9200](http://localhost:9200) (default username: elastic, password: changeme)

## Monitoring and Logging
The application is integrated with Prometheus and Grafana for monitoring, and Elasticsearch, Logstash, and Kibana (ELK stack) for logging. You can access the monitoring and logging dashboards using the following URLs:
- Prometheus: [http://localhost:9090](http://localhost:9090)
- Grafana: [http://localhost:3000](http://localhost:3000) (default username: admin, password: admin)
- Kibana: [http://localhost:5601](http://localhost:5601) (default username: elastic, password: changeme)

## Contributing
Contributions are welcome! Please feel free to submit a pull request or open an issue if you find any bugs or have suggestions for improvements.

## Contact
For any questions or inquiries, please contact the project maintainer at robert.rong@agile-solutions.ch

## Acknowledgements
This project is inspired by various open-source projects and tutorials. Special thanks to the Spring Boot community for their contributions and support.

## References
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [GitLab CI/CD Documentation](https://docs.gitlab.com/ee/ci/)
- [FluxCD Documentation](https://fluxcd.io/docs/)
- [Kubernetes Documentation](https://kubernetes.io/docs/home/)
- [Helm Documentation](https://helm.sh/docs/)
- [Kustomize Documentation](https://kubernetes-sigs.github.io/kustomize/)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)
- [Docker Documentation](https://docs.docker.com/)
- [Prometheus Documentation](https://prometheus.io/docs/introduction/overview/)
- [Grafana Documentation](https://grafana.com/docs/)
- [ELK Stack Documentation](https://www.elastic.co/what-is/elk-stack)
- [Spring Security Documentation](https://docs.spring.io/spring-security/site/docs/current/reference/html/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Data MongoDB Documentation](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [Spring Kafka Documentation](https://docs.spring.io/spring-kafka/docs/current/reference/html/)
- [Spring Boot Actuator Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Spring Boot DevTools Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)
- [Spring Boot Test Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)
- [Spring Boot Starter Web Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html)
- [Spring Boot Starter Security Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-security.html)
- [Spring Boot Starter Data JPA Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-jpa.html)
- [Spring Boot Starter Data MongoDB Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-mongodb.html)
- [Spring Boot Starter Kafka Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-kafka.html)
- [Spring Boot Starter Actuator Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Spring Boot Starter DevTools Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)
- [Spring Boot Starter Test Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)
- [Spring Boot Starter Web Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html)
- [Spring Boot Starter Security Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-security.html)
- [Spring Boot Starter Data JPA Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-jpa.html)
- [Spring Boot Starter Data MongoDB Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-mongodb.html)
- [Spring Boot Starter Kafka Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-kafka.html)
- [Spring Boot Starter Actuator Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Spring Boot Starter DevTools Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)
- [Spring Boot Starter Test Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)
- [Spring Boot Starter Web Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html)
- [Spring Boot Starter Security Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-security.html)
- [Spring Boot Starter Data JPA Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-jpa.html)
- [Spring Boot Starter Data MongoDB Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-mongodb.html)
- [Spring Boot Starter Kafka Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-kafka.html)





 
