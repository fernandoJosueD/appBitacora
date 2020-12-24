package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_pantalla_opcion.*
import java.text.SimpleDateFormat
import java.util.*


class pantallaOpcion : AppCompatActivity() {
        private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_opcion)

        traerDatoLicor()
        datoimctrabajar()
        dia()

        btnInformacion.setOnClickListener {
            //////////////////////
            desaspinner()
            almaspinner()
            cenaspinner()
            recuperabebida()
            ///////////////////
            resulteditexbebi()
            resulteditexcomi()
            resulteditexfruver()
            //-------------------
            }

        textenferm.setOnClickListener {
            val url = "https://docs.google.com/forms/d/e/1FAIpQLSdxhS9NUKP3UgOYxQQPitUsJf--JfmvVjMk4dwtndGdkhrn3A/viewform?usp=sf_link"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        btninfoali.setOnClickListener { startActivity(Intent(this, Scrollinginformacion::class.java)) }
        
    }

    fun traerDatoLicor() {
        val pin: String? = FirebaseAuth.getInstance().uid
        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("Datos")
            .document("datos_adi")
            .get().addOnSuccessListener { documentSnapshot ->
                try {
                    var map = documentSnapshot.data!! as kotlin.collections.Map<String, Any>
                   // var enfer = map["enfermedad"].toString()
                    var licor = map["bebe_licor"].toString()
                    var ejerci = map["tiempos de ejercicio"].toString()

                    textLicor.text = licor
                    // textenferm.setText(enfer)
                    textejerci.text = ejerci
                }catch (e:NullPointerException){
                    Toast.makeText(this,"",Toast.LENGTH_SHORT).show()
                }

            }

        }
    fun datoimctrabajar(){
        val pin: String? = FirebaseAuth.getInstance().uid
        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("Datos")
            .document("usuario")
            .get().addOnSuccessListener { documentSnapshot ->
                try {
                    var map = documentSnapshot.data!! as kotlin.collections.Map<String, Any>
                    var imcdata = map["imc"].toString()
                    textimcBase.text = imcdata
                    resulpes()
                }catch (e:NullPointerException){
                    Toast.makeText(this,"",Toast.LENGTH_SHORT).show()
                }
            }
    }
