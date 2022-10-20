package com.ace.whatmovie.presentation.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ace.whatmovie.R
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.model.Prefs
import com.ace.whatmovie.databinding.FragmentProfileBinding
import com.ace.whatmovie.di.ServiceLocator
import com.ace.whatmovie.presentation.ui.MainActivity
import com.ace.whatmovie.utils.viewModelFactory


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModelFactory {
        ProfileViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }

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

        observeData()
        setOnclickListeners()
    }

    private fun observeData() {
        viewModel.getAccountPrefs().observe(viewLifecycleOwner) {
                bindDataToForm(it)
        }
    }

    private fun setOnclickListeners() {
        binding.btnSaveAccount.setOnClickListener {
            saveAccount()
        }

        binding.btnLogOut.setOnClickListener {
            viewModel.saveLoginStatus(false)
            activity?.let {
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    private fun saveAccount() {
        if (validateInput()) {
            viewModel.updateUser(parseFormIntoEntity())
            Toast.makeText(context, getString(R.string.account_updated), Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(context, getString(R.string.fail_update_account), Toast.LENGTH_SHORT).show()
        }

    }

    private fun bindDataToForm(prefs: Prefs?) {
        prefs?.let {
            binding.etPassword.setText("")
            binding.etConfirmPassword.setText("")
            binding.etEmail.setText(prefs.email)
            binding.etUsername.setText(prefs.username)
        }
    }

    private fun parseFormIntoEntity(): AccountEntity {
        return AccountEntity(
            username = binding.etUsername.text.toString().trim(),
            email = binding.etEmail.text.toString().trim(),
            password = binding.etPassword.text.toString().trim()
        ).apply {
             viewModel.getAccountPrefs().observe(viewLifecycleOwner){
                accountId = it.accountId
                 viewModel.setAccount(username, email, password, accountId)
            }
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
            binding.etConfirmPassword.error = getString(R.string.please_confirm_password)
        }
        if (password != confirmPassword) {
            isValid = false
            Toast.makeText(requireContext(), getString(R.string.password_mismatch), Toast.LENGTH_SHORT)
                .show()
        }
        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}