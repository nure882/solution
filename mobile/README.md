# GasStation — Mobile (Android, Kotlin + Jetpack Compose)

Мобільний застосунок клієнта програмної системи автозаправної станції.
Структуру побудовано за сучасним підходом Android-розробки
(MVVM + Hilt + Retrofit + DataStore + Navigation Compose).

## Стек
- Kotlin, Jetpack Compose (Material 3), compileSdk 34, minSdk 26
- Архітектура MVVM, шари data / di / ui
- Hilt (DI), Retrofit + OkHttp + Gson (мережа)
- DataStore Preferences (JWT-сесія), Navigation Compose

## Можливості (роль CUSTOMER)
- Реєстрація та JWT-вхід, безпечне збереження токена у DataStore
- Авто-розлогування при HTTP 401 (через SessionManager)
- Перелік автозаправних станцій із пошуком
- Деталі станції: інформація + паливні колонки з цінами та залишком пального
- Перегляд власних знижкових талонів
- Профіль користувача, вихід

## Структура
    com/gasstation/
      data/api        ApiModels.kt, GasStationApi.kt (Retrofit)
      data/local      TokenDataStore.kt, SessionManager.kt
      data/repository AuthRepository, StationRepository, CouponRepository
      di              NetworkModule.kt (Hilt)
      ui/theme        Color, Theme, Type
      ui/navigation   NavGraph.kt (Routes, MainViewModel)
      ui/components   StationCard.kt
      ui/screens      login, register, home, stationdetail, coupons, profile
      GasStationApp.kt, MainActivity.kt

## Запуск
1. Відкрити папку mobile в Android Studio (воно згенерує Gradle wrapper та local.properties).
2. Запустити backend (../backend/gasstation). Застосунок звертається до
   http://10.0.2.2:8080/ (адреса хост-машини з Android-емулятора).
3. Запустити на емуляторі; створити клієнта через екран реєстрації.
