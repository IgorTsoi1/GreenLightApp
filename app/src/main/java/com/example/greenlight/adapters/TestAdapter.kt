package com.example.greenlight.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlight.databinding.TestAdminRecyclerItemBinding
import com.example.greenlight.databinding.TestRecyclerItemBinding
import com.example.greenlight.model.Test

class TestAdapter(): RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private var testList = ArrayList<Test>()
    var onItemClick : ((Test) -> Unit)? = null

    fun setTestList(testList: List<Test>){
        this.testList = testList as ArrayList<Test>
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: TestRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapter.ViewHolder {
       return ViewHolder(
           TestRecyclerItemBinding.inflate(
               LayoutInflater.from(parent.context)
           )
       )
    }

    override fun onBindViewHolder(holder: TestAdapter.ViewHolder, position: Int) {
       holder.binding.testBtn.text = testList[position].testName.toString()
        holder.binding.testBtn.setOnClickListener {
            onItemClick!!.invoke(testList[position])
        }

    }

    override fun getItemCount(): Int {
        return testList.size
    }
}