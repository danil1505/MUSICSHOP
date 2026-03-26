# Guitar Store Backend (Django)

Бэкенд для магазина гитар на Django REST Framework с поддержкой PostgreSQL, Docker и Kubernetes.

## 📋 Возможности

- ✅ REST API для управления гитарами, брендами и категориями
- ✅ Полнотекстовый поиск через PostgreSQL
- ✅ Фильтрация и пагинация
- ✅ Django Admin для управления данными
- ✅ Генерация 1 000 000 тестовых записей
- ✅ Docker и Docker Compose для локальной разработки
- ✅ Kubernetes манифесты для продакшена
- ✅ Swagger/OpenAPI документация

## 🚀 Быстрый старт с Docker

```bash
cd backend

# Запуск всех сервисов (PostgreSQL + Django + миграции)
docker-compose up --build

# Отдельно запустить генерацию данных (1 000 000 записей)
docker-compose run seed
```

После запуска:
- **API**: http://localhost:8000/api/
- **Admin**: http://localhost:8000/admin/
- **Swagger**: http://localhost:8000/api/docs/

## 📦 Локальная разработка (без Docker)

### Требования
- Python 3.10+
- PostgreSQL 17

### Установка

```bash
# Создание виртуального окружения
python -m venv venv
venv\Scripts\activate  # Windows
source venv/bin/activate  # Linux/Mac

# Установка зависимостей
pip install -r requirements.txt

# Копирование .env
copy .env.example .env  # Windows
cp .env.example .env  # Linux/Mac

# Применение миграций
python manage.py migrate

# Создание суперпользователя
python manage.py createsuperuser

# Генерация тестовых данных (1 000 000 записей)
python scripts/seed_data.py 1000000 5000

# Запуск сервера
python manage.py runserver
```

## 🎯 API Endpoints

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/guitars/` | Список гитар (с фильтрацией и пагинацией) |
| GET | `/api/guitars/{id}/` | Детальная информация |
| POST | `/api/guitars/` | Создать гитару |
| PUT | `/api/guitars/{id}/` | Обновить гитару |
| DELETE | `/api/guitars/{id}/` | Удалить гитару |
| GET | `/api/brands/` | Список брендов |
| GET | `/api/categories/` | Список категорий |

### Параметры фильтрации для `/api/guitars/`

- `search` — полнотекстовый поиск
- `brand` — ID бренда
- `category` — ID категории
- `minPrice`, `maxPrice` — диапазон цен
- `in_stock` — наличие на складе
- `page` — номер страницы
- `ordering` — сортировка (`price`, `-price`, `name`, `-created_at`)

### Примеры запросов

```bash
# Поиск гитар с фильтром по цене
curl "http://localhost:8000/api/guitars/?search=Fender&minPrice=50000&maxPrice=150000"

# Фильтр по бренду и категории
curl "http://localhost:8000/api/guitars/?brand=1&category=2&in_stock=true"

# Сортировка по цене (возрастание)
curl "http://localhost:8000/api/guitars/?ordering=price"
```

## 🐳 Docker Compose

### Сервисы

| Сервис | Описание | Порт |
|--------|----------|------|
| `db` | PostgreSQL 17 | 5432 |
| `web` | Django приложение | 8000 |
| `migrate` | Применение миграций | - |
| `seed` | Генерация тестовых данных | - |

### Команды

```bash
# Запуск всех сервисов
docker-compose up

# Запуск в фоновом режиме
docker-compose up -d

# Остановка
docker-compose down

# Просмотр логов
docker-compose logs -f web

# Запуск генерации данных
docker-compose run seed

# Запуск оболочки Django
docker-compose run web python manage.py shell
```

## ☸️ Kubernetes

### Требования
- Kubernetes кластер (minikube, kind, k3s, или облачный)
- kubectl
- Docker образ приложения

### Развертывание

```bash
# Сборка Docker образа
docker build -t guitarstore-backend:latest .

# Применение всех манифестов
kubectl apply -k k8s/

# Или по отдельности
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml
kubectl apply -f k8s/postgres-pvc.yaml
kubectl apply -f k8s/postgres-deployment.yaml
kubectl apply -f k8s/migrate-job.yaml
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/ingress.yaml

