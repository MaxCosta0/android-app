package br.com.imepac

import FichaTreinoPagerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class FichaTreino : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_ficha_treino)

        val treinos = listOf(
            GrupoMuscular(
                "Peito + Bíceps", listOf(
                    Exercicio("Supino reto com barra", "4 séries de 8 a 10 repetições"),
                    Exercicio("Supino inclinado com halteres", "3 séries de 10 a 12 repetições"),
                    Exercicio("Crucifixo", "3 séries de 12 a 15 repetições"),
                    Exercicio("Crossover", "3 séries de 12 a 15 repetições"),
                    Exercicio("Rosca direta com barra", "4 séries de 8 a 10 repetições"),
                    Exercicio("Rosca martelo com halteres", "3 séries de 10 a 12 repetições")
                )
            ),
            GrupoMuscular(
                "Costas + Tríceps", listOf(
                    Exercicio("Puxada alta", "4 séries de 8 a 10 repetições"),
                    Exercicio("Remada baixa", "3 séries de 10 a 12 repetições"),
                    Exercicio("Pullover com halter", "3 séries de 12 repetições"),
                    Exercicio("Remada unilateral com halter", "3 séries de 12 a 15 repetições"),
                    Exercicio("Tríceps testa com barra", "4 séries de 10 a 12 repetições"),
                    Exercicio("Tríceps corda", "3 séries de 12 a 15 repetições")
                )
            ),
            GrupoMuscular(
                "Ombros", listOf(
                    Exercicio("Desenvolvimento com halteres", "4 séries de 8 a 10 repetições"),
                    Exercicio("Elevação lateral", "3 séries de 12 a 15 repetições"),
                    Exercicio("Elevação frontal", "3 séries de 12 repetições"),
                    Exercicio("Crucifixo invertido", "3 séries de 12 a 15 repetições"),
                    Exercicio("Encolhimento com halteres", "3 séries de 15 repetições")
                )
            ),
            GrupoMuscular(
                "Quadríceps", listOf(
                    Exercicio("Agachamento livre", "4 séries de 8 a 10 repetições"),
                    Exercicio("Leg Press", "4 séries de 10 a 12 repetições"),
                    Exercicio("Cadeira extensora", "4 séries de 12 a 15 repetições"),
                    Exercicio("Afundo com halteres", "3 séries de 12 repetições (cada perna)")
                )
            ),
            GrupoMuscular(
                "Posterior de Perna", listOf(
                    Exercicio("Stiff com barra", "4 séries de 8 a 10 repetições"),
                    Exercicio("Mesa flexora", "4 séries de 12 a 15 repetições"),
                    Exercicio("Cadeira flexora", "4 séries de 12 a 15 repetições"),
                    Exercicio("Elevação de quadril (hip thrust)", "4 séries de 10 a 12 repetições")
                )
            )
        )

        val viewPager: ViewPager2 = findViewById(R.id.viewPagerFichaTreino)
        viewPager.adapter = FichaTreinoPagerAdapter(treinos)
    }
}
