package com.emre.kotlininstagram.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.emre.kotlininstagram.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@MainActivity, FeedAcitivty::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signIn(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.passText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {

            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent = Intent(this@MainActivity, FeedAcitivty::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity,it.localizedMessage, Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this@MainActivity, "Enter email and passwor", Toast.LENGTH_LONG).show()
        }

    }

    fun signUp(view: View) {

        val email = binding.emailText.text.toString()
        val password = binding.passText.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // İnternete veri gönderip geriye bir sonuç döneceğinden dolayı asenkron olarak yapılması gerekiyor. Bu yüzden listener(dinleyici) oluşturmamız lazım
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {  // Cevap döndüğünde yapılacaklar
                // success olduğunda
                val intent = Intent(this@MainActivity, FeedAcitivty::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                // Hata olduğunda
                Toast.makeText(this@MainActivity,it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        } else {

            Toast.makeText(this,"Enter email and password", Toast.LENGTH_LONG).show()

        }
    }
}