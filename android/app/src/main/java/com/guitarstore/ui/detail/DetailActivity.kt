package com.guitarstore.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.guitarstore.R
import com.guitarstore.api.RetrofitClient
import com.guitarstore.databinding.ActivityDetailBinding
import com.guitarstore.model.Guitar
import com.guitarstore.ui.addedit.AddEditActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var guitar: Guitar? = null
    private val api = RetrofitClient.api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val id = intent.getStringExtra("guitar_id") ?: run { finish(); return }
        loadGuitar(id)
    }

    private fun loadGuitar(id: String) {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val resp = api.getGuitar(id)
                if (resp.isSuccessful) {
                    guitar = resp.body()
                    guitar?.let { bindGuitar(it) }
                } else {
                    showError("Ошибка загрузки: ${resp.code()}")
                }
            } catch (e: Exception) {
                showError("Нет соединения: ${e.message}")
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun bindGuitar(g: Guitar) {
        supportActionBar?.title = g.name
        binding.tvName.text        = g.name
        binding.tvBrand.text       = "Бренд: ${g.brandName}"
        binding.tvCategory.text    = "Категория: ${g.categoryName}"
        binding.tvPrice.text       = formatPrice(g.price)
        binding.tvDescription.text = g.description ?: "Описание отсутствует"
        binding.tvStock.text       = if (g.inStock) "✓ В наличии" else "✗ Нет в наличии"
        binding.tvStock.setTextColor(
            getColor(if (g.inStock) R.color.color_in_stock else R.color.color_out_of_stock)
        )

        // Load real image from database
        Glide.with(this)
            .load(g.imageUrl)
            .placeholder(R.drawable.ic_guitar_placeholder)
            .error(R.drawable.ic_guitar_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(binding.ivGuitar)

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            intent.putExtra("guitar_id", g.id)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Удалить гитару?")
                .setMessage("«${g.name}» будет удалена безвозвратно.")
                .setPositiveButton("Удалить") { _, _ -> deleteGuitar(g.id) }
                .setNegativeButton("Отмена", null)
                .show()
        }
    }

    private fun deleteGuitar(id: String) {
        lifecycleScope.launch {
            try {
                val resp = api.deleteGuitar(id)
                if (resp.isSuccessful) finish()
                else showError("Ошибка удаления: ${resp.code()}")
            } catch (e: Exception) {
                showError("Нет соединения")
            }
        }
    }

    private fun formatPrice(price: Double): String {
        val fmt = NumberFormat.getNumberInstance(Locale("ru", "RU"))
        return "${fmt.format(price.toLong())} ₽"
    }

    private fun showError(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }
}
