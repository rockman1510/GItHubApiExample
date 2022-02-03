package com.example.githubapiexample.commits.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapiexample.BaseActivity
import com.example.githubapiexample.BaseApplication
import com.example.githubapiexample.R
import com.example.githubapiexample.commits.contract.CommitsByAuthorContract
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.presenter.CommitsByAuthorPresenter
import com.example.githubapiexample.commits.router.CommitsByAuthorRouter
import com.example.githubapiexample.commits.view.adapter.CommitAdapter
import com.example.githubapiexample.commits.viewmodel.CommitByAuthorViewModel
import com.example.githubapiexample.utils.CommonUtils
import com.example.githubapiexample.utils.CustomDialog
import kotlinx.android.synthetic.main.activity_author_commits.*
import kotlinx.android.synthetic.main.activity_commits.*
import kotlinx.android.synthetic.main.activity_commits.rvCommit
import javax.inject.Inject

class CommitsByAuthorActivity : BaseActivity(), CommitsByAuthorContract.View,
    CommitAdapter.CommitItemSelectListener {

    var presenter: CommitsByAuthorPresenter? = null

    @Inject
    lateinit var commitByAuthorViewModel: CommitByAuthorViewModel

    @Inject
    lateinit var commitAdapter: CommitAdapter

    private var authorEmail: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApplication).initDaggerComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_commits)
        initView()
//        commitByAuthorViewModel = ViewModelProvider(
//            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        )[CommitByAuthorViewModel::class.java]
        presenter = CommitsByAuthorPresenter(this, CommitsByAuthorRouter(this))
        presenter?.bindView(this, this, commitByAuthorViewModel)
        presenter?.onViewCreated(authorEmail)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        rvCommit.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCommit.adapter = commitAdapter
        commitAdapter.commitItemSelectListener = this
        rvCommit.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (CommonUtils.isLastItemVisible(
                        rvCommit, commitAdapter.itemCount
                    ) && !getLoadingDialog()?.isShowing()!!
                ) {
                    presenter?.onLastScroll(commitAdapter.itemCount)
                }
            }
        })
        intent?.let {
            authorEmail = it.getStringExtra(PARAMS_AUTHOR_EMAIL).toString()
            tvAuthor.text =
                "${getString(R.string.author)} ${it.getStringExtra(PARAMS_AUTHOR).toString()}"
            CommonUtils.loadImageToView(
                this@CommitsByAuthorActivity, ivAvatar,
                it.getStringExtra(PARAMS_AUTHOR_AVATAR).toString()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.unbindView()
    }

    override fun showLoadingDialog() {
        getLoadingDialog()?.show()
    }

    override fun hideLoadingDialog() {
        getLoadingDialog()?.dismiss()
    }

    override fun showErrorDialog(message: String) {
        CustomDialog.Builder(this).setTitle(getString(R.string.error)).setMessage(message)
            .setNeuralButton(getString(R.string.close), object : CustomDialog.OnNeuralListener {})
            .build().show()
    }

    override fun loadData(data: ArrayList<CommitModel>) {
        commitAdapter.addData(data)
        presenter?.onSaveData(authorEmail, commitAdapter.getDataList())
    }

    override fun noUpdatedData() {
        Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_SHORT).show()
    }

    override fun onSelect(commitModel: CommitModel) {
        presenter?.onItemClicked(commitModel)
    }

    companion object {
        const val PARAMS_AUTHOR = "PARAMS_AUTHOR"
        const val PARAMS_AUTHOR_EMAIL = "PARAMS_AUTHOR_EMAIL"
        const val PARAMS_AUTHOR_AVATAR = "PARAMS_AUTHOR_AVATAR"
    }

}