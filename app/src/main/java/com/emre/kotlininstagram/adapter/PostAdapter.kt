package com.emre.kotlininstagram.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emre.kotlininstagram.databinding.ActivityMainBinding
import com.emre.kotlininstagram.databinding.RecyclerRowBinding
import com.emre.kotlininstagram.model.Post
import com.squareup.picasso.Picasso

class PostAdapter(val postList: List<Post>): RecyclerView.Adapter<PostAdapter.PostHolder>() {

    class PostHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.recyclerEmailText.text = postList[position].email
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.recyclerImageView)
        holder.binding.recyclerCommentText.text = postList[position].comment
    }
}