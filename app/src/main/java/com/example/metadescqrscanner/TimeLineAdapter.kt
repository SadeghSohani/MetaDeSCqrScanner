package com.example.metadescqrscanner

import android.graphics.Color
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by paetztm on 2/6/2017.
 */
class TimeLineAdapter(private val layoutInflater: LayoutInflater,
                      private val timeLineList: List<TimeLineRowData>,
                      @param:LayoutRes private val rowLayout: Int) : RecyclerView.Adapter<TimeLineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = layoutInflater.inflate(rowLayout,
            parent,
            false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = timeLineList[position]
        when(timeLineList[position].metadata) {
            "CreateAsset" -> {
                holder.header.setBackgroundColor(Color.parseColor("#FFB300"))
                holder.headerTitle.text = "Asset created"
                holder.headerIcon.setImageResource(R.drawable.ic_create)
            }
            "PutAttr" -> {
                holder.header.setBackgroundColor(Color.parseColor("#D81B60"))
                holder.headerTitle.text = "Put attribute"
                holder.headerIcon.setImageResource(R.drawable.ic_put_gi)
            }
            "ChangeOwner" -> {
//                holder.header.setBackgroundColor(Color.parseColor("#D81B60"))
                holder.headerTitle.text = "Owner changed"
                holder.headerIcon.setImageResource(R.drawable.ic_change_owner)
            }
            "SellAsset" -> {
                holder.header.setBackgroundColor(Color.parseColor("#F4511E"))
                holder.headerTitle.text = "Asset bought"
                holder.headerIcon.setImageResource(R.drawable.ic_buy)
            }
            "ChangeStatus" -> {
                holder.header.setBackgroundColor(Color.parseColor("#AD1457"))
                holder.headerTitle.text = "Status changed"
                holder.headerIcon.setImageResource(R.drawable.ic_change_status)
            }
            "SendToShop" -> {
                holder.header.setBackgroundColor(Color.parseColor("#FB8C00"))
                holder.headerTitle.text = "Sent to shop"
                holder.headerIcon.setImageResource(R.drawable.ic_make_public)
            }
        }
        holder.fullName.text = item.instructions
    }

    override fun getItemCount(): Int = timeLineList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fullName: TextView = view.findViewById<View>(R.id.full_name_tv) as TextView
        val header: LinearLayout = view.findViewById<View>(R.id.layout_header) as LinearLayout
        val headerTitle: TextView = view.findViewById<View>(R.id.tv_headerTitle) as TextView
        val headerIcon: ImageView = view.findViewById<View>(R.id.img_headerIcon) as ImageView
    }
}