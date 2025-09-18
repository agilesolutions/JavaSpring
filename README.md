

# ShowCase project for Spring Boot with GitLab CI/CD 
Demonstrating how to build a Spring Boot AllInOne (JPA, MongoDB, Kafka) Back-End REST-Full application with GitLab CI/CD and deploy it to a Kubernetes cluster using FluxCD.
This application is set up with a WireMock server to mock Keycloak for testing Oauth2 login flows. This setup makes the application believe it is interacting with a real Keycloak OIDC Idp server during runtime. 

```
Note: This service is implemented as a Spring Boot monolith to demonstrate cross-cutting Spring Framework features such as security, scalability, service integration, and more. The design choices are intentional for this context and are not intended to represent microservices best practices.
```
## Features
- REST API with controller and controller advice
- Problem+JSON error handling
- Declarative REST client
- Spring Scheduler for scheduled tasks
- Kubernetes readiness checks and custom health indicator to enabling graceful shutdown
- Actuator for monitoring and management
- Micrometer for metrics collection
- OpenAPI/Swagger for API documentation
- Testing with JUnit, Mockito, Testcontainers, and WireMock
- Docker for containerization
- Kubernetes manifests for deployment
- FluxCD for GitOps-based continuous deployment
- Gradle for build automation
- Avro for data serialization
- Lombok for reducing boilerplate code
- Spring Data JPA for database access
- Spring Data MongoDB for MongoDB access
- Spring Kafka for Kafka integration
- Spring AMQP for RabbitMQ integration
- Spring Cloud Stream for building message-driven microservices
- Spring Boot DevTools for development convenience
- Spring Boot Test for testing support
- Spring REST Docs for API documentation
- Spring Actuator for application monitoring
- Spring Security OAuth2 for securing REST endpoints
- Spring Security JWT for JWT support
- Spring Validation for input validation
- Spring Retry for retrying operations
- Helm for packaging and deploying applications
- Kustomize for customizing Kubernetes manifests
- DevOps pipeline for CI/CD

## Prerequisites
- Java 21 or higher
- Gradle 7.0 or higher
- Docker
- Kubernetes one node cluster running on Docker for Desktop
- GitLab account with a project

