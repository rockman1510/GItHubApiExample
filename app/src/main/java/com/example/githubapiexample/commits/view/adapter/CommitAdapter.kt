package com.example.githubapiexample.commits.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapiexample.R
import com.example.githubapiexample.commits.model.CommitModel
import com.example.githubapiexample.utils.CommonUtils
import javax.inject.Inject


class CommitAdapter @Inject constructor(val context: Context) :
    RecyclerView.Adapter<CommitAdapter.ViewHolder>() {

    var commitItemSelectListener: CommitItemSelectListener? = null
    private val dataList: ArrayList<CommitModel> = ArrayList()
    fun getDataList(): ArrayList<CommitModel> = dataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_commit_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvSHA.text = "${context.getString(R.string.commit)} ${dataList[position].sha}"
        holder.tvAuthor.text = "${context.getString(R.string.author)} ${dataList[position].author}"
        CommonUtils.loadImageToView(context, holder.ivAvatar, dataList[position].authorAvatar)
        holder.rootView.setOnClickListener { commitItemSelectListener?.onSelect(dataList[position]) }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun addData(data: ArrayList<CommitModel>) {
        dataList += data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootView: CardView = itemView.findViewById(R.id.cvRootView)
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvSHA: TextView = itemView.findViewById(R.id.tvSHA)
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
    }

    interface CommitItemSelectListener {
        fun onSelect(commitModel: CommitModel)
    }
}
