from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import BrandViewSet, CategoryViewSet, GuitarViewSet

router = DefaultRouter()
router.register(r'brands', BrandViewSet, basename='brand')
router.register(r'categories', CategoryViewSet, basename='category')
router.register(r'guitars', GuitarViewSet, basename='guitar')

urlpatterns = [
    path('', include(router.urls)),
]
