package com.guitarstore.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J+\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u000e\b\u0002\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u000b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\u000fH\u00d6\u0001J\t\u0010 \u001a\u00020!H\u00d6\u0001R\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b8F\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u000f8F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0011\u00a8\u0006\""}, d2 = {"Lcom/guitarstore/model/PageResponse;", "T", "", "content", "", "page", "Lcom/guitarstore/model/PageInfo;", "(Ljava/util/List;Lcom/guitarstore/model/PageInfo;)V", "getContent", "()Ljava/util/List;", "last", "", "getLast", "()Z", "number", "", "getNumber", "()I", "getPage", "()Lcom/guitarstore/model/PageInfo;", "totalElements", "", "getTotalElements", "()J", "totalPages", "getTotalPages", "component1", "component2", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class PageResponse<T extends java.lang.Object> {
    @com.google.gson.annotations.SerializedName(value = "content")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<T> content = null;
    @com.google.gson.annotations.SerializedName(value = "page")
    @org.jetbrains.annotations.Nullable()
    private final com.guitarstore.model.PageInfo page = null;
    
    public PageResponse(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> content, @org.jetbrains.annotations.Nullable()
    com.guitarstore.model.PageInfo page) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<T> getContent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.guitarstore.model.PageInfo getPage() {
        return null;
    }
    
    public final long getTotalElements() {
        return 0L;
    }
    
    public final int getTotalPages() {
        return 0;
    }
    
    public final int getNumber() {
        return 0;
    }
    
    public final boolean getLast() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<T> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.guitarstore.model.PageInfo component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.guitarstore.model.PageResponse<T> copy(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> content, @org.jetbrains.annotations.Nullable()
    com.guitarstore.model.PageInfo page) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}