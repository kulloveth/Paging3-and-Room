package dev.kulloveth.countriesandlanguages.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kulloveth.countriesandlanguages.data.db.Cal
import dev.kulloveth.countriesandlanguages.databinding.CalItemBinding

class CalAdapter:PagingDataAdapter<Cal,CalAdapter.CalViewHolder>(CalDiffUtil) {

    override fun onBindViewHolder(holder: CalViewHolder, position: Int) {
        val  cal = getItem(position)
        Log.d("list","${snapshot().items.size}")
        cal?.let {
            holder.bind(cal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalViewHolder {
        val binding = CalItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CalViewHolder(binding)
    }

    class CalViewHolder(private val binding: CalItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val viewPool = RecyclerView.RecycledViewPool()
        fun bind(cal: Cal){
            binding.countryTv.text = cal.country
            val childLayoutManager = LinearLayoutManager(binding.root.context , LinearLayoutManager.HORIZONTAL, false)
            childLayoutManager.initialPrefetchItemCount = 3
            val adapter = LanguageAdapter()
            adapter.submitList(cal.languages)
              binding.langRv.apply {
                  layoutManager = childLayoutManager
                   this.adapter = adapter
                  setRecycledViewPool(viewPool)

              }
        }
    }

    object CalDiffUtil : DiffUtil.ItemCallback<Cal>() {
        override fun areItemsTheSame(oldItem: Cal, newItem: Cal): Boolean {
            return oldItem.country == newItem.country
        }

        override fun areContentsTheSame(oldItem: Cal, newItem: Cal): Boolean {
            return oldItem.country == newItem.country
                    && oldItem.languages == newItem.languages
        }
    }


}