package com.ace.whatmovie.ui.view

import android.content.ContentValues.TAG
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ace.whatmovie.R
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.databinding.FragmentLoginBinding
import com.ace.whatmovie.ui.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    var username by Delegates.notNull<Int>()

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var auth: FirebaseAuth

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

        // Bug Button
        binding.btnLoginGoogle.setOnClickListener{ bugButton() }
    }

    private fun bugButton(){
        System.out.println(2/0)
    }


    private fun goToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
    }

    private fun checkLogin() {
        if (validateInput()) {
            val username = binding.etUsername.text.toString()
            viewModel.getUser(username)

            viewModel.getUser.observe(viewLifecycleOwner) {
                checkAccount(it)
            }
        }
    }

    fun validateInput(): Boolean {
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

    fun checkAccount(account: AccountEntity?) {
        account?.let {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val loginStatus = username == account.username && password == account.password
            if (loginStatus) {
//                findNavController().navigate(R.id.action_loginFragment_self)
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

    fun saveLoginInfo(
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