package com.example.githubapiexample.commits.contract

import androidx.lifecycle.LifecycleOwner
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.viewmodel.CommitByAuthorViewModel

interface CommitsByAuthorContract {

    interface View {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showErrorDialog(message: String)
        fun loadData(data: ArrayList<CommitModel>)
        fun noUpdatedData()
    }

    interface Presenter {
        fun bindView(view: View, owner: LifecycleOwner, viewModel: CommitByAuthorViewModel)
        fun unbindView()
        fun onViewCreated(authorEmail: String)
        fun onLastScroll(size: Int)
        fun onSaveData(authorEmail: String, commitList: ArrayList<CommitModel>)
        fun onItemClicked(commitModel: CommitModel)
    }

    interface Router {
        fun openDetailCommit(commitModel: CommitModel)
    }

}