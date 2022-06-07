package com.neppplus.navermaptest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.navermaptest.databinding.ListItemPlaceBinding

class PlaceRecyclerViewAdapter(
    val mContext : Context,
    val mList : List<PlaceData>
) : RecyclerView.Adapter<PlaceRecyclerViewAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding : ListItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item : PlaceData) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListItemPlaceBinding.inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}