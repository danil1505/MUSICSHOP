# 🎸 GuitarStore — Мобильное приложение для магазина гитар

Учебный проект — полноценное Android-приложение для музыкального магазина с подключением к REST API и базой данных PostgreSQL.

---

## 📁 Структура проекта

```
MUSICSHOP/
├── guitar-store-api/     ← Бэкенд (Spring Boot, Java)
└── android/              ← Фронтенд (Android, Kotlin)
```

### 🖥️ Бэкенд — `guitar-store-api/`
REST API сервер на Spring Boot. Обрабатывает запросы от приложения, работает с базой данных PostgreSQL.

```
guitar-store-api/
└── src/main/java/com/guitarstore/guitar_store_api/
    ├── controller/
    │   ├── GuitarController.java       — эндпоинты для гитар (CRUD)
    │   └── ReferenceController.java    — эндпоинты для брендов и категорий
    ├── service/
    │   └── GuitarService.java          — бизнес-логика
    ├── repository/
    │   ├── GuitarRepository.java       — SQL запросы с фильтрацией
    │   ├── BrandRepository.java
    │   └── CategoryRepository.java
    ├── model/
    │   ├── Guitar.java                 — сущность гитары
    │   ├── Brand.java                  — сущность бренда
    │   └── Category.java               — сущность категории
    ├── dto/
    │   ├── GuitarDto.java              — DTO для ответа
    │   └── GuitarRequest.java          — DTO для создания/обновления
    └── config/
        └── GlobalExceptionHandler.java — обработка ошибок
```

### 📱 Фронтенд — `android/`
Android-приложение на Kotlin. Отображает список гитар, поддерживает поиск, фильтрацию, добавление и редактирование.

```
android/app/src/main/java/com/guitarstore/
├── ui/
│   ├── main/
│   │   ├── MainActivity.kt         — главный экран со списком гитар
│   │   └── FilterBottomSheet.kt    — диалог фильтрации
│   ├── detail/
│   │   └── DetailActivity.kt       — карточка товара
│   └── addedit/
│       └── AddEditActivity.kt      — форма добавления/редактирования
├── viewmodel/
│   ├── MainViewModel.kt            — логика главного экрана
│   └── AddEditViewModel.kt         — логика формы
├── adapter/
│   └── GuitarAdapter.kt            — адаптер RecyclerView
├── repository/
│   └── GuitarRepository.kt         — запросы к API
├── api/
│   ├── GuitarApi.kt                — Retrofit интерфейс
│   └── RetrofitClient.kt           — HTTP клиент
├── model/
│   └── Models.kt                   — модели данных
└── glide/
    └── GlideModule.kt              — настройка загрузки картинок
```

---

## 🛠️ Технологии

### Бэкенд
| Технология | Версия | Назначение |
|---|---|---|
| Java | 17 | Язык программирования |
| Spring Boot | 3.5.12 | Фреймворк |
| Spring Data JPA | — | Работа с БД |
| PostgreSQL | 17 | База данных |
| HikariCP | — | Пул соединений |
| Lombok | — | Упрощение кода |
| Maven | — | Сборка проекта |

### Фронтенд (Android)
| Технология | Версия | Назначение |
|---|---|---|
| Kotlin | 1.9.23 | Язык программирования |
| Android SDK | 34 | Платформа |
| Retrofit2 | 2.9.0 | HTTP запросы |
| Gson | — | JSON парсинг |
| Glide | 4.16.0 | Загрузка картинок |
| ViewModel + LiveData | 2.7.0 | MVVM архитектура |
| Material Components | 1.11.0 | UI компоненты |
| OkHttp | 4.12.0 | HTTP клиент |
| Coroutines | 1.7.3 | Асинхронность |

---

## 🗄️ База данных

**СУБД:** PostgreSQL 17  
**Название БД:** `guitarstore`

### Таблицы
| Таблица | Описание |
|---|---|
| `brands` | Бренды гитар (Gibson, Fender, Yamaha и др.) |
| `categories` | Категории (электрогитара, акустическая, бас и др.) |
| `guitars` | Основная таблица — 500 записей с гитарами |

### Особенности
- Полнотекстовый поиск через `tsvector` и `plainto_tsquery`
- Индексы на `brand_id`, `category_id`, `price` для быстрой фильтрации
- Триггер автообновления `search_vector` при изменении данных

---

## 🚀 Запуск проекта

