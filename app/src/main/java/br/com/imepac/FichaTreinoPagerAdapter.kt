import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.imepac.GrupoMuscular
import br.com.imepac.R

class FichaTreinoPagerAdapter(
    private val treinos: List<GrupoMuscular>
) : RecyclerView.Adapter<FichaTreinoPagerAdapter.FichaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FichaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ficha_treino, parent, false)
        return FichaViewHolder(view)
    }

    override fun onBindViewHolder(holder: FichaViewHolder, position: Int) {
        val grupoMuscular = treinos[position]
        holder.bind(grupoMuscular)
    }

    override fun getItemCount(): Int = treinos.size

    inner class FichaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvGrupoMuscular: TextView = view.findViewById(R.id.tvGrupoMuscular)
        private val recyclerViewExercicios: RecyclerView = view.findViewById(R.id.recyclerViewExercicios)

        fun bind(grupoMuscular: GrupoMuscular) {
            tvGrupoMuscular.text = grupoMuscular.nome
            recyclerViewExercicios.layoutManager = LinearLayoutManager(itemView.context)
            recyclerViewExercicios.adapter = ExerciciosAdapter(grupoMuscular.exercicios)
        }
    }
}
