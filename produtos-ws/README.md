## Executando em container
```
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/produtos-ws .
docker run -it --rm --name=produtos-ws -p 8090:8090 quarkus/produtos-ws
```
