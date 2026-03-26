from django.contrib import admin
from django.contrib.postgres.search import SearchVector
from .models import Brand, Category, Guitar


@admin.register(Brand)
class BrandAdmin(admin.ModelAdmin):
    list_display = ('name', 'country', 'founded_year')
    search_fields = ('name', 'country')
    list_filter = ('country',)


@admin.register(Category)
class CategoryAdmin(admin.ModelAdmin):
    list_display = ('name',)
    search_fields = ('name',)


@admin.register(Guitar)
class GuitarAdmin(admin.ModelAdmin):
    list_display = ('name', 'brand', 'category', 'price', 'in_stock', 'created_at')
    list_filter = ('brand', 'category', 'in_stock')
    search_fields = ('name', 'description', 'brand__name', 'category__name')
    readonly_fields = ('id', 'created_at')
    list_editable = ('price', 'in_stock')
    list_per_page = 50
    
    fieldsets = (
        ('Основная информация', {
            'fields': ('id', 'name', 'brand', 'category')
        }),
        ('Детали', {
            'fields': ('price', 'description', 'image_url', 'in_stock')
        }),
        ('Мета', {
            'fields': ('created_at',),
            'classes': ('collapse',)
        }),
    )
    
    def save_model(self, request, obj, form, change):
        super().save_model(request, obj, form, change)
        # Обновляем search_vector при сохранении
        Guitar.objects.filter(pk=obj.pk).update(
            search_vector=SearchVector('name', weight='A') + SearchVector('description', weight='B')
        )
