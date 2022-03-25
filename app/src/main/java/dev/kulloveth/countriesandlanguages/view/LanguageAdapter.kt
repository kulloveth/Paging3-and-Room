package dev.kulloveth.countriesandlanguages.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.kulloveth.countriesandlanguages.databinding.LangItemBinding

class LanguageAdapter:ListAdapter<String,LanguageAdapter.LanguageViewHolder>(CalDiffUtil) {

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val  lang = getItem(position)
            holder.bind(lang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = LangItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LanguageViewHolder(binding)
    }

    class LanguageViewHolder(private val binding: LangItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(lan:String){
            binding.langTv.text = lan
        }
    }

    object CalDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }


}