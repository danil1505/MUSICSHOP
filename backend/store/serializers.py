from rest_framework import serializers
from .models import Brand, Category, Guitar


class BrandSerializer(serializers.ModelSerializer):
    """Сериализатор для бренда"""
    class Meta:
        model = Brand
        fields = ['id', 'name', 'country', 'founded_year', 'description']


class CategorySerializer(serializers.ModelSerializer):
    """Сериализатор для категории"""
    class Meta:
        model = Category
        fields = ['id', 'name', 'description']


class GuitarSerializer(serializers.ModelSerializer):
    """Сериализатор для гитары"""
    brand_name = serializers.CharField(source='brand.name', read_only=True)
    category_name = serializers.CharField(source='category.name', read_only=True)

    class Meta:
        model = Guitar
        fields = [
            'id', 'name', 'brand', 'brand_name', 'category', 'category_name',
            'price', 'description', 'image_url', 'in_stock', 'created_at'
        ]
        read_only_fields = ['id', 'created_at']


class GuitarCreateUpdateSerializer(serializers.ModelSerializer):
    """Сериализатор для создания/обновления гитары"""
    class Meta:
        model = Guitar
        fields = [
            'name', 'brand', 'category', 'price',
            'description', 'image_url', 'in_stock'
        ]
