docker build -t backend .

docker tag <local-image> <your-dockerhub-username>/<image-name>:<tag>
docker tag backend:latest saurabh896/backend:latest

docker push saurabh896/backend:latest
