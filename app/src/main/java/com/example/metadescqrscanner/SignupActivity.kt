package com.example.metadescqrscanner

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SignupActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val edtUsername: EditText = findViewById(R.id.edt_username)
        val edtPassword: EditText = findViewById(R.id.edt_password)
        val edtRepeatPass: EditText = findViewById(R.id.edt_repeat_password)
        val btnNext: Button = findViewById(R.id.btn_next)

        val pref = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()

        val username: String? = pref.getString("username", "")
        if (username != null && username != "") {
            gotoMain()
        }

        btnNext.setOnClickListener {

            if (edtUsername.text.isEmpty()){
                Toast.makeText(this, "Please insert username.", Toast.LENGTH_LONG).show()
            } else if (edtPassword.text.isEmpty()){
                Toast.makeText(this, "Please insert password.", Toast.LENGTH_LONG).show()
            } else if (edtRepeatPass.text.isEmpty()){
                Toast.makeText(this, "Please repeat password.", Toast.LENGTH_LONG).show()
            } else if (edtRepeatPass.text.toString() != edtPassword.text.toString()) {
                Toast.makeText(this, "Incorrect password.", Toast.LENGTH_LONG).show()
            } else {
                editor.putString("username", edtUsername.text.toString())
                editor.putString("password", edtPassword.text.toString())
                editor.apply()
                gotoMain()
            }

        }

    }

    private fun gotoMain(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}