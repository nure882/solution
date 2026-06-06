# GasStation — повний стек програмної системи автозаправної станції

Повна реалізація програмної системи: три застосунки з єдиним REST API,
JWT-автентифікацією та чистою багатошаровою архітектурою.

## Склад
- backend/gasstation — Spring Boot REST API (Java 17, PostgreSQL, Flyway, JWT, Docker)
- web — React 18 + MUI + Vite, портал адміністрування (ролі ADMIN / WORKER)
- mobile — Android (Kotlin + Jetpack Compose), застосунок клієнта (роль CUSTOMER)

## Доменна модель
User (ADMIN / WORKER / CUSTOMER), Station, GasType, Pump, Coupon.

## Швидкий старт
1. Backend:  cd backend/gasstation && cp .env.example .env && docker compose up --build
   (Swagger: http://localhost:8080/swagger-ui.html; адмін admin@gas.com / admin123)
2. Web:      cd web && npm install && npm run dev   (http://localhost:3000)
3. Mobile:   відкрити папку mobile в Android Studio і запустити на емуляторі.

Деталі — у README кожного підпроєкту.
