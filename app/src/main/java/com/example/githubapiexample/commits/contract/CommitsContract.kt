package com.example.githubapiexample.commits.contract

import androidx.lifecycle.LifecycleOwner
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.viewmodel.CommitViewModel

interface CommitsContract {

    interface View {
        fun showLoadingDialog()
        fun hideLoadingDialog()
        fun showErrorDialog(message: String)
        fun loadData(data: ArrayList<CommitModel>)
        fun noUpdatedData()
    }

    interface Presenter {
        fun bindView(view: View, owner: LifecycleOwner, commitViewModel: CommitViewModel)
        fun unbindView(dataList: ArrayList<CommitModel>)
        fun onViewCreated()
        fun onLastScroll(size: Int)
        fun onSaveLocalData(data: ArrayList<CommitModel>)
        fun onItemClicked(commitModel: CommitModel)
    }

    interface Router {
        fun openDetailCommit(commitModel: CommitModel)
    }

}