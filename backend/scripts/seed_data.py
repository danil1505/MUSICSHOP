"""
Скрипт для генерации тестовых данных в базу данных.
Генерирует 1 000 000 записей гитар.
"""
import os
import sys
import random
from decimal import Decimal
from datetime import datetime, timedelta

import django

# Настройка Django
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'config.settings')
django.setup()

from faker import Faker
from django.contrib.postgres.search import SearchVector
from store.models import Brand, Category, Guitar

fake = Faker(['ru_RU', 'en_US'])

# Данные для генерации
BRANDS_DATA = [
    {'name': 'Gibson', 'country': 'USA', 'founded_year': 1902},
    {'name': 'Fender', 'country': 'USA', 'founded_year': 1946},
    {'name': 'Yamaha', 'country': 'Japan', 'founded_year': 1887},
    {'name': 'Ibanez', 'country': 'Japan', 'founded_year': 1957},
    {'name': 'ESP', 'country': 'Japan', 'founded_year': 1975},
    {'name': 'PRS', 'country': 'USA', 'founded_year': 1985},
    {'name': 'Jackson', 'country': 'USA', 'founded_year': 1980},
    {'name': 'Schecter', 'country': 'USA', 'founded_year': 1976},
    {'name': 'Epiphone', 'country': 'USA', 'founded_year': 1873},
    {'name': 'Squier', 'country': 'USA', 'founded_year': 1890},
    {'name': 'Martin', 'country': 'USA', 'founded_year': 1833},
    {'name': 'Taylor', 'country': 'USA', 'founded_year': 1974},
    {'name': 'Gibson', 'country': 'USA', 'founded_year': 1902},
    {'name': 'Takamine', 'country': 'Japan', 'founded_year': 1962},
    {'name': 'Washburn', 'country': 'USA', 'founded_year': 1883},
    {'name': 'Dean', 'country': 'USA', 'founded_year': 1977},
]

CATEGORIES_DATA = [
    {'name': 'Электрогитара', 'description': 'Твердотельная гитара с электрическими звукоснимателями'},
    {'name': 'Акустическая', 'description': 'Классическая акустическая гитара'},
    {'name': 'Классическая', 'description': 'Гитара с нейлоновыми струнами'},
    {'name': 'Бас-гитара', 'description': 'Низкочастотная гитара'},
    {'name': 'Полуакустическая', 'description': 'Гитара с резонаторной камерой'},
    {'name': 'Электроакустическая', 'description': 'Акустическая гитара со встроенным звукоснимателем'},
]


def create_brands_and_categories():
    """Создание брендов и категорий"""
    print("Создание брендов...")
    brands = {}
    for brand_data in BRANDS_DATA:
        brand, _ = Brand.objects.get_or_create(
            name=brand_data['name'],
            defaults=brand_data
        )
        brands[brand.name] = brand
    print(f"Создано {len(brands)} брендов")
    
    print("Создание категорий...")
    categories = {}
    for cat_data in CATEGORIES_DATA:
        cat, _ = Category.objects.get_or_create(
            name=cat_data['name'],
            defaults=cat_data
        )
        categories[cat.name] = cat
    print(f"Создано {len(categories)} категорий")
    
    return list(brands.values()), list(categories.values())


def generate_guitar_name(brand, category):
    """Генерация названия гитары"""
    prefixes = ['Pro', 'Super', 'Ultra', 'Classic', 'Vintage', 'Modern', 'Custom', 'Standard', 'Elite', 'Master']
    suffixes = ['X', 'S', 'Pro', 'Plus', 'Max', 'II', 'III', 'DX', 'LX', 'Custom']
    
    category_name = category.name.lower()
    if 'электро' in category_name:
        model_series = random.choice(['Strat', 'Tele', 'Les', 'SG', 'Flying', 'Explorer', 'Super'])
    elif 'акуст' in category_name or 'классич' in category_name:
        model_series = random.choice(['Dread', 'Concert', 'Grand', 'Folk', 'Classical'])
    elif 'бас' in category_name:
        model_series = random.choice(['Precision', 'Jazz', 'Active', 'Fretless'])
    else:
        model_series = random.choice(['Pro', 'Standard', 'Classic'])
    
    model_number = random.randint(100, 9999)
    suffix = random.choice(['', '', '', f' {random.choice(suffixes)}'])
    
    return f"{brand.name} {model_series} {model_number}{suffix}"