//------------------------------------
    fun anio ():Int{
        var timestamp:String = SimpleDateFormat("yyyy").format(Date())
        return timestamp.toInt()
    }
    fun mes ():Int{
        var timestamp:String = SimpleDateFormat("M").format(Date())
        return timestamp.toInt()
    }
    fun dia ():Int{
        var timestamp:String = SimpleDateFormat("d").format(Date())


        return timestamp.toInt()


    }
    fun restadia():String{
        var anio = anio()
        var mes = mes()
        var dia = dia()
        var ayer = dia -1 // restar el dia que hace falta

        var fecha = ("${anio}"+"_"+"${mes}"+"_"+"${ayer}")
       // textViewresumenHistorial.setText(fecha)

        return fecha
    }

    fun desaspinner(){
        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = restadia()

        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("desayuno")
            .document("$fecha")
            .get().addOnSuccessListener { documentSnapshot ->
                    try {
                var map = documentSnapshot.data!! as kotlin.collections.Map<String, Any>
                var categoria_comi =  map["$fecha catComida"].toString()
                var categoria_bebida =  map["$fecha catbebida"].toString()

                        des1.text = "$categoria_comi"
                        des2.text = "$categoria_bebida"
                recuperades()
                    } catch (e:NullPointerException){
                        des1.text = "ninguna"
                        des2.text = "ninguna"
                        recuperades()
                    }
            }
    }
    fun almaspinner(){
        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = restadia()

        FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document("$pin")
            .collection("almuerzo")
            .document("$fecha")
            .get().addOnSuccessListener { documentSnapshot ->
                try {
                var map = documentSnapshot.data!! as kotlin.collections.Map<String, Any>

                var categoria_comi =  map["$fecha catComida"].toString()
                var categoria_bebida =  map["$fecha catbebida"].toString()

                    alm1.text = "$categoria_comi"
                    alm2.text = "$categoria_bebida"
                recuperaalm()
                } catch (e:NullPointerException){
                    alm1.text = "ninguna"
                    alm2.text = "ninguna"
                }

            }
    }
    fun cenaspinner(){
        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = restadia()

            FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document("$pin")
                .collection("cena")
                .document("$fecha")
                .get().addOnSuccessListener { documentSnapshot ->
                    try {
                        var map = documentSnapshot.data!! as kotlin.collections.Map<String, Any>

                        var categoria_comi: String? = map["$fecha catComida"].toString()
                        var categoria_bebida: String? = map["$fecha catbebida"].toString()

                        cen1.text = "$categoria_comi"
                        cen2.text = "$categoria_bebida"
                        recuperacen()
                    } catch (e:NullPointerException){
                        cen1.text = "ninguna"
                        cen2.text = "ninguna"
                    }



        }



    }
    //*********funcion prinicpal prerecord*************************
    fun resulpes(){
        // mensaje para enfocar el peso y brindar un mensaje de recomendacion inicial
        var a:Int = 18
        var b:Int = 25
        var c:Int = 30

     var valorimc =textimcBase.text.toString().toFloat()
        if (valorimc.toFloat() < a){
            Toast.makeText(this, "necesita regular su dieta para subir de peso", Toast.LENGTH_SHORT).show()
            }
        if( valorimc.toInt() >=a && ( valorimc.toInt()<=b )){
            Toast.makeText(this, "ustede esta en el peso ideal", Toast.LENGTH_SHORT).show()
        }
        if (valorimc.toInt() >= c &&(valorimc.toInt() <=40)){
            Toast.makeText(this, "se recomienda de primera mano evitar alimentos que contengan grasas o azucar", Toast.LENGTH_SHORT).show()}
    }
    fun  recuperades (){
        //recupera resultado de la base de datos para consignar desayunos
         var traedato =des1.text.toString()
        if(traedato== "niguna"){
            textre1.text = "no hay datos"
        }
        if(traedato== "lacteos"){
            textre1.text = "un buen alimento como  $traedato con cereal adicional que cuenta con beneficios a la salud  dado que disminuyen los niveles de colesterol"
        }
        if(traedato== "carnes"){
            textre1.text = "usted come demasiada $traedato en desayuno es recomendable alimentos "
        }
        if(traedato== "pan"){
            textre1.text = "se recomienda consumir con moderacion $traedato y buscar  alternativas como tortilla o pan integral reducir el riesgo de obesidad "
        }
        if(traedato== "huevos"){
            textre2.text = " los $traedato Son ricos en colesterol, pero no afectan negativamente colesterol en la sangre"
        }
        if(traedato== "vegetales"){
            textre1.text = " los $traedato son alimentos que nos ayudan a limpiar el organismo aportando vitaminas"
        }
        if(traedato== "pastas"){
            textre1.text = "el consumo excesivo de $traedato Intolerancia. El gluten es una proteína que se encuentra el trigo, cebada y centeno. Las personas con intolerancia al gluten, enfermedad celiaca, deben evitan cualquier alimento a base de trigo, incluyendo la pasta."
        }
        if(traedato== "sopas"){
            textre1.text = "las $traedato hidratan el organismo y es recomendable consimir al menos 2 veces por semana"
        }
        if(traedato== "ensaladas"){
            textre1.text = "las $traedato ayudan a prevenir la anemia dado que son alimentos sin preservantes y son una fuente de vitaminas que regulan multiples procesos organicos del cuerpo"
        }
        if(traedato== "mariscos"){
            textre1.text = "los $traedato tienen un alto contenido en proteínas esenciales para el cuerpo.\n" +
                    "Aporta vitaminas A, B, D y E; entre otras dependiendo del pescado que sea. se recomienda acompañar de ensaladas."
        }
        if(traedato== "Frutas"){
            textre1.text = " las $traedato son una exelente fuente de vitaminas es importante acompañarlo con abundante agua"
        }
        if(traedato== "pizza"){
            textre1.text = "la $traedato La pizza contiene muy poca fibra y es alta en grasas y sal, lo que hace que tu sistema digestivo tenga problemas para absorber nutrientes y llevar a cabo correctamente la digestión. \n " +
                    "Se recomienda no ingerir en exceso o acompañado de un licuado de frutas que contenga fibra."
        }
        if(traedato== "amburguesa"){
            textre1.text = "la $traedato provee energía de grasas saturadas y azúcares que, en exceso, son perjudiciales para la salud y carecen de otros nutrientes esenciales."
        }

    }
    fun  recuperaalm (){
        //recupera resultado de la base de datos para consignar de almuerzos
        var traedato =alm1.text.toString()
        if(traedato== "niguna"){
            textre2.text = "no hay datos"
        }
        if(traedato== "lacteos"){
            textre2.text = "un buen almuerzo acompañado de $traedato con cereal adicional que cuenta con beneficios a la salud  dado que disminuyen los niveles de colesterol"
        }
        if(traedato== "carnes"){
            textre2.text = "la $traedato contiene colesterol y grasas saturadas, las cuales son malas para la salud, dando lugar a muchas enfermedades. se recomienda consumir vegetales o ensaladas "
        }
        if(traedato== "pan"){
            textre2.text = "se recomienda $traedato cosumer alternativas como tortilla o pan integral reducir el riesgo de obesidad"
        }
        if(traedato== "huevos"){
            textre2.text = " los $traedato contienen colina, que ayuda al desarrollo saludable del cerebro y reducen el riesgo de enfermedades del corazón. "
        }
        if(traedato== "vegetales"){
            textre2.text = " los $traedato son alimentos que nos ayudan a limpiar el organismo aportando vitaminas y fibra para ayudar al sistema digestivo a realizar una digestion suave gracias a la fibra que contienen los vegetales"
        }
        if(traedato== "pastas"){
            textre2.text = "la ingesta de $traedato excesiva podría originar reacciones tóxicas y deficiencias enzimáticas durante la digestión."
        }
        if(traedato== "sopas"){
            textre2.text = "las $traedato hidratan el organismo y es recomendable consimir al menos 2 veces por semana"
        }
        if(traedato== "ensaladas"){
            textre2.text = "las $traedato ayudan a prevenir la anemia dado que son alimentos sin preservantes y son una fuente de vitaminas que regulan multiples procesos organicos del cuerpo"
        }
        if(traedato== "mariscos"){
            textre2.text = "los $traedato se recomienda comer vegetales"
        }
        if(traedato== "Frutas"){
            textre2.text = " las $traedato son una exelente fuente de vitaminas "
        }
        if(traedato== "pizza"){
            textre2.text = "la $traedato consumirla con frecuencia aumenta los niveles de glucosa, generando obesidad, higado graso y hasta deabetes"
        }
        if(traedato== "amburguesa"){
            textre2.text = "el consumo de $traedato causa Enfermedades cardiovasculares producidas por el exceso de grasas saturadas.\n" +
                    "Digestiones lentas y pesadas, puesto que el método de cocción de los alimento es la fritura, utilizando aceite de palma, el cual suele ser reutilizado. se recomienda evitar este tipo de alimentos."
        }


    }
    fun  recuperacen (){
        //recupera resultado de la base de datos para consignar las cenas
        var traedato =cen1.text.toString()
        if(traedato== "niguna"){
            textre3.text = "no hay datos"
        }
        if(traedato== "lacteos"){
            textre3.text = "un alimento acompañado de $traedato como cereal quesos al parecer la ingesta de queso en cantidades limitadas ayuda a nuestro organismo a defenderse de las enfermedades del corazón y a reducir el riesgo de infarto."
        }
        if(traedato== "carnes"){
            textre3.text = "usted come demasiada $traedato en desayuno es recomendable alimentos suaves "
        }
        if(traedato== "pan"){
            textre3.text = "hay paradigmas sobre el $traedato y este nos aporta energía gracias a su elevado contenido en hidratos de carbono. es importante consumirlo con moderacion \n" +
                    "Asi mismo tambien es recomendable ingerir comidas suaves o facil digestion como fruta o verdura durante la cena. "
        }
        if(traedato== "huevos"){
            textre3.text = "los $traedato Son ricos en colesterol, pero no afectan negativamente colesterol en la sangre"
        }
        if(traedato== "vegetales"){
            textre3.text = "los $traedato Benefician el fortalecimiento de la flora bacteriana y mejoran la circulación."
        }
        if(traedato== "pastas"){
            textre3.text = "el consumo excesivo de $traedato  Las pastas contienen este elemento que cuando ingresa en el torrente sanguíneo de las personas que lo padecen, desencadena una respuesta inmune que daña el revestimiento del hígado por ende es importante moderar con frutas o verduras la alimentacion."
        }
        if(traedato== "sopas"){
            textre3.text = "las $traedato hidratan el organismo y es recomendable consimir al menos 2 veces por semana"
        }
        if(traedato== "ensaladas"){
            textre3.text = "las $traedato ayudan a prevenir la anemia dado que son alimentos sin preservantes y son una fuente de vitaminas que regulan multiples procesos organicos del cuerpo"
        }
        if(traedato== "mariscos"){
            textre3.text = "los $traedato se recomienda comer vegetales"
        }
        if(traedato== "Frutas"){
            textre3.text = "las $traedato son una exelente fuente de vitaminas "
        }
        if(traedato== "pizza"){
            textre3.text = "la $traedato contiene harina refinada es un ingrediente procesado que no contiene nutrientes por lo que si se consume con frecuencia la persona se sentira satisfecha" +
                    "pero no estara nutriendo correctamente el cuerpo Se recomienda comer con moderacion acompañado de bebidas con fibra como lo es jugos de frutas."
        }
        if(traedato== "amburguesa"){
            textre3.text = "la $traedato poseen alto contenido de calorías que dan energía al cuerpo por ende se recomienda realizar ejercicio y consumir la cantindad necesaria de agua."
        }


    }
    fun recuperabebida(){
        //recupera los resultados traidos de la base de datos para comparar
        var valora = des2.text.toString()
        var valorb = alm2.text.toString()
        var valorc =des2.text.toString()
//------------------------------------- inicia cambio a----------------------------------------------------------
        if (valora==valorc && (valora =="agua-natural")){
            textre4.text = "se recomienda ingerir agua suficiente durante el dia dado que hidrata el curpo  ayudando dolores de cabeza y migrañas"
        }
        if (valora==valorc && (valora =="te-u-cafe")){
            textre4.text = "las bebidas como el cafe mejora el rendimiento fisico ayudando a quemar grasas sinembargo siempres es importante alternar con  bebidas que aporten diferentes nutrientes a el organismo como bebidas naturales "
        }
        if (valora==valorc && (valora =="jugo-procesado")){
            textre4.text = "los jugos procesados contienen grandes cantidades de azucar que aumentan el riesgo de obesidad "
        }
        if (valora==valorc && (valora =="licuado-de-fruta")){
            textre4.text = "las frutas nos ayudan a prevenir distintas enfermedades como problemas cardiovasculares, trastornos digestivos"
        }
        if (valora==valorc && (valora =="gaseosa")){
            textre4.text = "el  un consumo elevado de gaseosas puede aumentar el riesgo de caries por el alto contenido de azúcar se recomienda ingerir bebidas como orchata, limonada o frescos de frutas"
        }
        if (valora==valorc && (valora =="cerveza")){
            textre4.text = "se recomienda tener precaucion al ingerir bebida alcohólica, afecta a las capacidades motoras y la atención"
        }
//--------------------------------------------------inicia cambio c-----------------------------------------------------

        if (valorb==valorc && (valorc =="agua-natural")){
            textre4.text = "se recomienda ingerir agua puesto que  Ayuda en la digestión y evita el estreñimiento"
        }
        if (valorb==valorc && (valorc =="te-u-cafe")){
            textre4.text = "se suele pensar que el café como una simple mezcla de agua y cafeína, pero la infusión tiene muchos otros nutrientes esenciales para nuestro organismo. Una taza de café contiene riboflavina (vitamina B2), ácido pantoténico (vitamina B5)"
        }
        if (valorb==valorc && (valorc =="jugo-procesado")){
            textre4.text = "consumir jugos procesados en reducidas cantidades puede no afectar sinembargo es de vital importancia no hacerlo en exceso puesto que puede afectar la salud"
        }
        if (valorb==valorc && (valorc =="licuado-de-fruta")){
            textre4.text = "Las frutas nos producen sensación de saciedad al contener fibra nos ayuda a regular el tránsito intestinal y evitar el estreñimiento"
        }
        if (valorb==valorc && (valorc =="gaseosa")){
            textre4.text = "las gaseosas, tanto regulares como Light, generan adicción debido al efecto que produce el azúcar y la cafeína en el sistema nervioso. es necesario buscar alternativas naturales como licuados de fruta"
        }
        if (valorb==valorc && (valorc =="cerveza")){
            textre4.text = "ingerir en exceso cerveza Puede dañar el corazón, hígado, estómago y cerebro es recomendable evitar consumirla en grandes cantidades"
        }
        //--------------------------------------------------inicia cambio b-----------------------------------------------------

        if (valorb==valorc && (valorb =="agua-natural")){
            textre4.text = "El agua no contiene calorías, grasa, carbohidratos ni azúcar y es un gran reemplazo de las bebidas con alto contenido calórico.\n" +
                    "\n"
        }
        if (valorb==valorc && (valorb =="te-u-cafe")){
            textre4.text = " el riesgo de sufrir depresión disminuye cuando aumentamos el consumo de café sinembargo tambien es recomendable buscar alternativas como limonada"
        }
        if (valorb==valorc && (valorb =="jugo-procesado")){
            textre4.text = "los jugos artificiales contienen muchos aditivos químicos y colorantes con azúcar, un carbohidrato simple que en exceso genera obesidad y trastornos metabólicos."
        }
        if (valorb==valorc && (valorb =="licuado-de-fruta")){
            textre4.text = "Por sus antioxidantes, previenen el envejecimiento prematuro de las células, dándote una piel más limpia, joven, suave y sana y mayor calidad de vida."
        }
        if (valorb==valorc && (valorb =="gaseosa")){
            textre4.text = "ingerir en exceso puede afectar la salud a largo plazo dado que contienen niveles altos de ácido fosfórico que se han asociado con cálculos renales y otros problemas de los riñones."
        }
        if (valorb==valorc && (valorb =="cerveza")){
            textre4.text = "Si se toma en ayunas puede producir hipoglucemia (bajo nivel de azúcar en la sangre es necesario tener precaucion"
        }

    }

