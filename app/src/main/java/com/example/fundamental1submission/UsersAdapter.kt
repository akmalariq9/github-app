package com.example.fundamental1submission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UsersAdapter(private val listUsers: List<MainUser>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        onItemClickCallback = callback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_users, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = listUsers[position]
        viewHolder.usernameTextView.text = user.login
        viewHolder.profileUrlTextView.text = user.htmlUrl
        Glide.with(viewHolder.avatarImageView.context)
            .load(user.avatarUrl)
            .into(viewHolder.avatarImageView)

        viewHolder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(user)
        }
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usernameTextView: TextView = view.findViewById(R.id.usernameTextView)
        val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)
        val profileUrlTextView: TextView = view.findViewById(R.id.profileUrlTextView)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MainUser)
    }
}
