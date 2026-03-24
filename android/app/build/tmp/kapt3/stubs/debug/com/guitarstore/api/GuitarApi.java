package com.guitarstore.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u000e0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJj\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00150\u00032\n\b\u0003\u0010\u0016\u001a\u0004\u0018\u00010\u000b2\n\b\u0003\u0010\u0017\u001a\u0004\u0018\u00010\u00182\n\b\u0003\u0010\u0019\u001a\u0004\u0018\u00010\u00182\n\b\u0003\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\n\b\u0003\u0010\u001c\u001a\u0004\u0018\u00010\u001b2\b\b\u0003\u0010\u001d\u001a\u00020\u00182\b\b\u0003\u0010\u001e\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u001fJ(\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010!\u00a8\u0006\""}, d2 = {"Lcom/guitarstore/api/GuitarApi;", "", "createGuitar", "Lretrofit2/Response;", "Lcom/guitarstore/model/Guitar;", "request", "Lcom/guitarstore/model/GuitarRequest;", "(Lcom/guitarstore/model/GuitarRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteGuitar", "Ljava/lang/Void;", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBrands", "", "Lcom/guitarstore/model/Brand;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCategories", "Lcom/guitarstore/model/Category;", "getGuitar", "getGuitars", "Lcom/guitarstore/model/PageResponse;", "search", "brand", "", "category", "minPrice", "", "maxPrice", "page", "size", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/Double;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateGuitar", "(Ljava/lang/String;Lcom/guitarstore/model/GuitarRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface GuitarApi {
    
    @retrofit2.http.GET(value = "guitars")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGuitars(@retrofit2.http.Query(value = "search")
    @org.jetbrains.annotations.Nullable()
    java.lang.String search, @retrofit2.http.Query(value = "brand")
    @org.jetbrains.annotations.Nullable()
    java.lang.Integer brand, @retrofit2.http.Query(value = "category")
    @org.jetbrains.annotations.Nullable()
    java.lang.Integer category, @retrofit2.http.Query(value = "minPrice")
    @org.jetbrains.annotations.Nullable()
    java.lang.Double minPrice, @retrofit2.http.Query(value = "maxPrice")
    @org.jetbrains.annotations.Nullable()
    java.lang.Double maxPrice, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "size")
    int size, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.guitarstore.model.PageResponse<com.guitarstore.model.Guitar>>> $completion);
    
    @retrofit2.http.GET(value = "guitars/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGuitar(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.guitarstore.model.Guitar>> $completion);
    
    @retrofit2.http.POST(value = "guitars")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createGuitar(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.guitarstore.model.GuitarRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.guitarstore.model.Guitar>> $completion);
    
    @retrofit2.http.PUT(value = "guitars/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateGuitar(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.guitarstore.model.GuitarRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.guitarstore.model.Guitar>> $completion);
    
    @retrofit2.http.DELETE(value = "guitars/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteGuitar(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.lang.Void>> $completion);
    
    @retrofit2.http.GET(value = "brands")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getBrands(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.guitarstore.model.Brand>>> $completion);
    
    @retrofit2.http.GET(value = "categories")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCategories(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.guitarstore.model.Category>>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}