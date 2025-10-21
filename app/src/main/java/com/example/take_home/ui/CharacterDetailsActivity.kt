package com.example.take_home.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.take_home.R
import com.example.take_home.data.Character
import com.example.take_home.databinding.ActivityCharacterDetailsBinding
import com.squareup.picasso.Picasso

class CharacterDetailsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCharacterDetailsBinding.inflate(layoutInflater) }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }


        val  character = intent.getParcelableExtra("character", Character::class.java)
        if (character != null) {
            binding.characterName.text = character.name
            binding.characterSpecies.text = character.species
            binding.characterStatus.text = character.status
            Picasso.get().load(character.image).into(binding.characterImage)
        }
    }
}