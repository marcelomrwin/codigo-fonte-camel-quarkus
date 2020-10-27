### Iniciar MySQL
```
docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=r3dh4t1! -d mysql:latest
```

### Conectar com MySQL
Utilize um cliente mysql.

```
mysql -P 3306 --protocol=tcp -u root -p
```
Insira o password `r3dh4t1!`

### Criar a base de dados
```
create database clientes character set utf8;
use clientes;
```

### Criar tabelas
```
create table clientes ( cpf varchar(14) PRIMARY KEY, nome varchar(100), email varchar(100), endereco varchar(100));
```

### Popular tabelas
```
insert into clientes values ('123.123.123-00','Matheus','matheus@email.com','RUA XYZ 123'),('321.321.321-99','Marcos','marcos@email.com','RUA ABC 321'),('000.000.000-55','Lucas','lucas@email.com','RUA VYZ 555'),('001.100.777-40','João','joao@email.com','RUA ZZZ 500');
```

Saia do cliente mysql com `exit`

### Execute um dump da base mysql
```
docker exec mysql sh -c 'exec mysqldump --databases clientes -p"r3dh4t1!"' > mysqldir/clientes.sql
```

### Execute um novo container mysql com os dados pré carregados
```
[mac]
docker run --name mysql -v /Users/marcelosales/desenvolvimento/workshops/codigofonte/camel/mysqldir:/docker-entrypoint-initdb.d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=r3dh4t1! -d mysql:latest

[fedora]
docker run --name mysql -v /home/masales/desenvolvimento/workshops/codigo-fonte-camel-quarkus/mysqldir:/docker-entrypoint-initdb.d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=r3dh4t1! -it --rm mysql:latest

```

## Criando projeto Camel Quarkus
```
mvn io.quarkus:quarkus-maven-plugin:1.9.0.Final:create -DprojectGroupId=com.redhat -DprojectArtifactId=camel-codigo-fonte -DclassName="com.redhat.camel.rest.ClientesResource" -Dpath="/clientes" -Dextensions=camel-quarkus-log,camel-quarkus-timer
```

### Adicionando extensão SQL
```
./mvnw quarkus:add-extension -Dextensions="agroal,jdbc-mysql,camel-quarkus-sql"
```

### Adicionando extensão direct
```
./mvnw quarkus:add-extension -Dextensions=camel-quarkus-direct
```
### Adicionando extensão openapi e rest
```
./mvnw quarkus:add-extension -Dextensions=camel-quarkus-jackson,camel-quarkus-rest,camel-quarkus-rest-openapi,camel-quarkus-openapi-java,quarkus-swagger-ui
```

## Gerar projeto de produtos
```
mvn archetype:generate -DarchetypeGroupId=org.apache.cxf.archetype -DarchetypeArtifactId=cxf-jaxws-javafirst -DarchetypeVersion=3.4.0
```
## Keycloak
```
docker run -p 9000:8080 --name keycloak -it --rm -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin quay.io/keycloak/keycloak:11.0.2
```

### Obter token Keycloak
```
curl -v -d "client_id=cliente" -d "username=masales@redhat.com" -d "password=r3dh4t1\!" -d "grant_type=password" -X POST "http://localhost:9000/auth/realms/camel/protocol/openid-connect/token" | jq -r .access_token
```

## Criando projeto Novo Sistema Quarkus
```
mvn io.quarkus:quarkus-maven-plugin:1.9.0.Final:create -DprojectGroupId=com.redhat -DprojectArtifactId=codigo-fonte-pedidos -DclassName="com.redhat.rest.PedidosResource" -Dpath="/pedidos" -Dextensions="resteasy-jsonb,quarkus-smallrye-openapi"
```