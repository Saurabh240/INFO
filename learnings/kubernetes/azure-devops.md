### Deploying a Spring Boot Application with React Frontend on Azure Kubernetes Service (AKS) with Azure DevOps Pipeline and Azure MySQL

This step-by-step guide will walk you through deploying a Spring Boot application with a React frontend on an Azure Kubernetes Service (AKS) cluster using Azure DevOps for CI/CD and Azure MySQL for the database.

---

### **Prerequisites**

1. Azure account.
2. Azure CLI installed locally.
3. Docker installed locally.
4. Azure Kubernetes Service (AKS) cluster.
5. Azure DevOps organization and project.
6. MySQL Database created in Azure.
7. A Spring Boot + React application ready to deploy.

---

### **Step 1: Set Up Azure MySQL Database**

1. **Create MySQL Database**:
   - Go to the [Azure Portal](https://portal.azure.com).
   - Search for **Azure Database for MySQL** and create a new MySQL database.
   - Save the **connection string** and credentials for later use in the Spring Boot application.

2. **Configure MySQL Settings in Spring Boot**:
   - Update the `application.properties` or `application.yml` file with the MySQL connection details:
     ```properties
     spring.datasource.url=jdbc:mysql://<your_mysql_server>.mysql.database.azure.com:3306/<database_name>?useSSL=true&requireSSL=false
     spring.datasource.username=<username>@<your_mysql_server>
     spring.datasource.password=<password>
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     spring.jpa.hibernate.ddl-auto=update
     ```

---

### **Step 2: Set Up Azure Kubernetes Service (AKS)**

1. **Create an AKS Cluster**:
   - Go to the **Azure Portal** and search for **Kubernetes Services**.
   - Click **Create** and configure the cluster (choose the region, node size, etc.).

2. **Install Azure CLI** and authenticate:
   ```bash
   az login
   az aks get-credentials --resource-group <your-resource-group> --name <aks-cluster-name>
   ```

3. **Verify AKS is configured correctly**:
   ```bash
   kubectl get nodes
   ```

---

### **Step 3: Dockerize the Spring Boot and React Application**

1. **Create a Dockerfile for Spring Boot**:
   ```Dockerfile
   FROM openjdk:17-jdk-alpine
   VOLUME /tmp
   ARG JAR_FILE=target/*.jar
   COPY ${JAR_FILE} app.jar
   ENTRYPOINT ["java","-jar","/app.jar"]
   ```

2. **Create a Dockerfile for React**:
   ```Dockerfile
   FROM node:14-alpine AS build
   WORKDIR /app
   COPY package.json ./
   RUN npm install
   COPY . .
   RUN npm run build

   FROM nginx:alpine
   COPY --from=build /app/build /usr/share/nginx/html
   EXPOSE 80
   CMD ["nginx", "-g", "daemon off;"]
   ```

3. **Build Docker Images**:
   ```bash
   docker build -t your-dockerhub-username/spring-boot-app .
   docker build -t your-dockerhub-username/react-app .
   ```

4. **Push the Docker images to Docker Hub**:
   ```bash
   docker push your-dockerhub-username/spring-boot-app
   docker push your-dockerhub-username/react-app
   ```

---

### **Step 4: Create Kubernetes Manifests for Spring Boot and React**

1. **Spring Boot Kubernetes Deployment** (`springboot-deployment.yaml`):
   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: springboot-app
   spec:
     replicas: 2
     selector:
       matchLabels:
         app: springboot-app
     template:
       metadata:
         labels:
           app: springboot-app
       spec:
         containers:
         - name: springboot-container
           image: your-dockerhub-username/spring-boot-app
           ports:
           - containerPort: 8080
   ```

2. **React Kubernetes Deployment** (`react-deployment.yaml`):
   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: react-app
   spec:
     replicas: 2
     selector:
       matchLabels:
         app: react-app
     template:
       metadata:
         labels:
           app: react-app
       spec:
         containers:
         - name: react-container
           image: your-dockerhub-username/react-app
           ports:
           - containerPort: 80
   ```

3. **Service for Spring Boot** (`springboot-service.yaml`):
   ```yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: springboot-service
   spec:
     selector:
       app: springboot-app
     ports:
       - protocol: TCP
         port: 80
         targetPort: 8080
     type: LoadBalancer
   ```

4. **Service for React** (`react-service.yaml`):
   ```yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: react-service
   spec:
     selector:
       app: react-app
     ports:
       - protocol: TCP
         port: 80
         targetPort: 80
     type: LoadBalancer
   ```

---

### **Step 5: Set Up Azure DevOps Pipeline**

1. **Create Azure DevOps Project**:
   - Go to [Azure DevOps](https://dev.azure.com) and create a new project.

2. **Create Repositories for Spring Boot and React**:
   - Push your Spring Boot and React code to separate repositories in Azure DevOps.

3. **Create Pipeline for Spring Boot**:
   - In Azure DevOps, go to **Pipelines > New Pipeline**.
   - Connect to your repository and create a YAML pipeline:
   ```yaml
   trigger:
     branches:
       include:
       - main

   pool:
     vmImage: 'ubuntu-latest'

   steps:
   - task: DockerInstaller@0
     inputs:
       dockerVersion: '17.09.0-ce'

   - script: |
       docker build -t your-dockerhub-username/spring-boot-app .
       docker login -u $(dockerUsername) -p $(dockerPassword)
       docker push your-dockerhub-username/spring-boot-app
     displayName: 'Build and Push Spring Boot Image'
   ```

4. **Create Pipeline for React** (similar to Spring Boot):
   ```yaml
   trigger:
     branches:
       include:
       - main

   pool:
     vmImage: 'ubuntu-latest'

   steps:
   - task: DockerInstaller@0
     inputs:
       dockerVersion: '17.09.0-ce'

   - script: |
       docker build -t your-dockerhub-username/react-app .
       docker login -u $(dockerUsername) -p $(dockerPassword)
       docker push your-dockerhub-username/react-app
     displayName: 'Build and Push React Image'
   ```

5. **Apply Kubernetes Manifests in Azure Pipeline**:
   - Add a step to deploy your applications to AKS:
   ```yaml
   - task: Kubernetes@1
     displayName: 'Deploy to AKS'
     inputs:
       connectionType: 'Azure Resource Manager'
       azureSubscriptionEndpoint: '<your-subscription>'
       azureResourceGroup: '<your-resource-group>'
       kubernetesCluster: '<your-cluster>'
       namespace: default
       command: apply
       useConfigurationFile: true
       configuration: |
         springboot-deployment.yaml
         springboot-service.yaml
         react-deployment.yaml
         react-service.yaml
   ```

---

### **Step 6: Access the Application**

1. **Get External IP**:
   - After deployment, get the external IP of the React and Spring Boot services:
   ```bash
   kubectl get svc
   ```

2. **Access Your Application**:
   - Use the external IP addresses to access your React frontend and Spring Boot backend.

---

This guide will help you set up a full-stack Spring Boot and React application on Azure Kubernetes Service (AKS), integrated with Azure DevOps for CI/CD and Azure MySQL for the database.