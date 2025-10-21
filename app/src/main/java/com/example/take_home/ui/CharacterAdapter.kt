package com.example.take_home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.take_home.R
import com.example.take_home.data.Character
import com.squareup.picasso.Picasso

class CharacterAdapter(private val onItemClick: (Character) -> Unit): PagingDataAdapter<Character, CharacterAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val character = getItem(position) ?: return
        holder.nameText.text = character.name
        holder.speciesText.text = character.species
        Picasso.get()
            .load(character.image)
            .placeholder(R.drawable.user)
            .error(R.drawable.not_found_magnifying_glass)
            .into(holder.imageView)
        holder.itemView.setOnClickListener { onItemClick(character) }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.characterImage)
    val nameText: TextView = itemView.findViewById(R.id.nameText)
    val speciesText: TextView = itemView.findViewById(R.id.speciesText)
}

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
}
}