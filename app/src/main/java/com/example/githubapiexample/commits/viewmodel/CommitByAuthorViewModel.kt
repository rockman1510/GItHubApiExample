package com.example.githubapiexample.commits.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapiexample.api.response.CommitResponse
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.utils.CommitUtils
import com.example.githubapiexample.repository.CommitsByAuthorRepository
import com.example.githubapiexample.roomdatabase.entity.CommitsByAuthorEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import javax.inject.Inject

class CommitByAuthorViewModel @Inject constructor(
    private val commitsByAuthorRepository: CommitsByAuthorRepository
) : ViewModel() {

    private val commitList: MutableLiveData<ArrayList<CommitModel>> = MutableLiveData()
    private val commitsByAuthorEntity: MutableLiveData<CommitsByAuthorEntity> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getCommitsByAuthor(
        authorEmail: String, limit: Int = 25, page: Int = 1, errorCallback: (Throwable) -> Unit
    ): LiveData<ArrayList<CommitModel>> {
        viewModelScope.launch {
            try {
                commitList.value = CommitUtils.convertToCommitModelList(
                    Gson().fromJson(
                        commitsByAuthorRepository.getAuthorCommitsApi(authorEmail, limit, page)
                            .body().toString(),
                        object : TypeToken<ArrayList<CommitResponse>>() {}.type
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                errorCallback(e)
            }
        }
        return commitList
    }

    fun getLocalCommitsByAuthor(author: String): LiveData<CommitsByAuthorEntity> {
        viewModelScope.launch {
            commitsByAuthorEntity.value =
                commitsByAuthorRepository.getLocalCommitsByAuthor(author).value
        }
        return commitsByAuthorEntity
    }

    fun renewLocalCommitsByAuthor(author: String, dataList: ArrayList<CommitModel>) {
        viewModelScope.launch {
            commitsByAuthorRepository.renewLocalCommitsByAuthor(author, dataList)
        }
//        return viewModelScope.async(Dispatchers.IO) {
//            dataBaseRepository.renewLocalCommitsByAuthor(author, dataList)
//            return@async true
//        }.await()
    }

}