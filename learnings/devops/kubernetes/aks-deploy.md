Here’s a step-by-step guide to setting up an Azure DevOps pipeline that builds a Docker image from a `Dockerfile` in the repository, pushes it to Docker Hub, and deploys the image to Azure Kubernetes Service (AKS).

### Prerequisites:
1. **Azure Subscription** for AKS.
2. **Azure DevOps Account**.
3. **Docker Hub Account** to push Docker images.
4. **Azure CLI** and **Kubectl** installed locally for testing.
5. **Azure Kubernetes Service (AKS)** configured (steps to create a cluster are below).
6. **Azure DevOps Service Connection** to authenticate with Azure.

---

### Step 1: Set Up an Azure Kubernetes Service (AKS) Cluster

#### Using Azure CLI
Run the following commands to create a Kubernetes cluster on Azure:

1. **Login to Azure**:
   ```bash
   az login
   ```

2. **Create a Resource Group**:
   ```bash
   az group create --name myResourceGroup --location eastus
   ```

3. **Create the AKS Cluster**:
   ```bash
   az aks create --resource-group myResourceGroup --name myAKSCluster --node-count 1 --enable-addons monitoring --generate-ssh-keys
   ```

4. **Get Credentials for Kubectl**:
   To connect the local `kubectl` with the newly created AKS cluster, run:
   ```bash
   az aks get-credentials --resource-group myResourceGroup --name myAKSCluster
   ```

5. **Test the Connection**:
   ```bash
   kubectl get nodes
   ```

---

### Step 2: Create Dockerfile in Your Repository

In your repository, create a `Dockerfile` to define the image that you want to build.

**Example `Dockerfile`:**
```dockerfile
# Use official OpenJDK as the base image
FROM openjdk:11-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file into the container
COPY target/my-spring-boot-app.jar /app/my-spring-boot-app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "my-spring-boot-app.jar"]
```

Make sure the file is at the root of your repository.

---

### Step 3: Set Up Docker Hub Credentials in Azure DevOps

1. Go to your **Azure DevOps project**.
2. Under **Project Settings**, go to **Service connections**.
3. Click **New service connection** > **Docker Registry**.
4. Select **Docker Hub**, and provide your Docker Hub username and password (or an access token).
5. Name the service connection (e.g., `dockerHubConnection`).

---

### Step 4: Create the Azure DevOps Pipeline

In your Azure DevOps repository, create a file named `azure-pipelines.yml` at the root. This pipeline will:

1. Build the Docker image.
2. Push the image to Docker Hub.
3. Deploy the image to the AKS cluster.

**azure-pipelines.yml:**

```yaml
trigger:
  branches:
    include:
      - main  # or master, depending on your branch

pool:
  vmImage: 'ubuntu-latest'

variables:
  dockerRegistryServiceConnection: 'dockerHubConnection' # Docker Hub service connection name
  imageRepository: '<your-dockerhub-username>/my-app' # DockerHub repository
  containerRegistry: 'docker.io'
  dockerHubId: '<your-dockerhub-username>' # Docker Hub username
  tag: '$(Build.BuildId)'
  kubernetesNamespace: 'default'
  kubernetesCluster: 'myAKSCluster' # Name of your AKS cluster
  resourceGroup: 'myResourceGroup'

steps:
  # Step 1: Checkout Code
  - task: Checkout@1

  # Step 2: Build Docker Image
  - task: Docker@2
    displayName: Build Docker Image
    inputs:
      containerRegistry: $(dockerRegistryServiceConnection)
      repository: $(imageRepository)
      command: build
      Dockerfile: '**/Dockerfile'
      tags: |
        $(tag)

  # Step 3: Push Docker Image to DockerHub
  - task: Docker@2
    displayName: Push Docker Image to DockerHub
    inputs:
      containerRegistry: $(dockerRegistryServiceConnection)
      repository: $(imageRepository)
      command: push
      tags: |
        $(tag)

  # Step 4: Deploy to AKS
  - task: AzureCLI@2
    inputs:
      azureSubscription: '<your-azure-service-connection>'
      scriptType: bash
      scriptLocation: inlineScript
      inlineScript: |
        # Configure kubectl to use the AKS cluster
        az aks get-credentials --resource-group $(resourceGroup) --name $(kubernetesCluster)

        # Deploy the application
        kubectl set image deployment/my-app my-app=$(imageRepository):$(tag)
      addSpnToEnvironment: true
```

### Explanation:

- **Step 1**: Checkout the source code.
- **Step 2**: Build the Docker image using the `Dockerfile` from the repository.
- **Step 3**: Push the built image to Docker Hub using the provided service connection.
- **Step 4**: Deploy the image to the Azure Kubernetes Service (AKS) cluster using `kubectl`.

---

### Step 5: Kubernetes Deployment Manifest

Create a `k8s-deployment.yml` file in your repository to define how your application should be deployed to AKS.

**Example `k8s-deployment.yml`:**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app
  labels:
    app: my-app
spec:
  replicas: 2
  selector:
    matchLabels:
      app: my-app
  template:
    metadata:
      labels:
        app: my-app
    spec:
      containers:
        - name: my-app
          image: <your-dockerhub-username>/my-app:latest
          ports:
            - containerPort: 8080
---
apiVersion: v1
kind: Service
metadata:
  name: my-app-service
spec:
  selector:
    app: my-app
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8080
  type: LoadBalancer
```

- **Deployment**: Specifies how many replicas (pods) of your app should be running and what Docker image to use.
- **Service**: Exposes the application via a load balancer.

---

### Step 6: Running the Pipeline

1. Push your code to the repository (this should trigger the pipeline if the branch is configured correctly).
2. The pipeline will:
   - Build the Docker image from the `Dockerfile`.
   - Push the image to Docker Hub.
   - Deploy the image to your Azure Kubernetes Service (AKS).

3. After the deployment, the application will be accessible via the load balancer IP, which you can get by running:
   ```bash
   kubectl get svc my-app-service
   ```

   Look for the **EXTERNAL-IP** field in the output.

---

### Optional: Test Locally

You can also run the application locally using Docker to verify that everything works before pushing to Azure.

1. **Build and Run Locally**:
   ```bash
   docker build -t my-app .
   docker run -p 8080:8080 my-app
   ```

2. **Test Application**:
   Access the application on `http://localhost:8080`.

This guide should help you set up the full pipeline in Azure DevOps to build, push, and deploy a Docker image to AKS. Let me know if you need further clarification!