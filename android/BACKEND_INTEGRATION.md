# Подключение Android приложения к Django бэкенду

## Изменения в Android приложении

### 1. BASE_URL

В `app/build.gradle` обновлён `BASE_URL`:

```gradle
// Для эмулятора Android
buildConfigField 'String', 'BASE_URL', '"http://10.0.2.2:8000/api/"'

// Для физического устройства (замените IP на ваш)
buildConfigField 'String', 'BASE_URL', '"http://192.168.1.XXX:8000/api/"'
```

**Важно:**
- `10.0.2.2` — это localhost для эмулятора Android
- Для реального устройства используйте IP компьютера в той же Wi-Fi сети

---

## Запуск

### 1. Запустить бэкенд

```bash
cd D:\project\MUSICSHOP\backend
docker-compose up -d
```

Проверить:
- http://localhost:8000/api/guitars/ — API
- http://localhost:8000/admin/ — Admin

### 2. Разрешить подключения из локальной сети

В `backend/docker-compose.yml` порт уже открыт:

```yaml
ports:
  - "8000:8000"
```

Для доступа из внешней сети в `backend/config/settings.py`:

```python
ALLOWED_HOSTS = ['*']  # Уже настроено
```

### 3. Запустить Android приложение

1. Открыть проект в Android Studio
2. Sync Gradle
3. Запустить на эмуляторе или устройстве

---

## Настройка для физического устройства

### 1. Узнать IP компьютера

```bash
# Windows
ipconfig

# Найдите IPv4 адрес в разделе "Беспроводная сеть" или "Ethernet"
# Например: 192.168.1.100
```

### 2. Обновить BASE_URL

В `android/app/build.gradle`:

```gradle
buildConfigField 'String', 'BASE_URL', '"http://192.168.1.100:8000/api/"'
```

### 3. Разрешить трафик в брандмауэре

```bash
# Windows (PowerShell от администратора)
New-NetFirewallRule -DisplayName "Django Backend" -Direction Inbound -LocalPort 8000 -Protocol TCP -Action Allow
```

### 4. Пересобрать приложение

```bash
# В Android Studio: Build → Clean Project → Rebuild Project
```

---

## Проверка подключения

### 1. Тест с эмулятора

Откройте в приложении любой экран и проверьте:

- Загружается ли список гитар
- Работает ли поиск
- Работает ли фильтрация

### 2. Логи

```bash
# Logcat фильтр
adb logcat | grep -i "retrofit\|guitar"
```

### 3. Прямой запрос

С эмулятора (через adb shell):

```bash
adb shell
curl http://10.0.2.2:8000/api/guitars/
```

---

## Возможные проблемы

### 1. "Connection refused"

**Причина:** Бэкенд не запущен или порт закрыт

**Решение:**
```bash
docker-compose up -d
docker ps  # проверить что контейнер запущен
```

### 2. "Unable to resolve host"

**Причина:** Неправильный URL или нет сети

**Решение:**
- Проверить BASE_URL
- Убедиться что эмулятор/устройство в той же сети

### 3. "404 Not Found"

**Причина:** Неправильный путь API

**Решение:**
- URL должен заканчиваться на `/api/`
- Проверить: `http://<host>:8000/api/guitars/`

### 4. "400 Bad Request" при создании/обновлении

**Причина:** Неправильный формат данных

**Решение:**
- Проверить GuitarRequest (имена полей должны совпадать)
- Включить логирование Retrofit для отладки

---

## Отладка API запросов

### Включить подробное логирование

В `RetrofitClient.kt` уже настроен `HttpLoggingInterceptor`:

```kotlin
.addInterceptor(HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
})
```

### Просмотр запросов

1. Запустить приложение
2. Выполнить действие (поиск, фильтр, CRUD)
3. Открыть Logcat в Android Studio
4. Фильтр: `Retrofit`

Пример вывода:
```
--> GET http://10.0.2.2:8000/api/guitars/?search=Fender&page=1
--> END GET
<-- 200 OK (156ms)
<-- END HTTP (2048-byte body)
```

---

## API Endpoints

| Метод | Endpoint | Описание |
|-------|----------|----------|
| GET | `/api/guitars/` | Список с пагинацией |
| GET | `/api/guitars/{id}/` | Детали |
| POST | `/api/guitars/` | Создать |
| PUT | `/api/guitars/{id}/` | Обновить |
| DELETE | `/api/guitars/{id}/` | Удалить |
| GET | `/api/brands/` | Бренды |
| GET | `/api/categories/` | Категории |

### Параметры для GET /api/guitars/

- `search` — поиск по названию
- `brand` — ID бренда
- `category` — ID категории
- `minPrice` — мин. цена
- `maxPrice` — макс. цена
- `page` — номер страницы (начинается с 1)
- `page_size` — размер страницы (по умолчанию 20)

---

## Формат данных

### Guitar (ответ API)

```json
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
  "created_at": "2024-01-01T00:00:00Z"
}
```

### PageResponse (пагинация)

```json
{
  "count": 1000000,
  "next": "http://.../api/guitars/?page=2",
  "previous": null,
  "results": [...]
}
```

---

## Сборка и запуск

```bash
cd D:\project\MUSICSHOP\android

# Очистка
./gradlew clean

# Сборка debug
./gradlew assembleDebug

# Установка на устройство
adb install app/build/outputs/apk/debug/app-debug.apk
```

Или просто нажмите **Run** в Android Studio.
