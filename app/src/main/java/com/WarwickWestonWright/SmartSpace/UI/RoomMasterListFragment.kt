package com.WarwickWestonWright.SmartSpace.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.WarwickWestonWright.SmartSpace.DataObjects.RoomMasterObj
import com.WarwickWestonWright.SmartSpace.R

class RoomMasterListFragment : Fragment() {

    private var columnCount = 1
    private lateinit var roomMasterObjList: MutableList<RoomMasterObj>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null) {
            roomMasterObjList = mutableListOf()
            roomMasterObjList.addAll(arguments?.getParcelableArrayList<RoomMasterObj>("RoomMasterObjList")!!)
        }
        else {
            roomMasterObjList = mutableListOf()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.room_master_list_fragment, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = RoomMasterObjAdapter(roomMasterObjList, activity as RoomMasterObjAdapter.IRoomMasterObjAdapter)
            }
        }
        return view
    }
}