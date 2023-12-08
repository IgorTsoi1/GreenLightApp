package com.example.greenlight.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlight.databinding.AnswerAdminRecyclerItemBinding
import com.example.greenlight.databinding.TestAdminRecyclerItemBinding
import com.example.greenlight.model.Answer
import com.example.greenlight.model.Test

class AnswerAdminAdapter(): RecyclerView.Adapter<AnswerAdminAdapter.ViewHolder>() {

    private var answerList = ArrayList<Answer>()
    var onUpdateClick : ((Answer) -> Unit)? = null
    var onDeleteClick : ((Answer) -> Unit)? = null

    fun setAnswerList(answerList: List<Answer>){
        this.answerList = answerList as ArrayList<Answer>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: AnswerAdminRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerAdminAdapter.ViewHolder {
       return ViewHolder(
           AnswerAdminRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: AnswerAdminAdapter.ViewHolder, position: Int) {
       holder.binding.name.text = answerList[position].answer.toString()
        holder.binding.editBtn.setOnClickListener {
            onUpdateClick!!.invoke(answerList[position])
        }
        holder.binding.deleteBtn.setOnClickListener {
            onDeleteClick!!.invoke(answerList[position])
        }
    }

    override fun getItemCount(): Int {
        return answerList.size
    }
}