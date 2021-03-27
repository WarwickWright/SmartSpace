package com.WarwickWestonWright.SmartSpace.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.WarwickWestonWright.SmartSpace.DataObjects.RoomMasterObj
import com.WarwickWestonWright.SmartSpace.R
import com.squareup.picasso.Picasso

class RoomMasterObjAdapter(private val values: MutableList<RoomMasterObj>, private val iRoomMasterObjAdapter : IRoomMasterObjAdapter) :
        RecyclerView.Adapter<RoomMasterObjAdapter.ViewHolder>(),
        View.OnClickListener {

    interface IRoomMasterObjAdapter {
        fun roomMasterObjAdapterCallBack(roomMasterObj : RoomMasterObj)
    }

    override fun onClick(v: View?) {
        if(v is LinearLayout) {
            iRoomMasterObjAdapter.roomMasterObjAdapterCallBack(v.tag as RoomMasterObj)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_master_item, parent, false)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemView.tag = item
        holder.lbKey.text = item.getKey()
        holder.lblFriendlyName.text = item.getName()
        holder.lblCapacity.text = item.getCapacity().toString()
        Picasso.get().load(item.getThumbnailUrl()).into(holder.imgThumb)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lbKey: TextView = view.findViewById(R.id.lbKey)
        val lblFriendlyName: TextView = view.findViewById(R.id.lblFriendlyName)
        val lblCapacity: TextView = view.findViewById(R.id.lblCapacity)
        val imgThumb: ImageView = view.findViewById(R.id.imgThumb)

        override fun toString(): String {
            return super.toString() + " '" + lbKey.text + "'"
        }
    }
}