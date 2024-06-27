# Users API

## Propósito

O propósito deste repositório é oferecer um exemplo de autorização (usando API Key) e autenticação de usuário (usando JWT), em Spring.

## Como foi feito
A autorização com a API Key é feita através de um filtro customizado.
A autenticação de usuário é feita com a geração de um JWT a partir do registro de seu email e senha no banco de dados (PostgresSQL).
O repositório conta com um SecretKeyGenerator usado tanto para gerar a SecretKey do JWT quanto para gerar a API Key.

## Requisitos

- Java 17
- Maven
- IDE (caso use Visual Studio Code, a extensão Spring Dashboard é recomendada)

## Instruções

### Clone o repositório:
  ```bash
git clone https://github.com/seu-usuario/users-api.git
cd users-api
```

### Crie um database no PostgresSQL. Você pode usar a linah de comando, se usar psql ou uma interface gráfica como pgAdmin ou DBeaver.

```
  psql -U postgres <OU O SEU USUÁRIO>
```
adicione a senha

```
CREATE DATABASE <NOME DO SEU DB>;
```

### Crie o arquivo application.properties na pasta resources:

```
spring.application.name=users

# server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/<NOME DO SEU DB>
spring.datasource.username=postgres <OU O SEU USUARIO PADRÂO>
spring.datasource.password=<SENHA DO POSTGRES
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=update

springdoc.swagger-ui.url=swagger.yaml

security.jwt.token.secret-key=<UMA CHAVE SECRETA PARA CODIFICAÇÂO DO JWT. VOCÊ PODE USAR O SecretKeyGenerator NA PASTA /util>
security.jwt.token.expire-length=3600000 <AQUI MARCAMOS O TOKEN COM EXPIRAÇÃO DE 1 HORA, MUDE CASO NECESSÁRIO>

security.api.key.secret=<A API KEY REGISTRADA 
```

### Execute a aplicação usando a IDE (ou uma extensão como Spring Dashboard, no Visual Studio Code)

```bash
mvn spring-boot:run
```
## Confira os endpoints e a Documentação da API
A documentação da API está disponível no Swagger. Após iniciar a aplicação, você pode acessar a documentação em:
```bash
http://localhost:8080/swagger-ui.html
```
