# 🚀 Event-Driven Notification System (Microservices Architecture)

This project demonstrates a scalable **Event-Driven Microservices Architecture** using **Spring Boot** and **Apache Kafka**, implementing asynchronous communication, fault tolerance with **Retry & Dead Letter Queue (DLQ)**, and inter-service communication using **Feign Client**.

---

# 🧠 Architecture Overview

```
User Service
     │
     │ (Feign Client - Sync Call)
     ▼
Notification Service (Producer)
     │
     │ (Publishes Event)
     ▼
Kafka Topic (notification-topic)
     │
     ▼
Email Service (Consumer)
     │
     ▼
Send Email + Update Status
```

---

# 🔥 Key Features

* ✅ Event-driven communication using Kafka
* ✅ Asynchronous notification processing
* ✅ Retry mechanism for failed events
* ✅ Dead Letter Queue (DLQ) for error handling
* ✅ Database persistence using Spring Data JPA
* ✅ Inter-service communication using Feign Client
* ✅ Externalized configuration using `.env`
* ✅ Scalable and loosely coupled architecture

---

# ⚙️ Tech Stack

* **Backend:** Spring Boot
* **Microservices:** Spring Cloud
* **Messaging:** Apache Kafka
* **Database:** MySQL
* **ORM:** Spring Data JPA
* **Communication:** Feign Client
* **Build Tool:** Maven
* **Config Management:** Environment Variables (.env)
* **Containerization (optional):** Docker

---

# 📂 Project Structure

```
parent-project/
│
├── .env                            # Shared environment variables (if needed)
├── common-lib/                     # Shared DTOs, enums, utilities
│   └── src/main/java/com/common/dto/
│       └── NotificationEvent.java
├── user-service/                   # Manages users
│   ├── .env                        # Service-specific env
│   ├── pom.xml
│   └── src/main/resources/
│       └── application.properties
├── notification-service/           # Produces Kafka events
│   ├── .env
│   ├── pom.xml
│   └── src/main/resources/
│       └── application.properties
├── email-service/                  # Consumes Kafka events, sends emails
│   ├── .env
│   ├── pom.xml
│   └── src/main/resources/
│       └── application.properties
└── docker-compose.yml              # Optional: for running all services + Kafka + MySQL

```

---

# 🔄 System Flow

1. **User Service**

   * Creates user / triggers notification
   * Calls notification-service via Feign

2. **Notification Service**

   * Stores notification in DB (status = PENDING)
   * Publishes event to Kafka topic

3. **Kafka**

   * Acts as message broker
   * Ensures asynchronous communication

4. **Email Service**

   * Consumes event
   * Sends email
   * Updates status → SENT / FAILED

---

# 🗄️ Database Design

### Notification Table

| Field     | Description             |
| --------- | ----------------------- |
| id        | Primary key             |
| eventId   | Unique event ID         |
| userId    | User identifier         |
| email     | User email              |
| message   | Notification message    |
| type      | EMAIL / SMS             |
| status    | PENDING / SENT / FAILED |
| timestamp | Event time              |

---

# 🛡️ Fault Tolerance

* 🔁 Retry mechanism for failed messages
* ☠️ Dead Letter Queue (DLQ) for unprocessed events
* 📌 At-least-once delivery guarantee

---

# 🔐 Environment Configuration

Create a `.env` file in root:

```
# Common
KAFKA_BOOTSTRAP=localhost:

# User Service
USER_SERVICE_PORT=

# Notification Service
NOTIFICATION_SERVICE_PORT=8081
NOTIFICATION_DB_URL=jdbc:mysql://localhost:3306/notification_db
NOTIFICATION_DB_USERNAME=
NOTIFICATION_DB_PASSWORD=

# Email Service
EMAIL_SERVICE_PORT=8082
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USERNAME=your-email@gmail.com
EMAIL_PASSWORD=your-app-password
```

---

# ▶️ How to Run the Project

## 1️⃣ Start Kafka & Zookeeper (Docker)

```bash
docker-compose up -d
```

---

How run kafka using docker
Command : 

docker run -d --name kafka -p 9092:9092 -e KAFKA_NODE_ID=1 -e KAFKA_PROCESS_ROLES=broker,controller -e KAFKA_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_CONTROLLER_LISTENER_NAMES=CONTROLLER -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT -e KAFKA_CONTROLLER_QUORUM_VOTERS=1@localhost:9093 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 apache/kafka


Step 2: Create Kafka Topic (IMPORTANT)

Run : 
docker exec -it kafka bash

Go to Kafka Bin Folder
Run:
cd /opt/kafka/bin

Now Create Topic
./kafka-topics.sh --create --topic notification-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

Verify

docker exec -it kafka /opt/kafka/bin//kafka-topics.sh --list --bootstrap-server localhost:9092

SHORTCUT (Pro Way)

Instead of going inside container:

docker exec kafka /opt/kafka/bin/kafka-topics.sh --create --topic notification-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

docker exec kafka /opt/kafka/bin/kafka-topics.sh --create --topic email-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1


Verify Message in Kafka

docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh --topic notification-topic --from-beginning --bootstrap-server localhost:9092

docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh \ --topic email-topic \ --from-beginning \ --bootstrap-server localhost:9092


Stop Kafka container:

docker stop kafka


Delete topic and recreate:

docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --delete --topic notification-topic --bootstrap-server localhost:9092

docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --create --topic notification-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --create --topic notification-topic.DLT --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1




# 📡 API Endpoints

### User Service

```
POST /users
```

### Notification Service

```
POST /notifications
```

---

# 📊 Notification Lifecycle

```
PENDING → SENT
        ↘ FAILED → DLQ
```

---

# 🚀 Future Enhancements

* 🔹 API Gateway (Spring Cloud Gateway)
* 🔹 Service Discovery (Eureka)
* 🔹 Redis Caching
* 🔹 Monitoring (Prometheus + Grafana)
* 🔹 Centralized Config Server

---

# 🧠 Key Concepts Demonstrated

* Microservices Architecture
* Event-Driven Design
* Loose Coupling
* Asynchronous Processing
* Producer-Consumer Model
* Scalability & Fault Tolerance

---

# 💥 Why This Project Matters

This project reflects **real-world distributed system design** used in production systems like:

* E-commerce notifications
* Banking alerts
* Messaging platforms

---

# 👨‍💻 Author

**Vinayak Upadhyay (Vinni)**

---

# ⭐ If you like this project

Give it a ⭐ on GitHub and feel free to contribute!

---
