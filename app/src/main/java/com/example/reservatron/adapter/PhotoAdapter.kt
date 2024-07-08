package com.example.reservatron.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reservatron.R
import com.example.reservatron.databinding.ItemPhotoBinding
import com.example.reservatron.model.login.Photo

class PhotoAdapter(
    private val photoList: List<Photo>,
    private val listener: OnPhotoClickListener
): RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhotoViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PhotoAdapter.PhotoViewHolder, position: Int) {
        val imagenU = photoList[position].url
        holder.bind(imagenU, listener)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
    class PhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(imagenU: String, listener: OnPhotoClickListener) {
            val binding = ItemPhotoBinding.bind(itemView)
            // Check if the image URL ends with ".avif"
            if (imagenU.endsWith(".avif")) {
                // Handle AVIF image loading separately
                // For example, show a placeholder or an error image
                binding.imgPhoto.setImageResource(R.drawable.loandig)
            } else {
                // For non-AVIF images, continue using Glide as usual
                Glide.with(itemView.context)
                    .load(imagenU)
                    .into(binding.imgPhoto)
            }


            binding.root.setOnClickListener {
                listener.onGaleriaClick(imagenU)
            }

        }

    }

    interface OnPhotoClickListener {
        fun onGaleriaClick(imageUrl: String)


    }
}

