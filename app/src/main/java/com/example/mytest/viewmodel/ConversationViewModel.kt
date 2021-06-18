package com.example.mytest.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.mytest.model.request.SendMessage
import com.example.mytest.model.response.conversation.ListData
import com.example.mytest.model.response.conversation.Messages
import com.example.mytest.model.response.profile.ProfileData
import com.example.mytest.repo.MessageRepo
import com.example.mytest.utils.ResourceHelper
import kotlinx.coroutines.launch

class ConversationViewModel @ViewModelInject constructor(
    private val repository: MessageRepo
) : ViewModel() {

    private val _token = MutableLiveData<String>()
    private val _id = MutableLiveData<Int>()
    lateinit var token: String


    private val _conversationList = _token.switchMap { token ->
        repository.getAllConversation(token)
    }
    val conversationList: LiveData<ResourceHelper<List<ListData>>> = _conversationList

    fun start(token: String) {
        _token.value = token
    }

    private val _messageList = (_id).switchMap { id ->
        repository.getMessageList(id,token = token)
    }

    val messageList: LiveData<ResourceHelper<List<Messages>>> = _messageList

    fun startMessageObservation(id: Int,token: String){
        _id.value=id
        this.token = token
    }

    val sendMessageResponse: MutableLiveData<ResourceHelper<Unit>> = MutableLiveData()

    fun sendMessage(token: String, sendMessage: SendMessage, id: Int)=viewModelScope.launch {
        sendMessageResponse.postValue(ResourceHelper.loading())
        val responseData = repository.sendMessage(token,id,sendMessage)
        sendMessageResponse.postValue(responseData)
    }


}