# 🎸 GuitarStore — Мобильное приложение для магазина гитар

Полноценный fullstack-проект: Android-приложение + Django REST API + PostgreSQL с поддержкой 1 000 000 записей, Docker и Kubernetes.

---

## 📁 Структура проекта

```
MUSICSHOP/
├── backend/              ← Бэкенд (Django REST Framework, Python)
└── android/              ← Фронтенд (Android, Kotlin)
```

### 🐍 Бэкенд — `backend/`

REST API сервер на Django REST Framework. Поддержка полнотекстового поиска, фильтрации, пагинации.

```
backend/
├── config/
│   ├── settings.py           — настройки Django
│   ├── urls.py               — корневые URL
│   └── wsgi.py               — WSGI конфигурация
├── store/
│   ├── models.py             — модели (Brand, Category, Guitar)
│   ├── serializers.py        — DRF сериализаторы
│   ├── views.py              — ViewSets для API
│   ├── urls.py               — маршруты API
│   └── admin.py              — настройки Django Admin
├── scripts/
│   └── seed_data.py          — генерация 1 000 000 записей
├── k8s/                      — Kubernetes манифесты
├── Dockerfile                — Docker образ
├── docker-compose.yml        — Docker Compose
└── requirements.txt          — Python зависимости
```

### 📱 Фронтенд — `android/`

Android-приложение на Kotlin. Отображает список гитар, поддерживает поиск, фильтрацию, CRUD операции.

```
android/
├── app/
│   ├── src/main/java/com/guitarstore/
│   │   ├── ui/
│   │   │   ├── main/
│   │   │   │   ├── MainActivity.kt       — главный экран
│   │   │   │   └── FilterBottomSheet.kt  — диалог фильтрации
│   │   │   ├── detail/
│   │   │   │   └── DetailActivity.kt     — карточка товара
│   │   │   └── addedit/
│   │   │       └── AddEditActivity.kt    — форма CRUD
│   │   ├── viewmodel/
│   │   │   ├── MainViewModel.kt
│   │   │   └── AddEditViewModel.kt
│   │   ├── adapter/
│   │   │   └── GuitarAdapter.kt
│   │   ├── repository/
│   │   │   └── GuitarRepository.kt
│   │   ├── api/
│   │   │   ├── GuitarApi.kt
│   │   │   └── RetrofitClient.kt
│   │   └── model/
│   │       └── Models.kt
│   └── build.gradle
└── BACKEND_INTEGRATION.md    — документация интеграции
```

---

## 🛠️ Технологии

### Бэкенд
| Технология | Версия | Назначение |
|---|---|---|
| Python | 3.12 | Язык программирования |
| Django | 5.0.6 | Веб-фреймворк |
| Django REST Framework | 3.15.1 | REST API |
| PostgreSQL | 17 | База данных |
| WhiteNoise | 6.6.0 | Раздача статики |
| Gunicorn | 22.0.0 | WSGI сервер |
| Faker | 25.0.1 | Генерация данных |
| Docker | — | Контейнеризация |
| Kubernetes | — | Оркестрация |

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
**База данных:** `guitarstore`

### Таблицы
| Таблица | Описание |
|---|---|
| `brands` | 15 брендов (Gibson, Fender, Yamaha, Ibanez, ESP...) |
| `categories` | 6 категорий (электрогитара, акустическая, бас...) |
| `guitars` | **1 000 000 записей** с полнотекстовым поиском |

### Особенности
- Полнотекстовый поиск через `tsvector` + GIN индекс
- Индексы на `brand_id`, `category_id`, `price`
- UUID для первичных ключей
- CHECK constraint на `price > 0`

---

## 🚀 Быстрый запуск (Docker)

### Требования
- Docker Desktop
- Android Studio
- Android эмулятор или устройство (API 26+)

---

### 1️⃣ Запуск бэкенда

```bash
cd MUSICSHOP/backend
docker-compose up -d
```

**Проверка:**
- http://localhost:8000/api/guitars/ — API
- http://localhost:8000/admin/ — Admin (логин: `admin`, пароль: `admin`)
- http://localhost:8000/api/docs/ — Swagger документация

---

### 2️⃣ Генерация тестовых данных (1 000 000 записей)

```bash
docker-compose run seed
```

⏱ ~140 секунд (~7300 записей/сек)

---

### 3️⃣ Запуск Android приложения

#### Для эмулятора:

1. Открой `android/` в Android Studio
2. **Sync Gradle**
3. **Run ▶** (эмулятор автоматически подключится к `10.0.2.2:8000`)

#### Для физического устройства:

**1.** Узнать IP компьютера:
```bash
ipconfig
# Найдите IPv4 в разделе "Беспроводная сеть" (например, 192.168.1.100)
```

**2.** Обновить `android/app/build.gradle`:
```gradle
buildConfigField 'String', 'BASE_URL', '"http://192.168.1.100:8000/api/"'
```

**3.** Разрешить порт в брандмауэре (PowerShell от администратора):
```bash
New-NetFirewallRule -DisplayName "Django Backend" -Direction Inbound -LocalPort 8000 -Protocol TCP -Action Allow
```

**4.** Включить отладку по USB на телефоне → подключить → **Run ▶**

---

## 📡 API эндпоинты

| Метод | Endpoint | Описание |
|---|---|---|
| GET | `/api/guitars/` | Список гитар с пагинацией |
| GET | `/api/guitars/{id}/` | Детали гитары |
| POST | `/api/guitars/` | Создать гитару |
| PUT | `/api/guitars/{id}/` | Обновить гитару |
| DELETE | `/api/guitars/{id}/` | Удалить гитару |
| GET | `/api/brands/` | Список брендов (15) |
| GET | `/api/categories/` | Список категорий (6) |

