# orders
Projeto desafio orders
Gerenciamento de Pedidos

📋 Descrição do Projeto
Este projeto implementa um sistema para o gerenciamento de pedidos (Order Service) que realiza:

Recebimento de pedidos: integrado ao Produto Externo A para receber os pedidos.
Cálculo do valor total: soma o valor de todos os itens do pedido.
Consulta e envio de pedidos processados: disponibiliza os pedidos processados com seus respectivos status para o Produto Externo B.
Escalabilidade: considerando um volume diário de 150 mil a 200 mil pedidos.

🛠️ Tecnologias Utilizadas
- Java 17: Linguagem base para o desenvolvimento do serviço.
- Spring Boot: Framework para simplificar o desenvolvimento com Java.
- Módulos utilizados:
- Spring Web: Exposição de APIs REST.
- Spring Data JPA: Persistência de dados com integração ao banco de dados.
- Spring AMQP: Comunicação com o RabbitMQ.
- RabbitMQ: Fila para comunicação assíncrona entre sistemas.
- PostgreSQL: Banco de dados relacional para armazenar os pedidos.
- Docker: Containerização do projeto e serviços auxiliares.

⚙️ Estrutura do Projeto
📂 Componentes Principais
Order Service:
- Gerencia os pedidos recebidos.
- Calcula o valor total dos itens no pedido.
- Atualiza o status do pedido.

RabbitMQ:
- Fila para comunicação com sistemas externos (Produto A e Produto B).

PostgreSQL:
- Armazena informações sobre pedidos e seus status.

Produto Externo A:
- Envia pedidos ao sistema.

Produto Externo B:
- Recebe os pedidos processados pelo sistema.

🚀 Como Executar o Projeto
Pré-requisitos
- Docker e Docker Compose instalados.
- Ambiente configurado para rodar aplicações em contêineres.

Passo a Passo

Clone o repositório:

bash
git clone https://github.com/seu-repositorio/orders-service.git
cd orders-service
Compile o projeto:

bash
mvn clean package
Suba os contêineres com Docker Compose:

bash
Copiar código
docker-compose up --build
O sistema estará disponível em: http://localhost:8080

🗄️ Estrutura do Código
Principais Pacotes
controller: Contém os endpoints da API REST.
service: Lógica de negócios.
model: Entidades do banco de dados.
dto: Objetos de transferência de dados.
repository: Interface para persistência de dados.
config: Configurações de RabbitMQ, banco de dados, etc.
📋 Endpoints Disponíveis
Pedidos
Criar Pedido

POST /orders
Corpo da Requisição (JSON):
json
Copiar código
{"total_value": 10,"status": "PENDING","items": [{"productName": "","price": 5000.0,"quantity": 3 }]}
Resposta:
json
Copiar código
{
    "id": "d1446247-b08a-48a4-847c-9022d1306995",
    "totalValue": 0.0,
    "status": "PENDING",
    "items": [
        {
            "id": "1c2c640b-ec7c-4986-912b-172757cee245",
            "productName": "",
            "price": 5000.0,
            "quantity": 3
        }
    ]
}

Consultar Pedido

GET /orders/{id}
Resposta:
json
Copiar código
{
    "id": "d1446247-b08a-48a4-847c-9022d1306995",
    "totalValue": 0.0,
    "status": "PENDING",
    "items": [
        {
            "id": "1c2c640b-ec7c-4986-912b-172757cee245",
            "productName": "",
            "price": 5000.0,
            "quantity": 3
        }
    ]
}

🧪 Testes
Para executar os testes do projeto:

- bash

mvn test

🐳 Docker Compose
O projeto utiliza um arquivo docker-compose.yml para orquestrar os serviços. Aqui estão os principais contêineres:

orders-app: Serviço principal (Spring Boot).
rabbitmq: Fila de mensagens.
postgres-db: Banco de dados relacional.

📈 Escalabilidade
O sistema foi projetado para processar até 200 mil pedidos por dia, utilizando o RabbitMQ para garantir a escalabilidade e desacoplamento entre serviços.
