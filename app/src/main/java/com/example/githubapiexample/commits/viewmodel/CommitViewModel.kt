package com.example.githubapiexample.commits.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapiexample.api.response.CommitResponse
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.utils.CommitUtils
import com.example.githubapiexample.repository.CommitsRepository
import com.example.githubapiexample.roomdatabase.entity.CommitsEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommitViewModel @Inject constructor(private val commitsRepository: CommitsRepository) :
    ViewModel() {

    private val TAG = CommitViewModel::class.java.simpleName

    private val commitList: MutableLiveData<ArrayList<CommitModel>> = MutableLiveData()
    private val commitEntity: MutableLiveData<CommitsEntity> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getCommits(
        limit: Int = 25, page: Int = 1, errorCallback: (Throwable) -> Unit
    ): LiveData<ArrayList<CommitModel>> {
        viewModelScope.launch {
            try {
                commitList.value = CommitUtils.convertToCommitModelList(
                    Gson().fromJson(
                        commitsRepository.getCommitsApi(limit, page).body().toString(),
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

    fun getLocalCommits(): LiveData<CommitsEntity> {
        viewModelScope.launch {
            commitEntity.value = commitsRepository.getLocalCommits().value
        }
        return commitEntity
    }

    fun renewLocalCommits(commitList: ArrayList<CommitModel>) {
        viewModelScope.launch {
            commitsRepository.renewLocalCommits(commitList)
        }
    }


}