## Project Structure
```
kotlinspring/
├── build.gradle
├── settings.gradle
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/agilesolutions/
│   │   │       ├── AllInOneApplication.java     
│   │   │       ├── actuator/
│   │   │       │   ├── CustomHealthCheck.java
│   │   │       │   ├── CustomMongoHealthInducator.java  
│   │   │       │   └── HealthService.java        
│   │   │       ├── config/
│   │   │       │   ├── ApplicationProperties.java
│   │   │       │   ├── KafkaConfig.java
│   │   │       │   ├── MongoDBConfig.java  
│   │   │       │   ├── MvcConfig.java  
│   │   │       │   └── RestConfig.java        
│   │   │       ├── controller/
│   │   │       │   └── StockControllerjava
│   │   │       ├── dto/
│   │   │       │   ├── ShareDto.java
│   │   │       │   └── StockResponse.java
│   │   │       ├── exception/
│   │   │       │   ├── BusinessException.java
│   │   │       │   ├── CustomontrollerAdvice.java
│   │   │       │   └── Problemjava
│   │   │       ├── init/
│   │   │       │   ├── KafkaInitializer.java
│   │   │       │   └── MongoDBInitializer.java
│   │   │       ├── jpa/
│   │   │       │   └── JPA packages and components...
│   │   │       ├── kafka/
│   │   │       │   └── Kafka packages and components...
│   │   │       ├── model/
│   │   │       │   └── StockData.java
│   │   │       ├── mongo/
│   │   │       │   └── MongoDB packages and components...
│   │   │       ├── mvc/
│   │   │       │   ├── AvroDeserelizer.java
│   │   │       │   └── AvroSerializer.java
│   │   │       ├── rest/
│   │   │       │   └── StockClient.java
│   │   │       └── service/
│   │   │           └── StockService.java
│   │   └── resources/
│   │   ├── rest/
│   │       └── share.avsc -- Avro schema
│   │   ├── application.yml
│   │   └── data.yml -- initial load scripts
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
This will Gradle build, test and package SpringBoot jar. Accordingly build a docker image, ready to get deployed to Kubernetes with Kustomize overlay local. 
Running the kubernetes manifests on the metrics directory will spin up a pre-configured Prometheus and Grafana POD. Prometheus is pre-configured to scrape the 
actuator/prometheus endpoint and absorbing SpringBoot runtime statistics.
### Build the application
```
gradle build
```
This will compile the code, run tests, and create a JAR file in the `build/libs` directory.

### Run the application
You can run the application using the following command:
```bash
gradle release # compile, test, package and docker build
kubectl apply -k ./kustomize/overlays/local # kustomize deploy springboot deployment and service
kubectl apply -f ./metrics # deploy k8s deployments and services to run Prometheus and Grafana.
kubectl logs -f -n allinone -l app=allinone # check the logs on springboot
kubectl logs -f -n monitoring -l app=prometheus # tail the logs on prometheus
kubectl logs -f -n monitoring -l app=grafana # tail the logs on grafana
```

- [Swagger UI](http://localhost:30080/swagger-ui.html)
- [Grafana UI](http://localhost:30070/)
- [Prometheus UI](http://localhost:30090/)

### Setup grafana dashboard
- use admin/admin credentials to login and resetting password
- import dashboard [12900](https://grafana.com/grafana/dashboards/12900-springboot-apm-dashboard/)
- select prometheus datasource (pre-configured on deployment)
- And your first runtime statistics will show...


## Gradle Tasks
- `generateAvro`: Generates Avro classes from the Avro schema files.
- `bootRun`: Runs the Spring Boot application.
- `build`: Builds the application and creates a JAR file.
- `test`: Runs the tests.
- `integrationTest`: Runs the integration tests with PostgreSQL, MongoDB and Kafka test containers
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

## Security
This application is setup to securing and authorizing REST endpoints using Spring Security with Oauth2 and JWT. This application combines Authorization server, Resource Sever and Client Appication in one single application. Method level security is implemented using `@PreAuthorize` annotations. 
1. **Authorization Server** - Issues and validates OAuth2 tokens
2. **Resource Server** - Hosts protected resources requiring OAuth2 authentication
3. **Client Application** - Makes authenticated requests using OAuth2 tokens

### Architecture
This is depicting a typical OIDC Authorization Code Flow. 
1. The client requests an access token from the Authorization Server
2. 
2. The Authorization Server validates credentials and returns a token
3. The client includes this token when requesting protected resources
4. The Resource Server validates the token before serving the request

<img title="OIDC Authorization Code Flow" alt="Alt text" src="/images/oauth2_architecture.png">


### Security Testing
You need to have docker for desktop running and local kubernetes cluster started. Run the following commands to...
1. Build spring boot jar and pack in on a docker image.
2. run the kustomize local overlay to set up a k8s namespace, deployment and service binding to nodeport 30080
3. kubectl apply everything in metrics folder, k8s deploying prometheus and grafana and binding node ports.
3. run swagger UI from [http://localhost:30080/swagger-ui.html](http://localhost:30080/swagger-ui.html)
4. check prometheus collected metrics through [actuator/prometheus endpoint](http://localhost:30080/actuator/prometheus)
```
gradle release
kubectl apply -k ./kustomize/overlays/local
kubectl apply -f ./metrics
kubectl logs -f -n allinone -l app=allinone
kubectl logs -f -n monitoring -l app=prometheus
kubectl logs -f -n monitoring -l app=grafana
```
This application provides two sets of endpoints:
- Public endpoints: `/swagger-ui.html, /actuator, /v3/api-docs` - accessible without authentication.
- Secured endpoints: `/api/**` - require authentication and authorization Authorization: Bearer <access_token>

#### Oauth2.0 client and resource server setup
- I am faking up OAuth2 server (/oauth2/token + JWKS) issues valid RS256-signed JWTs.
- The resource server validates the JWTs using the JWKS endpoint exposed by the fake OAuth2 server.
- The client application uses the OAuth2 Authorization Code flow to obtain access tokens from the fake OAuth2 server.
- The client application includes the access tokens in the Authorization header when making requests to the resource server.
- A JwtDecoder that validates the signature, issuer, and audience of the JWTs.
- An OAuth2AuthorizedClientService that stores authorized clients in memory.
- The resource server validates the access tokens and authorizes access to protected resources based on the scopes and roles contained in the tokens (A JWKS endpoint (/.well-known/jwks.json) exposing the public key)
- The application uses method-level security annotations (e.g., @PreAuthorize) to enforce authorization rules on specific endpoints, Maps the roles claim into Spring Security authorities so @PreAuthorize("hasRole('ADMIN')") works.
- The application uses a custom UserDetailsService to load user details from an in-memory store for authentication purposes.
- The application uses BCryptPasswordEncoder to hash and verify user passwords.
- The application uses a custom JwtAuthenticationConverter to extract roles from the JWT and map them to Spring Security authorities.

#### Oauth2.0 client (OIDC authorization code grant flow)processing flow:
1. The client application initiates the OAuth2 Authorization Code flow by redirecting the user to the authorization endpoint of the fake OAuth2 server.
2. The user authenticates with the fake OAuth2 server and grants consent to the client application
3. The fake OAuth2 server redirects the user back to the client application with an authorization code.
4. The client application exchanges the authorization code for an access token by making a POST request to the token endpoint of the fake OAuth2 server.
5. The fake OAuth2 server validates the authorization code and returns an access token (a JWT signed with RS256) to the client application.
6. The client application includes the access token in the Authorization header when making requests to the resource server.
7. The resource server validates the access token and authorizes access to protected resources based on the scopes and roles contained in the token.
8. The application uses method-level security annotations (e.g., @PreAuthorize) to enforce authorization rules on specific endpoints.
9. The application uses a custom UserDetailsService to load user details from an in-memory store for authentication purposes.
10. The application uses BCryptPasswordEncoder to hash and verify user passwords.
11. The application uses a custom JwtAuthenticationConverter to extract roles from the JWT and map them to Spring Security authorities.
12. The application logs security-related events for auditing and monitoring purposes.
        
## Observability with Micrometer to collecting JVM, CPU and HTTP metrics
- JVM Memory: jvm.memory.used, jvm.memory.max, jvm.gc.pause, etc.
- CPU: system.cpu.usage, process.cpu.usage, system.load.average.1m, etc.
- HTTP Requests: http.server.requests — counts, timers, percentiles, tags by status, method, URI.

### Grafana dashboard and install


<img title="Grafana SpringBoot APM Dashboard" alt="Alt text" src="/images/dashboard.png">

```bash
kubectl apply -f ./metrics # deploy k8s deployments and services to run Prometheus and Grafana.
kubectl logs -f -n monitoring -l app=prometheus # tail the logs on prometheus
kubectl logs -f -n monitoring -l app=grafana # tail the logs on grafana
```

- [Swagger UI](http://localhost:30080/swagger-ui.html)
- [Grafana UI](http://localhost:30070/)
- [Prometheus UI](http://localhost:30090/)

### Setup grafana dashboard
- import dashboard [12900](https://grafana.com/grafana/dashboards/12900-springboot-apm-dashboard/)
- select prometheus datasource (pre-configured on deployment)

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
- [Spring REST Dos](https://spring.io/projects/spring-restdocs)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Spring Data MongoDB Documentation](https://spring.io/projects/spring-data-mongodb)
- [Spring Kafka Documentation](https://spring.io/projects/spring-kafka)
- [Spring Boot Actuator Documentation](https://docs.spring.io/spring-boot/api/rest/actuator/index.html)
- [Spring Boot DevTools Documentation](https://docs.spring.io/spring-boot/reference/using/devtools.html)
- [Spring Boot Test Documentation](https://docs.spring.io/spring-boot/reference/testing/index.html)
- [Spring Boot Starter Web Documentation](https://docs.spring.io/spring-boot/reference/web/servlet.html)
- [Spring Boot Holistic Project overview](https://spring.io/projects/spring-boot)




 
