package com.ace.whatmovie.presentation.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ace.whatmovie.R
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.model.Prefs
import com.ace.whatmovie.databinding.ActivityProfileBinding
import com.ace.whatmovie.di.ServiceLocator
import com.ace.whatmovie.presentation.ui.MainActivity
import com.ace.whatmovie.utils.viewModelFactory
import java.io.ByteArrayOutputStream
import java.io.File

class ProfileActivity : AppCompatActivity() {
    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModelFactory {
        ProfileViewModel(ServiceLocator.provideServiceLocator(this), application)
    }

    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
        setOnclickListeners()
    }

    private fun observeData() {
        viewModel.getAccountPrefs().observe(this) {
            bindDataToForm(it)
        }
        viewModel.getProfilePicture().observe(this) {
            if (it.isNullOrEmpty().not()){
                setProfilePicture(convertStringToBitMap(it))
            } else {
                Toast.makeText(this,"No profile picture", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setProfilePicture(bitmap: Bitmap) {
        binding.profilePicture.load(bitmap) {
            crossfade(true)
            placeholder(R.drawable.ic_pp_placeholder)
            transformations(RoundedCornersTransformation())
        }
    }

    private fun convertStringToBitMap(string: String): Bitmap {
        val imageBytes = Base64.decode(string, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    private fun setOnclickListeners() {
        binding.btnSaveAccount.setOnClickListener {
            saveAccount()
        }

        binding.btnLogOut.setOnClickListener {
            viewModel.saveLoginStatus(false)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnChangeProfilePicture.setOnClickListener {
            checkingPermissions()
        }
    }

    private fun checkingPermissions() {
        val REQUEST_CODE_PERMISSION = 200
        if (isGranted(
                this, Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(this)
            .setMessage("Select Picture")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private fun openCamera() {
        val photoFile = File.createTempFile(
            "IMG_",
            ".jpg",
            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )

        uri = FileProvider.getUriForFile(
            this,
            "${this.packageName}.provider",
            photoFile
        )
        cameraResult.launch(uri)

    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                handleCameraImage(uri)
//                val imageUri = data.getData()
            }
        }

    private fun handleCameraImage(uri: Uri) {
        binding.profilePicture.load(uri) {
            crossfade(true)
            placeholder(R.drawable.ic_pp_placeholder)
            target { result ->
                val bitmap = (result as BitmapDrawable).bitmap
                viewModel.setProfilePicture(convertBitMapToString(bitmap))
            }
            transformations(RoundedCornersTransformation())
        }
    }

    private fun convertBitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun openGallery() {
        intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.profilePicture.load(result) {
                crossfade(true)
                placeholder(R.drawable.ic_pp_placeholder)
                target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    viewModel.setProfilePicture(convertBitMapToString(bitmap))
                }
                transformations(RoundedCornersTransformation())
            }

        }

    private fun isGranted(
        profileActivity: ProfileActivity,
        permission: String,
        permissions: Array<String>,
        request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(profileActivity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(profileActivity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(profileActivity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, please allow from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("Package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun saveAccount() {
        if (validateInput()) {
            viewModel.updateUser(parseFormIntoEntity())
            Toast.makeText(this, getString(R.string.account_updated), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.fail_update_account), Toast.LENGTH_SHORT)
                .show()
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

    private val viewLifecycleOwner = this
    private fun parseFormIntoEntity(): AccountEntity {
        return AccountEntity(
            username = binding.etUsername.text.toString().trim(),
            email = binding.etEmail.text.toString().trim(),
            password = binding.etPassword.text.toString().trim()
        ).apply {
            viewModel.getAccountPrefs().observe(viewLifecycleOwner) {
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
            Toast.makeText(
                this,
                getString(R.string.password_mismatch),
                Toast.LENGTH_SHORT
            )
                .show()
        }
        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}