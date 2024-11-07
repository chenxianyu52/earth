package com.xianyu.earthquake.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xianyu.earthquake.R
import com.xianyu.earthquake.data.EarthShowData


class ItemAdapter(
    private val items: List<EarthShowData?>,
    private val itemClick: OnItemClickListener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position] ?: return
        item.apply {
            holder.title.text = place
            holder.mag.text = mag.toString()
            holder.mag.setTextColor(color)
            holder.time.text = time
        }
        holder.itemView.setOnClickListener {
            itemClick.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val mag: TextView = itemView.findViewById(R.id.mag)
        val time: TextView = itemView.findViewById(R.id.time)
    }

    // 定义点击回调接口
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}