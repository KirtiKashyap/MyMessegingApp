package com.example.mytest.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.mytest.model.request.TokenRequest
import com.example.mytest.model.response.profile.ProfileData
import com.example.mytest.model.response.token.GetToken
import com.example.mytest.repo.AuthRepo
import com.example.mytest.utils.ResourceHelper
import kotlinx.coroutines.launch

class AuthViewModel @ViewModelInject constructor(private val authRepository: AuthRepo) : ViewModel(){
    private val _token = MutableLiveData<String>()
    val tokenData: MutableLiveData<ResourceHelper<GetToken>> = MutableLiveData()

    fun getToken(tokenRequest: TokenRequest) = viewModelScope.launch() {
        tokenData.postValue(ResourceHelper.loading())
        val token = authRepository.getAuthorization(tokenRequest)
        tokenData.postValue(token)
    }

    private val _userProfile = _token.switchMap { token ->
        authRepository.getUserProfile(token)
    }

    val userProfile: LiveData<ResourceHelper<ProfileData>> = _userProfile


}