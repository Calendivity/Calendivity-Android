package com.capstone.bangkit.calendivity.presentation.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.bangkit.calendivity.data.models.GetRefreshTokenDataModel
import com.capstone.bangkit.calendivity.data.models.RefreshTokenDataModel
import com.capstone.bangkit.calendivity.data.models.UpdateUserInfoDataModel
import com.capstone.bangkit.calendivity.data.repo.MainRepo
import com.capstone.bangkit.calendivity.domain.models.Resource
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserInfoViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {
    private val _res = MutableLiveData<Resource<JsonObject>>()
    val res: LiveData<Resource<JsonObject>>
        get() = _res

    fun updateUserInfo(accessToken: String, updateUserInfo: UpdateUserInfoDataModel) =
        viewModelScope.launch {
            _res.postValue(Resource.loading(null))
            mainRepo.updateUserInfo(accessToken, updateUserInfo).let {
                if (it.isSuccessful) {
                    _res.postValue(Resource.success(it.body()))
                } else {
                    _res.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }
        }
}