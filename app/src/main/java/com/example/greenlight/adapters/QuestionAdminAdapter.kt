package com.example.greenlight.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlight.databinding.QuestionAdminRecyclerItemBinding
import com.example.greenlight.databinding.TestAdminRecyclerItemBinding
import com.example.greenlight.model.Question
import com.example.greenlight.model.Test

class QuestionAdminAdapter(): RecyclerView.Adapter<QuestionAdminAdapter.ViewHolder>() {

    private var questionList = ArrayList<Question>()
    var onUpdateClick : ((Question) -> Unit)? = null
    var onDeleteClick : ((Question) -> Unit)? = null

    fun setQuestionList(questionList: List<Question>){
        this.questionList = questionList as ArrayList<Question>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: QuestionAdminRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAdminAdapter.ViewHolder {
       return ViewHolder(
           QuestionAdminRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: QuestionAdminAdapter.ViewHolder, position: Int) {
       holder.binding.name.text = questionList[position].question.toString()
        holder.binding.editBtn.setOnClickListener {
            onUpdateClick!!.invoke(questionList[position])
        }
        holder.binding.deleteBtn.setOnClickListener {
            onDeleteClick!!.invoke(questionList[position])
        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }
}