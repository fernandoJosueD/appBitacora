package com.example.myapplication.Mostrar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mostrar_info_persona.*

class mostrarInfoPersona : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_info_persona)


        mostrarInformacion()


    }

    fun mostrarInformacion() {
        val pin: String? = FirebaseAuth.getInstance().uid

        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("Datos")
            .document("datos_adi")
            .get().addOnSuccessListener { documentSnapshot ->
                try {
                    var map = documentSnapshot.data!! as Map<String, Any>

                    var vita = map["consume vitamina"].toString()
                    var vita2 = map["tipo vitamina"].toString()
                    var estres = map["nivel de estres"].toString()
                    var tiem = map["comidas al dia"].toString()
                    var golo = map["consume golosinas"].toString()
                    var ejer = map["realiza ejercicio"].toString()
                    var ejertemp = map["tiempos de ejercicio"].toString()
                    var dieta = map["usa una dieta"].toString()
                    var dieta2 = map["resultados de dieta"].toString()
                    var enfer = map["enfermedad"].toString()
                    var licor = map["bebe_licor"].toString()


                    ida.text = "usted $vita ingiere vitamina"
                    idb.text = "tipo de vitamina consumida $vita2"
                    idc.text = "el nivel de estres que mantiene es $estres"
                    ide.text = "usted realiza $tiem comidas"
                    idf.text = "usted $golo consume golosinas"
                    idg.text = "usted $ejer hace ejercicio"
                    idh.text = "usted dedica $ejertemp de ejercicio al dia"
                    idi.text = "usa alguna dieta $dieta"
                    idj.text = "resultado de dieta $dieta2"
                    idk.text = "tiene alguna enfermedad $enfer"
                    idl.text = "ingiere licor $licor"

                }catch (e:NullPointerException){
                    ida.text = "aun no se almacena"
                    idb.text = "aun no se almacena"
                    idc.text = "aun no se almacena"
                    ide.text = "aun no se almacena"
                    idf.text = "aun no se almacena"
                    idg.text = "aun no se almacena"
                    idh.text = "aun no se almacena"
                    idi.text = "aun no se almacena"
                    idj.text = "aun no se almacena"
                    idk.text = "aun no se almacena"
                    idl.text = "aun no se almacena"
                }


            }
    }



}