package com.example.metadescqrscanner

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class WalletActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        pref  = applicationContext.getSharedPreferences("MyPref", MODE_PRIVATE)

        val tvBalance: TextView = findViewById(R.id.tv_balance)
        val cardMoney: CardView = findViewById(R.id.card_money)
        val btnBack: ImageView = findViewById(R.id.ic_back)
        val balance: Float = pref.getFloat("balance", 0.0F)
        tvBalance.text = balance.toString()

        btnBack.setOnClickListener {
            onBackPressed()
        }

        cardMoney.setOnClickListener {
            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogView: View = inflater.inflate(R.layout.layout_balance_dialog, null)
            dialogBuilder.setView(dialogView)
            val dialog: AlertDialog = dialogBuilder.create()

            val button: Button = dialogView.findViewById(R.id.btn_payment) as Button

            button.setOnClickListener {

                val edtBalance: EditText = dialogView.findViewById(R.id.edt_balance) as EditText
                if (edtBalance.text.isEmpty()) {
                    Toast.makeText(this, "Please insert amount.", Toast.LENGTH_LONG).show()
                } else {
                    val editor: SharedPreferences.Editor = pref.edit()
                    val newBalance = balance + edtBalance.text.toString().toFloat()
                    editor.putFloat("balance", newBalance)
                    editor.apply()
                    tvBalance.text = newBalance.toString()
                    dialog.dismiss()
                }

            }

            dialog.show()

        }

    }


}