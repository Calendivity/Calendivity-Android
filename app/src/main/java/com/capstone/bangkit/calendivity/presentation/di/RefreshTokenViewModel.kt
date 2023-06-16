package com.capstone.bangkit.calendivity.presentation.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.bangkit.calendivity.data.models.GetRefreshTokenDataModel
import com.capstone.bangkit.calendivity.data.models.RefreshTokenDataModel
import com.capstone.bangkit.calendivity.data.repo.MainRepo
import com.capstone.bangkit.calendivity.domain.models.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RefreshTokenViewModel @Inject constructor(private val mainRepo: MainRepo) : ViewModel() {
    private val _res = MutableLiveData<Resource<GetRefreshTokenDataModel>>()
    val res: LiveData<Resource<GetRefreshTokenDataModel>>
        get() = _res

    fun getRefreshToken(refreshTokenDataModel: RefreshTokenDataModel) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        mainRepo.getRefreshToken(refreshTokenDataModel).let {
            if (it.isSuccessful) {
                _res.postValue(Resource.success(it.body()))
            } else {
                _res.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
}