///////////////////////////////////////recupera info de editext filtrado
        fun resulteditexcomi(){

    val pin: String? = FirebaseAuth.getInstance().uid
    var fecha = restadia()

    FirebaseFirestore.getInstance()
        .collection("filtrob")
        .document("$pin")
        .collection("almuerzo")
        .document("$fecha")
        .get().addOnSuccessListener { documentSnapshot ->
                try {
                    var map = documentSnapshot.data!! as kotlin.collections.Map<String, Any>
                    var catalmfiltro = map["comida"].toString()
                    textre5.text = "$catalmfiltro"
                } catch (e:NullPointerException){
                    resulteditexcomidesayuno()
                }
        }
}
        fun resulteditexbebi(){
        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = restadia()
        FirebaseFirestore.getInstance()
            .collection("filtro")
            .document("$pin")
            .collection("almuerzo")
            .document("$fecha")
            .get().addOnSuccessListener { documentSnapshot ->
                try {
                var map = documentSnapshot.data!! as kotlin.collections.Map<String, Any>
                var catalmfiltrob =  map["bebida"].toString()
                    textre6.text = "$catalmfiltrob"
                } catch (e:java.lang.NullPointerException){
                    Toast.makeText(this,"no hay datos guardados",Toast.LENGTH_SHORT).show()
                }

            }
    }
        fun resulteditexfruver(){
        val pin: String? = FirebaseAuth.getInstance().uid

        FirebaseFirestore.getInstance()
            .collection("filtroc")
            .document("$pin")
            .get().addOnSuccessListener { documentSnapshot ->
                try {
                var map = documentSnapshot.data!! as kotlin.collections.Map<String, Any>
                var catalmfiltrob =  map["comida"].toString()
                    textre7.text = "$catalmfiltrob"
                } catch (e:NullPointerException){
                    Toast.makeText(this,"no hay datos guardados",Toast.LENGTH_SHORT).show()
                }


            }
    }
    //-----------------------------

    fun resulteditexcomidesayuno(){

        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = restadia()

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("desayuno")
            .document("$fecha")
            .get().addOnSuccessListener { documentSnapshot ->
                try {
                    var map = documentSnapshot.data!! as kotlin.collections.Map<String, Any>
                    var catalmfiltro = map["comida"].toString()
                    textre5.text = "$catalmfiltro"
                } catch (e:NullPointerException){
                   resulteditexcomicena()
                }
            }
    }
    fun resulteditexcomicena(){

        val pin: String? = FirebaseAuth.getInstance().uid
        var fecha = restadia()

        FirebaseFirestore.getInstance()
            .collection("filtrob")
            .document("$pin")
            .collection("cena")
            .document("$fecha")
            .get().addOnSuccessListener { documentSnapshot ->
                try {
                    var map = documentSnapshot.data!! as kotlin.collections.Map<String, Any>
                    var catalmfiltro = map["comida"].toString()
                    textre5.text = "$catalmfiltro"
                } catch (e:NullPointerException){
                    Toast.makeText(this, "No hay datos guardados",Toast.LENGTH_SHORT).show()
                }
            }
    }


}