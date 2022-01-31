package com.example.githubapiexample.commits.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapiexample.api.response.CommitResponse
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.utils.CommitUtils
import com.example.githubapiexample.repository.ApiRepository
import com.example.githubapiexample.repository.DataBaseRepository
import com.example.githubapiexample.roomdatabase.entity.CommitsByAuthorEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

class CommitByAuthorViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dataBaseRepository: DataBaseRepository
) : ViewModel() {

    private lateinit var commitList: MutableLiveData<ArrayList<CommitModel>>

    @SuppressLint("CheckResult")
    fun getCommitsByAuthor(
        authorEmail: String, limit: Int = 25, page: Int = 1, errorCallback: (Throwable) -> Unit
    ): LiveData<ArrayList<CommitModel>> {
        commitList = MutableLiveData()
        apiRepository.getAuthorCommitsApi(authorEmail, limit, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<ArrayList<CommitResponse>>() {
                override fun onNext(response: ArrayList<CommitResponse>) {
                    commitList.value = CommitUtils.convertToCommitModelList(response)
                }

                override fun onError(e: Throwable) {
                    errorCallback(e)
                }

                override fun onComplete() {

                }

            })
        return commitList
    }

    fun getLocalCommitsByAuthor(author: String): LiveData<CommitsByAuthorEntity> {
        return dataBaseRepository.getCommitsByAuthor(author)
    }

    suspend fun renewLocalCommitsByAuthor(
        author: String, dataList: ArrayList<CommitModel>
    ): Boolean {
        return GlobalScope.async(Dispatchers.IO) {
            dataBaseRepository.renewLocalCommitsByAuthor(author, dataList)
            return@async true
        }.await()
    }

}