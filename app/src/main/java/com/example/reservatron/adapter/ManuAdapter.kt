package com.example.reservatron.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reservatron.databinding.ItemMenuBinding
import com.example.reservatron.model.login.LisMenus
import com.example.reservatron.model.login.Menus

class ManuAdapter (
    private val menuList: LisMenus,
    private val listener: OnMenuClickListener
    ) : RecyclerView.Adapter<ManuAdapter.MenuViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
            val binding =
                ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MenuViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
            val menu = menuList[position]
            holder.bind(menu)
        }

        override fun getItemCount(): Int = menuList.size

        inner class MenuViewHolder(private val binding: ItemMenuBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(menu: Menus) {
                binding.menuItemButton.text = menu.name
                binding.menuItemButton.setOnClickListener {
                    listener.onMenuClick(menu)
                }
            }
        }

        interface OnMenuClickListener {
            fun onMenuClick(menu: Menus)

        }
    }