### Параметры фильтрации для GET /api/guitars/

```
?search=Fender          — полнотекстовый поиск
&brand=2                — фильтр по бренду (id)
&category=1             — фильтр по категории (id)
&minPrice=50000         — минимальная цена
&maxPrice=150000        — максимальная цена
&page=1                 — номер страницы (начинается с 1)
&page_size=20           — размер страницы
```

### Пример запроса
```bash
curl "http://localhost:8000/api/guitars/?search=Fender&minPrice=50000&maxPrice=150000&page=1"
```

### Пример ответа
```json
{
  "count": 19587,
  "next": "http://.../api/guitars/?page=2",
  "previous": null,
  "results": [
    {
      "id": "uuid-string",
      "name": "Fender Stratocaster",
      "brand": 2,
      "brand_name": "Fender",
      "category": 1,
      "category_name": "Электрогитара",
      "price": "150000.00",
      "description": "...",
      "image_url": "https://...",
      "in_stock": true,
      "created_at": "2026-03-26T10:00:00Z"
    }
  ]
}
```

---

## 📱 Экраны приложения

| Экран | Файл | Описание |
|---|---|---|
| Список гитар | `MainActivity.kt` | Поиск, фильтры, пагинация, pull-to-refresh |
| Карточка товара | `DetailActivity.kt` | Детали, редактирование, удаление |
| Добавление/редактирование | `AddEditActivity.kt` | Форма с валидацией |
| Фильтры | `FilterBottomSheet.kt` | Бренд, категория, цена |

---

## 🎨 Дизайн

- **Material Components** (DayNight тема)
- Цвета: тёмно-бордовый `#7B1C2E` + золотой `#C9A84C`
- CardView с закруглёнными углами
- Shimmer эффект загрузки
- Поддержка светлой и тёмной темы

---

## 🏗️ Архитектура

### Бэкенд
```
ViewSet → Serializer → Model → PostgreSQL
              ↓
         WhiteNoise (статика)
```

### Android (MVVM)
```
Activity → ViewModel → Repository → Retrofit → Backend
             ↓
         LiveData
```

---

## 🐳 Docker

### Запуск
```bash
cd backend
docker-compose up -d
```

### Сервисы
| Сервис | Порт | Описание |
|---|---|---|
| `db` | 5433 | PostgreSQL 17 |
| `web` | 8000 | Django + Gunicorn |
| `seed` | — | Генерация 1M записей |

### Команды
```bash
# Просмотр логов
docker logs -f backend-web-1

# Остановка
docker-compose down

# Остановка + удаление БД
docker-compose down -v

# Перезапуск
docker-compose restart
```

---

## ☸️ Kubernetes

### Развёртывание

```bash
# Включить Kubernetes в Docker Desktop Settings → Kubernetes → Enable

# Применить манифесты
kubectl apply -k backend/k8s/

# Проверить статус
kubectl get all -n guitarstore

# Пробросить порт
kubectl port-forward -n guitarstore svc/backend-service 8000:80

# Запустить генерацию данных
kubectl apply -f backend/k8s/seed-job.yaml
```

### Компоненты
| Ресурс | Описание |
|---|---|
| Namespace | `guitarstore` |
| Deployment | backend (3 реплики), postgres |
| Service | backend-service, postgres-service |
| HPA | автомасштабирование 3-10 реплик |
| Job | миграции, генерация данных |
| Ingress | внешний доступ через nginx |

📖 **Подробно:** [`backend/k8s/README.md`](backend/k8s/README.md)

---

## 🧪 Тестирование API

### Проверка количества записей
```bash
curl -s http://localhost:8000/api/guitars/ | python -c "import sys,json; d=json.load(sys.stdin); print(f'Записей: {d[\"count\"]:,}')"
```

### Поиск с фильтром
```bash
curl "http://localhost:8000/api/guitars/?search=Gibson&minPrice=100000"
```

### Swagger UI
Откройте http://localhost:8000/api/docs/ для интерактивной документации.

---

## 🔧 Локальная разработка (без Docker)

### Бэкенд

```bash
cd backend

# Виртуальное окружение
python -m venv venv
venv\Scripts\activate  # Windows

# Установка зависимостей
pip install -r requirements.txt

# Создание БД
psql -U postgres -c "CREATE DATABASE guitarstore;"

# Миграции
python manage.py migrate

# Суперпользователь
python manage.py createsuperuser

# Генерация данных
python scripts/seed_data.py 1000000 5000

# Запуск
python manage.py runserver
```

### Фронтенд

```bash
# Sync Gradle в Android Studio
# Или из командной строки:
cd android
gradlew sync
gradlew assembleDebug
```

---

## 📊 Производительность

| Операция | Время |
|---|---|
| Генерация 1M записей | ~140 сек |
| Поиск по 1M записей | <100 мс |
| Пагинация (20 записей) | <50 мс |
| Фильтрация | <100 мс |

---

## 📄 Документация

- [`backend/README.md`](backend/README.md) — бэкенд документация
- [`android/BACKEND_INTEGRATION.md`](android/BACKEND_INTEGRATION.md) — интеграция Android
- [`backend/k8s/README.md`](backend/k8s/README.md) — Kubernetes

---

## 🎓 Учебные цели

Проект демонстрирует:
- ✅ Fullstack разработку (Backend + Frontend)
- ✅ REST API дизайн
- ✅ Работу с PostgreSQL (индексы, полнотекстовый поиск)
- ✅ Docker контейнеризацию
- ✅ Kubernetes оркестрацию
- ✅ MVVM архитектуру на Android
- ✅ Генерацию больших объёмов данных
- ✅ Оптимизацию производительности

---

## 📝 Лицензия

MIT
