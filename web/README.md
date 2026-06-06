# GasStation — Web (React + MUI + Vite)

Front-end адміністрування програмної системи автозаправної станції.
Окремі інтерфейси за ролями: **адміністратор** (Admin) і **працівник** (Worker).
Структуру побудовано за класичним підходом до SPA на React.

## Стек
- React 18, Vite 5
- MUI (Material UI) 6, @emotion
- React Router DOM 6
- Axios (JWT interceptor)
- Власна реалізація i18n (українська / англійська)

## Можливості
- JWT-вхід/реєстрація, збереження сесії в localStorage, авто-розлогування на 401
- Рольова маршрутизація: ADMIN бачить усі розділи, WORKER — лише талони
- CRUD: станції, типи пального, паливні колонки, знижкові талони
- Управління користувачами (створення працівників/адмінів, блокування, видалення)
- Перемикач мови UK/EN, кастомна MUI-тема

## Запуск
```bash
npm install
npm run dev     # http://localhost:3000  (проксі /api → http://localhost:8080)
```
Спершу підніміть backend (див. `../backend/gasstation`). Початковий вхід:
`admin@gas.com` / `admin123`.

## Структура
```
src/
  api/         axios + gasStationApi (усі ендпоінти)
  context/     AuthContext (роль), LanguageContext (i18n)
  components/  layout (AppLayout, ProtectedRoute), common (ConfirmDialog, ...)
  pages/       Login, Register, Dashboard, Stations, StationDetail,
               GasTypes, Pumps, Coupons, Users, Profile
  i18n/        translations.js (uk / en)
  theme/       theme.js
```
