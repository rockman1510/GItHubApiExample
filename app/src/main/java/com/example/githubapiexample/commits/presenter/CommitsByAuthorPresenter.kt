package com.example.githubapiexample.commits.presenter

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.githubapiexample.Constants
import com.example.githubapiexample.R
import com.example.githubapiexample.commits.contract.CommitsByAuthorContract
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.viewmodel.CommitByAuthorViewModel
import com.example.githubapiexample.utils.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CommitsByAuthorPresenter constructor(
    private val context: Context,
    private val router: CommitsByAuthorContract.Router
) : CommitsByAuthorContract.Presenter {

    private var activityLifecycleOwner: LifecycleOwner? = null
    private var view: CommitsByAuthorContract.View? = null
    private var commitByAuthorViewModel: CommitByAuthorViewModel? = null

    private var authorEmail: String = ""
    private var isLoadLocal = false
    override fun bindView(
        view: CommitsByAuthorContract.View,
        owner: LifecycleOwner,
        commitByAuthorViewModel: CommitByAuthorViewModel
    ) {
        this.view = view
        activityLifecycleOwner = owner
        this.commitByAuthorViewModel = commitByAuthorViewModel
    }

    override fun unbindView() {
        view = null
    }

    override fun onViewCreated(authorEmail: String) {
        this.authorEmail = authorEmail
        view?.showLoadingDialog()
        if (ConnectionUtils.checkNetworkEnabled(context)) {
            commitByAuthorViewModel?.getCommitsByAuthor(
                authorEmail, Constants.LIMIT_LOADING_ITEM_NUMBER, 1, ::onError
            )!!.observe(activityLifecycleOwner!!, { loadData(it) })
        } else {
            isLoadLocal = true
            commitByAuthorViewModel?.getLocalCommitsByAuthor(authorEmail)!!
                .observe(activityLifecycleOwner!!, {
                    if (it != null) {
                        loadData(it.commitList)
                    } else {
                        loadData(ArrayList())
                    }
                })
        }

    }


    private fun onError(error: Throwable) {
        view?.hideLoadingDialog()
        error.message?.let { view?.showErrorDialog(it) }
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
            commitByAuthorViewModel?.getCommitsByAuthor(
                authorEmail, Constants.LIMIT_LOADING_ITEM_NUMBER,
                (size / Constants.LIMIT_LOADING_ITEM_NUMBER) + 1, ::onError
            )!!.observe(activityLifecycleOwner!!, { loadData(it) })
        } else {
            view?.hideLoadingDialog()
            view?.showErrorDialog(context.getString(R.string.no_internet_msg))
        }
    }

    override fun onSaveData(authorEmail: String, commitList: ArrayList<CommitModel>) {
        if (!isLoadLocal) {
            GlobalScope.launch(Dispatchers.Main) {
                commitByAuthorViewModel?.renewLocalCommitsByAuthor(authorEmail, commitList)
            }
        }
    }

    override fun onItemClicked(commitModel: CommitModel) {
        router.openDetailCommit(commitModel)
    }
}