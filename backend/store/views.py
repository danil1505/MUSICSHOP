from rest_framework import viewsets, filters, status
from rest_framework.response import Response
from django_filters.rest_framework import DjangoFilterBackend
from django.contrib.postgres.search import SearchVector
from .models import Brand, Category, Guitar
from .serializers import (
    BrandSerializer, CategorySerializer,
    GuitarSerializer, GuitarCreateUpdateSerializer
)


class BrandViewSet(viewsets.ReadOnlyModelViewSet):
    """ViewSet для брендов (только чтение)"""
    queryset = Brand.objects.all()
    serializer_class = BrandSerializer
    filter_backends = [filters.OrderingFilter]
    ordering_fields = ['name', 'country']


class CategoryViewSet(viewsets.ReadOnlyModelViewSet):
    """ViewSet для категорий (только чтение)"""
    queryset = Category.objects.all()
    serializer_class = CategorySerializer
    filter_backends = [filters.OrderingFilter]
    ordering_fields = ['name']


class GuitarViewSet(viewsets.ModelViewSet):
    """ViewSet для гитар (CRUD + поиск + фильтрация)"""
    queryset = Guitar.objects.all()
    filter_backends = [DjangoFilterBackend, filters.SearchFilter, filters.OrderingFilter]
    filterset_fields = ['brand', 'category', 'in_stock']
    search_fields = ['name', 'description', 'brand__name', 'category__name']
    ordering_fields = ['price', 'created_at', 'name']

    def get_serializer_class(self):
        if self.action in ['create', 'update', 'partial_update']:
            return GuitarCreateUpdateSerializer
        return GuitarSerializer

    def get_queryset(self):
        queryset = Guitar.objects.all()
        
        # Фильтр по цене
        min_price = self.request.query_params.get('minPrice')
        max_price = self.request.query_params.get('maxPrice')
        if min_price:
            queryset = queryset.filter(price__gte=min_price)
        if max_price:
            queryset = queryset.filter(price__lte=max_price)
        
        # Полнотекстовый поиск через PostgreSQL
        search = self.request.query_params.get('search')
        if search:
            queryset = queryset.annotate(
                search=SearchVector('name', weight='A') + SearchVector('description', weight='B')
            ).filter(search=search)
        
        return queryset

    def create(self, request, *args, **kwargs):
        response = super().create(request, *args, **kwargs)
        # Обновляем search_vector после создания
        instance = Guitar.objects.get(pk=response.data['id'])
        Guitar.objects.filter(pk=instance.pk).update(
            search_vector=SearchVector('name', weight='A') + SearchVector('description', weight='B')
        )
        return response

    def update(self, request, *args, **kwargs):
        response = super().update(request, *args, **kwargs)
        # Обновляем search_vector после обновления
        Guitar.objects.filter(pk=self.kwargs['pk']).update(
            search_vector=SearchVector('name', weight='A') + SearchVector('description', weight='B')
        )
        return response
