package br.com.imepac

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Exercicio(val nome: String, val series: String)

data class GrupoMuscular(val nome: String, val exercicios: List<Exercicio>)

class FichaTreinoAdapter(private val gruposMusculares: List<GrupoMuscular>) :
    RecyclerView.Adapter<FichaTreinoAdapter.GrupoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grupo_muscular, parent, false)
        return GrupoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        val grupoMuscular = gruposMusculares[position]
        holder.nomeGrupoMuscular.text = grupoMuscular.nome
        holder.exercicios.text = grupoMuscular.exercicios.joinToString("\n") {
            "${it.nome} - ${it.series}"
        }
    }

    override fun getItemCount() = gruposMusculares.size

    class GrupoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeGrupoMuscular: TextView = view.findViewById(R.id.nomeGrupoMuscular)
        val exercicios: TextView = view.findViewById(R.id.exercicios)
    }
}
