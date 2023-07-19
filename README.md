# Teste Técnico Sicred Sobre Testes de API

Este projeto foi elaborado pensando em garantir uma cobertura de testes para as APIs (Gerenciamento de produtos).

##  Requisitos
* Java 11+ JDK deve estar instalado
* Maven deve estar instalado e configurado no path da aplicação
* Allure report deve estar instalado e configurado na máquina

## Como executar os testes
É Possivel executar pela propria IDE, selecionado qual Teste deseja executar e simplismente dar play no método <br>
do teste ou podera executar pela linha de comando atraves do comando:<br>

Rodar todos os testes<br>
```bash
mvn test 
```
Rodar teste especifico utilizando o nome do método<br>
```bash
mvn test -Dtest="TestSicred#t01_shouldGetUsers"
```

## Como startar o allure server
Executar o comando abaixo:<br>
allure serve /path/to/project/target/surefire-reports/

## O que foi entregue?
* Testes funcionais (Positivos e negativos)
* Report com allure report e log (log4j)
* Relatório de bugs e melhorias (relatorio-bugs-melhorias-encontrado.docx)na raiz do projeto


