# Wallet Challenge Backend

Este projeto consiste em uma aplicação backend desenvolvida para o desafio "Wallet Challenge", implementando funcionalidades essenciais de uma carteira digital.

---

## Tecnologias Utilizadas

As seguintes tecnologias foram empregadas no desenvolvimento deste projeto:

* **Spring Boot**: Framework robusto para o desenvolvimento de aplicações Java.
* **Spring MVC**: Módulo do Spring para a construção de aplicações web e RESTful.
* **Spring Data JDBC**: Facilita a interação com bancos de dados JDBC.
* **Spring Data JPA**: Facilita a implementação de repositórios baseados em JPA.
* **Spring for Apache Kafka**: Integração com o Apache Kafka para mensageria assíncrona.
* **Docker Compose**: Ferramenta para definir e executar aplicações Docker multi-container.
* **H2 Database**: Banco de dados relacional em memória para desenvolvimento e testes.

---

## ⚙️ Como Executar o Projeto

Siga os passos abaixo para colocar a aplicação em funcionamento:

### 1. Clonar o Repositório

Primeiro, clone o repositório do projeto para sua máquina local:

```bash
git clone [https://github.com/Ronaldop19/wallet-challange.git](https://github.com/Ronaldop19/wallet-challange.git)
```

### Navegue até o repositório clonado
```bash
cd wallet-challange
```

2. Iniciar o Kafka com Docker Compose
Este projeto utiliza o Kafka para processamento de mensagens. Certifique-se de ter o Docker e o Docker Compose instalados. No diretório raiz do projeto, execute:
```bash
docker-compose up -d
```
### Este comando irá iniciar os serviços do Kafka (e suas dependências, como o ZooKeeper) em segundo plano. ###

3. Executar a Aplicação Spring Boot
Após o Kafka estar em execução, você pode iniciar a aplicação Spring Boot. Certifique-se de ter o Java (JDK 21 ou superior, geralmente) instalado.

Você pode executar a aplicação através de sua IDE (IntelliJ IDEA, Eclipse, VS Code) ou via linha de comando (se for um projeto Maven ou Gradle):

```bash
# Se for um projeto Maven:
mvn spring-boot:run

# Se for um projeto Gradle:
./gradlew bootRun
```

4. Acessar a Aplicação
Uma vez que a aplicação Spring Boot esteja rodando, você poderá acessá-la no seu navegador ou via ferramenta de API (como Postman/Insomnia) através do endereço:

http://localhost:8080


📞 Endpoints da API
A aplicação expõe os seguintes endpoints para gerenciamento de transações:
1. Criar uma Nova Transação (POST /transaction)
Este endpoint permite criar uma nova transação financeira entre dois usuários.

Método: POST

URL: http://localhost:8080/transaction

Corpo da Requisição (JSON):
Envie um objeto JSON com os campos value, payer (ID do usuário que está pagando) e payee (ID do usuário que está recebendo).

```bash
{
    "value": 100.0,
    "payer": 1,
    "payee": 2
}
```

2. Listar Todas as Transações (GET /transaction)
Este endpoint retorna uma lista de todas as transações registradas no sistema.

Método: GET

URL: http://localhost:8080/transaction
Entendido! Para complementar seu README.md, vamos adicionar uma seção sobre como interagir com a API, incluindo exemplos de requisições e respostas.

📞 Endpoints da API
A aplicação expõe os seguintes endpoints para gerenciamento de transações:

1. Criar uma Nova Transação (POST /transaction)
Este endpoint permite criar uma nova transação financeira entre dois usuários.

Método: POST

URL: http://localhost:8080/transaction

Corpo da Requisição (JSON):
Envie um objeto JSON com os campos value, payer (ID do usuário que está pagando) e payee (ID do usuário que está recebendo).

JSON
```bash
{
	"payer": 1,
	"payee": 2,
	"value": 100
}
```
- Exemplo de Resposta (HTTP 200 OK):
```bash
{
    "id": "c5c63f45-b307-4c70-b3ca-f6a33667a93b",
    "payer": 1,
    "payee": 2,
    "value": 100.00,
    "createdAt": "2025-07-24T14:27:11.0440109"
}
```
createdAt: Timestamp da criação da transação.

id: ID único da transação gerado pelo sistema.

payee: ID do usuário que recebeu o valor.

payer: ID do usuário que pagou o valor.

value: Valor da transação.


- Exemplo de Resposta (HTTP 403 Forbidden) quando a transição não for autorizada pelo serviço externo consultado:
```bash
Transaction not authorized - Reason: Service returned 403 Forbidden: Transaction[id=null, payer=1, payee=2, value=100.00, createdAt=null]
```

- Exemplo de Resposta (HTTP 400 Bad Request):
```bash
Invalid transaction: Transaction[id=null, payer=1, payee=2, value=10000.00, createdAt=null]
```

2. Listar Todas as Transações (GET /transaction)
Este endpoint retorna uma lista de todas as transações registradas no sistema.

Método: GET

URL: http://localhost:8080/transaction

- Exemplo de Resposta (HTTP 200 OK):

```bash
[
    {
        "id": "e176f1ab-96b7-4a2c-b8da-0b19c3d01b85",
        "payer": 1,
        "payee": 2,
        "value": 100.00,
        "createdAt": "2025-07-24T14:25:20.489284"
    },
    {
        "id": "f008021a-6487-48cb-b4b2-a233ec1dd205",
        "payer": 1,
        "payee": 2,
        "value": 100.00,
        "createdAt": "2025-07-24T14:25:23.955985"
    }
]
```
A resposta é um array de objetos, onde cada objeto representa uma transação com os mesmos campos descritos acima.


📝 Observações:
Certifique-se de que a porta 8080 não esteja sendo utilizada por outra aplicação.

O banco de dados H2 é em memória, o que significa que os dados serão perdidos ao reiniciar a aplicação.
