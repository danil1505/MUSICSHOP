package com.guitarstore.ui.detail;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0002J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u0013\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\u0010\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/guitarstore/ui/detail/DetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "api", "Lcom/guitarstore/api/GuitarApi;", "binding", "Lcom/guitarstore/databinding/ActivityDetailBinding;", "guitar", "Lcom/guitarstore/model/Guitar;", "bindGuitar", "", "g", "deleteGuitar", "id", "", "formatPrice", "price", "", "loadGuitar", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "showError", "msg", "app_debug"})
public final class DetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.guitarstore.databinding.ActivityDetailBinding binding;
    @org.jetbrains.annotations.Nullable()
    private com.guitarstore.model.Guitar guitar;
    @org.jetbrains.annotations.NotNull()
    private final com.guitarstore.api.GuitarApi api = null;
    
    public DetailActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadGuitar(java.lang.String id) {
    }
    
    private final void bindGuitar(com.guitarstore.model.Guitar g) {
    }
    
    private final void deleteGuitar(java.lang.String id) {
    }
    
    private final java.lang.String formatPrice(double price) {
        return null;
    }
    
    private final void showError(java.lang.String msg) {
    }
}