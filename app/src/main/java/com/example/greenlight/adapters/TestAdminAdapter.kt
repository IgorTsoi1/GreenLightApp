package com.example.greenlight.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlight.databinding.TestAdminRecyclerItemBinding
import com.example.greenlight.model.Test

class TestAdminAdapter(): RecyclerView.Adapter<TestAdminAdapter.ViewHolder>() {

    private var testList = ArrayList<Test>()
    var onUpdateClick : ((Test) -> Unit)? = null
    var onDeleteClick : ((Test) -> Unit)? = null

    fun setTestList(testList: List<Test>){
        this.testList = testList as ArrayList<Test>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: TestAdminRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdminAdapter.ViewHolder {
       return ViewHolder(
           TestAdminRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: TestAdminAdapter.ViewHolder, position: Int) {
       holder.binding.name.text = testList[position].testName.toString()
        holder.binding.editBtn.setOnClickListener {
            onUpdateClick!!.invoke(testList[position])
        }
        holder.binding.deleteBtn.setOnClickListener {
            onDeleteClick!!.invoke(testList[position])
        }
    }

    override fun getItemCount(): Int {
        return testList.size
    }
}