package com.example.greenlight.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlight.databinding.ChoiceQuestionAdminRecyclerItemBinding
import com.example.greenlight.databinding.ChoiceTestAdminRecyclerItemBinding
import com.example.greenlight.databinding.TestAdminRecyclerItemBinding
import com.example.greenlight.model.Question
import com.example.greenlight.model.Test

class ChoiceQuestionAdminAdapter(): RecyclerView.Adapter<ChoiceQuestionAdminAdapter.ViewHolder>() {

    private var questionList = ArrayList<Question>()
    var onItemClick : ((Question) -> Unit)? = null


    fun setQuestionList(questionList: List<Question>){
        this.questionList = questionList as ArrayList<Question>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ChoiceQuestionAdminRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceQuestionAdminAdapter.ViewHolder {
       return ViewHolder(
           ChoiceQuestionAdminRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: ChoiceQuestionAdminAdapter.ViewHolder, position: Int) {
       holder.binding.name.text = questionList[position].question.toString()
        holder.binding.choiceButton.setOnClickListener {
            onItemClick!!.invoke(questionList[position])
        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }
}