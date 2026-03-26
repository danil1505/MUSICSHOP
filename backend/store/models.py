from django.db import models
from django.contrib.postgres.search import SearchVectorField
from django.contrib.postgres.indexes import GinIndex
from django.core.validators import MinValueValidator
import uuid


class Brand(models.Model):
    """Бренд гитар"""
    name = models.CharField(max_length=100, unique=True)
    country = models.CharField(max_length=100, blank=True)
    founded_year = models.IntegerField(null=True, blank=True)
    description = models.TextField(blank=True)

    class Meta:
        verbose_name = 'Бренд'
        verbose_name_plural = 'Бренды'
        ordering = ['name']

    def __str__(self):
        return self.name


class Category(models.Model):
    """Категория гитар"""
    name = models.CharField(max_length=100, unique=True)
    description = models.TextField(blank=True)

    class Meta:
        verbose_name = 'Категория'
        verbose_name_plural = 'Категории'
        ordering = ['name']

    def __str__(self):
        return self.name


class Guitar(models.Model):
    """Гитара"""
    id = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)
    name = models.CharField(max_length=200)
    brand = models.ForeignKey(Brand, on_delete=models.CASCADE, related_name='guitars')
    category = models.ForeignKey(Category, on_delete=models.CASCADE, related_name='guitars')
    price = models.DecimalField(
        max_digits=10,
        decimal_places=2,
        validators=[MinValueValidator(0.01)]
    )
    description = models.TextField(blank=True)
    image_url = models.URLField(blank=True)
    in_stock = models.BooleanField(default=True)
    created_at = models.DateTimeField(auto_now_add=True)
    
    # Поле для полнотекстового поиска
    search_vector = SearchVectorField(null=True, editable=False)

    class Meta:
        verbose_name = 'Гитара'
        verbose_name_plural = 'Гитары'
        ordering = ['-created_at']
        indexes = [
            GinIndex(fields=['search_vector']),
            models.Index(fields=['brand']),
            models.Index(fields=['category']),
            models.Index(fields=['price']),
        ]

    def __str__(self):
        return self.name
