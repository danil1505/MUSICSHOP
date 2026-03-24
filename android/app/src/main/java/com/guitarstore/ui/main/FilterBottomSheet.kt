package com.guitarstore.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.guitarstore.databinding.BottomSheetFilterBinding
import com.guitarstore.model.Brand
import com.guitarstore.model.Category
import com.guitarstore.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    private var selectedBrand: Brand?       = null
    private var selectedCategory: Category? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Populate brand dropdown
        viewModel.brands.observe(viewLifecycleOwner) { brands ->
            val items = listOf(Brand(0, "Все бренды")) + brands
            val adapter = ArrayAdapter(requireContext(),
                android.R.layout.simple_dropdown_item_1line, items)
            binding.actvBrand.setAdapter(adapter)
            binding.actvBrand.setOnItemClickListener { _, _, pos, _ ->
                selectedBrand = if (pos == 0) null else items[pos]
            }
            // restore current filter
            val cur = viewModel.filterBrand
            if (cur != null) {
                val b = brands.find { it.id == cur }
                if (b != null) binding.actvBrand.setText(b.name, false)
            }
        }

        // Populate category dropdown
        viewModel.categories.observe(viewLifecycleOwner) { cats ->
            val items = listOf(Category(0, "Все категории")) + cats
            val adapter = ArrayAdapter(requireContext(),
                android.R.layout.simple_dropdown_item_1line, items)
            binding.actvCategory.setAdapter(adapter)
            binding.actvCategory.setOnItemClickListener { _, _, pos, _ ->
                selectedCategory = if (pos == 0) null else items[pos]
            }
            val cur = viewModel.filterCategory
            if (cur != null) {
                val c = cats.find { it.id == cur }
                if (c != null) binding.actvCategory.setText(c.name, false)
            }
        }

        // Restore price filters
        viewModel.filterMinPrice?.let { binding.etMinPrice.setText(it.toLong().toString()) }
        viewModel.filterMaxPrice?.let { binding.etMaxPrice.setText(it.toLong().toString()) }

        binding.btnApply.setOnClickListener {
            val minPrice = binding.etMinPrice.text.toString().toDoubleOrNull()
            val maxPrice = binding.etMaxPrice.text.toString().toDoubleOrNull()
            viewModel.applyFilters(
                brand    = selectedBrand?.id,
                category = selectedCategory?.id,
                minPrice = minPrice,
                maxPrice = maxPrice
            )
            dismiss()
        }

        binding.btnReset.setOnClickListener {
            viewModel.clearFilters()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
