package com.emre.kotlininstagram.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.emre.kotlininstagram.R
import com.emre.kotlininstagram.adapter.PostAdapter
import com.emre.kotlininstagram.databinding.ActivityFeedAcitivtyBinding
import com.emre.kotlininstagram.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FeedAcitivty : AppCompatActivity() {

    private lateinit var binding: ActivityFeedAcitivtyBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var postAraryList: ArrayList<Post>
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedAcitivtyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore
        postAraryList = ArrayList<Post>()

        getData()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter(postAraryList)
        binding.recyclerView.adapter = postAdapter
    }

    private fun getData() {

        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(this@FeedAcitivty,error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty){


                        postAraryList.clear()
                        val documents = value.documents

                        for (document in documents) {
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            val post = Post(userEmail, comment, downloadUrl)

                            postAraryList.add(post)
                        }
                        postAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.feed_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.signOut) {
            auth.signOut()
            val intentToMainActivity = Intent(this@FeedAcitivty, MainActivity::class.java)
            startActivity(intentToMainActivity)
            finish()
        } else if (item.itemId == R.id.addPost) {
            val intentToUpload = Intent(this@FeedAcitivty, UploadActivity::class.java)
            startActivity(intentToUpload)
        }
        return super.onOptionsItemSelected(item)
    }
}