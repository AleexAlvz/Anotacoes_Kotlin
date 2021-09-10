package br.com.alexalves.anotacoes_kotlin.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.model.Anotacao
import br.com.alexalves.anotacoes_kotlin.model.EnumStatus

class AnotacoesAdapter(
    private val anotacoes: List<Anotacao>,
    private val context: Context
    ) : RecyclerView.Adapter<AnotacoesAdapter.ViewHolderAnotacao>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAnotacao {
        val layout = LayoutInflater.from(context).inflate(R.layout.item_anotacoes, parent, false)
        return ViewHolderAnotacao(layout)
    }

    override fun onBindViewHolder(holder: ViewHolderAnotacao, position: Int) {
       holder.titulo.text = anotacoes[position].titulo
        holder.descricao.text = anotacoes[position].descricao
        setStatusImage(position, holder)
    }

    private fun setStatusImage(
        position: Int,
        holder: ViewHolderAnotacao
    ) {
        when (anotacoes[position].status) {
            EnumStatus.PENDENTE -> {
                holder.statusImage.setImageResource(R.color.yellow)
                holder.statusText.text = EnumStatus.PENDENTE.toString()
            }
            EnumStatus.CANCELADO -> {
                holder.statusImage.setImageResource(R.color.red)
                holder.statusText.text = EnumStatus.CANCELADO.toString()
            }
            EnumStatus.CONCLUIDO -> {
                holder.statusImage.setImageResource(R.color.green)
                holder.statusText.text = EnumStatus.CONCLUIDO.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return anotacoes.size
    }

    class ViewHolderAnotacao(itemView: View): RecyclerView.ViewHolder(itemView){
        val titulo = itemView.findViewById<TextView>(R.id.text_titulo_item)
        val descricao = itemView.findViewById<TextView>(R.id.text_descricao_item)
        val statusImage = itemView.findViewById<ImageView>(R.id.image_status_item)
        val statusText = itemView.findViewById<TextView>(R.id.image_text_status_item)
    }

}
