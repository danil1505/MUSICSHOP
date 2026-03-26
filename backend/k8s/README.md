# Инструкция по развёртыванию Kubernetes

## Предварительные требования

1. **Docker Desktop с включенным Kubernetes**
   - Откройте Docker Desktop Settings
   - Перейдите в раздел **Kubernetes**
   - Включите **Enable Kubernetes**
   - Нажмите **Apply & Restart**

2. **kubectl** - установлен (проверка: `kubectl version --client`)

## Проверка кластера

После включения Kubernetes в Docker Desktop:

```bash
# Проверка подключения
kubectl cluster-info

# Проверка ноды
kubectl get nodes
```

## Развёртывание приложения

### Вариант 1: Через kustomize (рекомендуется)

```bash
cd backend

# Применить все манифесты
kubectl apply -k k8s/

# Проверить статус
kubectl get all -n guitarstore
```

### Вариант 2: По отдельности

```bash
# Создать namespace
kubectl apply -f k8s/namespace.yaml

# Применить конфигурацию
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml

# Развернуть PostgreSQL
kubectl apply -f k8s/postgres-pvc.yaml
kubectl apply -f k8s/postgres-deployment.yaml

# Применить миграции
kubectl apply -f k8s/migrate-job.yaml
kubectl wait -n guitarstore job/migrate --for=condition=complete --timeout=120s

# Развернуть backend
kubectl apply -f k8s/backend-deployment.yaml

# Настроить ingress
kubectl apply -f k8s/ingress.yaml
```

## Проверка развёртывания

```bash
# Проверить поды
kubectl get pods -n guitarstore

# Проверить сервисы
kubectl get services -n guitarstore

# Проверить_jobs (миграции и генерация данных)
kubectl get jobs -n guitarstore

# Просмотр логов backend
kubectl logs -n guitarstore -l app=backend

# Просмотр логов миграции
kubectl logs -n guitarstore job/migrate
```

## Генерация тестовых данных

```bash
# Запустить job для генерации 1 000 000 записей
kubectl apply -f k8s/seed-job.yaml

# Следить за прогрессом
kubectl get jobs -n guitarstore -w
kubectl logs -n guitarstore job/seed-data
```

## Доступ к приложению

### Через port-forward (локальный доступ)

```bash
# Проброс порта для backend
kubectl port-forward -n guitarstore svc/backend-service 8000:80

# Теперь API доступно по адресу http://localhost:8000
```

### Через Ingress

Если настроен Ingress контроллер:

```bash
# Добавить в hosts файл (C:\Windows\System32\drivers\etc\hosts):
# 127.0.0.1 api.guitarstore.local

# Доступ по адресу http://api.guitarstore.local
```

## Масштабирование

```bash
# Изменить количество реплик
kubectl scale -n guitarstore deployment/backend --replicas=5

# Проверить HPA
kubectl get hpa -n guitarstore
```

## Очистка

```bash
# Удалить все ресурсы
kubectl delete -k k8s/

# Или удалить namespace (удалит всё внутри)
kubectl delete namespace guitarstore
```

## Диагностика проблем

```bash
# Описать под (показать события)
kubectl describe pod -n guitarstore -l app=backend

# Проверить события namespace
kubectl get events -n guitarstore --sort-by='.lastTimestamp'

# Проверить логи конкретного пода
kubectl logs -n guitarstore <pod-name>

# Выполнить команду в поде
kubectl exec -n guitarstore <pod-name> -- python manage.py shell
```

## Архитектура развёртывания

```
┌─────────────────────────────────────────────────────┐
│                  Kubernetes Cluster                  │
│                   (Docker Desktop)                   │
└─────────────────────────────────────────────────────┘
                         │
         ┌───────────────┴───────────────┐
         │                               │
┌────────▼────────┐            ┌────────▼────────┐
│  Ingress        │            │   HPA           │
│  (nginx)        │            │  (3-10 реплик)  │
│  :80            │            │                 │
└────────┬────────┘            └────────┬────────┘
         │                               │
         └───────────────┬───────────────┘
                         │
         ┌───────────────┴───────────────┐
         │                               │
┌────────▼────────┐            ┌────────▼────────┐
│  Backend Pod #1 │            │  Backend Pod #2 │
│  :8000          │            │  :8000          │
│  +-------------+│            │+--------------+ │
│  | Django     ││            ││ Django      │ │
│  | Gunicorn   ││     ...    ││ Gunicorn    │ │
│  +-------------+│            │+--------------+ │
└────────┬────────┘            └───────┬─────────┘
         │                             │
         └──────────────┬──────────────┘
                        │
              ┌─────────▼──────────┐
              │  postgres-service  │
              │     ClusterIP      │
              │       :5432        │
              └─────────┬──────────┘
                        │
              ┌─────────▼──────────┐
              │   PostgreSQL Pod   │
              │   PersistentVolume │
              │      (10Gi)        │
              └────────────────────┘
```

## Ресурсы

| Ресурс | Имя | Описание |
|--------|-----|----------|
| Namespace | guitarstore | Изолированное пространство |
| ConfigMap | guitarstore-config | Конфигурация (DB_HOST, DEBUG...) |
| Secret | guitarstore-secret | Секреты (SECRET_KEY, DB_PASSWORD) |
| Deployment | postgres | PostgreSQL база данных |
| Service | postgres-service | Внутренний доступ к БД |
| PVC | postgres-pvc | Постоянное хранилище (10Gi) |
| Job | migrate | Применение миграций Django |
| Deployment | backend | Django приложение (3 реплики) |
| Service | backend-service | Внутренний балансировщик |
| HPA | backend-hpa | Автомасштабирование (3-10 реплик) |
| Job | seed-data | Генерация 1M записей |
| Ingress | backend-ingress | Внешний доступ (nginx) |
