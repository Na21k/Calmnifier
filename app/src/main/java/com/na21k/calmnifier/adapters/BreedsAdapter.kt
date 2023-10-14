package com.na21k.calmnifier.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.na21k.calmnifier.databinding.BreedsListItemBinding
import com.na21k.calmnifier.model.BreedModel

class BreedsAdapter : RecyclerView.Adapter<BreedsAdapter.BreedViewHolder>() {

    var items: List<BreedModel> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BreedsListItemBinding.inflate(inflater, parent, false)

        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class BreedViewHolder(private val binding: BreedsListItemBinding) : ViewHolder(binding.root) {

        fun bind(model: BreedModel) {
            //TODO: load the image using Glide
            binding.breedName.text = model.name
        }
    }
}
