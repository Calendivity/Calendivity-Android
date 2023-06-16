package com.capstone.bangkit.calendivity.presentation.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.bangkit.calendivity.data.models.UserInfoDataModel
import com.capstone.bangkit.calendivity.data.repo.MainRepo
import com.capstone.bangkit.calendivity.domain.models.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {
    private val _res = MutableLiveData<Resource<UserInfoDataModel>>()
    val res: LiveData<Resource<UserInfoDataModel>>
        get() = _res

    fun getUserInfo(accessToken: String) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        mainRepo.getUserInfo(accessToken).let {
            if (it.isSuccessful) {
                Timber.d("Data : $it")
                _res.postValue(Resource.success(it.body()))
            } else {
                _res.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}