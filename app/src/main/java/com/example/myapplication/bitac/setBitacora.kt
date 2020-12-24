package com.example.myapplication.bitac

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MenuIncio
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_set_bitacora.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class setBitacora : AppCompatActivity() {
    private var almacenaprefere: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_bitacora)
        almacenaprefere = getSharedPreferences("preferencias", MODE_PRIVATE)

        butBitVolver.setOnClickListener { startActivity(Intent(this, MenuIncio::class.java)) }
        btnBitTerminar.setOnClickListener { startActivity(Intent(this, MenuIncio::class.java)) }

        btnBitGuardar.setOnClickListener {

            almacenarDatos()
            validacion_bebidas()
            detallecarnes()
            verfrut()
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MenuIncio::class.java))

        }
        btnBitMostrar.setOnClickListener { imprimirDatos() }
        btnBitUpdate.setOnClickListener { updateBitacora()
            Toast.makeText(this, "Datos Actualizados", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MenuIncio::class.java))
        }

    }
    //recoge datos en spinner-----------------------------
        fun tipo_comida(): String {
            //----------declaracion final de datos ----------------------------------
            var tipoComidas = arrayOf("")
            var comidastipo: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipoComidas)
            comidastipo = findViewById<View>(R.id.spinnerTipodeComida) as Spinner
            var resultipoComida =
                comidastipo.selectedItem.toString() //recupera el valor de las los tipos comidas

            return resultipoComida
        }
        fun tipo_bebida(): String {
            //----------declaracion final de datos ----------------------------------
            var tipoBebida = arrayOf("")
            var spinnnertipoBebida: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipoBebida)
            spinnnertipoBebida = findViewById<View>(R.id.spinner_tipoBebida) as Spinner
            var resultipoBebida =
                spinnnertipoBebida.selectedItem.toString() //recupera el valor de las los tipos comidas

            return resultipoBebida
        }
        fun tipo_postre(): String {
            //----------declaracion final de datos ----------------------------------
            var tipoPostre = arrayOf("")
            var spinnertipoPostre: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipoPostre)
            spinnertipoPostre = findViewById<View>(R.id.spinner_postre) as Spinner
            var resultPostre =
                spinnertipoPostre.selectedItem.toString() //recupera el valor de las los tipos comidas

            return resultPostre
        }
    //-----------------------------------------------------
    //recoge detalles de tiempos y nivel de apetito
        fun cantidad_apetito(): String {
            //----------declaracion final de datos ----------------------------------
            var cant_hambre = arrayOf("")
            var spinnercan_hambre: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cant_hambre)
            spinnercan_hambre = findViewById<View>(R.id.spinner_cantidad_hambre) as Spinner
            var resulhambre =
                spinnercan_hambre.selectedItem.toString() //recupera el valor nivel de hambre
            return resulhambre

        }
        /*fun comio_acompanado(): String {
            //----------declaracion final de datos ----------------------------------
            var tipoAcompanado = arrayOf("")
            var spinnercanAcompanado: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipoAcompanado)
            spinnercanAcompanado = findViewById<View>(R.id.spinner_acompañado) as Spinner
            var resulcompania =
                spinnercanAcompanado.selectedItem.toString() //recupera el valor si esta acompañado
            return resulcompania
        }*/
        fun posicionComida(): String {
            //----------declaracion final de datos ----------------------------------
            var tipo_posicion = arrayOf("")
            var spinnerPosicion: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipo_posicion)
            spinnerPosicion = findViewById<View>(R.id.spinner_posicionComida) as Spinner
            var spinnerPos =
                spinnerPosicion.selectedItem.toString() //recupera el valor si esta acompañado

            return spinnerPos

        }
        fun tiempoComida(): String {

            //----------declaracion final de datos ----------------------------------
            var tipo_tiempo = arrayOf("")
            var spinner_tiempo_desalmcen: Spinner
            // imprimir dato spinner
            var adaptador: ArrayAdapter<String> =
                ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, tipo_tiempo)
            spinner_tiempo_desalmcen = findViewById<View>(R.id.spinner_comida) as Spinner
            var tiempos_deComidas =
                spinner_tiempo_desalmcen.selectedItem.toString() //recupera el valor si esta acompañado

            return tiempos_deComidas

        }
        //formato de fechas----------------------------
        fun fechatimestamp(): String {
            //formato para guardar datos en la bd como referencia
            var timestamp = SimpleDateFormat("yyyy_M_d").format(Date())
            return timestamp
        }
        fun fechaCurrentDate(): String {  //genera fecha para mostrar a usuario
            //formato de fecha para mostrar en la bd al usuario
            val calendar =
                Calendar.getInstance().time //instancia Date para fecha
            //------------------------------------
            val currentDate = DateFormat.getDateInstance(DateFormat.FULL)
                .format(calendar.time) //almacena el dato formato a string
            return currentDate

        }
        fun muestra_horaActual(): String {
            val tiempo = Calendar.getInstance()
            //formato de fecha para mostrar en la bd al usuario
            val hora = tiempo.get(Calendar.HOUR_OF_DAY)
            val minutos = tiempo.get(Calendar.MINUTE)
            val segundos = tiempo.get(Calendar.SECOND)
            //------------------------------------
            var horario: String = ("$hora:$minutos:$segundos")

            //Toast.makeText(this,"la hora es : $hora:$minutos:$segundos",Toast.LENGTH_SHORT).show() //almacena el dato formato a string
            return horario
        }
        //----------------------------------------------
    //funciones para almacenar acutalizar y mostrar bitacora alimenticia
        fun almacenarDatos() {
            var pin = FirebaseAuth.getInstance().uid

            //------------------------------------------------------------------
            var fechaAlmacenado = fechatimestamp() // genera fecha para la carpeta en bd y almacena
            var fechaUsuario = fechaCurrentDate()
            var tiempoComida_desalcen = tiempoComida()
            var hora = muestra_horaActual()

            var detallecomida = editDetalleComida.text.toString()
            var detalleBebida = editTdetalleBebida.text.toString()
            var actividadDia = editactividadDia.text.toString()
            var detalleHumor = editComentario_humor.text.toString()

            var tipoComida = tipo_comida()
            var tipobebida = tipo_bebida()
            var tipoPostre = tipo_postre()
            var cantHambre = cantidad_apetito()
            //var enCompania = comio_acompanado()
            var posicion = posicionComida()

            var map = mutableMapOf<String, Any>()

            map ["$fechaAlmacenado tiem_comi"] = tiempoComida_desalcen
            map["$fechaAlmacenado catComida"] = tipoComida
            map["$fechaAlmacenado catbebida"] = tipobebida
            map["$fechaAlmacenado categoria_postre"] = tipoPostre
            map["$fechaAlmacenado nivel_apetito"] = cantHambre
          //  map["$fechaAlmacenado comio_entorno"] = enCompania
            map["$fechaAlmacenado en_posicion"] = posicion
            map["$fechaAlmacenado detalle_comida"] = detallecomida
            map["$fechaAlmacenado detalle_bebida"] = detalleBebida
            map["$fechaAlmacenado tipo_actividad"] = actividadDia
            map["$fechaAlmacenado estado_animo"] = detalleHumor
            map["$fechaAlmacenado fecha detalle"] = fechaUsuario
            map["$fechaAlmacenado hora"] = hora

            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document("$pin")
                .collection("$tiempoComida_desalcen")
                .document("$fechaAlmacenado")
                .set(map)
            //******************
            alberga()
            bebida()

        }
        fun updateBitacora(){
            var pin = FirebaseAuth.getInstance().uid

            //------------------------------------------------------------------
            var fechaAlmacenado = fechatimestamp() // genera fecha para la carpeta en bd y almacena
            var fechaUsuario = fechaCurrentDate()
            var tiempoComida_desalcen = tiempoComida()
            var hora = muestra_horaActual()

            var detallecomida = editDetalleComida.text.toString()
            var detalleBebida = editTdetalleBebida.text.toString()
            var actividadDia = editactividadDia.text.toString()
            var detalleHumor = editComentario_humor.text.toString()

            var tipoComida = tipo_comida()
            var tipobebida = tipo_bebida()
            var tipoPostre = tipo_postre()
            var cantHambre = cantidad_apetito()
          /*  var enCompania = comio_acompanado()*/
            var posicion = posicionComida()

            var map = mutableMapOf<String, Any>()

            map ["$fechaAlmacenado tiem_comi"] = tiempoComida_desalcen
            map["$fechaAlmacenado catComida"] = tipoComida
            map["$fechaAlmacenado catbebida"] = tipobebida
            map["$fechaAlmacenado categoria_postre"] = tipoPostre
            map["$fechaAlmacenado nivel_apetito"] = cantHambre
            /*map["$fechaAlmacenado comio_entorno"] = enCompania*/
            map["$fechaAlmacenado en_posicion"] = posicion
            map["$fechaAlmacenado detalle_comida"] = detallecomida
            map["$fechaAlmacenado detalle_bebida"] = detalleBebida
            map["$fechaAlmacenado tipo_actividad"] = actividadDia
            map["$fechaAlmacenado estado_animo"] = detalleHumor
            map["$fechaAlmacenado fecha detalle"] = fechaUsuario
            map["$fechaAlmacenado hora"] = hora

            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document("$pin")
                .collection("$tiempoComida_desalcen")
                .document("$fechaAlmacenado")
                .update(map)

        }
        fun imprimirDatos(){
            var tiempoComida_desalcen = tiempoComida()
            val pin: String? = FirebaseAuth.getInstance().uid
            var fecha = fechatimestamp()
            FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fecha")
            .get().addOnSuccessListener { documentSnapshot ->
                    try {
                var map = documentSnapshot.data!! as Map<String, Any>
                //------------guardo en una variable los datos de comida del dia

                  var timepo_comida =  map ["$fecha tiem_comi"].toString()
                  var categoria_comi =  map["$fecha catComida"].toString()
                  var categoria_postre=  map["$fecha categoria_postre"].toString()
                       var nivelapetito =  map["$fecha nivel_apetito"].toString()
                          //var entorno = map["$fecha comio_entorno"].toString()
                          var posicion = map["$fecha en_posicion"].toString()
                          var detalle_comida = map["$fecha detalle_comida"].toString()
                          var detalle_bebida = map["$fecha detalle_bebida"].toString()
                          //var tipo_actividad= map["$fecha tipo_actividad"].toString()
                          var estado_animo = map["$fecha estado_animo"].toString()
                          var fechadetalle = map["$fecha fecha detalle"].toString()
                    var bebida =map["$fecha catbebida"].toString()

                //------------------------imprimir en un alert dialogo----------------------------
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Bitacora de ${timepo_comida} guardada")
                    builder.setMessage(
                            "categoria comida ${categoria_comi} \n" +
                            "postre ${categoria_postre} \n" +
                            "bebida tomada ${bebida} \n" +
                            "nivel de apetito ${nivelapetito} \n" +
                           // "lugar o entorno ${entorno} \n " +
                            "como ${timepo_comida} ${posicion} \n" +
                            "su comida fue ${detalle_comida} \n" +
                            "su bebida fue ${detalle_bebida} \n" +
                            "tipo de comida ${timepo_comida} \n" +
                            "su estado de animo es ${estado_animo} \n" +
                            "fecha que se almaceno ${fechadetalle}")

                    //-----------------------------------------------------------------------
                    builder.setPositiveButton("regresar al inicio",{dialogInterface: DialogInterface, i: Int ->
                        finish()
                    })
                    builder.setNegativeButton("permanecer aqui",{dialogInterface: DialogInterface, i: Int->})
                    builder.show()
                    } catch (e:NullPointerException){
                        Toast.makeText(this, "no ha guardado $tiempoComida_desalcen",Toast.LENGTH_SHORT).show()
                    }

                }

            }
    ///------------------------------------------------------------------------------
    // funciones para filtrar los datos en detalle de comidas y bebidas

        fun validacion_bebidas(){
        if (!editTdetalleBebida.text.toString().isEmpty()) {

            var detalle_bebida =
                editTdetalleBebida.text.toString().split(" ") //separa en espacios una candena
            var liquido =
                ("coca cocacola mirinda pepsi seven coquita naranjada sprite fanta powerade gallo ace bull monster amp nos" +
                        " cafe te limonada licuado jugo orchata atol naranja leche atol cerveza incaparina rica" +
                        " vino heineken ron dorada guaro victoria quetzalteca picosita").split(
                    " "
                ) //agregar mas tipos

            for (item in detalle_bebida) {
                for (i in liquido) {
                    if (item.equals(i, ignoreCase = true)) {
                        when (i) {
                            "coca" -> bebida1()
                            "cocacola" -> bebida1()
                            "mirinda" -> bebida2()
                            "pepsi" -> bebida3()
                            "seven" -> bebida4()
                            "coquita" -> bebida5()
                            "naranjada" -> bebida6()
                            "sprite" -> bebida7()
                            "fanta" -> bebida8()
                            "powerede" -> bebida9()
                            "cafe" -> bebida10()
                            "limonada" -> bebida11()
                            "jugo" -> bebida12()
                            "orchata" -> bebida13()
                            "atol" -> bebida14()
                            "naranja" -> bebida15()
                            "leche" -> bebida16()
                            "incaparina" -> bebida14()
                            "cerveza" -> bebida17()
                            "gallo" -> bebida18()
                            "ace" -> bebida19()
                            "bull" -> bebida20()
                            "rica" -> bebida21()
                            "amp" -> bebida22()
                            "monster" -> bebida22()
                            "nos" -> bebida22()
                            "vino" -> bebida23()
                            "heineken" -> bebida24()
                            "ron" -> bebida25()
                            "dorada" -> bebida19()
                            "guaro" -> bebida26()
                            "victoria" -> bebida26()
                            "quetzalteca" -> bebida26()
                            "picosita" -> bebida27()
                            "licuado" -> bebida28()

                        }
                    }
                }
            }
        }
        else{
            bebida()
        }

        } // fin de la funcion validar bebidas
        fun detallecarnes () {
            if (!editDetalleComida.text.toString().isEmpty()) {
                var detalle_comida = editDetalleComida.text.toString()
                    .split(" ") //splint () separa en espacios una candena
                var comid = ("filete cerdo chicharrones carnitas pescado camaron Lomo muslo espinazo" +
                        " pecho costillas pato gallina alitas pechuga menudos viuda cecina jamon azada asada vistec" +
                        " adobada hueso lomito chorizos longanizas moronga pan salchichas caldo marisco mariscos" +
                        " mcdonalds campero caesars dominos burger hut bell tacos granjero pinulito chinito subway pizza piza" +
                        " huevo huevos cereal tortrix ricito dorito maruchan hamburguesa amburguesa pollo carne").split(" ")
                for (item in detalle_comida) {
                    for (i in comid) {
                        if (item.equals(i, ignoreCase = true)) {
                            when (item) {
                                "filete" -> alberga1()
                                "cerdo" -> alberga2()
                                "chicharrones" -> alberga3()
                                "carnitas" -> alberga4()
                                "pescado" -> alberga5()
                                "lomo" -> alberga6()
                                "muslo" -> alberga7()
                                "espinazo" -> alberga8()
                                "pecho" -> alberga9()
                                "costillas" -> alberga10()
                                "pato" -> alberga11()
                                "gallina" -> alberga12()
                                "alitas" -> alberga13()
                                "pechuga" -> alberga14()
                                "menudos" -> alberga15()
                                "viuda" -> alberga16()
                                "cecina" -> alberga17()
                                "jamon" -> alberga18()
                                "azada" -> alberga19()
                                "asada" -> alberga19()
                                "carne" -> alberga19()
                                "vistec" -> alberga20()
                                "adobado" -> alberga21()
                                "adobada" -> alberga21()
                                "hueso" -> alberga22()
                                "caldo" -> alberga22()
                                "lomito" -> alberga23()
                                "chorizos" -> alberga24()
                                "longaniza" -> alberga24()
                                "moronga" -> alberga25()
                                "pan" -> alberga26()
                                "salchichas" -> alberga27()
                                "mcdonalds" -> alberga28()
                                "amburguesa" -> alberga28()
                                "hamburguesa" -> alberga28()
                                "burger" -> alberga28()
                                "campero" -> alberga29()
                                "granjero" -> alberga29()
                                "pinulito" -> alberga29()
                                "caesars" -> alberga30()
                                "pizza" -> alberga30()
                                "piza" -> alberga30()
                                "dominos" -> alberga30()
                                "hutt" -> alberga30()
                                "bell" -> alberga31()
                                "tacos" -> alberga31()
                                "chinito" -> alberga32()
                                "huevo" -> alberga33()
                                "huevos" -> alberga33()
                                "cereal" -> alberga34()
                                "tortrix" -> alberga35()
                                "dorito" -> alberga36()
                                "ricito" -> alberga37()
                                "maruchan" -> alberga38()
                                "pollo" -> alberga39()
                                "ceviche" -> alberga40()
                                "camaron" -> alberga40()
                                "marisco" -> alberga41()
                                "mariscos" -> alberga41()
                            }
                        }
                    }
                }
            }
            else{
                alberga()}
        }
        fun verfrut(){
            if (!editDetalleComida.text.toString().isEmpty()){

            var detalleverdura = editDetalleComida.text.toString().split(" ")
            var frutveg = ("papa papas guisquil zanahoria elote espinacas espinaca brocoli brocoly remolacha tomate ajo cebolla" +
                    " coliflor pepino lechuga rabano platano frijol frijoles maiz ensalada" +
                    " banano fresas naranja melon uvas manzana aguacate bombom dulces chicles caramelos caramelos chocolates elotitos" +
                    " mango mandarina papaya piña melocoton limon tortrix arroz").split(" ")
            for (item in detalleverdura){
                for (i in frutveg){
                    if (item.equals(i,ignoreCase = true)){
                       //*************************************************
                        when (i){
                            "papa" -> otros1()
                            "papas" -> otros1()
                            "guisquil" -> otros2()
                            "zanahoria" -> otros3()
                            "elote" -> otros3()
                            "espinacas" -> otros4()
                            "espinaca" -> otros4()
                            "brocoli" -> otros5()
                            "brocoly" -> otros5()
                            "coliflor" -> otros5()
                            "remolacha" -> otros6()
                            "tomate" -> otros7()
                            "ajo" -> otros8()
                            "cebolla" -> otros9()
                            "pepino" -> otros10()
                            "lechuga" -> otros11()
                            "rabano" -> otros12()
                            "frijol" -> otros13()
                            "frijoles" -> otros13()
                            "maiz" -> otros14()
                            "ensalada" -> otros15()
                            "banano" -> otros16()
                            "platano" -> otros16()
                            "fresas" -> otros17()
                            "naranja" -> otros18()
                            "melon" -> otros19()
                            "uvas" -> otros20()
                            "manzana" -> otros21()
                            "aguacate" -> otros22()
                            "mango" -> otros23()
                            "mandarina" -> otros24()
                            "papaya" -> otros25()
                            "piña" -> otros26()
                            "melocoton" -> otros27()
                            "limon" -> otros28()
                            "tortrix" -> otros29()
                            "arroz" -> otros30()
                            "bombom" -> otros31()
                            "dulces" -> otros31()
                            "chicles" -> otros31()
                            "caramelos" -> otros31()
                            "chocolates" -> otros31()
                            "chocolate" -> otros31()
                            "caramelo" -> otros31()
                            "elotitos" -> otros32()


                        }
                    }

                }
            }
        }
            else{
                otros()}
        }

    ////////////////////////////////////////////////////////////////////////////////////////////////
