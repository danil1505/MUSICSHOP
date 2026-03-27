package com.guitarstore.viewmodel

import androidx.lifecycle.*
import com.guitarstore.model.*
import com.guitarstore.repository.GuitarRepository
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private val repo = GuitarRepository()

    // ── filter state ─────────────────────────────────────────────
    var searchQuery: String?  = null
    var filterBrand: Int?     = null
    var filterCategory: Int?  = null
    var filterMinPrice: Double? = null
    var filterMaxPrice: Double? = null
    private var currentPage   = 0
    private var totalPages    = 1
    private var isLoading     = false

    // ── UI state ──────────────────────────────────────────────────
    private val _guitars      = MutableLiveData<List<Guitar>>(emptyList())
    val guitars: LiveData<List<Guitar>> = _guitars

    private val _loading      = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error        = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    val brands     = MutableLiveData<List<Brand>>(emptyList())
    val categories = MutableLiveData<List<Category>>(emptyList())

    init {
        loadReferences()
        loadGuitars(reset = true)
    }

    fun loadGuitars(reset: Boolean = false) {
        if (isLoading) return
        if (!reset && currentPage >= totalPages) return

        if (reset) {
            currentPage = 1  // Django pagination starts from 1
            totalPages  = 1
            _guitars.value = emptyList()
        }

        isLoading = true
        _loading.value = true

        viewModelScope.launch {
            try {
                val resp = repo.getGuitars(
                    search    = searchQuery,
                    brand     = filterBrand,
                    category  = filterCategory,
                    minPrice  = filterMinPrice,
                    maxPrice  = filterMaxPrice,
                    page      = currentPage
                )
                if (resp.isSuccessful) {
                    val body = resp.body()!!
                    totalPages = body.totalPages
                    val existing = if (reset) emptyList() else _guitars.value ?: emptyList()
                    _guitars.value = existing + body.results  // Django uses 'results' not 'content'
                    currentPage++
                } else {
                    _error.value = "Ошибка: ${resp.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Нет соединения: ${e.message}"
            } finally {
                isLoading = false
                _loading.value = false
            }
        }
    }

    fun loadNextPage() = loadGuitars(reset = false)

    fun search(query: String?) {
        searchQuery = if (query.isNullOrBlank()) null else query
        loadGuitars(reset = true)
    }

    fun applyFilters(brand: Int?, category: Int?, minPrice: Double?, maxPrice: Double?) {
        filterBrand     = brand
        filterCategory  = category
        filterMinPrice  = minPrice
        filterMaxPrice  = maxPrice
        loadGuitars(reset = true)
    }

    fun clearFilters() {
        filterBrand    = null
        filterCategory = null
        filterMinPrice = null
        filterMaxPrice = null
        loadGuitars(reset = true)
    }

    fun deleteGuitar(id: String) {
        viewModelScope.launch {
            try {
                repo.deleteGuitar(id)
                loadGuitars(reset = true)
            } catch (e: Exception) {
                _error.value = "Ошибка удаления: ${e.message}"
            }
        }
    }

    private fun loadReferences() {
        viewModelScope.launch {
            try {
                brands.value     = repo.getBrands().body()     ?: emptyList()
                categories.value = repo.getCategories().body() ?: emptyList()
            } catch (_: Exception) {}
        }
    }

    fun hasMorePages() = currentPage < totalPages
}
