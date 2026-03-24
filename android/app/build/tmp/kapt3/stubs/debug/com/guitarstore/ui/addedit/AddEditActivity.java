package com.guitarstore.ui.addedit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\u0012\u0010\u001b\u001a\u00020\u001a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u001aH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0012R\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0018\u0010\u000f\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001f"}, d2 = {"Lcom/guitarstore/ui/addedit/AddEditActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/guitarstore/databinding/ActivityAddEditBinding;", "brands", "", "Lcom/guitarstore/model/Brand;", "categories", "Lcom/guitarstore/model/Category;", "editId", "", "getEditId", "()Ljava/lang/String;", "editId$delegate", "Lkotlin/Lazy;", "selectedBrandId", "", "Ljava/lang/Integer;", "selectedCategoryId", "viewModel", "Lcom/guitarstore/viewmodel/AddEditViewModel;", "getViewModel", "()Lcom/guitarstore/viewmodel/AddEditViewModel;", "viewModel$delegate", "observeViewModel", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "save", "app_debug"})
public final class AddEditActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.guitarstore.databinding.ActivityAddEditBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy editId$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.guitarstore.model.Brand> brands;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.guitarstore.model.Category> categories;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer selectedBrandId;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer selectedCategoryId;
    
    public AddEditActivity() {
        super();
    }
    
    private final com.guitarstore.viewmodel.AddEditViewModel getViewModel() {
        return null;
    }
    
    private final java.lang.String getEditId() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void observeViewModel() {
    }
    
    private final void save() {
    }
}