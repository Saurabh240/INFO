Containers and Orchastration

Containers are the completed isolated environment provider

Container vs Images

Images are the template using which we create containers
  while containers are the running instances

The process of automatically deploying and managing the container and scale them is orchastration


Node : A node is a physical or virtual machine on which kubernetes is deployed

Cluster : A cluster is a set of nodes group together

minikube start

minkube status

kubectl get po -A

kubectl create deployment hello-minikube --image=kicbase/echo-server:1.0

kubectl get deployments

kubectl expose deployment hello-minikube --type=NodePort --port=8080

kubectl get services hello-minikube

minikube service hello-minikube

minikube service hello-minikube --url

kubectl port-forward service/hello-minikube 7080:8080
Tada! Your application is now available at http://localhost:7080/.

minikube pause

minikube unpause

minikube stop

Change the default memory limit (requires a restart):
minikube config set memory 9001

Browse the catalog of easily installed Kubernetes services:
minikube addons list

minikube delete --all


minikube start -p aged --kubernetes-version=v1.16.1

kubectl delete services hello-minikube

kubectl delete deployment hello-minikube



kubectl run nginx --image=nginx

kubectl get pods

kubectl describe pod nginx

kubectl get pods -o wide






