package com.demo.videochat.app

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyVideoAdapter(private var items: ArrayList<Feature>): RecyclerView.Adapter<MyVideoAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(col: View) : RecyclerView.ViewHolder(col) {
        var txtName: TextView? = null
        var txtComment: TextView? = null

        init {
            this.txtName = col.findViewById(R.id.txtName)
            this.txtComment = col.findViewById(R.id.txtComment)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoItem = items[position]
        holder.txtName?.text = videoItem.properties.title
        holder.txtComment?.text = Html.fromHtml(videoItem.properties.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_video_item, parent, false)

        return ViewHolder(itemView)
    }
}