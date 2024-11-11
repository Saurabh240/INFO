## Redis Setup

- A Redis container was deployed as part of a Docker Compose stack.
- Redis is accessible to both applications, with all its configuration and data stored under the path: /var/www/redis.

## Bahrainmenus Application

- The application has been migrated to the new path: /var/www/bahrainmenus.flyoapp.com.
- It is now running inside a Docker container, with Nginx handling the port forwarding on the host.

## Qanaar Application

- The Qanaar app is also running in a Docker container, with Nginx managing the host's port forwarding.

## Redis Connectivity Verification

To verify Redis connectivity from the application containers, you can follow these steps:

### 1. Enter the container:

docker exec -it <container_name> bash

### 2. Run Redis CLI:

redis-cli -h redis -p 6379 ping

### Expected Output:

If everything is working, the output will be:
PONG

### Redis Connection Details:

- Redis Host: redis
- Redis Port: 6379


please ensure to ask about:
1. Redis connection details in code.
2. ⁠check redis connectivity in website.
3. ⁠Code issue in qanaar.com 