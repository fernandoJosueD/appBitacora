package com.example.myapplication.inserts

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MenuIncio
import com.example.myapplication.Mostrar.mostrarInfoPersona
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_set_info_persona.*

class setInfoPersona : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_info_persona)


        butregresar.setOnClickListener { startActivity(Intent(this, MenuIncio::class.java)) }
        btnDatExtraterminar.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MenuIncio::class.java
                )
            )
        }
        btnDatExtraGuardar.setOnClickListener {

            if (validacionText()) {
                tomavitaminas()
                Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MenuIncio::class.java))
            }
        }

        btnModalmostrarconf.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    mostrarInfoPersona::class.java
                )
            )
        }

        btnsetperUpdate.setOnClickListener {
            if (validacionText()) {
               updateTomavitaMinas()
                Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MenuIncio::class.java))
            }
        }

    }
    //---------------------------------------
    fun updateTomavitaMinas(){
        if (radiobtsi.isChecked) {
            updatesi()
        } else if (radiobtno.isChecked) {
            updateno()
        }

    }
    fun tomavitaminas() {
        if (radiobtsi.isChecked) {
            almacenarDatosVitaminaSI()

        } else if (radiobtno.isChecked) {
            almacenarDatosVinaminaNo()

        }

    }

    fun tipoVitamina(): String {

        //----------declaracion final de datos ----------------------------------
        var tomvitamin = arrayOf("a", "b", "c", "d", "e", "k")
        var vitamina: Spinner
        // imprimir dato spinner
        var adaptador: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tomvitamin)
        vitamina = findViewById<View>(R.id.spinnertipovitamina) as Spinner
        var tipoVitamina =
            vitamina.selectedItem.toString() //obtiene el elemento seleccionado del spinner raza perro

        return tipoVitamina


    }

    fun comidasaldia(): String {
        //----------declaracion final de datos ----------------------------------
        var datocomida = arrayOf("cuatro tiempos", "tres tiempos", "dos tiempos", "solo una")
        var mascota: Spinner
        // imprimir dato spinner
        var adaptador: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, datocomida)
        mascota = findViewById<View>(R.id.spinnercomidas) as Spinner
        var cantidadmeriendas =
            mascota.selectedItem.toString() //obtiene el elemento seleccionado del spinner raza perro

        //txtmostrar.setText(seleccionActual)
        return cantidadmeriendas

    }

    fun consumeGolosinas(): String {
        var arraygolosinas = arrayOf(
            "no consumo",
            "1",
            "3",
            "varias veces al dia",
            "esporadicamente",
            "todos los dias"
        )
        var spinnergolosina: Spinner
        var adaptador: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arraygolosinas)
        spinnergolosina = findViewById<Spinner>(R.id.spinnergolosinas)
        var medirGolosinas = spinnergolosina.selectedItem.toString()
        return medirGolosinas
    }

    fun actividadSemanal_ejercicio(): String {
        var arrayactividadsemanal = arrayOf("no", "1", "2", "3", "4", "5", "6", "7")
        var spinnerActividad: Spinner
        var adaptador: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrayactividadsemanal)
        spinnerActividad = findViewById<Spinner>(R.id.spinnerejercicios_si_no)
        val cuantosDias = spinnerActividad.selectedItem.toString()

        return cuantosDias
    }

    fun actividadTiempo(): String {
        var arraytiempoactividad = arrayOf("10", "20", "30", "60", "120", "variado")
        var spinnertiempo: Spinner
        var adaptador: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arraytiempoactividad)
        spinnertiempo = findViewById<Spinner>(R.id.spinner_tiempo_actividad)
        var tiempActividad = spinner_tiempo_actividad.selectedItem.toString()

        return tiempActividad
    }

    fun nivelEstres(): String {
        var arraynivelEstres = arrayOf("")
        var spinnerstres: Spinner
        var adaptador: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arraynivelEstres)
        spinnerstres = findViewById<Spinner>(R.id.spinnerNivelEstres)
        var nivel_de_estres = spinnerNivelEstres.selectedItem.toString()

        return nivel_de_estres
    }

    //----------------------------------------
    fun almacenarDatosVitaminaSI() {

        // recupera mi uid de firebase y correo si asi lo deseo
        var currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // startActivity(Intent(this,activity_segunda_actividad::class.java))
            var pin = currentUser.uid.toString()
            var emailcorreo = currentUser.email.toString()


            var alcho = editxtbebidasAlcho.text.toString()
            var enfermedad = editxtEnfermedad.text.toString()
            var vitamin: String = "si"
            var tipovitamin = tipoVitamina()
            var numcomidas = comidasaldia()
            var golosinas = consumeGolosinas()
            var ejercicio = actividadSemanal_ejercicio()
            var tiempoejercicio = actividadTiempo()
            var nivelestres = nivelEstres()
            var tipoDieta = editTextdieta.text.toString()
            var resultadoDieta = editextresultdieta.text.toString()

            var map = mutableMapOf<String, Any>()

            map["consume vitamina"] = vitamin
            map["tipo vitamina"] = tipovitamin
            map["comidas al dia"] = numcomidas
            map["consume golosinas"] = golosinas
            map["realiza ejercicio"] = ejercicio
            map["tiempos de ejercicio"] = tiempoejercicio
            map["nivel de estres"] = nivelestres
            map["usa una dieta"] = tipoDieta
            map["resultados de dieta"] = resultadoDieta
            map["enfermedad"] = enfermedad
            map["bebe_licor"] = alcho


            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document("$pin")
                .collection("Datos")
                .document("datos_adi")
                .set(map)
        }


    }

    fun almacenarDatosVinaminaNo() {
        // recupera mi uid de firebase y correo si asi lo deseo
        var currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // startActivity(Intent(this,activity_segunda_actividad::class.java))
            var pin = currentUser.uid.toString()
            var emailcorreo = currentUser.email.toString()
            //println("mi emailAcural es :" + emailcorreo + " pin" + pin)

            var alcho = editxtbebidasAlcho.text.toString()
            var enfermedad = editxtEnfermedad.text.toString()
            var vitamin: String = "no"
            var tipovitamin = "ninguna"
            var numcomidas = comidasaldia()
            var golosinas = consumeGolosinas()
            var ejercicio = actividadSemanal_ejercicio()
            var tiempoejercicio = actividadTiempo()
            var nivelestres = nivelEstres()
            var tipoDieta = editTextdieta.text.toString()
            var resultadoDieta = editextresultdieta.text.toString()

            var map = mutableMapOf<String, Any>()

            map["consume vitamina"] = vitamin
            map["tipo vitamina"] = tipovitamin
            map["comidas al dia"] = numcomidas
            map["consume golosinas"] = golosinas
            map["realiza ejercicio"] = ejercicio
            map["tiempos de ejercicio"] = tiempoejercicio
            map["nivel de estres"] = nivelestres
            map["usa una dieta"] = tipoDieta
            map["resultados de dieta"] = resultadoDieta
            map["enfermedad"] = enfermedad
            map["bebe_licor"] = alcho

            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document("$pin")
                .collection("Datos")
                .document("datos_adi")
                .set(map)
        }


    }

    fun validacionText(): Boolean {
        var respuesta = true
        var alc = editxtbebidasAlcho.text.toString()
        var enfer = editxtEnfermedad.text.toString()

        if (alc.isBlank()) {
            editxtbebidasAlcho.error = "ingrere si usted bebe alcohol"
            respuesta = false
        }
        if (enfer.isBlank()) {
            editxtEnfermedad.error = "ingrese si cuenta con alguna enfermedad"
            respuesta = false
        }
        return respuesta
    }

    //---------------------------update------------

    fun updatesi(){
        val pin: String? = FirebaseAuth.getInstance().uid

        var alcho = editxtbebidasAlcho.text.toString()
        var enfermedad = editxtEnfermedad.text.toString()
        var vitamin: String = "si"
        var tipovitamin = tipoVitamina()
        var numcomidas = comidasaldia()
        var golosinas = consumeGolosinas()
        var ejercicio = actividadSemanal_ejercicio()
        var tiempoejercicio = actividadTiempo()
        var nivelestres = nivelEstres()
        var tipoDieta = editTextdieta.text.toString()
        var resultadoDieta = editextresultdieta.text.toString()

        var map = mutableMapOf<String, Any>()

        map["consume vitamina"] = vitamin
        map["tipo vitamina"] = tipovitamin
        map["comidas al dia"] = numcomidas
        map["consume golosinas"] = golosinas
        map["realiza ejercicio"] = ejercicio
        map["tiempos de ejercicio"] = tiempoejercicio
        map["nivel de estres"] = nivelestres
        map["usa una dieta"] = tipoDieta
        map["resultados de dieta"] = resultadoDieta
        map["enfermedad"] = enfermedad
        map["bebe_licor"] = alcho


        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("Datos")
            .document("datos_adi")
            .update(map)
    }
    fun updateno(){
        val pin: String? = FirebaseAuth.getInstance().uid


        var alcho = editxtbebidasAlcho.text.toString()
        var enfermedad = editxtEnfermedad.text.toString()
        var vitamin: String = "no"
        var tipovitamin = "ninguna"
        var numcomidas = comidasaldia()
        var golosinas = consumeGolosinas()
        var ejercicio = actividadSemanal_ejercicio()
        var tiempoejercicio = actividadTiempo()
        var nivelestres = nivelEstres()
        var tipoDieta = editTextdieta.text.toString()
        var resultadoDieta = editextresultdieta.text.toString()

        var map = mutableMapOf<String, Any>()

        map["consume vitamina"] = vitamin
        map["tipo vitamina"] = tipovitamin
        map["comidas al dia"] = numcomidas
        map["consume golosinas"] = golosinas
        map["realiza ejercicio"] = ejercicio
        map["tiempos de ejercicio"] = tiempoejercicio
        map["nivel de estres"] = nivelestres
        map["usa una dieta"] = tipoDieta
        map["resultados de dieta"] = resultadoDieta
        map["enfermedad"] = enfermedad
        map["bebe_licor"] = alcho

        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("Datos")
            .document("datos_adi")
            .update(map)
    }


}




