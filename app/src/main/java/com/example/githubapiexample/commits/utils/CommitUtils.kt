package com.example.githubapiexample.commits.utils

import com.example.githubapiexample.api.response.CommitResponse
import com.example.githubapiexample.commits.model.CommitModel

class CommitUtils {

    companion object {

        fun convertToCommitModelList(commitResponseList: ArrayList<CommitResponse>): ArrayList<CommitModel> {
            val dataList = ArrayList<CommitModel>()
            commitResponseList?.forEach {
                dataList.add(fromResponseToModel(it))
            }
            return dataList
        }

        private fun fromResponseToModel(response: CommitResponse): CommitModel {
            var author = response.commit.author.name
            var avatar = ""
            response.author?.let {
                author = response.author.login
                avatar = response.author.avatar_url
            }
            return CommitModel(
                response.sha, response.node_id, response.commit.message, response.url,
                author, response.commit.author.email, avatar, response.commit.author.date
            )
        }

    }
}