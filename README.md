# Projeto de estudo MVC java de Raphael Mendes Stopa

## Rodando o projeto

Este projeto foi desenvolvido, usando uma técnica para desenvolvimento ágil que usa um Docker de Mysql com migrations do liquibase. Para rodar o Docker use o comando abaixo:

```
docker-compose -p “project_raphael” -f src/main/docker/mysql.yml up -d
```

Na primeira inicialização, pode demorar um pouco. Por favor, seja paciente. Dependendo da IDE, pode ser necessário um pequeno ajuste para melhor conexão com o banco de dados. String de conexão: jdbc:mysql://localhost:3306/gft?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true


## Atenção

Validei este projeto com o Spring Security. Para poder logar use:

```
UserName: admin
Password: admin
```

Estes dados estarão automaticamente no banco de dados graças ao liquibase.
