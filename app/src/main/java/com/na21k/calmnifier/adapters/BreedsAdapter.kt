package com.na21k.calmnifier.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.na21k.calmnifier.CAT_API_BASE_URL
import com.na21k.calmnifier.CAT_API_IMAGES_ENDPOINT_NAME
import com.na21k.calmnifier.R
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
            val imageId = model.referenceImageId
            val imageModelUrl = CAT_API_BASE_URL + CAT_API_IMAGES_ENDPOINT_NAME + imageId
            //TODO: load the image model and show the image from its url

            Glide.with(itemView)
                .load("https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg")
                .placeholder(R.drawable.ic_image_24)
                .error(R.drawable.ic_error_24)
                .into(binding.breedPhoto)

            binding.breedName.text = model.name
        }
    }
}
