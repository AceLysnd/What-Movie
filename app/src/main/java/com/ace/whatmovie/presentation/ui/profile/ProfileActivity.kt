package com.ace.whatmovie.presentation.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ace.whatmovie.R
import com.ace.whatmovie.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val profileFragment = ProfileFragment()
        fragmentTransaction.add(R.id.profile_fragment_container, profileFragment)
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}