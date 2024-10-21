## Deploying Spring boot web application on Kubernetes (https://www.youtube.com/watch?v=7o7e8OAAWyg)

minikube start

minikube status

eval $(minikube docker-env)

docker buiid -t ${app_name:version} .

## Create a deployment file like deployment.yaml

apiVersion: apps/v1
kind: Deployment # Kubernetes resource kind we are creating
metadata:
name: spring-boot-k8s
spec:
selector:
matchLabels:
app: spring-boot-k8s
replicas: 2 # Number of replicas that will be created for this deployment
template:
metadata:
labels:
app: spring-boot-k8s
spec:
containers: - name: spring-boot-k8s
image: springboot-k8s-example:1.0 # Image that will be used to containers in the cluster
imagePullPolicy: IfNotPresent
ports: - containerPort: 8080 # The port that the container is running on in the cluster

kubectl apply -f deployment.yaml

kubectl get deployments

kubectl get pods

kubectl logs ${pod_name}

## Service plays the role of sevice discovery as well as it acts as a load balancer

apiVersion: v1 # Kubernetes API version
kind: Service # Kubernetes resource kind we are creating
metadata: # Metadata of the resource kind we are creating
name: springboot-k8s-svc
spec:
selector:
app: spring-boot-k8s
ports: - protocol: "TCP"
port: 8080 # The port that the service is running on in the cluster
targetPort: 8080 # The port exposed by the service
type: NodePort # type of the service.

kubectl apply -f service.yaml

kubectl get service

kubectl get nodes -o wide

minikube dashboard

## spring boot with crud

apiVersion: apps/v1
kind: Deployment
metadata:
name: springboot-crud-deployment
spec:
selector:
matchLabels:
app: springboot-k8s-mysql
replicas: 3
template:
metadata:
labels:
app: springboot-k8s-mysql
spec:
containers: - name: springboot-crud-k8s
image: springboot-crud-k8s:1.0
ports: - containerPort: 8080
env: # Setting Enviornmental Variables - name: DB_HOST # Setting Database host address from configMap
valueFrom :
configMapKeyRef :
name : db-config
key : host

            - name: DB_NAME  # Setting Database name from configMap
              valueFrom :
                configMapKeyRef :
                  name : db-config
                  key :  dbName

            - name: DB_USERNAME  # Setting Database username from Secret
              valueFrom :
                secretKeyRef :
                  name : mysql-secrets
                  key :  username

            - name: DB_PASSWORD # Setting Database password from Secret
              valueFrom :
                secretKeyRef :
                  name : mysql-secrets
                  key :  password

---

apiVersion: v1 # Kubernetes API version
kind: Service # Kubernetes resource kind we are creating
metadata: # Metadata of the resource kind we are creating
name: springboot-crud-svc
spec:
selector:
app: springboot-k8s-mysql
ports: - protocol: "TCP"
port: 8080 # The port that the service is running on in the cluster
targetPort: 8080 # The port exposed by the service
type: NodePort # type of the service.

####################################

# Define a 'Persistent Voulume Claim'(PVC) for Mysql Storage, dynamically provisioned by cluster

apiVersion: v1
kind: PersistentVolumeClaim
metadata:
name: mysql-pv-claim # name of PVC essential for identifying the storage data
labels:
app: mysql
tier: database
spec:
accessModes: - ReadWriteOnce #This specifies the mode of the claim that we are trying to create.
resources:
requests:
storage: 1Gi #This will tell kubernetes about the amount of space we are trying to claim.

---

# Configure 'Deployment' of mysql server

apiVersion: apps/v1
kind: Deployment
metadata:
name: mysql
labels:
app: mysql
tier: database
spec:
selector: # mysql Pod Should contain same labels
matchLabels:
app: mysql
tier: database
strategy:
type: Recreate
template:
metadata:
labels: # Must match 'Service' and 'Deployment' selectors
app: mysql
tier: database
spec:
containers: - image: mysql:5.7 # image from docker-hub
args: - "--ignore-db-dir=lost+found" # Workaround for https://github.com/docker-library/mysql/issues/186
name: mysql
env: - name: MYSQL_ROOT_PASSWORD
valueFrom :
secretKeyRef :
name : mysql-secrets
key : password

            - name: MYSQL_DATABASE # Setting Database Name from a 'ConfigMap'
              valueFrom :
                configMapKeyRef :
                  name : db-config
                  key :  dbName


          ports:
            - containerPort: 3306
              name: mysql
          volumeMounts:        # Mounting voulume obtained from Persistent Volume Claim
            - name: mysql-persistent-storage
              mountPath: /var/lib/mysql #This is the path in the container on which the mounting will take place.
      volumes:
        - name: mysql-persistent-storage # Obtaining 'vloume' from PVC
          persistentVolumeClaim:
            claimName: mysql-pv-claim

---

# Define a 'Service' To Expose mysql to Other Services

apiVersion: v1
kind: Service
metadata:
name: mysql # DNS name
labels:
app: mysql
tier: database
spec:
ports: - port: 3306
targetPort: 3306
selector: # mysql Pod Should contain same labels
app: mysql
tier: database
clusterIP: None # We Use DNS, Thus ClusterIP is not relevant

===============================================================

## ConfigMap

apiVersion : v1
kind : ConfigMap
metadata:
name : db-config
data:
host : mysql
dbName: javatechie

## Mysql secrets

apiVersion : v1
kind : Secret
metadata:
name : mysql-secrets
data:
username : cm9vdA==
password : cm9vdA==

#### Kubectl commands

kubectl get pods

kubectl describe pod <podname>


ReplicaSet for scaling and load balancing the pods

kubectl create -f replicaset-definition.yaml

kubectl get replicaset

kubectl delete replicaset myapp-replicaset (also deletes the pod created using replica set)

kubectl replace -f replicaset-definition.yaml

kubectl scale --replicas=6 -f replicaset-definition.yaml

Deployment creates replicaset and pods both

kubectl create -f deployment.yaml

kubectl create -f deployment.yaml --record (to record the changes in deployment)

kubectl get deployment

kubectl apply -f deployment.yaml

kubectl rollout undo deployment/myapp-deployment

If your service is of type LoadBalancer and you want to simulate cloud-like behavior, you can start a tunnel that routes traffic to the service's external port.
minikube tunnel

minikube service react-frontend-service

minikube service react-frontend-service --url




