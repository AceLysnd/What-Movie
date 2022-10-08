package com.ace.whatmovie.presentation.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.databinding.FragmentProfileBinding
import com.ace.whatmovie.di.ServiceLocator
import com.ace.whatmovie.presentation.ui.MainActivity
import com.ace.whatmovie.presentation.ui.login.LoginFragment
import com.ace.whatmovie.presentation.ui.register.RegisterViewModel
import com.ace.whatmovie.utils.viewModelFactory


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModelFactory {
        RegisterViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(
            LoginFragment.LOGIN_SHARED_PREF,
            Context.MODE_PRIVATE
        )

        binding.btnSaveAccount.setOnClickListener {
            saveAccount()
        }

        binding.btnLogOut.setOnClickListener {
            sharedPreferences.edit {
                putBoolean(LoginFragment.LOGGED_IN_KEY, false)
            }
            activity?.let {
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    private fun saveAccount() {
        validateInput()
        viewModel.updateUser(parseFormIntoEntity())
        updateUsername(parseFormIntoEntity().username)
        Toast.makeText(context, "Account Updated!", Toast.LENGTH_SHORT).show()
    }

    private fun parseFormIntoEntity(): AccountEntity {
        return AccountEntity(
            username = binding.etUsername.text.toString().trim(),
            email = binding.etEmail.text.toString().trim(),
            password = binding.etPassword.text.toString().trim())
    }

    private fun updateUsername(username: String) {
        sharedPreferences.edit {
            putString(LoginFragment.USERNAME, username)
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = "Username is empty!"
        }
        if (email.isEmpty()) {
            isValid = false
            binding.etEmail.error = "Email is empty!"
        }
        if (password.isEmpty()) {
            isValid = false
            binding.etPassword.error = "Password is empty!"
        }
        if (confirmPassword.isEmpty()) {
            isValid = false
            binding.etConfirmPassword.error = "Please confirm password"
        }
        if (password != confirmPassword) {
            isValid = false
            Toast.makeText(requireContext(), "Check again, password mismatch!", Toast.LENGTH_SHORT).show()
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}