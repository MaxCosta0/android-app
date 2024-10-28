package br.com.imepac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {
    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var bt_entrada: Button
    private lateinit var progressbar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)
        supportActionBar?.hide()

        val iconRegistration: ImageView = findViewById(R.id.icon_registration)
        iconRegistration.setOnClickListener{
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        iniciarComponentes()
            val linkFormCadastro = findViewById<TextView>(R.id.text_tela_cadastro)
            linkFormCadastro.setOnClickListener{
                val intent = Intent(this, FormCadastro::class.java)
                startActivity(intent)
            }

        bt_entrada.setOnClickListener{
            val email = edit_email.text.toString()
            val senha = edit_senha.text.toString()
            if (email.isEmpty() || senha.isEmpty()) {
                val mensagemErro = "Campos não preenchidos, tente naovamente"
                val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_LONG)
                snackbar.show()
            }else{
                autenticarUsuario()
            }
        }
    }

    fun autenticarUsuario() {
        val email = edit_email.text.toString()
        val senha = edit_senha.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    progressbar.visibility = View.GONE

                    val user = FirebaseAuth.getInstance().currentUser
                    val intent = Intent(this@FormLogin, TelaPrincipal::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    val mensagemErro = task.exception?.message
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Erro ao autenticar usuário: $mensagemErro",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
    }

    fun iniciarComponentes() {
        edit_email = findViewById(R.id.edit_email)
        edit_senha = findViewById(R.id.edit_senha)
        bt_entrada = findViewById(R.id.bt_entrada)
        progressbar = findViewById(R.id.progressbar)
    }
}