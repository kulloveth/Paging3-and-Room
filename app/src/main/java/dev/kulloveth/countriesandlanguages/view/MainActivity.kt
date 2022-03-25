package dev.kulloveth.countriesandlanguages.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import dev.kulloveth.countriesandlanguages.app.App
import dev.kulloveth.countriesandlanguages.app.AppScope
import dev.kulloveth.countriesandlanguages.databinding.ActivityMainBinding
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val repository = (application as App).repository
        val factory = CalViewModel.Factory(repository)
        val viewModel = ViewModelProvider(this, factory)[CalViewModel::class.java]

        val adapter = CalAdapter()

        binding.calRv.adapter = adapter
        binding.calRv.layoutManager = LinearLayoutManager(this)


        lifecycleScope.launch {
            viewModel.fetchAll().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppScope.cancel()
    }
}