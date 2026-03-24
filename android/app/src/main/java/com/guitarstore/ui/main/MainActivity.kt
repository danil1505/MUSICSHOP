package com.guitarstore.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guitarstore.R
import com.guitarstore.adapter.GuitarAdapter
import com.guitarstore.databinding.ActivityMainBinding
import com.guitarstore.ui.addedit.AddEditActivity
import com.guitarstore.ui.detail.DetailActivity
import com.guitarstore.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: GuitarAdapter
    private var searchView: SearchView? = null
    private val searchDebounce = android.os.Handler(android.os.Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupRecyclerView()
        setupFab()
        setupSwipeRefresh()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = GuitarAdapter { guitar ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("guitar_id", guitar.id)
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter       = this@MainActivity.adapter
            setHasFixedSize(true)
            itemAnimator  = null

            // ── Load next page on scroll ──────────────────────────
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                    if (dy <= 0) return
                    val lm     = layoutManager as LinearLayoutManager
                    val last   = lm.findLastVisibleItemPosition()
                    val total  = lm.itemCount
                    if (total > 0 && last >= total - 4 && viewModel.hasMorePages()) {
                        viewModel.loadNextPage()
                    }
                }
            })
        }
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadGuitars(reset = true)
        }
    }

    private fun observeViewModel() {
        viewModel.guitars.observe(this) { list ->
            adapter.submitList(list)
            binding.tvEmpty.visibility =
                if (list.isEmpty()) android.view.View.VISIBLE
                else                android.view.View.GONE
        }

        viewModel.loading.observe(this) { loading ->
            binding.swipeRefresh.isRefreshing = loading && adapter.itemCount == 0
            if (!loading) binding.swipeRefresh.isRefreshing = false
        }

        viewModel.error.observe(this) { msg ->
            if (!msg.isNullOrEmpty()) {
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView?.queryHint = "Поиск гитар..."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean { return false }
            override fun onQueryTextChange(text: String?): Boolean {
                searchDebounce.removeCallbacksAndMessages(null)
                searchDebounce.postDelayed({ viewModel.search(text) }, 300)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter) {
            FilterBottomSheet().show(supportFragmentManager, "filter")
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGuitars(reset = true)
    }
}
