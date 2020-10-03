package com.demo.videochat.app

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoFragment : Fragment() {

    lateinit var progressDialog: ProgressDialog
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyVideoAdapter

    var dataList = ArrayList<Feature>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_video, container, false)

        recyclerView = view.findViewById(R.id.my_recycler_view)

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        recyclerView.adapter = MyVideoAdapter(dataList)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        getData()
        return view
    }

    private fun getData() {
        val call: Call<VideoItemModel> = ApiClient.getClient.getPhotos()
        call.enqueue(object : Callback<VideoItemModel> {

            override fun onResponse(
                call: Call<VideoItemModel>?,
                response: Response<VideoItemModel>?
            ) {
                progressDialog.dismiss()

                val videoItemModel: VideoItemModel = response!!.body()!!

                dataList.addAll(videoItemModel.features)
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<VideoItemModel>?, t: Throwable?) {
                progressDialog.dismiss()
            }

        })
    }

}