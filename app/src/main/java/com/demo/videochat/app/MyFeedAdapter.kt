package com.demo.videochat.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyFeedAdapter(private var items: List<FeedItemModel>): RecyclerView.Adapter<MyFeedAdapter.ViewHolder>() {

    var onItemClick: ((FeedItemModel) -> Unit)? = null
    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(col: View) : RecyclerView.ViewHolder(col) {
        var txtName: TextView? = null
        var txtLive: TextView? = null
        var txtStopLive: TextView? = null

        init {
            this.txtName = col.findViewById(R.id.txtName)
            this.txtLive = col.findViewById(R.id.txtLive)
            this.txtStopLive = col.findViewById(R.id.txtStopLive)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedItem = items[position]

        val name = feedItem.name + " - " + feedItem.created
        holder.txtName?.text = name

        if(feedItem.live == 0) {
            holder.txtLive?.visibility = View.GONE
            holder.txtStopLive?.visibility = View.GONE
        }else {
            holder.txtLive?.visibility = View.VISIBLE
            holder.txtStopLive?.visibility = View.VISIBLE
        }

        holder.txtStopLive?.setOnClickListener {
            onItemClick?.invoke(items[position])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_feed_item, parent, false)

        return ViewHolder(itemView)
    }
}