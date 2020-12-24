package com.example.myapplication.Mostrar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.MenuIncio
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_imc.*

class imc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)


        textView11.setOnClickListener {  startActivity(Intent(this, MenuIncio::class.java)) }
    }
}