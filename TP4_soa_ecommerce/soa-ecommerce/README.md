# TP4 - Architecture SOA avec Spring Boot

## Structure du projet

```
soa-ecommerce/
├── eureka-server/          # Service de découverte (port 8761)
├── product-service/        # Service produits (port 8081)
├── order-service/          # Service commandes (port 8082)
└── api-gateway/            # API Gateway - Exercice 2 (port 8080)
```

## Démarrage

### Ordre obligatoire de démarrage

**1. Eureka Server**
```bash
cd eureka-server
mvn spring-boot:run
# Vérifier : http://localhost:8761
```

**2. Product Service**
```bash
cd product-service
mvn spring-boot:run
# H2 Console : http://localhost:8081/h2-console
```

**3. Order Service**
```bash
cd order-service
mvn spring-boot:run
# H2 Console : http://localhost:8082/h2-console
```

**4. API Gateway (Exercice 2)**
```bash
cd api-gateway
mvn spring-boot:run
```

---

## Tests avec Postman / cURL

### Test 1 : Créer un produit
```
POST http://localhost:8081/api/products
Content-Type: application/json

{
  "name": "Laptop",
  "description": "Ordinateur portable haute performance",
  "price": 1200.00,
  "stock": 10
}
```

### Test 2 : Créer un second produit
```
POST http://localhost:8081/api/products
Content-Type: application/json

{
  "name": "Souris",
  "description": "Souris sans fil",
  "price": 35.00,
  "stock": 50
}
```

### Test 3 : Lister les produits
```
GET http://localhost:8081/api/products
```

### Test 4 : Créer une commande (Exercice 1 : stock mis à jour automatiquement)
```
POST http://localhost:8082/api/orders?productId=1&quantity=2
```

### Test 5 : Vérifier le stock mis à jour (doit être 8 après la commande)
```
GET http://localhost:8081/api/products/1
```

### Test 6 : Lister les commandes
```
GET http://localhost:8082/api/orders
```

### Test 7 : Commande avec stock insuffisant (Exercice 1 - doit retourner 400)
```
POST http://localhost:8082/api/orders?productId=1&quantity=999
```

---

## Exercice 1 : Mise à jour du stock

**Endpoint ajouté dans Product Service :**
```
PUT http://localhost:8081/api/products/{id}/stock
Content-Type: application/json

{ "quantity": 3 }
```

**Ce qui se passe lors d'une commande :**
1. Order Service récupère le produit via Feign
2. Vérifie que stock >= quantité demandée
3. Crée la commande avec statut PENDING
4. Appelle `PUT /api/products/{id}/stock` pour décrémenter le stock
5. Passe la commande en CONFIRMED

---

## Exercice 2 : API Gateway (port 8080)

Après démarrage de la Gateway :

```
# Accès aux produits via Gateway
GET  http://localhost:8080/products
POST http://localhost:8080/products
GET  http://localhost:8080/products/1

# Accès aux commandes via Gateway
GET  http://localhost:8080/orders
POST http://localhost:8080/orders?productId=1&quantity=1
GET  http://localhost:8080/orders/1
```

---

## Exercice 3 : Gestion des erreurs

**Produit inexistant :**
```
POST http://localhost:8082/api/orders?productId=9999&quantity=1
→ 404 "Le produit demandé est introuvable dans le service produit."
```

**Stock insuffisant :**
```
POST http://localhost:8082/api/orders?productId=1&quantity=9999
→ 400 "Stock insuffisant pour le produit ID 1..."
```

**Service Product indisponible :**
→ Arrêter product-service, puis créer une commande
→ 503 "Impossible de contacter le service produit."

---

## Architecture

```
Client
  │
  ▼
API Gateway :8080
  ├──→ Product Service :8081  ──→ H2 (productdb)
  └──→ Order Service   :8082  ──→ H2 (orderdb)
                │
                └── Feign ──→ Product Service
  
  Eureka Server :8761  ← tous les services s'y enregistrent
```
