package br.com.imepac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPrincipal : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var userEmail: EditText
    private lateinit var weightValue: TextView
    private lateinit var heightValue: TextView
    private lateinit var bmiValue: TextView
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)

        iniciarComponents()
        fetchAllNames()
        db = FirebaseFirestore.getInstance()

        // Exemplo de dados fixos para medidas corporais
        weightValue.text = "75 kg"
        heightValue.text = "1.80 m"
        bmiValue.text = "23.1"

        val iconSettings: ImageView = findViewById(R.id.icon_settings)
        iconSettings.setOnClickListener {
            val intent = Intent(this, Tela_Perfil::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        this.userEmail.setText(userEmail)
        if (userEmail != null) {
            buscarNomeDoEmail(userEmail)
        }
    }

    fun buscarNomeDoEmail(email: String) {
        val usuariosRef = db.collection("Usuarios")
        val query = usuariosRef.whereEqualTo("email", email)
        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documento = querySnapshot.documents[0]
                    val nome = documento.getString("nome")
                    if (nome != null) {
                        this.userName.setText(nome)
                    }else{
                        println("Nome não encontrado para o e-mail $email")
                    }
                }else {
                    println("Nenhum documento encontrado para o e-mail $email")
                }
            }.addOnFailureListener { e -> println("Erro ao buscar documento: $e")}
    }

    fun iniciarComponents() {
        userName = findViewById(R.id.user_name)
        userEmail = findViewById(R.id.user_email)
        weightValue = findViewById(R.id.weight_value)
        heightValue = findViewById(R.id.height_value)
        bmiValue = findViewById(R.id.bmi_value)
    }

    fun fetchAllNames() {
        val db = FirebaseFirestore.getInstance()
        val usuariosRef = db.collection("usuarios")

        usuariosRef.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val nome = document.getString("nome")
                println("Nome: $nome")
            }
        }.addOnFailureListener{ exception ->
            println("Erro ao buscar os nomes: ${exception.message}")
        }
    }
}
