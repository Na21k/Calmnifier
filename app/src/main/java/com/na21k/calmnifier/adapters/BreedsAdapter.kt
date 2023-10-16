package com.na21k.calmnifier.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.na21k.calmnifier.R
import com.na21k.calmnifier.databinding.BreedsListItemBinding
import com.na21k.calmnifier.model.BreedModel
import com.na21k.calmnifier.model.ImageModel

class BreedsAdapter(val onItemActionListener: OnItemActionListener) :
    RecyclerView.Adapter<BreedsAdapter.BreedViewHolder>() {

    var items: List<BreedModel> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var itemImages: List<ImageModel?> = listOf()
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

    inner class BreedViewHolder(private val binding: BreedsListItemBinding) :
        ViewHolder(binding.root) {

        private lateinit var mModel: BreedModel

        init {
            itemView.setOnClickListener { onItemActionListener.itemOpen(mModel) }
        }

        fun bind(model: BreedModel) {
            mModel = model
            binding.breedName.text = model.name

            if (itemImages.isNotEmpty()) {
                val imageId = model.referenceImageId
                val imageModel = itemImages.firstOrNull() { it?.id == imageId }

                Glide.with(itemView)
                    .load(imageModel?.url ?: R.drawable.ic_pets_24)
                    .placeholder(R.drawable.ic_pets_24)
                    .error(R.drawable.ic_error_24)
                    .into(binding.breedPhoto)
            }
        }
    }

    interface OnItemActionListener {
        fun itemOpen(model: BreedModel)
    }
}
