package com.example.take_home.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.take_home.R
import com.example.take_home.data.AppDatabase
import com.example.take_home.databinding.ActivityCharacterListBinding
import com.example.take_home.viewmodel.CharacterViewModel
import com.example.take_home.viewmodel.CharactereViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterListActivity : AppCompatActivity() {

private val viewModel: CharacterViewModel by viewModels {
    // Get the database instance safely and pass it to the factory
    val database = AppDatabase.getDatabase(applicationContext)
    CharactereViewModelFactory(database)
}
    private val binding by lazy { ActivityCharacterListBinding.inflate(layoutInflater) }
    private val adapter = CharacterAdapter { character ->
        val intent = Intent(this, CharacterDetailsActivity::class.java)
        intent.putExtra("character", character)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchBar()
        observePagingData()
        handleLoadStates()
        setupRefresh()
    }

    private fun setupRecyclerView() {
        val loadStateAdapter = LoadStateAdapter { adapter.retry()}
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter.withLoadStateFooter(loadStateAdapter)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupSearchBar() {
        binding.searchBar.setNavigationOnClickListener {
            Toast.makeText(this, "Clicked on navigation item", Toast.LENGTH_LONG).show()
        }

        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                val queryText = binding.searchView.editText.text.toString()
                binding.searchBar.setText(queryText)
                viewModel.updateSearchQuery(queryText.takeIf { it.isNotBlank() })
                Toast.makeText(this, "Search query: $queryText", Toast.LENGTH_LONG).show()
                binding.searchView.hide()
                true
            } else {
                false
            }
        }
        viewModel.updateSearchQuery(null)
    }

    private fun observePagingData() {
        lifecycleScope.launch {
            viewModel.charactersFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun handleLoadStates(){
        adapter.addLoadStateListener { loadStates ->
            val refreshState = loadStates.refresh
            binding.progressBarlist.visibility = if (refreshState is LoadState.Loading) View.VISIBLE else View.GONE
            binding.errorTextView.visibility = if (refreshState is LoadState.Error) View.VISIBLE else View.GONE
        }
    }

    private fun setupRefresh(){
        binding.swipedRefresh.setOnRefreshListener {
            adapter.refresh()
            binding.swipedRefresh.isRefreshing = false
        }
    }


}