package com.guitarstore.viewmodel

import androidx.lifecycle.*
import com.guitarstore.model.*
import com.guitarstore.repository.GuitarRepository
import kotlinx.coroutines.launch

class AddEditViewModel : ViewModel() {

    private val repo = GuitarRepository()

    val brands     = MutableLiveData<List<Brand>>(emptyList())
    val categories = MutableLiveData<List<Category>>(emptyList())

    private val _guitar  = MutableLiveData<Guitar?>()
    val guitar: LiveData<Guitar?> = _guitar

    private val _saved   = MutableLiveData(false)
    val saved: LiveData<Boolean> = _saved

    private val _error   = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    init { loadReferences() }

    fun loadGuitar(id: String) {
        viewModelScope.launch {
            try {
                val resp = repo.getGuitar(id)
                if (resp.isSuccessful) _guitar.value = resp.body()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun save(id: String?, name: String, brandId: Int, categoryId: Int,
             price: Double, description: String?, imageUrl: String?, inStock: Boolean) {
        _loading.value = true
        val req = GuitarRequest(name, brandId, categoryId, price, description, imageUrl, inStock)
        viewModelScope.launch {
            try {
                val resp = if (id == null) repo.createGuitar(req)
                           else           repo.updateGuitar(id, req)
                if (resp.isSuccessful) _saved.value = true
                else _error.value = "Ошибка сохранения: ${resp.code()}"
            } catch (e: Exception) {
                _error.value = "Нет соединения: ${e.message}"
            } finally {
                _loading.value = false
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
}
