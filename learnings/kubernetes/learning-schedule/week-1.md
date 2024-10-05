### **Week 1: Kubernetes Basics – Detailed Guide**

#### **Day 1: Introduction to Kubernetes**

##### **1.1 What is Kubernetes?**
Kubernetes (K8s) is an open-source container orchestration platform. It automates deployment, scaling, and management of containerized applications. Key concepts include:
- **Nodes**: Machines (virtual or physical) that run your application in containers.
- **Pods**: Smallest deployable units in Kubernetes that encapsulate containers.
- **Services**: Expose your application to the network, internally or externally.

##### **1.2 Kubernetes Architecture**
- **Master Node**: Manages the cluster (API server, etcd, scheduler, controller).
- **Worker Nodes**: Run the actual applications inside pods.

##### **1.3 Setting Up Minikube**
Minikube is a tool that lets you run Kubernetes locally.
- Install [Minikube](https://minikube.sigs.k8s.io/docs/start/) for your system.
- Start a local Kubernetes cluster:
  ```bash
  minikube start
  ```

##### **1.4 Basic `kubectl` Commands**
`kubectl` is the command-line tool for interacting with Kubernetes clusters.

- **Create a pod**:
  ```bash
  kubectl run myapp --image=nginx --port=80
  ```

- **Get resources**:
  ```bash
  kubectl get pods
  kubectl get nodes
  ```

- **Describe a resource**:
  ```bash
  kubectl describe pod myapp
  ```

- **Delete a resource**:
  ```bash
  kubectl delete pod myapp
  ```

---

#### **Day 2: Pods and Deployments**

##### **2.1 Pod Lifecycle**
- **Pending**: Pod created, waiting for resources.
- **Running**: Pod successfully scheduled.
- **Succeeded**: Pod completed its work.
- **Failed**: Pod ran and failed.
- **CrashLoopBackOff**: Pod failed, trying to restart.

##### **2.2 Deploying an Application with `kubectl`**
You can create, scale, and manage containers using **Deployments** in Kubernetes.

- Create a `deployment.yaml` file:
  ```yaml
  apiVersion: apps/v1
  kind: Deployment
  metadata:
    name: myapp-deployment
  spec:
    replicas: 3
    selector:
      matchLabels:
        app: myapp
    template:
      metadata:
        labels:
          app: myapp
      spec:
        containers:
        - name: myapp
          image: nginx
          ports:
          - containerPort: 80
  ```

- Deploy the application:
  ```bash
  kubectl apply -f deployment.yaml
  ```

- Check the running Pods:
  ```bash
  kubectl get pods
  ```

##### **2.3 Scaling a Deployment**
Scaling is managed by adjusting the `replicas` field in the Deployment manifest.

- Scale the deployment to 5 replicas:
  ```bash
  kubectl scale deployment myapp-deployment --replicas=5
  ```

- Verify the scaling:
  ```bash
  kubectl get deployments
  ```

---

#### **Day 3: Services and Networking**

##### **3.1 Kubernetes Networking**
Kubernetes provides several ways to expose Pods to external traffic or internally within the cluster.

- **ClusterIP**: Default, exposes the service inside the cluster.
- **NodePort**: Exposes the service on a port on each node.
- **LoadBalancer**: Uses a cloud provider's load balancer to expose the service.

##### **3.2 Exposing an Application with a Service**
Create a `service.yaml` to expose the application using NodePort:
```yaml
apiVersion: v1
kind: Service
metadata:
  name: myapp-service
spec:
  selector:
    app: myapp
  ports:
    - protocol: TCP
      port: 80
      targetPort: 80
      nodePort: 30007
  type: NodePort
```

- Apply the service:
  ```bash
  kubectl apply -f service.yaml
  ```

- Access the service:
  ```bash
  minikube service myapp-service
  ```

This command will open the service in your default web browser, allowing you to interact with the application.

---

### **Recap of Week 1:**
1. You’ve set up a Kubernetes cluster locally using Minikube.
2. You’ve learned the basics of `kubectl` to manage Pods and Deployments.
3. You scaled your application and exposed it using Kubernetes Services.

Each of these days provides practical experience with Kubernetes resources.