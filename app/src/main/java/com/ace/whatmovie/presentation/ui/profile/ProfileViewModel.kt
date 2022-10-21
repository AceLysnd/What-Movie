package com.ace.whatmovie.presentation.ui.profile

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.model.Prefs
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.utils.workers.TAG_OUTPUT
import com.ace.whatmovie.wrapper.Resource
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: LocalRepository, application: Application) : ViewModel() {

    val updateResult = MutableLiveData<Resource<Number>>()

    internal var imageUri: Uri? = null
    internal var outputUri: Uri? = null

    private val workManager = WorkManager.getInstance(application)

    internal val outputWorkInfos: LiveData<List<WorkInfo>>

    init {
//        imageUri = getImageUri(application.applicationContext)
        // TODO: WORKMANAGER 

        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }


    fun updateUser(account: AccountEntity) {
        viewModelScope.launch {
            updateResult.postValue(repository.updateAccount(account))
        }
    }

    fun setAccount(username: String, email: String, password: String, accountId: Long) {
        viewModelScope.launch {
            repository.setAccount(username, email, password, accountId)
        }
    }

    fun setProfilePicture(profilePicture: String) {
        viewModelScope.launch {
            repository.setProfilePicture(profilePicture)
        }
    }

    fun saveLoginStatus(loginStatus: Boolean) {
        viewModelScope.launch {
            repository.setLoginStatus(loginStatus)
        }
    }

    fun getAccountPrefs(): LiveData<Prefs> {
        return repository.getAccountPrefs()
    }

    fun getProfilePicture(): LiveData<String> {
        return repository.getProfilePicture()
    }
}