# TPs_Genie_Logicielle
## TP Architecture : Du Monolithe au SOA

---

## 📌 Description
4 travaux pratiques progressifs sur l'évolution architecturale :
1. **Monolithe Spring Boot** (CRUD produits)
2. **Modularisation** (DTO, MapStruct, modules métier)
3. **Architecture distribuée** (Node.js, Nginx, MongoDB, Redis, RabbitMQ)
4. **Architecture SOA** (Spring Cloud, Eureka, Feign, API Gateway)

---

## 🛠 Technologies

### TP1 & TP2 — Spring Boot
`Spring Boot` `Spring Data JPA` `PostgreSQL` `MapStruct` `Lombok` `Postman`

### TP3 — Architecture Distribuée
`Node.js` `Express` `Docker` `Docker Compose` `Nginx` `MongoDB` `Redis` `RabbitMQ` `Postman`

### TP4 — Architecture SOA
`Spring Boot` `Spring Cloud` `Eureka` `OpenFeign` `Spring Cloud Gateway` `H2` `Lombok` `Postman`

---

## 📂 Structure
```
TPs_Genie_Logicielle/
├── TP1_Monolithe/                 # API monolithique simple + exercice Category
├── TP2_monolithe_modulaire/
│   └── monolith/                  # Version modulaire + DTOs
│       ├── product/               # Module produits
│       ├── customer/              # Module clients
│       └── order/                 # Module commandes
├── TP3_architecture_distribuee/   # Système distribué (6 composants)
│   ├── docker-compose.yml
│   ├── nginx/
│   └── app/
├── TP4_soa_ecommerce/             # Architecture orientée services
│   ├── eureka-server/             # Service de découverte (port 8761)
│   ├── product-service/           # Gestion produits (port 8081)
│   ├── order-service/             # Gestion commandes (port 8082)
│   └── api-gateway/               # Point d'entrée unique (port 8080)
└── TPs_GL_Enoncé.pdf              # Énoncé des travaux pratiques
```

---

## 🚀 Objectifs pédagogiques

- Comprendre l'architecture en couches (Entity, Repository, Service, Controller)
- Introduire la modularisation et les DTOs
- Observer le load balancing, cache et bus de messages
- Implémenter une architecture SOA avec découverte de services
- Gérer la communication inter-services via REST (Feign)
- Centraliser les accès avec une API Gateway
- Gérer les erreurs distribuées (404, 400, 503)

---

## ▶️ Lancement

### TP1 & TP2
```bash
mvn spring-boot:run
```

### TP3
```bash
docker-compose up --build
```

### TP4 — Ordre obligatoire
```bash
# 1. Eureka Server
cd TP4_soa_ecommerce/eureka-server && mvn spring-boot:run

# 2. Product Service
cd TP4_soa_ecommerce/product-service && mvn spring-boot:run

# 3. Order Service
cd TP4_soa_ecommerce/order-service && mvn spring-boot:run

# 4. API Gateway
cd TP4_soa_ecommerce/api-gateway && mvn spring-boot:run
```

---

## 🔗 Vérification TP4
- Eureka Dashboard : http://localhost:8761
- Product Service  : http://localhost:8081/api/products
- Order Service    : http://localhost:8082/api/orders
- API Gateway      : http://localhost:8080/products

---

## 👨‍💻 Réalisé par
**Abderrahman MACHHOURI**
Filière : Intelligence Artificielle et Cybersécurité
Encadré par : Pr. BE EL BAGHAZAOUI — ENSA Béni Mellal
Année universitaire : 2025-2026