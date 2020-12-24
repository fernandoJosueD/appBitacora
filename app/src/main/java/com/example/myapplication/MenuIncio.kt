@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.Mostrar.mostrarInfoBit
import com.example.myapplication.bitac.setBitacora
import com.example.myapplication.inserts.setInfoPersona
import com.example.myapplication.inserts.setPersona
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu_incio.*

class MenuIncio : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private var googleApiClient: GoogleApiClient? = null

    private var imagenusuariogmail: ImageView? = null
    private var textnombre_de_usuario: TextView? = null
    private var textcorreoUsario: TextView? = null
    private var id_usuario: TextView? = null
    var googleSignInClient: GoogleSignInClient?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_incio)


        //   /-----------------actividad para validar correo con google---------------------------------
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        //------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------

      //  imagenusuariogmail = findViewById<View>(R.id.imagenusuariogmail) as ImageView
//        textnombre_de_usuario = findViewById<View>(R.id.textnombre_de_usuario) as TextView
  //      textcorreoUsario = findViewById<View>(R.id.textcorreoUsario) as TextView
    //    id_usuario = findViewById<View>(R.id.id_usuario) as TextView
//---------------------------------------------------------------------------------------------

        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API)
            .build()
//-------------------------------------------------------------------------------------------------*/
                    // botones en imagen
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()

        imgBitacora.setOnClickListener {
            startActivity(Intent(this,setBitacora::class.java))
        }
        imgConfig.setOnClickListener {
            startActivity(Intent(this,setPersona::class.java))
        }
        imgSalud.setOnClickListener {
            startActivity(Intent(this,setInfoPersona::class.java))
        }
        butexit.setOnClickListener {

            finish()
        }
        imgLeerBit.setOnClickListener {
           startActivity(Intent(this,mostrarInfoBit::class.java))
        }

        butexit.setOnClickListener { salirDeLaAplicacion()
            startActivity(Intent(this,MainActivity::class.java))
        }
        imgReco.setOnClickListener {  startActivity(Intent(this,pantallaOpcion::class.java))}

        imageinfoalime.setOnClickListener { startActivity(Intent(this,Scrollinginformacion::class.java)) }



    }

    fun salirDeLaAplicacion(){
        FirebaseAuth.getInstance().signOut()
        //salir de google--------------
        googleSignInClient?.signOut()
        finish()
    }



    override fun onStart() {
        super.onStart()
        val opr =
            Auth.GoogleSignInApi.silentSignIn(googleApiClient)
        if (opr.isDone) {
            val result = opr.get()
            handleSignInResult(result)
        } else {
            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
        }
    }
///agregar persistencia o escucha habilitado por siempre

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {

            val account = result.signInAccount
            textnombre_de_usuario?.text = account!!.displayName
            textcorreoUsario?.text = account.email
            id_usuario?.text = account.id
            imagenusuariogmail?.let { Glide.with(this).load(account.photoUrl).into(it) }
        } else {

        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }



}