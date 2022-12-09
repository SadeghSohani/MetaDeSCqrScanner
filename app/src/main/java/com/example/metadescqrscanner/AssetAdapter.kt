package com.example.metadescqrscanner

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView

/**
 * Created by paetztm on 2/6/2017.
 */
class AssetAdapter(private val layoutInflater: LayoutInflater,
                   private val assets: List<Asset>,
                   @param:LayoutRes private val rowLayout: Int,
                   private val jwt: String,
                   private val pref:SharedPreferences
) : RecyclerView.Adapter<AssetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = layoutInflater.inflate(rowLayout,
            parent,
            false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = assets[position]
//        holder.type.text = item.type
        holder.type.text = "Oil"
        holder.date.text = pref.getString(item.id, "00/00/00")
        holder.item.setOnClickListener {
            val intent = Intent(layoutInflater.context, HistoryTimeLineActivity::class.java)
            intent.putExtra("jwt", jwt)
            intent.putExtra("id", item.id)
            intent.putExtra("FromList", true)
            layoutInflater.context.startActivity(intent)
        }
        Log.i("Adapter", item.toString())
    }

    override fun getItemCount(): Int = assets.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val type: TextView = view.findViewById<View>(R.id.tv_type) as TextView
        val date: TextView = view.findViewById<View>(R.id.tv_date) as TextView
        val item: CardView = view.findViewById<View>(R.id.chicken_item) as CardView
    }
}