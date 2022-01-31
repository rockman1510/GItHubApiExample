package com.example.githubapiexample.commits.view.activity

import android.content.Intent
import android.os.Bundle
import com.example.githubapiexample.BaseActivity
import com.example.githubapiexample.R
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_detail_commit.*

class DetailCommitActivity : BaseActivity() {

    var commitModel: CommitModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_commit)
        loadData()
        btAuthorCommit.setOnClickListener { launchCommitByAuthorActivity() }
        btClose.setOnClickListener { finish() }
    }

    private fun loadData() {
        intent?.let {
            commitModel = intent.getParcelableExtra(PARAMS_COMMIT_MODEL_EXTRA)
            tvSHA.text = commitModel?.sha
            tvCommitDate.text = commitModel?.commitDate
            tvMessage.text = commitModel?.message
            tvUrl.text = commitModel?.url
            tvAuthor.text = commitModel?.author
            CommonUtils.loadImageToView(this, ivAvatar, commitModel?.authorAvatar!!)
        }
    }

    private fun launchCommitByAuthorActivity() {
        val intent = Intent(this, CommitsByAuthorActivity::class.java)
        intent.putExtra(CommitsByAuthorActivity.PARAMS_AUTHOR, commitModel?.author)
        intent.putExtra(CommitsByAuthorActivity.PARAMS_AUTHOR_EMAIL, commitModel?.authorEmail)
        intent.putExtra(CommitsByAuthorActivity.PARAMS_AUTHOR_AVATAR, commitModel?.authorAvatar)
        startActivity(intent)
        finish()
    }

    companion object {
        const val PARAMS_COMMIT_MODEL_EXTRA = "PARAMS_COMMIT_MODEL_EXTRA"
    }
}