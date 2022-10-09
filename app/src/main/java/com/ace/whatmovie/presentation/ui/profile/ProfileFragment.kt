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
import com.ace.whatmovie.R
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.databinding.FragmentProfileBinding
import com.ace.whatmovie.di.ServiceLocator
import com.ace.whatmovie.presentation.ui.MainActivity
import com.ace.whatmovie.presentation.ui.login.LoginFragment
import com.ace.whatmovie.presentation.ui.login.LoginFragment.Companion.ACCOUNT_ID
import com.ace.whatmovie.presentation.ui.register.RegisterViewModel
import com.ace.whatmovie.utils.viewModelFactory
import com.ace.whatmovie.wrapper.Resource


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

        observeData()
        getInitialData()
        setOnclickListeners()
    }

    private fun getAccountId(): Long? {
        return sharedPreferences.getLong(ACCOUNT_ID, 0)
    }

    private fun getInitialData() {
        getAccountId()?.let { viewModel.getAccountById(it) }
    }

    private fun observeData() {
        viewModel.detailDataResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> bindDataToForm(it.payload)
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_getting_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {}
            }
        }
    }

    private fun setOnclickListeners() {
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
        Toast.makeText(context, getString(R.string.account_updated), Toast.LENGTH_SHORT).show()
    }

    private fun bindDataToForm(data: AccountEntity?) {
        data?.let {
            binding.etPassword.setText("")
            binding.etConfirmPassword.setText("")
            binding.etEmail.setText(data.email)
            binding.etUsername.setText(data.username)
        }
    }

    private fun parseFormIntoEntity(): AccountEntity {
        return AccountEntity(
            username = binding.etUsername.text.toString().trim(),
            email = binding.etEmail.text.toString().trim(),
            password = binding.etPassword.text.toString().trim()
        ).apply {
            accountId = getAccountId()!!
        }
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