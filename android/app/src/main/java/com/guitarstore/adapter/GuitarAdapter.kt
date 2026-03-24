package com.guitarstore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.guitarstore.R
import com.guitarstore.databinding.ItemGuitarBinding
import com.guitarstore.model.Guitar
import java.text.NumberFormat
import java.util.Locale

class GuitarAdapter(
    private val onClick: (Guitar) -> Unit
) : ListAdapter<Guitar, GuitarAdapter.GuitarViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuitarViewHolder {
        val binding = ItemGuitarBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GuitarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuitarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GuitarViewHolder(
        private val binding: ItemGuitarBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(guitar: Guitar) {
            binding.tvName.text     = guitar.name
            binding.tvBrand.text    = guitar.brandName
            binding.tvCategory.text = guitar.categoryName
            binding.tvPrice.text    = formatPrice(guitar.price)
            binding.tvStock.text    = if (guitar.inStock) "В наличии" else "Нет в наличии"
            binding.tvStock.setTextColor(
                binding.root.context.getColor(
                    if (guitar.inStock) R.color.color_in_stock
                    else               R.color.color_out_of_stock
                )
            )

            // Load real image from database image_url
            Glide.with(binding.ivGuitar.context)
                .load(guitar.imageUrl)
                .placeholder(R.drawable.ic_guitar_placeholder)
                .error(R.drawable.ic_guitar_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(300, 200)
                .centerCrop()
                .into(binding.ivGuitar)

            binding.root.setOnClickListener { onClick(guitar) }
        }

        private fun formatPrice(price: Double): String {
            val fmt = NumberFormat.getNumberInstance(Locale("ru", "RU"))
            return "${fmt.format(price.toLong())} ₽"
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Guitar>() {
            override fun areItemsTheSame(a: Guitar, b: Guitar)    = a.id == b.id
            override fun areContentsTheSame(a: Guitar, b: Guitar) = a == b
        }
    }
}
