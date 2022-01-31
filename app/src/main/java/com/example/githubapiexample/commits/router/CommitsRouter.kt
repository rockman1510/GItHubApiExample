package com.example.githubapiexample.commits.router

import android.content.Intent
import com.example.githubapiexample.commits.contract.CommitsContract
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.view.activity.CommitsActivity
import com.example.githubapiexample.commits.view.activity.DetailCommitActivity

class CommitsRouter constructor(private val activity: CommitsActivity) :
    CommitsContract.Router {

    override fun openDetailCommit(commitModel: CommitModel) {
        val intent = Intent(activity, DetailCommitActivity::class.java)
        intent.putExtra(DetailCommitActivity.PARAMS_COMMIT_MODEL_EXTRA, commitModel)
        activity.startActivity(intent)
    }

}