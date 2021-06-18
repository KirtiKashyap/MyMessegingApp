package com.example.mytest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers


fun <T, A> performOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> ResourceHelper<A>,
                            saveCallResult: suspend (A) -> Unit): LiveData<ResourceHelper<T>> =
    liveData(Dispatchers.IO) {
        emit(ResourceHelper.loading())
        val source = databaseQuery.invoke().map { ResourceHelper.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == ResourceHelper.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == ResourceHelper.Status.ERROR) {
            emit(ResourceHelper.error(responseStatus.message!!))
            emitSource(source)
        }
    }