def generate_description(brand, category, price):
    """Генерация описания гитары"""
    templates = [
        f"Превосходная {category.name.lower()} от {brand.name}. Отличное качество звука и удобство игры.",
        f"Профессиональная {category.name.lower()} для музыкантов любого уровня. Цена: {price}₽.",
        f"Классический дизайн и современное звучание. {brand.name} - выбор профессионалов.",
        f"Идеальный инструмент для студии и сцены. {category.name} от легендарного бренда.",
        f"Высококачественная {category.name.lower()} с превосходной отделкой и звучанием.",
        f"Эта {category.name.lower()} сочетает традиции и инновации. Рекомендуется для всех уровней.",
        f"Непревзойденное качество {brand.name}. Подходит для любого жанра музыки.",
        f"Отличный выбор для начинающих и профессионалов. Проверенное качество.",
    ]
    return random.choice(templates)


def bulk_create_guitars(count=1_000_000, batch_size=1000):
    """Массовое создание гитар"""
    brands, categories = create_brands_and_categories()
    
    print(f"\nГенерация {count:,} гитар...")
    print(f"Размер батча: {batch_size}")
    
    total_created = 0
    guitars_to_create = []
    
    start_time = datetime.now()
    
    for i in range(count):
        brand = random.choice(brands)
        category = random.choice(categories)
        
        # Генерация цены с учетом категории
        base_prices = {
            'Электрогитара': (30000, 300000),
            'Акустическая': (15000, 150000),
            'Классическая': (10000, 100000),
            'Бас-гитара': (25000, 250000),
            'Полуакустическая': (40000, 400000),
            'Электроакустическая': (20000, 200000),
        }
        min_price, max_price = base_prices.get(category.name, (10000, 200000))
        price = Decimal(str(random.randint(min_price, max_price)))
        
        guitar = Guitar(
            name=generate_guitar_name(brand, category),
            brand=brand,
            category=category,
            price=price,
            description=generate_description(brand, category, price),
            image_url=f"https://example.com/guitars/{random.randint(1, 10000)}.jpg",
            in_stock=random.choice([True, True, True, False]),  # 75% в наличии
            created_at=fake.date_time_between(start_date='-2y', end_date='now'),
        )
        guitars_to_create.append(guitar)
        
        # Пакетная вставка
        if len(guitars_to_create) >= batch_size:
            Guitar.objects.bulk_create(guitars_to_create)
            total_created += len(guitars_to_create)
            elapsed = (datetime.now() - start_time).total_seconds()
            rate = total_created / elapsed if elapsed > 0 else 0
            print(f"  Создано: {total_created:,} ({rate:.0f} записей/сек)")
            guitars_to_create = []
    
    # Вставка оставшихся
    if guitars_to_create:
        Guitar.objects.bulk_create(guitars_to_create)
        total_created += len(guitars_to_create)
    
    elapsed = (datetime.now() - start_time).total_seconds()
    print(f"\n✓ Готово! Создано {total_created:,} гитар за {elapsed:.1f} сек")
    print(f"  Средняя скорость: {total_created/elapsed:.0f} записей/сек")
    
    return total_created


def update_search_vectors():
    """Обновление search_vector для всех гитар"""
    print("\nОбновление search_vector...")
    
    Guitar.objects.update(
        search_vector=SearchVector('name', weight='A') + SearchVector('description', weight='B')
    )
    
    count = Guitar.objects.exclude(search_vector__isnull=True).count()
    print(f"✓ Обновлено {count:,} search_vector")


def show_stats():
    """Показать статистику"""
    print("\n" + "="*50)
    print("СТАТИСТИКА БАЗЫ ДАННЫХ")
    print("="*50)
    print(f"Бренды: {Brand.objects.count()}")
    print(f"Категории: {Category.objects.count()}")
    print(f"Гитары: {Guitar.objects.count():,}")
    
    if Guitar.objects.exists():
        print(f"\nДиапазон цен:")
        print(f"  Мин: {Guitar.objects.order_by('price').first().price}₽")
        print(f"  Макс: {Guitar.objects.order_by('-price').first().price}₽")
        avg_price = Guitar.objects.all().aggregate(models.Avg('price'))['price__avg']
        print(f"  Среднее: {avg_price:,.0f}₽")
        
        print(f"\nВ наличии: {Guitar.objects.filter(in_stock=True).count():,}")
        print(f"Нет в наличии: {Guitar.objects.filter(in_stock=False).count():,}")


if __name__ == '__main__':
    from django.db import models
    
    # Количество для генерации (по умолчанию 1 000 000)
    count = int(sys.argv[1]) if len(sys.argv) > 1 else 1_000_000
    batch_size = int(sys.argv[2]) if len(sys.argv) > 2 else 1000
    
    print("="*50)
    print("ГЕНЕРАЦИЯ ТЕСТОВЫХ ДАННЫХ")
    print("="*50)
    print(f"Цель: {count:,} гитар")
    print(f"Батч: {batch_size} записей")
    print("="*50)
    
    bulk_create_guitars(count=count, batch_size=batch_size)
    update_search_vectors()
    show_stats()
