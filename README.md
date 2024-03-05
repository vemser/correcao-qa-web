<h1 align="center"> 
Testes API Correção
</h1>

<p align="center">
  <img alt="GitHub language count" src="https://img.shields.io/github/languages/count/vemser/correcao-qa-web?color=%2304D361">

  <img alt="GitHub Repository size" src="https://img.shields.io/github/repo-size/vemser/correcao-qa-web">
  
  <a href="https://github.com/vemser/correcao-qa-web/commits/main">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/vemser/correcao-qa-web">
  </a>
  
  <img alt="License MIT" src="https://img.shields.io/badge/license-MIT-brightgreen">

  <img alt="Status Em Desenvolvimento" src="https://img.shields.io/badge/status-em%20desenvolvimento-green">
</p>

<p align="center">
  <a href="#-sobre-o-projeto">Sobre o projeto</a> •
  <a href="#-ferramentas">Ferramentas</a> •
  <a href="#-integrantes">Integrantes</a>
</p>

## 💻 Sobre o Projeto

Esse projeto é dedicado à implementação e armazenamento de testes para a API do sistema Correção. Trata-se de testes funcionais e de integração destinados a verificar o comportamento e a robustez da API em diferentes cenários. Desde testes básicos de CRUD (Create, Read, Update, Delete) até casos mais complexos de autenticação, manipulação de dados e interações com outros sistemas, todos os aspectos da API são minuciosamente testados para garantir sua qualidade e confiabilidade. O projeto busca assegurar que a API opere de forma consistente e sem problemas, contribuindo para uma experiência de usuário fluida e satisfatória.

## 📑 Documentos

- **[Plano de Teste](https://docs.google.com/document/d/1SxLgyNhLnXAYeXYCTrsq6NWP6tMdFnrjHGIxlEaN9G4/edit)**
- **[Teste Exploratórios](https://docs.google.com/document/d/1SKQBuc9KmqkJDnJjGq9zEj3r1nSP5qJZxXwKIN-kibs/edit)**
- **[Pesquisa de Projeto](https://docs.google.com/forms/d/1_6oCTheYJjrXvQU1D5m4M7oordWl4G608pYQvKzt2mA/edit#responses)**
- **[Trello](https://trello.com/b/pPZydmbX/vem-ser-corre%C3%A7%C3%A3o)**

## 🛣️ Como executar

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas: [IntelliJ IDEA](https://www.jetbrains.com/idea/) e [JDK 17](https://www.oracle.com/java/technologies/downloads/).

### Clonar o repositório

```bash

# Clone este repositório
$ git clone https://github.com/vemser/correcao-qa-ui.git

# Acesse a pasta do projeto no seu terminal/cmd
$ cd correcao-qa-ui

```

### Adicionar as propriedades

```properties

# Crie um arquivo configsettings.properties na pasta ./src/main/resources

CompiladorURL={COMPILADOR_URL}
ApiURL={API_URL}
AlunoUsername={ALUNO_USERNAME}
AlunoPassword={ALUNO_PASSWORD}
InstrutorUsername={INSTRUTOR_USERNAME}
InstrutorPassword={INSTRUTOR_PASSWORD}

```

```properties
# Crie um arquivo allure.properties na pasta ./src/test/resources

allure.results.directory=target/allure-results

```

### Executar os testes

```bash

# Execute os teste com o Maven
$ mvn clean test

```

### Gerar relatório

```bash

# Após a execução dos testes, gere um relatório
$ mvn allure:serve

```

## 🛠 Ferramentas

<details>
  <summary><b>Tecnologias</b></summary>
  
  - **[REST Assured](https://rest-assured.io/)**
  - **[JUnit5](https://junit.org/junit5/)**
  - **[Docker](https://www.docker.com/)**
  - **[Postman](https://www.postman.com/)**
  - **[Jenkins](https://www.jenkins.io/)**
  - **[Allure Report](https://allurereport.org/)**
  - **[IntelliJ IDEA](https://www.jetbrains.com/idea/)**
  - **[JDK 17](https://www.oracle.com/java/technologies/downloads/)**

</details>

<details>
  <summary><b>Organização e Documentação</b></summary>
  
  - **[Google Docs](https://docs.google.com/)**
  - **[Trello](https://trello.com/)**
  - **[Discord](https://discord.com/)**
  
</details>

## 👥 Integrantes

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/brayanbenet">
        <img src="https://avatars.githubusercontent.com/u/63371569?v=4" width="100px"/><br>
        <sub>
          <b>Brayan Gabriel</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/gabrielvendas">
        <img src="https://avatars.githubusercontent.com/u/115078106?s=400&u=5d4b1146a08b63a3c82e11417f096dda0087c1ac&v=4" width="100px"/><br>
        <sub>
          <b>Gabriel Sales</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/ItaloLacerda">
        <img src="https://avatars.githubusercontent.com/u/99690658?v=4" width="100px;" /><br>
        <sub>
          <b>Italo Lacerda</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Luh-Santos">
      <img src="https://avatars.githubusercontent.com/u/79276231?v=4" width="100px;" /><br>
        <sub>
          <b>Luísa dos Santos</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/VitorColombo">
      <img src="https://avatars.githubusercontent.com/u/110260819?v=4" width="100px;" /><br>
        <sub>
          <b>Vitor Nunes</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
<br/>
