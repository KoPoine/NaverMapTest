package com.neppplus.navermaptest

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.navermaptest.Models.PlaceData
import com.neppplus.navermaptest.databinding.ListItemPlaceBinding

class PlaceRecyclerViewAdapter(
    val mContext : Context,
    val mList : List<PlaceData>
) : RecyclerView.Adapter<PlaceRecyclerViewAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding : ListItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item : PlaceData) {

            binding.titleTxt.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                Html.fromHtml(item.title).toString()    }

            binding.addressTxt.text = item.roadAddress

            binding.showMapBtn.setOnClickListener {
                val myIntent = Intent(mContext, MapActivity::class.java)
                myIntent.putExtra("placeData", item)
                mContext.startActivity(myIntent)
            }
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