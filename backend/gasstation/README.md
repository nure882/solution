# GasStation — Backend (Spring Boot)

REST API серверної частини програмної системи автозаправної станції.
Архітектуру побудовано за принципом класичної багатошарової (layered) архітектури:
`controller → service → repository → model (entity/dto/enums)`, безпека на JWT.

## Стек
- Java 17, Spring Boot 3.5
- Spring Web, Spring Data JPA, Spring Security
- PostgreSQL + Flyway (міграції)
- JWT (jjwt 0.11.5), BCrypt
- springdoc-openapi (Swagger UI)

## Доменна модель
- **User** — користувач (ролі `ADMIN`, `WORKER`, `CUSTOMER`)
- **Station** — автозаправна станція (city, address, workTime, description, equipmentList)
- **GasType** — тип пального (name)
- **Pump** — паливна колонка (number, gasCount, cost, station, gasType)
- **Coupon** — знижковий талон (customer, gasType, liters, expirationDate, useDate, active)

## Запуск (Docker)
```bash
cp .env.example .env          # за потреби відредагувати
docker compose up --build
# API:        http://localhost:8080  (через Nginx LB: http://localhost:80)
# Swagger UI: http://localhost:8080/swagger-ui.html
```

## Запуск локально (без Docker)
Підніміть PostgreSQL (БД `gasstation`), задайте змінні `DB_URL`, `DB_USER`,
`DB_PASSWORD`, `JWT_SECRET` (Base64, ≥32 байти), потім:
```bash
./mvnw spring-boot:run
```

## Початковий обліковий запис
Під час першого старту автоматично створюється адміністратор:
`admin@gas.com` / `admin123` (див. `config/DataSeeder.java`).

## Основні ендпоінти
| Метод | Шлях | Доступ |
|------|------|--------|
| POST | `/api/auth/register` | публічний (реєстрація CUSTOMER) |
| POST | `/api/auth/login` | публічний |
| GET  | `/api/auth/me` | автентифікований |
| GET/POST/PUT/DELETE | `/api/stations` | GET — усі; зміни — ADMIN |
| GET/POST/PUT/DELETE | `/api/gas-types` | GET — усі; зміни — ADMIN |
| GET/POST/PUT/DELETE | `/api/pumps` | GET — усі; зміни — ADMIN |
| GET/POST/PUT/DELETE | `/api/coupons` | ADMIN, WORKER |
| GET | `/api/coupons/my` | CUSTOMER (власні талони) |
| GET/POST/PUT/DELETE | `/api/users` | ADMIN |
| GET | `/api/info` | публічний (instanceId — для демонстрації масштабування) |

## Масштабування (ЛР4)
`docker-compose.yaml` містить сервіс `nginx-balancer` (Round Robin) і дозволяє
горизонтальне масштабування бекенду:
```bash
docker compose up --scale gasstation-app=3 -d
artillery quick --count 500 --num 10 http://localhost/api/info
```
