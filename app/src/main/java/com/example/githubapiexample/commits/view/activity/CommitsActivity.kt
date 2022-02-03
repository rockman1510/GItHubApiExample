package com.example.githubapiexample.commits.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapiexample.BaseActivity
import com.example.githubapiexample.BaseApplication
import com.example.githubapiexample.R
import com.example.githubapiexample.commits.contract.CommitsContract
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.commits.presenter.CommitsPresenter
import com.example.githubapiexample.commits.router.CommitsRouter
import com.example.githubapiexample.commits.view.adapter.CommitAdapter
import com.example.githubapiexample.commits.viewmodel.CommitViewModel
import com.example.githubapiexample.utils.CommonUtils
import com.example.githubapiexample.utils.CustomDialog
import kotlinx.android.synthetic.main.activity_commits.*
import javax.inject.Inject

class CommitsActivity : BaseActivity(), CommitsContract.View,
    CommitAdapter.CommitItemSelectListener {

    var presenter: CommitsPresenter? = null

    @Inject
    lateinit var commitViewModel: CommitViewModel

    @Inject
    lateinit var commitAdapter: CommitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApplication).initDaggerComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commits)
        initView()
//        commitViewModel = ViewModelProvider(
//            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        )[CommitViewModel::class.java]


        presenter = CommitsPresenter(this, CommitsRouter(this))
        presenter?.bindView(this, this, commitViewModel)
        presenter?.onViewCreated()
    }

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

    }

    override fun onDestroy() {
        presenter?.unbindView(commitAdapter.getDataList())
        super.onDestroy()
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
        presenter?.onSaveLocalData(commitAdapter.getDataList())
    }

    override fun noUpdatedData() {
        Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_SHORT).show()
    }

    override fun onSelect(commitModel: CommitModel) {
        presenter?.onItemClicked(commitModel)
    }

}