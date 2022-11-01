package com.sepia.pets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sepia.pets.R
import com.sepia.pets.databinding.PetsListAdapterBinding
import com.sepia.pets.models.AllPets
import com.sepia.pets.models.Pet

class PetsListAdapter(private val onSelect: (Pet?) -> Unit) : RecyclerView.Adapter<PetsListAdapter.PetsListViewHolder>() {
    lateinit var getAllPets: AllPets

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetsListViewHolder {
        val binding = DataBindingUtil.inflate<PetsListAdapterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.pets_list_adapter,
            parent,
            false)
        return PetsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetsListViewHolder, position: Int) {
        holder.binding.petsListModel = getAllPets.pets[position]
        holder.binding.petsItem.setOnClickListener{
            onSelect(getAllPets.pets[position])
        }
    }

    override fun getItemCount(): Int = getAllPets.pets.size

    fun setAllPets(allPets: AllPets) {
        this.getAllPets = allPets
        notifyDataSetChanged()

    }

    inner class PetsListViewHolder(var binding: PetsListAdapterBinding)
        : RecyclerView.ViewHolder(binding.root)

}