### Требования
- Java 17
- PostgreSQL 17
- PgAdmin 4
- Android Studio
- Android телефон или эмулятор (API 26+)

---

### Шаг 1 — Настройка базы данных

**1.** Открой PgAdmin 4

**2.** Создай базу данных:
```
ПКМ на Databases → Create → Database → имя: guitarstore
```

**3.** Открой Query Tool и выполни SQL скрипт для создания таблиц и заполнения данными:
```
Tools → Query Tool → File → Open → full_setup.sql → F5
```

**4.** Проверь что всё создалось:
```sql
SELECT COUNT(*) FROM guitars; -- должно быть 500
```

---

### Шаг 2 — Запуск бэкенда

**1.** Открой папку `guitar-store-api` в IntelliJ IDEA

**2.** Настрой подключение к БД в файле:
```
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/guitarstore
spring.datasource.username=postgres
spring.datasource.password=ВАШ_ПАРОЛЬ
```

**3.** Запусти `GuitarStoreApplication.java` → правой кнопкой → **Run**

**4.** Проверь что бэкенд работает — открой в браузере:
```
http://localhost:8080/guitars
http://localhost:8080/brands
http://localhost:8080/categories
```

---

### Шаг 3 — Запуск Android приложения

**1.** Открой папку `android` в Android Studio

**2.** Укажи IP компьютера в `app/build.gradle`:
```gradle
// Для эмулятора:
buildConfigField 'String', 'BASE_URL', '"http://10.0.2.2:8080/"'

// Для реального телефона (замени на свой IP):
buildConfigField 'String', 'BASE_URL', '"http://192.168.X.X:8080/"'
```

**3.** Узнай IP компьютера:
```cmd
ipconfig
```
Найди **IPv4-адрес** в разделе WiFi или Ethernet.

**4.** Синхронизируй проект:
```
File → Sync Project with Gradle Files
```

**5.** Подключи телефон по USB (включи отладку по USB в настройках разработчика)

**6.** Нажми **Run ▶**

---

## 📡 API эндпоинты

| Метод | URL | Описание |
|---|---|---|
| GET | `/guitars` | Список гитар с фильтрацией и пагинацией |
| GET | `/guitars/{id}` | Карточка гитары |
| POST | `/guitars` | Добавить гитару |
| PUT | `/guitars/{id}` | Обновить гитару |
| DELETE | `/guitars/{id}` | Удалить гитару |
| GET | `/brands` | Список брендов |
| GET | `/categories` | Список категорий |

### Параметры фильтрации для GET /guitars
```
?search=Gibson        — полнотекстовый поиск
&brand=1              — фильтр по бренду (id)
&category=1           — фильтр по категории (id)
&minPrice=10000       — минимальная цена
&maxPrice=100000      — максимальная цена
&page=0               — номер страницы (с 0)
&size=20              — количество на странице
```

### Пример запроса
```
GET http://localhost:8080/guitars?search=Gibson&minPrice=50000&page=0&size=20
```

### Пример ответа
```json
{
  "content": [
    {
      "id": "uuid",
      "name": "Gibson Les Paul Standard",
      "brandId": 1,
      "brandName": "Gibson",
      "categoryId": 1,
      "categoryName": "Электрогитара",
      "price": 145000.00,
      "description": "...",
      "imageUrl": "https://...",
      "inStock": true,
      "createdAt": "2026-01-01T00:00:00Z"
    }
  ],
  "page": {
    "size": 20,
    "number": 0,
    "totalElements": 500,
    "totalPages": 25
  }
}
```

---

## 📱 Экраны приложения

| Экран | Файл | Описание |
|---|---|---|
| Список гитар | `MainActivity.kt` | Главный экран, поиск, фильтры, пагинация |
| Карточка товара | `DetailActivity.kt` | Детальная информация, кнопки редактирования и удаления |
| Добавление/редактирование | `AddEditActivity.kt` | Форма с валидацией |
| Фильтры | `FilterBottomSheet.kt` | Выбор бренда, категории, диапазона цены |

---

## 🎨 Дизайн

- **Material Components** (тема MaterialComponents.DayNight)
- Цвета: тёмно-бордовый `#7B1C2E` + золотой `#C9A84C`
- Карточки с закруглёнными углами
- Поддержка светлой и тёмной темы

---

## 🏗️ Архитектура

### Бэкенд
```
Controller → Service → Repository → PostgreSQL
```

### Android (MVVM)
```
Activity/Fragment → ViewModel → Repository → Retrofit API → Backend
```
