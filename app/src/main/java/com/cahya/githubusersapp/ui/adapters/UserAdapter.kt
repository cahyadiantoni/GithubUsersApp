package com.cahya.githubusersapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cahya.githubusersapp.R
import com.cahya.githubusersapp.data.local.entity.ModelUser
import com.cahya.githubusersapp.databinding.ItemUserBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder (private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ModelUser) {
            Glide.with(itemView)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_person)
                .into(binding.ivAvatar)
            binding.tvUsername.text = user.login
            binding.tvUrl.text = user.htmlUrl

            binding.root.setOnClickListener {
                onItemClickListener?.let { it(user) }
            }

        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ModelUser>() {
        override fun areItemsTheSame(oldItem: ModelUser, newItem: ModelUser): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: ModelUser, newItem: ModelUser): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((ModelUser) -> Unit)? = null

    fun setOnItemClickListener(listener: (ModelUser) -> Unit) {
        onItemClickListener = listener
    }
}