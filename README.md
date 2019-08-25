# Projeto CRUD de cadastro de clientes

A arquitetura do projeto foi montado utilizando as seguintes tecnologias:

* Spring Boot - para construção do backend
* H2 - Base de dados em memoria para persistência dos clientes.
* ReactJS com material-design - Para construção da interface visual
* Swagger - Usado para docuemntar os serviços


## Instruções para executar o projeto


Após realizar o download do projeto, executar os seguintes comandos:

```sh
 mvn clean package
```
Este comando acima irá compilar os pacotes da aplicação java e do frontend

Após o término da execução executar o comando:

```sh
docker-compose up
```

Caso deseje que se execute em background adicionar o parametro -d ao final:

```sh
docker-compose up -d
```

Caso seje necesário refazer o build utilizar o seguinte comando:

```sh
docker-compose up --build
```

Este comando irá subir todas as imagens dos projetos em containers dockers.


Caso deseje pode-se subir cada projeto separadamente também, no caso do projeto curd-cliente-frontend será necessário acessar a pasta e eecutar o comando:

```sh
npm install
```

O console do banco h2 está liberado para acesso. Em JDBC URL utilizar **jdbc:h2:mem:application** e as credencias de usuário e senha são:  usuário **sa** e senha **sa**

Endereços disponíveis:

* [Documentação serviços para o frontend](http://localhost:8080/swagger-ui.html)

* [Aplicação frontend](http://localhost:3000)

* [Acesso ao console do H2](http://localhost:8080/h2-console)


#### Considerações Finais

* Foi utilizado o conceito JWT junto ao Spring security para acesso aos recursos de clientes.

* Em relação ao controle de acesso, o ideal é se utilizar um gateway para gerenciar a autenticação junto com um service discovery.

