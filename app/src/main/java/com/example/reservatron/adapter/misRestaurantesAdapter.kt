package com.example.reservatron.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reservatron.databinding.ItemMisResBinding
import com.example.reservatron.model.login.Restaurant
import com.example.reservatron.model.login.Restaurantes

class misRestaurantesAdapter (
    val restaurantes: Restaurantes,
    val listener: OnMisRestauranteClickListener
) : RecyclerView.Adapter<misRestaurantesAdapter.RestauranteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {

        val binding = ItemMisResBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestauranteViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return restaurantes.size
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        val item = restaurantes[position]
        holder.bind(item, listener)
    }

    class RestauranteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurante: Restaurant, listener: OnMisRestauranteClickListener) {
            val binding = ItemMisResBinding.bind(itemView)
            binding.btnEdit
            binding.apply {
                tituloRestaurante.text = restaurante.name
                Glide.with(itemView).load(restaurante.logo).into(logoRestaurante)
                root.setOnClickListener {
                    listener.onRestauranteClick(restaurante)
                }
                btnEdit.setOnClickListener {
                    listener.editarRestaurante(restaurante)
                }
                btnDelete.setOnClickListener {
                    listener.eliminarRestaurante(restaurante)
                }
            }
        }
    }

    interface OnMisRestauranteClickListener {
        fun onRestauranteClick(restaurante: Restaurant)
        abstract fun editarRestaurante(restaurante: Restaurant)
        fun eliminarRestaurante(restaurante: Restaurant)

    }
}