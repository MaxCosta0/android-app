package br.com.imepac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FormCadastro : AppCompatActivity() {
    private lateinit var edit_nome:EditText
    private lateinit var edit_email:EditText
    private lateinit var edit_senha:EditText
    private lateinit var btnCadastrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)
        supportActionBar?.hide()

        val iconLogin: ImageView = findViewById(R.id.icon_login)

        iconLogin.setOnClickListener{
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
        }

        edit_nome = findViewById(R.id.edit_nome)
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        btnCadastrar = findViewById(R.id.btn_cadastrar)

        btnCadastrar.setOnClickListener{
            val nome = edit_nome.text.toString().trim()
            val email = edit_email.text.toString().trim()
            val senha = edit_senha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                val mensagemErro = "Campos nao preenchidos, tente novamente"
                val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_LONG)
                snackbar.show()
            }else{
                cadastrarUsuario(it)
            }
        }
//        val linkFormCadastro = findViewById<TextView>(R.id.title_cadastro)
//        linkFormCadastro.setOnClickListener{
//            val telaCadastro = Intent(this, FormCadastro::class.java)
//            startActivity(telaCadastro)
//        }

    }

    fun cadastrarUsuario(it: View) {
        val email = edit_email.text.toString().trim()
        val senha = edit_senha.text.toString().trim()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    salvarDadosUsuario()
                    val mensagemOk = "Cadastro realizado om sucesso"
                    val snackbar = Snackbar.make(it, mensagemOk, Snackbar.LENGTH_LONG)
                    snackbar.show()
                }else{
                    val mensagemErro = "Erro ao cadastrar usuario"
                    val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
    }

    fun salvarDadosUsuario() {
        val db = FirebaseFirestore.getInstance()
        val nome = edit_nome.text.toString().trim()
        val usuarioId = FirebaseAuth.getInstance().currentUser?.uid
        val email = FirebaseAuth.getInstance().currentUser?.email

        if (usuarioId == null || email == null) {
            println("Erro na autenticação")
        }

        val usuarios = hashMapOf(
            "nome" to nome,
            "email" to email,
            "uid" to usuarioId
        )

        db.collection("Usuarios")
            .add(usuarios)
            .addOnSuccessListener { documentReference ->
                println("Documento adicionado com ID: ${documentReference.id}")
            }
            .addOnFailureListener{ e ->
                println("Erro ao adicionar documento: $e")
            }

    }
}