# Запуск генерации данных
kubectl apply -f k8s/seed-job.yaml

# Проверка статуса
kubectl get pods -n guitarstore
kubectl get services -n guitarstore
kubectl get jobs -n guitarstore

# Просмотр логов
kubectl logs -n guitarstore -l app=backend
kubectl logs -n guitarstore job/migrate
kubectl logs -n guitarstore job/seed-data

# Удаление всех ресурсов
kubectl delete -k k8s/
```

### Архитектура Kubernetes

```
┌─────────────────────────────────────────────────────────┐
│                     Ingress (nginx)                      │
│                  api.guitarstore.local                   │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│              backend-service (ClusterIP)                 │
│                    Port: 80                              │
└────────────────────┬────────────────────────────────────┘
                     │
         ┌───────────┼───────────┐
         ▼           ▼           ▼
    ┌─────────┐ ┌─────────┐ ┌─────────┐
    │ Pod #1  │ │ Pod #2  │ │ Pod #3  │  ← HPA (3-10 реплик)
    │ :8000   │ │ :8000   │ │ :8000   │
    └─────────┘ └─────────┘ └─────────┘
                     │
                     ▼
    ┌────────────────────────────────┐
    │   postgres-service (ClusterIP) │
    │           Port: 5432           │
    └────────────┬───────────────────┘
                 │
                 ▼
    ┌────────────────────────────────┐
    │      PostgreSQL Pod            │
    │    PersistentVolume (10Gi)     │
    └────────────────────────────────┘
```

## 🔧 Конфигурация

### Переменные окружения

| Переменная | Описание | По умолчанию |
|------------|----------|--------------|
| `SECRET_KEY` | Секретный ключ Django | - |
| `DEBUG` | Режим отладки | `True` |
| `ALLOWED_HOSTS` | Разрешенные хосты | `*` |
| `DB_NAME` | Имя базы данных | `guitarstore` |
| `DB_USER` | Пользователь БД | `postgres` |
| `DB_PASSWORD` | Пароль БД | `123` |
| `DB_HOST` | Хост БД | `localhost` |
| `DB_PORT` | Порт БД | `5432` |
| `CORS_ALLOW_ALL` | Разрешить все CORS | `True` |

## 📊 Генерация данных

Скрипт `scripts/seed_data.py` генерирует тестовые данные:

```bash
# Генерация 1 000 000 записей
python scripts/seed_data.py 1000000 5000

# Параметры:
#   1000000 - количество записей
#   5000    - размер батча для bulk_create
```

### Что генерируется:
- 16 брендов (Gibson, Fender, Yamaha, Ibanez, ESP, PRS...)
- 6 категорий (Электрогитара, Акустическая, Классическая, Бас...)
- 1 000 000 гитар с реалистичными данными

## 📝 Django Admin

Админ-панель доступна по адресу `/admin/`.

### Возможности:
- Просмотр и редактирование гитар
- Фильтрация по бренду, категории, наличию
- Поиск по названию и описанию
- Массовое редактирование (цена, наличие)
- 50 записей на страницу

## 🏗️ Структура проекта

```
backend/
├── config/              # Настройки Django
│   ├── settings.py      # Основные настройки
│   ├── urls.py          # Корневые URL
│   └── wsgi.py          # WSGI конфигурация
├── store/               # Основное приложение
│   ├── models.py        # Модели (Brand, Category, Guitar)
│   ├── serializers.py   # DRF сериализаторы
│   ├── views.py         # ViewSets
│   ├── urls.py          # Маршруты API
│   └── admin.py         # Настройки админки
├── scripts/             # Скрипты
│   └── seed_data.py     # Генерация данных
├── k8s/                 # Kubernetes манифесты
├── Dockerfile           # Docker образ
├── docker-compose.yml   # Docker Compose
└── requirements.txt     # Python зависимости
```

## 🔌 Интеграция с Android приложением

Обновите `BASE_URL` в `android/app/build.gradle`:

```gradle
buildConfigField "String", "BASE_URL", '"http://<your-backend-url>/api/"'
```

Для локальной разработки с Docker:
```gradle
buildConfigField "String", "BASE_URL", '"http://10.0.2.2:8000/api/"'  # Эмулятор
```

## 📄 Лицензия

MIT
