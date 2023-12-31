package com.example.greenlight.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlight.databinding.RadiobuttonRecyclerItemBinding
import com.example.greenlight.model.Answer

class RadioButtonAnswerAdapter: RecyclerView.Adapter<RadioButtonAnswerAdapter.ViewHolder>() {

    private var answerList = ArrayList<Answer>()
    var onItemClick : ((Answer) -> Unit)? = null
    private var lastCheckedPosition = -1

    fun setAnswerList(answerList: List<Answer>){
        this.answerList = answerList as ArrayList<Answer>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RadiobuttonRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RadioButtonAnswerAdapter.ViewHolder {
        return ViewHolder(
            RadiobuttonRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: RadioButtonAnswerAdapter.ViewHolder, position: Int) {

        holder.binding.radioButton.text = answerList[position].answer

        holder.binding.radioGroup.setOnCheckedChangeListener(null)

        holder.binding.radioGroup.clearCheck()

        holder.binding.radioButton.isChecked = position == lastCheckedPosition

        holder.binding.radioButton.setOnClickListener {
            lastCheckedPosition = holder.adapterPosition
            notifyItemRangeChanged(0,answerList.size)
            onItemClick!!.invoke(answerList[position])
        }

    }

    override fun getItemCount(): Int {
        return answerList.size
    }
}