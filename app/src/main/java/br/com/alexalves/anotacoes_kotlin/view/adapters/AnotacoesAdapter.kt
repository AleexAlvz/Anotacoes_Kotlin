package br.com.alexalves.anotacoes_kotlin.view.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alexalves.anotacoes_kotlin.R
import br.com.alexalves.anotacoes_kotlin.model.Anotacao
import br.com.alexalves.anotacoes_kotlin.model.EnumStatus
import br.com.alexalves.anotacoes_kotlin.model.Usuario

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
            EnumStatus.PENDENTE -> holder.imageView.setImageResource(R.color.yellow)
            EnumStatus.CANCELADO -> holder.imageView.setImageResource(R.color.red)
            EnumStatus.CONCLUIDO -> holder.imageView.setImageResource(R.color.green)
        }
    }

    override fun getItemCount(): Int {
        return anotacoes.size
    }

    class ViewHolderAnotacao(itemView: View): RecyclerView.ViewHolder(itemView){
        val titulo = itemView.findViewById<TextView>(R.id.text_titulo_item)
        val descricao = itemView.findViewById<TextView>(R.id.text_descricao_item)
        val imageView = itemView.findViewById<ImageView>(R.id.image_status_item)
    }

}
