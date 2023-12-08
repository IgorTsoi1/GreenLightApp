package com.example.greenlight.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greenlight.activities.TestMainActivity
import com.example.greenlight.adapters.TestAdapter
import com.example.greenlight.databinding.FragmentHomeBinding
import com.example.greenlight.datasource.ServiceBuilder
import com.example.greenlight.interfaces.Api
import com.example.greenlight.model.ApiResponse
import com.example.greenlight.model.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private  var testList = ArrayList<Test>()
    private lateinit var api: Api
    private lateinit var testAdapter: TestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = ServiceBuilder.buildService(Api::class.java)
        testAdapter = TestAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        getAllTests()

        testAdapter.onItemClick = {test->
            val intent = Intent(this.requireActivity(), TestMainActivity::class.java)
            intent.putExtra("TEST_ID",test.testId)
            startActivity(intent)
        }

    }

    private fun getAllTests() {
        api.getAllTests().enqueue(object: Callback<ApiResponse<Test>>{
            override fun onResponse(
                call: Call<ApiResponse<Test>>,
                response: Response<ApiResponse<Test>>
            ) {
                if(response.isSuccessful){
                    testList = response.body()!!.result as ArrayList<Test>
                    testAdapter.setTestList(testList)

                }
            }

            override fun onFailure(call: Call<ApiResponse<Test>>, t: Throwable) {
                Log.e("zxc1235",t.message.toString())
            }

        })
    }

    private fun prepareRecyclerView() {

        val myGridLayoutManager = object : GridLayoutManager(this.requireActivity(),2, VERTICAL,true){
            override fun canScrollVertically(): Boolean {
                return true
            }
        }

        binding.recyclerView.apply {
            layoutManager = myGridLayoutManager
            adapter = testAdapter
        }

    }
}