package com.WarwickWestonWright.SmartSpace.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.WarwickWestonWright.SmartSpace.DataObjects.RoomMasterObj
import com.WarwickWestonWright.SmartSpace.MainActivity
import com.WarwickWestonWright.SmartSpace.R
import com.WarwickWestonWright.SmartSpace.ViewModels.RoomMasterObjViewModel
import java.util.*

class MasterFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var lytMasterContainer: LinearLayout
    private lateinit var roomMasterFragment: RoomMasterListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.roomMasterObjViewModel = ViewModelProvider(this).get(RoomMasterObjViewModel::class.java)
        //arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.master_fragment, container, false)
        lytMasterContainer = rootView.findViewById(R.id.lytMasterContainer)

        MainActivity.roomMasterObjViewModel.selected.observe(viewLifecycleOwner, Observer<MutableList<RoomMasterObj>> { roomMasterObjList ->
            val bundle = Bundle()
            lytMasterContainer.bringToFront()
            roomMasterFragment = RoomMasterListFragment()
            bundle.putParcelableArrayList("RoomMasterObjList", roomMasterObjList as ArrayList<out RoomMasterObj>)
            roomMasterFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()!!.replace(R.id.lytMasterContainer, roomMasterFragment, "RoomMasterListFragment").commit()
        })

        return rootView
    }
}