package com.guitarstore.ui.addedit

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.guitarstore.databinding.ActivityAddEditBinding
import com.guitarstore.model.Brand
import com.guitarstore.model.Category
import com.guitarstore.viewmodel.AddEditViewModel
import com.google.android.material.snackbar.Snackbar

class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding
    private val viewModel: AddEditViewModel by viewModels()
    private val editId: String? by lazy { intent.getStringExtra("guitar_id") }

    private var brands:     List<Brand>    = emptyList()
    private var categories: List<Category> = emptyList()
    private var selectedBrandId:    Int? = null
    private var selectedCategoryId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        supportActionBar?.title = if (editId == null) "Добавить гитару" else "Редактировать"

        if (editId != null) viewModel.loadGuitar(editId!!)

        observeViewModel()

        binding.btnSave.setOnClickListener { save() }
    }

    private fun observeViewModel() {
        viewModel.brands.observe(this) { list ->
            brands = list
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, list)
            binding.actvBrand.setAdapter(adapter)
            binding.actvBrand.setOnItemClickListener { _, _, pos, _ ->
                selectedBrandId = list[pos].id
            }
        }

        viewModel.categories.observe(this) { list ->
            categories = list
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, list)
            binding.actvCategory.setAdapter(adapter)
            binding.actvCategory.setOnItemClickListener { _, _, pos, _ ->
                selectedCategoryId = list[pos].id
            }
        }

        viewModel.guitar.observe(this) { g ->
            g ?: return@observe
            binding.etName.setText(g.name)
            binding.etPrice.setText(g.price.toLong().toString())
            binding.etDescription.setText(g.description)
            binding.etImageUrl.setText(g.imageUrl)
            binding.switchInStock.isChecked = g.inStock

            selectedBrandId = g.brandId
            selectedCategoryId = g.categoryId

            val brand = brands.find { it.id == g.brandId }
            val cat   = categories.find { it.id == g.categoryId }
            if (brand != null)  binding.actvBrand.setText(brand.name, false)
            if (cat != null)    binding.actvCategory.setText(cat.name, false)
        }

        viewModel.saved.observe(this) { saved ->
            if (saved) finish()
        }

        viewModel.error.observe(this) { msg ->
            if (!msg.isNullOrEmpty())
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
        }

        viewModel.loading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            binding.btnSave.isEnabled = !loading
        }
    }

    private fun save() {
        val name = binding.etName.text.toString().trim()
        if (name.isEmpty()) {
            binding.tilName.error = "Введите название"
            return
        }
        binding.tilName.error = null

        val price = binding.etPrice.text.toString().toDoubleOrNull()
        if (price == null || price <= 0) {
            binding.tilPrice.error = "Введите корректную цену"
            return
        }
        binding.tilPrice.error = null

        if (selectedBrandId == null) {
            Snackbar.make(binding.root, "Выберите бренд", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (selectedCategoryId == null) {
            Snackbar.make(binding.root, "Выберите категорию", Snackbar.LENGTH_SHORT).show()
            return
        }

        viewModel.save(
            id          = editId,
            name        = name,
            brandId     = selectedBrandId!!,
            categoryId  = selectedCategoryId!!,
            price       = price,
            description = binding.etDescription.text.toString().trim().ifEmpty { null },
            imageUrl    = binding.etImageUrl.text.toString().trim().ifEmpty { null },
            inStock     = binding.switchInStock.isChecked
        )
    }
}
