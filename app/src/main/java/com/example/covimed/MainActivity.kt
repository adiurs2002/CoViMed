package com.example.covimed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.jar.Attributes

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        val etext1 = findViewById<EditText>(R.id.etext1)
        val etext2 = findViewById<EditText>(R.id.etext2)
        val etext3 = findViewById<EditText>(R.id.etext3)
        val etext4 = findViewById<EditText>(R.id.etext4)
        val etext5 = findViewById<EditText>(R.id.etext5)
        val etext6 = findViewById<EditText>(R.id.etext6)
        val etext7 = findViewById<EditText>(R.id.etext7)



//        Log.i("Check FireStore", "onCreate: " + userCollectionRef)

        btnSubmit.setOnClickListener{
            val reportNum = etext1.text.toString()
            val icmr = etext2.text.toString()
            val patientName = etext3.text.toString()
            val age = etext4.text.toString()
            val sex = etext5.text.toString()
            val district = etext6.text.toString()
            val state = etext7.text.toString()


            val user = User(reportNum, icmr, patientName, age, sex, district, state,)
            saveUser(user)
        }

    }

    private fun saveUser(user: User) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val db = FirebaseFirestore.getInstance()
            val userCollectionRef = db.collection("users")
            userCollectionRef.add(user).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Sucessfully saved data", Toast.LENGTH_LONG).show()
           }
        }catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()
            }
        }
   }
}