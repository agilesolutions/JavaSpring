### Java Spring template project

This project is based on a GitLab [Project Template](https://docs.gitlab.com/ee/gitlab-basics/create-project.html).

Improvements can be proposed in the [original project](https://gitlab.com/gitlab-org/project-templates/spring).

### CI/CD with Auto DevOps

This template is compatible with [Auto DevOps](https://docs.gitlab.com/ee/topics/autodevops/).

If Auto DevOps is not already enabled for this project, you can [turn it on](https://docs.gitlab.com/ee/topics/autodevops/#enabling-auto-devops) in the project settings.

### Spring Boot all-in-one backend reference application
- Spring Framework
  - Spring data JPA
  - Spring data MongoDB
  - Spring Web
    - controller
    - controller advice
    - problem+json
    - declarative RestClient
  - Spring kafka
  - Spring security
  - Spring Scheduler
  - Spring Retry
- Helm
- Kustomize
- DevOps pipeline
- Terraform AKS

### run


```
gradle generateAvro
gradle bootRun
```
http://localhost:8080/jpa/shares
http://localhost:8080/mongo/shares
http://localhost:8080/api/assets/stockPrices/AAPL

## GITLAB CI kubernetes deploy to public cluster

- Store k8s credentials as protected variables
- create kubeconfig file
- 

```
kubectl config view --minify --flatten > kubeconfig.yaml
base64 kubeconfig.yaml
```


