package br.com.imepac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaPrincipal : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var userEmail: EditText
    private lateinit var weightValue: EditText
    private lateinit var heightValue: EditText
    private lateinit var bmiValue: TextView
    private lateinit var saveButton: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)

        db = FirebaseFirestore.getInstance()
        iniciarComponents()
        carregarDados()
        fetchAllNames()

        val iconSettings: ImageView = findViewById(R.id.icon_settings)
        iconSettings.setOnClickListener {
            val intent = Intent(this, Tela_Perfil::class.java)
            startActivity(intent)
        }

        saveButton.setOnClickListener {
            salvarDados()
        }

        // Configura o link para a ficha de treino
        val fichaTreinoLink: TextView = findViewById(R.id.link_ficha_treino)
        fichaTreinoLink.setOnClickListener {
            val intent = Intent(this, FichaTreino::class.java)
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
        saveButton = findViewById(R.id.save_button)
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

    fun salvarDados() {
        val novoPeso = weightValue.text.toString().replace(",", ".").toDoubleOrNull()
        val novaAltura = heightValue.text.toString().replace(",", ".").toDoubleOrNull()
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        if (novoPeso != null && novaAltura != null && userEmail != null) {
            val usuariosRef = db.collection("Usuarios")

            // Busca o documento do usuário pelo e-mail
            usuariosRef.whereEqualTo("email", userEmail).get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val document = querySnapshot.documents[0] // Primeiro documento encontrado
                        val usuarioRef = document.reference // Referência ao documento do usuário

                        // Atualizar os dados de peso e altura
                        usuarioRef.update(mapOf(
                            "peso" to novoPeso,
                            "altura" to novaAltura
                        )).addOnSuccessListener {
                            println("Dados de peso e altura atualizados com sucesso!")
                        }.addOnFailureListener { e ->
                            println("Erro ao atualizar dados: $e")
                        }
                        carregarDados()
                    } else {
                        println("Documento do usuário não encontrado com o e-mail fornecido.")
                    }
                }.addOnFailureListener { e ->
                    println("Erro ao buscar documento do usuário: $e")
                }
        } else {
            println("Valores inválidos para peso, altura ou e-mail.")
        }
    }

    fun carregarDados() {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        if (userEmail != null) {
            val usuariosRef = db.collection("Usuarios")

            // Busca o documento do usuário pelo e-mail
            usuariosRef.whereEqualTo("email", userEmail).get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val document = querySnapshot.documents[0] // Primeiro documento encontrado
                        val peso = document.getDouble("peso") ?: 0.0
                        val alturaCm = document.getDouble("altura") ?: 0.0
                        val alturaM = alturaCm / 100 // Converte altura para metros

                        // Define os valores no layout
                        weightValue.setText(peso.toString())
                        heightValue.setText(alturaCm.toString()) // Mostra a altura em cm

                        // Calcula o IMC e exibe no campo de IMC
                        if (peso > 0 && alturaM > 0) {
                            val imc = peso / (alturaM * alturaM)
                            bmiValue.text = String.format("%.2f", imc)
                        } else {
                            bmiValue.text = "N/A" // Caso os valores sejam inválidos
                        }
                    } else {
                        println("Documento do usuário não encontrado com o e-mail fornecido.")
                    }
                }.addOnFailureListener { e ->
                    println("Erro ao buscar documento do usuário: $e")
                }
        } else {
            println("E-mail do usuário é inválido ou o usuário não está autenticado.")
        }
    }

}
