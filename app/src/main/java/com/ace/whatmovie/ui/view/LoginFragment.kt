package com.ace.whatmovie.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ace.whatmovie.R
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.databinding.FragmentLoginBinding
import com.ace.whatmovie.di.ServiceLocator
import com.ace.whatmovie.ui.viewmodel.LoginViewModel
import com.ace.whatmovie.utils.viewModelFactory
import com.ace.whatmovie.wrapper.Resource
import kotlin.properties.Delegates

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    var username by Delegates.notNull<Int>()

    private val viewModel: LoginViewModel by viewModelFactory {
        LoginViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        isLoginInfoValid()
        setOnclickListeners()
    }

    private fun setOnclickListeners() {
        binding.btnLogin.setOnClickListener { checkLogin() }
        binding.tvGotoRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }


    private fun goToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
    }

    private fun checkLogin() {
        if (validateInput()) {
            val username = binding.etUsername.text.toString()
            viewModel.getUser(username)

            viewModel.getUser.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Success -> checkAccount(it.payload)
                    is Resource.Error ->
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_getting_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    else -> {}
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = getString(R.string.username_is_empty)
        }
        if (password.isEmpty()) {
            isValid = false
            binding.etPassword.error = getString(R.string.password_is_empty)
        }
        return isValid
    }

    private fun checkAccount(account: AccountEntity?) {
        account?.let {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val loginStatus = username == account.username && password == account.password
            if (loginStatus) {
                goToHome()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.username_or_password_incorrect),
                    Toast.LENGTH_SHORT
                ).show()
            }
            saveLoginInfo(
                account.username,
                account.email,
                account.password,
                account.accountId,
                loginStatus
            )
        }
    }

    private fun saveLoginInfo(
        username: String,
        email: String,
        password: String,
        accountId: Long,
        loginStatus: Boolean
    ) {
        viewModel.setAccount(username, email, password, accountId)
        viewModel.saveLoginStatus(loginStatus)
    }

    private fun isLoginInfoValid() {
        viewModel.getLoginStatus().observe(viewLifecycleOwner) {
            if (it) {
                goToHome()
                Toast.makeText(requireContext(), "Login Verified", Toast.LENGTH_SHORT).show()
            }
        }
    }
}