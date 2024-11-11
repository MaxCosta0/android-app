import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.imepac.Exercicio

class ExerciciosAdapter(
    private val exercicios: List<Exercicio>
) : RecyclerView.Adapter<ExerciciosAdapter.ExercicioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercicioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return ExercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExercicioViewHolder, position: Int) {
        val exercicio = exercicios[position]
        holder.bind(exercicio)
    }

    override fun getItemCount(): Int = exercicios.size

    inner class ExercicioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvExercicioNome: TextView = view.findViewById(android.R.id.text1)
        private val tvExercicioDetalhes: TextView = view.findViewById(android.R.id.text2)

        fun bind(exercicio: Exercicio) {
            tvExercicioNome.text = exercicio.nome
            tvExercicioDetalhes.text = exercicio.series
        }
    }
}
