package com.ace.whatmovie.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ace.whatmovie.R
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.databinding.FragmentRegisterBinding
import com.ace.whatmovie.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateAccount.setOnClickListener {
            createAccount()
        }

        binding.tvGotoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun createAccount() {
        if (validateInput()) {
            val user = AccountEntity(
                username = binding.etUsername.text.toString(),
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
            viewModel.registerUser(user)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            Toast.makeText(context, getString(R.string.account_created), Toast.LENGTH_SHORT).show()
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
            binding.etUsername.error = getString(R.string.username_is_empty)
        }
        if (email.isEmpty()) {
            isValid = false
            binding.etEmail.error = getString(R.string.email_is_empty)
        }
        if (password.isEmpty()) {
            isValid = false
            binding.etPassword.error = getString(R.string.password_is_empty)
        }
        if (confirmPassword.isEmpty()) {
            isValid = false
            binding.etConfirmPassword.error = getString(R.string.confirm_password)
        }
        if (password != confirmPassword) {
            isValid = false
            Toast.makeText(requireContext(), getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show()
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}