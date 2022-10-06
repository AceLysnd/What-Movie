package com.ace.whatmovie.presentation.ui.login

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ace.whatmovie.R
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.databinding.FragmentLoginBinding
import com.ace.whatmovie.di.ServiceLocator
import com.ace.whatmovie.utils.viewModelFactory
import com.ace.whatmovie.wrapper.Resource

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModelFactory {
        LoginViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }

    private lateinit var sharedPreferences: SharedPreferences

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
        sharedPreferences = requireContext().getSharedPreferences(LOGIN_SHARED_PREF, MODE_PRIVATE)

        binding.btnLogin.setOnClickListener { checkLogin() }

        if (isLoginInfoValid()) {
            goToHome()
        }

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
                            "Error while getting data",
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
            binding.etUsername.error = "Username is empty!"
        }
        if (password.isEmpty()) {
            isValid = false
            binding.etPassword.error = "Password is empty!"
        }
        return isValid
    }

    private fun checkAccount(account: AccountEntity?) {
        account?.let {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val userLoggedIn = username == account.username && password == account.password
            if (userLoggedIn) {
                goToHome()
            } else {
                Toast.makeText(context, "Username or password incorrect!", Toast.LENGTH_SHORT).show()
            }
            saveLoginInfo(userLoggedIn)
        }
    }

    private fun saveLoginInfo(loginInfo: Boolean) {
        sharedPreferences.edit {
            putBoolean(LOGGED_IN_KEY, loginInfo)
        }
    }

    private fun isLoginInfoValid(): Boolean {
        return sharedPreferences.getBoolean(LOGGED_IN_KEY, false)
    }

    companion object {
        const val LOGIN_SHARED_PREF = "login_shared_pref"
        const val LOGGED_IN_KEY = "logged_in"
    }
}