//----- funcion recomendacion bibida---
    fun bebida(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "no hay datos"

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida1(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "se recomienda ingerir menos gaseosa puede beber limonada es mas saludable para su cuerpo" +
                "puesto que las excesiva cantidad llena el hidado de grasa"

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida2(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La mayoría de los refrescos tienen una gran cantidad de azúcar, por lo que beberlos en exceso puede provocar sobrepeso, obesidad y caries." +
                " se recomienda beber como alternativa jugo de papaya al ser una fruta de tipo carnoso tiene abundante agua, alrededor del 90 por ciento. Gracias a este elevado porcentaje en agua es un excelente diurético."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida3(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = " Las bebidas contienen cafeína pueden acabar provocando nerviosismo, taquicardias, insomnio y temblores. " +
                "como alternativa se recomienda beber Una limonada puesto que es una forma sencilla  de hidratarse bien. "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida4(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Abusar de estas bebidas como seven que no contribuyen al cuerpo humano para hidratarlo . se recomienda ingerir agua puesto que siempre debe ser la fuente principal de hidratación. "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida5(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "las bebidas como coca cola estan elaboradas con carbonato sódico, un elemento que puede contribuir a elevar la tensión arterial por el alto grado de niveles de azucar que contiene. No es aconsejable su consumo para personas con hipertensión. " +
                "se recomienda La papaya puesto que aporta vitamina C en cantidades tan elevadas que 100 gramos de alimento cubren el cien por cien de la cantidad diaria recomendada para un adulto medio."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida6(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "las bebidas como naranjada  contienen ácido fosfórico, un aditivo que puede provocar la pérdida de minerales en el organismo. " +
                "se recomienda beber jugo de naranja natural porque mejora la digestión y combate el estreñimiento, por lo que se recomienda tomarlo en las mañanas."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida7(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Los refrescos contienen niveles altos de ácido fosfórico que se han asociado con cálculos renales y otros problemas de los riñones. " +
                "se recomienda beber jugo de papaya dado que ayuda a aumentar las defensas naturales del organismo"

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida8(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "20 minutos después de beber una gaseosa, el nivel de azúcar en la sangre incrementa rápidamente causando una explosión de insulina. Tu hígado responde convirtiendo el azúcar en grasa. " +
                "se recomienda beber limonada dado que Los limones contienen fitonutrientes o sustancias que protegen al cuerpo de la enfermedad por su vitamina c."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida9(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La mayoría de las gaseosas contienen jarabe de maíz alto en " +
                "fructuosa, un endulzante que se ha relacionado con un riesgo de sufrir diabetes y enfermedades cardíacas." +
                "se recomienda bebidas naturales para su consumo mitigando el riesgo de enfermedades futuras"

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida10(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "se suele  pensar en el café como una simple mezcla de agua y cafeína, " +
                "pero la infusión tiene muchos otros nutrientes esenciales para nuestro organismo. Una taza de café " +
                "contiene riboflavina (vitamina B2), ácido pantoténico (vitamina B5), manganeso, potasio, magnesio y niacina."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida11(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "El ácido ayuda a descomponer los alimentos, por lo que el estómago " +
                "está lleno de esta sustancia. El ácido del limón podría ayudar a los ácidos estomacales, " +
                "que disminuyen con el envejecimiento."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida12(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Los jugos naturales son fuente importante para tener un aliento fresco, piel tersa y suave, flexibilidad en las articulaciones "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida13(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Entre las propiedades más destacadas de la horchata natural de " +
                " se encuentran las siguientes. -No contiene fosfatos ni glucosa y es baja en sodio. -Es rica en vitaminas C y E.  "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida14(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Principalmente las nutricionales aportadas por los atoles: " +
                "rico en fibra, contiene antioxidantes, potasio, magnesio, fósforo y zinc, así como vitamina B1 y B7.  "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida15(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "El jugo de naranjaLo mejor es consumirlo por las mañanas. tómalo fresco, así conserva más nutrientes.\n" +
                "Evita añadir azúcar, lo mejor es natural. "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida16(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = " La leche de vaca proporciona una gran cantidad de proteínas" +
                " fácilmente digeribles y de alto valor biológico, ya que aportan los aminoácidos para cubrir los requerimientos humanos, incluidos los esenciales. "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida17(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "ingerir bebidas alcohólicas, aun en pequeñas cantidades, afecta" +
                " a las capacidades motoras y la atención, por lo que no se debe conducir después de " +
                "haberla consumido. Además, la cerveza en exceso puede producir ardores, acidez o reflujo " +
                "gastroesofágico, pues, por su composición, fomenta la secreción del ácido gástrico."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida18(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "como beneficio la cerveza gallo Por sus ingredientes la cerveza es un alimento saludable. " +
                "contiene diferentes sustancias nutritivas como son las vitaminas del grupo B (en especial destaca el ácido fólico) " +
                "y contiene un ligero porcentaje de carbohidratos (maltodextrina) y de alcohol. \n" +
                "sinembargo el consumo prolongado de alcohol daña las neuronas. Esto puede provocar un daño permanente a su memoria, a su capacidad de " +
                "razonamiento y a la forma como se comporta. "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida19(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "el exceso de ingerir bebidas como Dorada ACE Puede perjudicar diferentes partes del cuerpo como por ejemplo " +
                "el corazón, hígado, estómago y cerebro. "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida20(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Un red bull es una bebida energética, siempre que se consuma con moderación. Y es que además del posible daño hepático, " +
                "la ingesta excesiva de bebidas energéticas se ha relacionado con problemas cardíacos, insomnio y nerviosismo, " +
                "depresión, estrés o ansiedad.  Se recomienda ingerir liquidos como agua natural o licuados que aportan nutritivamente e hidratan el cuerpo. "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida21(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Las bebidas como las rica (roja, naranja, uva) tienden a estar con abundante azúcar tiene fructuosa y glucosa. La glucosa la acepta bien el cuerpo, pero la fructuosa solo la metaboliza el hígado.\n" +
                "Cuando ingieres muchas bebidas azucaradas, el hígado tiene mucho trabajo y no alcanza a procesar todo, así que convierte la fructuosa en grasa y resultado es… pues un hígado graso. "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida22(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "las bebidas energizantes provocan inconvenientes en la salud del consumidor como por rejemplo el corazón late más rápido, " +
                "esto sucede porque las bebidas energéticas no solo aumentan los niveles de estrés, también aumentan la frecuencia cardíaca," +
                " la presión arterial y hacen que la sangre sea un poco más espesa. Se recomienda frescos naturales o licuados a base de frutas o agua natural para hidratar el cuerpo. "

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida23(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "El vino es agua, contiene un valor calórico que varía en función de la gradación " +
                "alcohólica y el contenido en azúcares derivados del tipo de uva y el proceso de fermentación. \n " +
                "el vino es una bebida alcohólica. El alcohol es un agente deshidratante y, a su vez, su abuso de alcohol inhibe el" +
                " ritmo respiratorio, la frecuencia cardíaca y los mecanismos de control en el cerebro. Se recomienda si se consume hacerlo con moderacion."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida24(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La cerveza posee bajo contenido en sodio. Este elemento, cuando se " +
                "consume en exceso, causa derrames cerebrales. Por otro lado, la cerveza contiene mucha vitamina " +
                "B que es esencial para el sistema nervioso y la generación de células."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida25(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "El daño a los nervios a raíz del abuso del alcohol puede causar muchos problemas, algunos de los cuales son:\n" +
                "Entumecimiento o sensación dolorosa de hormigueo en brazos o piernas.\n" +
                "Problemas con las erecciones en los hombres.\n" +
                "Goteos de orina o dificultad para orinar."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida26(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La gran mayoría de las desventajas del alcohol aparecen cuando " +
                "se redondea la curva de beber moderadamente a beber en grandes cantidades. El consumo en " +
                "exceso de alcohol puede desarrollar inflamación en el hígado, el cual es conocida como hepatitis " +
                "alcohólica, y cirrosis, o bien cicatrización del hígado."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida27(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "El alcohol es perfecto para alterar la química de tu cerebro. Una " +
                "encuesta británica realizada hace poco determino que las personas que sufren de ansiedad o " +
                "depresión tenían el doble de las probabilidades de ser bebedores pesados o problemáticos." +
                " La depresión conlleva a diversos problemas de salud, a veces incluso te empuja a la muerte."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun bebida28(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Los licuados son un alimento con una gran concentración de nutrientes" +
                " muy beneficiosos para todos los sistemas del organismo: minerales esenciales, vitaminas, enzimas, " +
                "antioxidantes, clorofila y fibra; además, su forma líquida hace que podamos digerirlos más fácilmente.."

        var map = mutableMapOf<String, Any>()
        map ["bebida"] = recomendacion
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }

    // funciones recomendacion comida carnes--------------------------------------------------------
    fun alberga(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "no hay datos"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga1(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Si exageramos en el consumo de carne procesada podemos hacer mal a nuestro intestino, hasta llegar el extremo de causar cáncer." +
                " Así que es conveniente no abusar, ya que también podemos desarrollar otras enfermedades como la diabetes, colesterol alto y estreñimiento. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga2(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La ingesta exceciva dla carne de cerdo Eleva el riesgo de padecer cirrosis es recomendable comer con " +
                "moderacion o alternativas "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga3(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La ingesta exceciva chicharrones contiene un alto contenido graso en comparacion con otras carnes"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga4(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La carne de cerdo puede el tipo de carne mas contaminada por bacterias " +
                "por ende es necesario cuidar donde se consume y evitar enfermedades por su mal realizacion en la " +
                "cosina"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga5(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "El pescado es un alimento lleno de beneficios representa una excelente fuente de proteínas, hierro y otros minerales" +
                "es recomendable consumirlo como minimo 2 veces a la semana acompañado de verdura"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga6(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Comer carne es bueno para el cuerpo ya que le proporciona vitaminas B2 y B12, además es considerado como uno de los alimentos bajos en azúcar"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga7(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La carne es rica en zinc, que ayuda a protegernos contra el daño oxidativo, a la cicatrización de la piel y para crear hemoglobina (es una hemoproteína de la sangre)."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga8(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = " es importante conocer que el consumo de carnes en exceso genera proteínas y grasas de más con lo cual es recomendable " +
                "tomar en consideracion ingerir alimentos que incluyan vegetales como aguacate, brocoly o ensaladas"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga9(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = " la ingesta de carne aporta hierro a nuestro cuerpo,  un elemento importante para mantener un adecuado transporte de oxígeno en nuestra sangre."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga10(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = " porción de 85 gramos (3 onzas) de costillas obtendrás 210 calorías y por cada costilla 24 gramos de proteínas y 12 gramos de grasas. Las proteínas son básicas para mantener tus músculos en buen estado y las de cerdo te suministrarán todos los aminoácidos necesarios para reconstruir tu tejido muscular."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga11(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La carne de pato brinda muchos beneficios, como el aporte de vitaminas B (riboflavina, tiamina, niacina y vitamina B12 y B5, útil para combatir el estrés y las migrañas, además de reducir el colesterol)"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga12(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = " la gallina es un alimento con un alto contenido en ácidos grasos poliinsaturados, " +
                "ácidos grasos monoinsaturados, grasa, proteínas, ácidos grasos saturados, vitamina B3, calorías, vitamina B6 y colesterol" +
                "es un alimento bastante nutritivo y recomendado."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga13(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "las alitas de pollo contienen una las carnes que se digiere fácilmente, por lo que es excelentes para tener una buena digestion"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga14(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La carne de pollo es una de las más recomendadas por los expertos para" +
                " incorporar a nuestra dieta proteínas y nutrientes de alta calidad (valor biológico). Además, " +
                "posee un bajo contenido en grasa lo que la hace ideal para cualquier tipo de dieta."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga15(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Las menudos de pollo Nutre y fortalece los cartílagos, los cuales son esenciales porque unen los huesos a los ligamentos y músculos."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga16(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "el consumo de carnes en exceso genera proteínas y grasas de más por consiguiente es recomendable" +
                "no consumirlo en exceso buscando alternativas como sopas o ensaladas de verduras"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga17(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La cecina es preparada a base de perder agua, siendo el resultado final una altísima concentración de proteínas. " +
                "Por eso, la cecina es un buen aliado en el proceso de recuperación muscular de deportistas o, simplemente, en sus dietas."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga18(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Es importante conocer que es recomendable evitar el jamon si se tiene algun familiar con enfermedades coronarias y arteriosclerosis, por su alto contenido en grasa total y colesterol\n" +
                "enfermos oncológicos puesto que puede causar complicaciones"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga19(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "la carne asada a nivel nutricional, la carne roja es un alimento " +
                "principalmente proteico, rico en minerales como hierro y zinc y vitaminas como la B12.\n " +
                "La parte negativa de la carne roja es su contenido en en exceso (puede dar lugar a la gota y cálculos en los riñones " +
                "y en las vías urinarias). como todo alimento es importante consumir con moderacion como tambien combinar con alimentos como" +
                " vegetales o frutas."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga20(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "la carne es rica en zinc, que ayuda a protegernos contra el daño oxidativo, a la cicatrización de la piel y para crear hemoglobina." +
                "es recomendable consumir acompañado de agua o vegetales"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga21(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Cuando la carne se somete a temperaturas muy elevadas (como en las parrillas y barbacoas) se crean una serie de compuestos considerados cancerígenos (como las aminas heterocíclicas y los hidrocarburos policíclicos aromáticos).\n" +
                " Algunos estudios han observado que marinar o adobar las carnes puede disminuir en parte la producción de estos compuestos."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga22(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = " existen beneficios nutritivos al incorporar los caldos a una dieta balanceada." +
                "  “A muchas personas les cuesta hidratarse correctamente. El caldo de hueso es completamente líquido y " +
                "es una forma de mantenernos hidratados. Si estás padeciendo de alguna infección respiratoria o estomacal, " +
                "la hidratación es extremadamente importante”."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga23(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "la carne como lomito es una fuente de vitamina B12; esta vitamina nos ayuda a metabolizar proteínas, a formar glóbulos rojos y a darle mantenimiento al sistema nervioso central."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga24(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "la ingesta de embutidos como el chorizo o longaniza en una dieta regular aumenta el riesgo de desarrollar" +
                "enfermedades cardiovasculares, ademas de que por su alto contenido en grasa y sodio favorecen a la adiccion de este alimento" +
                "se recomienda buscar alternativas como caldos, ensaladas o mariscos"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga25(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La carne roja procesada contiene comúnmente sodio," +
                " nitratos, fosfatos y otros aditivos alimentarios, y las carnes ahumadas y asadas" +
                " también poseen hidrocarburos aromáticos policíclicos, que pueden contribuir a un mayor riesgo de insuficiencia cardiaca"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga26(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "El pan no engorda Siempre y cuando se consuma con moderación y en las cantidades necesarias, " +
                "por ende es necesario realizar actividad  física o ejercicio para evitar subir de peso y mantener una vida saludable"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga27(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "en las salchichas se utilizan “preservantes mediante sodio, nitratos y otros conservadores”. Las altas cantidades de sodio que se usan para conservar los embutidos afectan al sistema cardíaco y pueden elevar los niveles de la presión arterial."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga28(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "las hamburguesas contienen energía que proviene de grasas saturadas y azúcares que, en exceso, son perjudiciales para la salud y carecen de otros nutrientes esenciales." +
                "junto a las papas con un mayor riesgo de enfermedades cardíacas."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga29(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "el consumo de alimentos fritos, como papas fritas, pollo frito y snacks fritos, con un mayor riesgo de enfermedades cardíacas. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga30(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "el consumo de pizza afecta la digestion con estreñimiento, puesto que" +
                " contiene muy poca fibra y es alta en grasas y sal, lo que hace que tu sistema digestivo tenga " +
                "problemas para absorber nutrientes y llevar a cabo correctamente la digestión. se recomienda no ingerir en exceso o acompañado de licuado de papaya"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga31(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "consumir tacos constantemente aumenta el riesgo de sufrir" +
                " cáncer de estómago, además, puede desarrollar gastritis, problemas cardiacos y enfermedades neurodegenerativas como Alzheimer. \n " +
                "adicional la tortilla contienen ácidos grasos, estos te hacen blanco de enfermedades del corazón, diabetes tipo 2 e hipertensión, " +
                "incluso derrames cerebrales es recomendable no ingerir en exceso"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga32(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La comida china contiene son echas a base de la salsa de soya, que se utiliza mucho en este menú " +
                "es naturalmente tóxica ya que contiene antinutrientes y sustancias que alteran el equilibrio hormonal\n " +
                "El consumo regular de éste puede resultar en efectos colaterales adversos, incluyendo depresión, desorientación," +
                " fatiga y obesidad. Se recomienda consumir vegetales o frutas para una alimentacion saludable."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga33(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Un desayuno rico en proteinas te llena y te pone en buena forma. Por eso puede ser bueno desayunar con huevo."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga34(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "Los cereales son una muy buena opción para desayunar, nos aportan " +
                "energía y son ricos en múltiples vitaminas y minerales, saludables tanto para los adultos como para los niños."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga35(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = " las golosinas o comida chatarra como tortrix se crean con productos químicos" +
                " para conservarlos hasta por muchos meses sin echarse a perder, aditivos, saborizantes, " +
                "colorante, etc. que no benefician en nada al organismo.\n " +
                "una nutrición deficiente que le acarreara al consumidor una larga  lista de diferentes padecimientos.\n" +
                "Se recomienda ingerir alimentos como frutas o verduras que aportan nutritivamente al cuerpo"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga36(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "las golosinas o comida chatarra como el dorito se crean con productos químicos" +
                " para conservarlos hasta por muchos meses sin echarse a perder, aditivos, saborizantes, " +
                "colorante, etc. que no benefician en nada al organismo.\n " +
                "una nutrición deficiente que le acarreara al consumidor una larga  lista de diferentes padecimientos.\n" +
                "Se recomienda ingerir alimentos como frutas o verduras que aportan nutritivamente al cuerpo"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga37(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "las golosinas o comida chatarra se crean con productos químicos" +
                " para conservarlos hasta por muchos meses sin echarse a perder, aditivos, saborizantes, " +
                "colorante, etc. que no benefician en nada al organismo.\n " +
                "una nutrición deficiente que le acarreara al consumidor una larga  lista de diferentes padecimientos.\n" +
                "Se recomienda ingerir alimentos como frutas o verduras que aportan nutritivamente al cuerpo"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga38(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "las sopas instantaneas contienen GMS tiene un efecto tóxico en las células nerviosas que favoren la obesidad esto acompañado de los" +
                "preservantes que evitan que el cuerpo genere una buena digestion Se recomienda no consumir en exceso buscando alternativas como pollo, ensaladas o frutas para" +
                "beneficio nutricional del curpo."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga39(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "La carne de pollo aporta vitaminas principalmente del complejo B, destacando" +
                " la Niacina o vitamina B3 que es fundamental para el metabolismo de las grasas y azúcares en el cuerpo, " +
                "así como para mantener las células saludables. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga40(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "El camarón sirve como una excelente fuente de proteína magra. Cada " +
                "porción de 6 onzas proporciona 39 gramos de proteína, una cantidad significativa de los 46 gramos " +
                "recomendados al día para las mujeres y los 56 gramos para los hombres.  "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }
    fun alberga41(){
        var pin = FirebaseAuth.getInstance().uid
        var fechaAlmacenado = fechatimestamp()
        var tiempoComida_desalcen = tiempoComida()
        var recomendacion :String = "hay que destacar que los mariscos se trata de un tipo de alimento con " +
                "poco valor calórico, lo cual lo convierte en un imprescindible para aquellas personas con " +
                "problemas de salud o que estén llevando a cabo una dieta de pérdida de peso. Aporta vitaminas A, B, D y E; " +
                "entre otras dependiendo del pescado que sea. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("$tiempoComida_desalcen")
            .document("$fechaAlmacenado")
            .set(map)
    }

/////////////funciones para verfrut //////////////////////////////////////////////////////////////
        fun otros(){
    var pin = FirebaseAuth.getInstance().uid
    var recomendacion :String = "no hay datos"

    var map = mutableMapOf<String, Any>()
    map ["comida"] = recomendacion

    FirebaseFirestore.getInstance()
        .collection("filtroc")
        .document("$pin")
        .set(map)
}
        fun otros1(){
    var pin = FirebaseAuth.getInstance().uid
    var recomendacion :String = "La papa es un tipo de carbohidrato complejo, lo que la convierte en una fuente de energía 100% natural."

    var map = mutableMapOf<String, Any>()
    map ["comida"] = recomendacion

    FirebaseFirestore.getInstance()
        .collection("filtroc")
        .document("$pin")
        .set(map)
}
        fun otros2(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "tiene muy pocas calorías y un importante nivel de fibra, esta combinación es ideal si se desea perder peso."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros3(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "El elote Contiene carotenoides antioxidantes, como luteína, " +
                "zeaxantina y beta-criptoxantina, que actúan contra la diabetes, el cáncer, los problemas cardiacos"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros4(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "las espinacas Es rica en fibra, vitamina A, B1, B2, C, K, calcio, fósforo, hierro, ácido fólico, magnesio, zinc y betacarotenos, estos últimos poseen potente actividad antioxidante.\n" +
                "Las espinacas son buenísimas en dietas para adelgazar ya que producen saciedad y sólo aportan 16 calorías por cada 100 gramos."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros5(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "Ayuda a eliminar el colesterol malo del organismo y tiene un " +
                "alto contenido en fibra, por lo que nos protege frente a las enfermedades cardiovasculares."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros6(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "La remolacha es su compuesto en fenólicos, los cuales " +
                "proporcionan un gran poder antioxidante, incluso después de cocida. También colabora " +
                "con las defensas del organismo gracias a su alto contenido en vitamina C, fibra y minerales"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros7(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "El tomate es rico en vitaminas y minerales: esta hortaliza " +
                "aporta vitamina C, un potente antioxidante natural, además de vitamina A, K, hierro y potasio."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros8(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "Gracias a su composición, el ajo nos puede ayudar a mejorar" +
                " nuestra salud, reforzar nuestras defensas y hacer que estemos más fuertes y protegidos ante agentes externos."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros9(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "Las cebollas son una fuente de fibra, lo que contribuye a la " +
                "ingesta de fibras solubles. El tipo de fibra presente en las cebollas puede reducir" +
                " la probabilidad de desarrollar enfermedades cardiovasculares, como presión arterial alta"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros10(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "el pepino gracias a la hidratación que proporciona, favorece múltiples" +
                " procesos internos al nutrir las células. El pepino contiene muchas vitaminas del grupo B, muy" +
                " efectivas para relajar el sistema nervioso y aliviar el impacto de la ansiedad y el estrés."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros11(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "La lechuga tiene una gran cantidad de fibra dietética, que ayuda" +
                " a mejorar la digestión y estimula el movimiento de los intestinos. Además, es útil en casos" +
                " de retención de líquidos, cálculos renales y problemas de flatulencias."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros12(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "el rabano contiene un alto nivel vitamina C y fibra, muy favorable para" +
                " el tránsito intestinal. Tiene propiedades diuréticas, que combaten la retención de " +
                "líquidos. Entre sus componentes contiene glucosinolatos, que se encargan de cuidar el hígado y la vesícula."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros13(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "los frijoles contienen carbohidratos de absorción lenta.\n" +
                "Tienen un alto contenido en ácido fólico, tiamina, riboflavina y niacina.\n" +
                "Aporta magnesio, potasio, zinc, calcio y fósforo.\n" +
                "Es una gran fuente de fibra (unos 17 gramos)"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros14(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "el maiz Rico en fibra e hidratos de carbono el maíz es saciante y ayuda" +
                " a controlar nuestro apetito. Rico en ácido fólico y otras vitaminas"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros15(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "La ensalada regula la funcion intestinal puesto que los vegetales son ricos en fibra" +
                " y previenen el estreñimiento, contribuyen a reducir el colesterol controlando los niveles de glucosa en la sangre" +
                "lo cual beneficia a las personas con diabetes "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros16(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "El banano es excelente para el sistema digestivo por su alto contenido en " +
                "potasio. Además su efecto laxante natural te puede ayudar a combatir el estreñimiento. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros17(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "Las fresas contienen fibra, que ayuda a regular los procesos " +
                "digestivos y a reducir la sensación de hambre. Ayudan a disminuir el nivel de colesterol " +
                "malo en sangre, gracias a la gran cantidad de ácido ascórbico, lecitina y pectina que contiene el fruto. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros18(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "La naranja es un poderoso antioxidante por la gran cantidad de" +
                " Vitamina C que contiene, por ello favorece la cicatrización, y refuerza es sistema inmunológico de organismo. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros19(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "El melon Refuerza el sistema inmunológico, formación de anticuerpos. La cebolla es otro alimento con esta propiedad "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros20(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "Las uvas son ricas en antioxidantes, su índice glucémico no es alto, sino medio; son ricas en fibra"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros21(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "La manzana tiene mucha fibra. De cada 100 gramos de manzana, 2,4 son de fibra, esto" +
                "ayuda al sistema digestivo en el proceso de digestion."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros22(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "El aguacate Contiene vitaminas K, C, B5, B6 y E, potasio y ácido fólico. \n" +
                "El potasio te ayuda a reducir la presión arterial."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros23(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "El mango, por ser una gran fuente de vitamina B6 y hierro, " +
                "es uno de esos alimentos ideales para la salud del cerebro. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros24(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "La mandarina es un poderoso antioxidante por la gran cantidad de" +
                " Vitamina C que contiene, por ello favorece la cicatrización, y refuerza es sistema inmunológico de organismo. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros25(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "La papaya aporta vitamina C en cantidades tan elevadas que 100 " +
                "gramos de alimento cubren el cien por cien de la cantidad diaria recomendada para un adulto medio. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros26(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "la piña gran parte de la composición de la piña es agua y fibra.\n" +
                "Es baja en calorías, así que es una fruta ideal para cuando se está a dieta. "

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros27(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "El melocoton Rico en fibra y agua y tiene un alto contenido en carotenos.\n" +
                "Vitaminas A, B1, B2, B6, C y E es recomendable seguir consumiendo fruta"

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros28(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "El limon gracias a su composición, el limón tiene propiedades " +
                "beneficiosas para la salud: es diurético, tiene un gran poder antibacteriano, ayuda " +
                "a reforzar el sistema inmunológico y nervioso (gracias al potasio) y tiene capacidad antioxidante."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros29(){
        var pin = FirebaseAuth.getInstance().uid
        var recomendacion :String = "Las golosinas son alimentos elaborados por la industria que causan placer, " +
                "pero carecen de valor nutricional; es decir, son calorías vacías las golosinas, al ser alimentos " +
                "ricos en hidratos de carbono de absorción rápida, elevan los niveles de glucemia en el organismo, " +
                "que entre otras cosas, pueden llevar a la obesidad, predisponen a padecer diabetes tipo 2 y caries dentales, entre otras enfermedades."

        var map = mutableMapOf<String, Any>()
        map ["comida"] = recomendacion

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .set(map)
    }
        fun otros30(){
            var pin = FirebaseAuth.getInstance().uid
            var recomendacion :String = "El arroz es una Excelente fuente de energía funcional para el organismo, pues uno de sus principales " +
                    "compuestos es el hidrato de carbono, que actúa como combustible para el cuerpo y contribuye al funcionamiento normal del cerebro."

            var map = mutableMapOf<String, Any>()
            map ["comida"] = recomendacion

            FirebaseFirestore.getInstance()
                .collection("filtroc")
                .document("$pin")
                .set(map)
        }
        fun otros31(){
            var pin = FirebaseAuth.getInstance().uid
            var recomendacion :String = "La OMS destaca que el consumo de azúcar libre aumenta el " +
                    "riesgo de caries dental. Los azúcares libres son los que los fabricantes, cocineros " +
                    "o consumidores añaden a los alimentos o las bebidas que se van a consumir. El exceso de " +
                    "calorías procedentes de alimentos y bebidas con un alto contenido en azúcar libre también contribuye al " +
                    "aumento de peso, que puede dar lugar a sobrepeso y obesidad."

            var map = mutableMapOf<String, Any>()
            map ["comida"] = recomendacion

            FirebaseFirestore.getInstance()
                .collection("filtroc")
                .document("$pin")
                .set(map)
        }
        fun otros32(){
            var pin = FirebaseAuth.getInstance().uid
            var recomendacion :String = "La comida chatarra como tal esta especialmente creada a base de  grasas, azúcares, sal lo que provoca un riesgo para la salud como el colesterol. \n " +
                    "Por contra, la comida saludable contiene en mayor medida: vitaminas, proteínas, fibras, minerales y carbohidratos. \n " +
                    "por lo que se recomienda ingerir frutas o ensaladas para evitar el exceso de productos que dañen la salud a futuro. "

            var map = mutableMapOf<String, Any>()
            map ["comida"] = recomendacion

            FirebaseFirestore.getInstance()
                .collection("filtroc")
                .document("$pin")
                .set(map)
        }
}