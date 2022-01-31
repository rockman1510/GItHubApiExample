package com.example.githubapiexample.commits.router

import android.content.Intent
import com.example.githubapiexample.commits.contract.CommitsByAuthorContract
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.view.activity.CommitsByAuthorActivity
import com.example.githubapiexample.commits.view.activity.DetailCommitActivity

class CommitsByAuthorRouter constructor(private val activity: CommitsByAuthorActivity) :
    CommitsByAuthorContract.Router {

    override fun openDetailCommit(commitModel: CommitModel) {
        val intent = Intent(activity, DetailCommitActivity::class.java)
        intent.putExtra(DetailCommitActivity.PARAMS_COMMIT_MODEL_EXTRA, commitModel)
        activity.startActivity(intent)
        activity.finish()
    }

}