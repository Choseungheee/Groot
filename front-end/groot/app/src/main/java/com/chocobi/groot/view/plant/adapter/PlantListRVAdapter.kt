package com.chocobi.groot.view.plant.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chocobi.groot.R

class PlantListRVAdapter(val items: MutableList<String>) :
    RecyclerView.Adapter<PlantListRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        Log.d("PlantListRVAdapter", "onCreateViewHolder() $items")
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_plant_list_item, parent, false)

        return ViewHolder(view)
    }

    //    아이템 클릭 리스터 인터페이스
//        interface ItemClickListener {
//            fun onPostBtnClick(view: View, position: Int)
//            fun onScanBtnClick(view: View, position: Int)
//            fun onDetailBtnClick(view: View, position: Int)

//        private lateinit var postBtnClickListner: ItemClickListener
//        private lateinit var scanBtnClickListner: ItemClickListener
//        private lateinit var detailBtnClickListner: ItemClickListener
//        fun setItemClickListener(itemClickListener: ItemClickListener) {
//            this.postBtnClickListner = itemClickListener
//            this.scanBtnClickListner = itemClickListener
//            this.detailBtnClickListner = itemClickListener
//        }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    //    전체 리사이클러뷰의 개수
    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: String) {
            val rv_text = itemView.findViewById<TextView>(R.id.plant_name)
            rv_text.text = item
        }
    }

}
