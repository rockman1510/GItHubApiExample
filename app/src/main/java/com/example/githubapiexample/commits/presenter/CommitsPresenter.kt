package com.example.githubapiexample.commits.presenter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.example.githubapiexample.Constants
import com.example.githubapiexample.R
import com.example.githubapiexample.commits.contract.CommitsContract
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.viewmodel.CommitViewModel
import com.example.githubapiexample.utils.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommitsPresenter constructor(
    private val context: Context,
    private val router: CommitsContract.Router
) : CommitsContract.Presenter {

    private var activityLifecycleOwner: LifecycleOwner? = null
    private var view: CommitsContract.View? = null
    private var commitViewModel: CommitViewModel? = null

    private var isLoadLocal = false
    override fun bindView(
        view: CommitsContract.View, owner: LifecycleOwner, viewModel: CommitViewModel
    ) {
        this.view = view
        activityLifecycleOwner = owner
        this.commitViewModel = viewModel
    }

    override fun unbindView(dataList: ArrayList<CommitModel>) {
        view = null
    }

    override fun onViewCreated() {
        view?.showLoadingDialog()
        if (ConnectionUtils.checkNetworkEnabled(context)) {
            commitViewModel?.getCommits(Constants.LIMIT_LOADING_ITEM_NUMBER, 1, ::onError)!!
                .observe(activityLifecycleOwner!!) { loadData(it) }
        } else {
            isLoadLocal = true
            commitViewModel?.getLocalCommits()!!.observe(activityLifecycleOwner!!) {
                if (it != null) {
                    loadData(it.commitList)
                } else {
                    loadData(ArrayList())
                }
            }
        }
    }

    private fun onError(error: Throwable) {
        view?.hideLoadingDialog()
        error.message?.let { view?.showErrorDialog(error.message.toString()) }
    }

    private fun loadData(dataList: ArrayList<CommitModel>) {
        view?.hideLoadingDialog()
        if (!dataList.isNullOrEmpty()) {
            view?.loadData(dataList)
        } else {
            view?.noUpdatedData()
        }
    }

    override fun onLastScroll(size: Int) {
        view?.showLoadingDialog()
        if (ConnectionUtils.checkNetworkEnabled(context)) {
            commitViewModel?.getCommits(
                Constants.LIMIT_LOADING_ITEM_NUMBER,
                (size / Constants.LIMIT_LOADING_ITEM_NUMBER) + 1, ::onError
            )!!.observe(activityLifecycleOwner!!) { loadData(it) }
        } else {
            view?.hideLoadingDialog()
            view?.showErrorDialog(context.getString(R.string.no_internet_msg))
        }
    }

    override fun onSaveLocalData(data: ArrayList<CommitModel>) {
        if (!isLoadLocal) {
            commitViewModel?.renewLocalCommits(data)
        }
    }

    override fun onItemClicked(commitModel: CommitModel) {
        router.openDetailCommit(commitModel)
    }
}