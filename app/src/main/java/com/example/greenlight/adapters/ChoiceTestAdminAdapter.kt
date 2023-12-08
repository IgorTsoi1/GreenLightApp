package com.example.greenlight.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlight.databinding.ChoiceTestAdminRecyclerItemBinding
import com.example.greenlight.databinding.TestAdminRecyclerItemBinding
import com.example.greenlight.model.Test

class ChoiceTestAdminAdapter(): RecyclerView.Adapter<ChoiceTestAdminAdapter.ViewHolder>() {

    private var testList = ArrayList<Test>()
    var onItemClick : ((Test) -> Unit)? = null


    fun setTestList(testList: List<Test>){
        this.testList = testList as ArrayList<Test>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ChoiceTestAdminRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceTestAdminAdapter.ViewHolder {
       return ViewHolder(
           ChoiceTestAdminRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: ChoiceTestAdminAdapter.ViewHolder, position: Int) {
       holder.binding.name.text = testList[position].testName.toString()
        holder.binding.choiceButton.setOnClickListener {
            onItemClick!!.invoke(testList[position])
        }
    }

    override fun getItemCount(): Int {
        return testList.size
    }
}