package com.example.githubapiexample.commits.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapiexample.api.response.CommitResponse
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.utils.CommitUtils
import com.example.githubapiexample.repository.ApiRepository
import com.example.githubapiexample.repository.DataBaseRepository
import com.example.githubapiexample.roomdatabase.entity.CommitsEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

class CommitViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dataBaseRepository: DataBaseRepository
) : ViewModel() {

    private val TAG = CommitViewModel::class.java.simpleName

    private lateinit var commitList: MutableLiveData<ArrayList<CommitModel>>

    @SuppressLint("CheckResult")
    fun getCommits(
        limit: Int = 25, page: Int = 1, errorCallback: (Throwable) -> Unit
    ): LiveData<ArrayList<CommitModel>> {
        commitList = MutableLiveData()
        apiRepository.getCommitsApi(limit, page).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<ArrayList<CommitResponse>>() {
                override fun onNext(response: ArrayList<CommitResponse>) {
                    Log.d(TAG, "getCommits: onNext")
                    commitList.value = CommitUtils.convertToCommitModelList(response)
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "getCommits: onError")
                    errorCallback(e)
                }

                override fun onComplete() {

                }

            })
        return commitList
    }

    fun getLocalCommits(): LiveData<CommitsEntity> {
        return dataBaseRepository.getLocalCommits()
    }

    suspend fun renewLocalCommits(commitList: ArrayList<CommitModel>):Boolean{
        return GlobalScope.async(Dispatchers.IO) {
            dataBaseRepository.renewLocalCommits(commitList)
            return@async true
        }.await()
    }


}