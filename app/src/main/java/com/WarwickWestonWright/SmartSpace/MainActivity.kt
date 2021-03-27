package com.WarwickWestonWright.SmartSpace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.WarwickWestonWright.SmartSpace.App.App
import com.WarwickWestonWright.SmartSpace.DataObjects.RoomMasterObj
import com.WarwickWestonWright.SmartSpace.DeSerializers.DeSerializers
import com.WarwickWestonWright.SmartSpace.Repos.IRoomDetailRepo
import com.WarwickWestonWright.SmartSpace.Repos.IRoomMasterRepo
import com.WarwickWestonWright.SmartSpace.Repos.RoomDetailRepo
import com.WarwickWestonWright.SmartSpace.Repos.RoomMasterRepo
import com.WarwickWestonWright.SmartSpace.UI.DetailFragment
import com.WarwickWestonWright.SmartSpace.UI.MasterFragment
import com.WarwickWestonWright.SmartSpace.UI.RoomMasterObjAdapter
import com.WarwickWestonWright.SmartSpace.Utilities.BubbleSort
import com.WarwickWestonWright.SmartSpace.ViewModels.RoomMasterObjViewModel

class MainActivity : AppCompatActivity(),
        IRoomMasterRepo,
        IRoomDetailRepo,
        RoomMasterObjAdapter.IRoomMasterObjAdapter {

    private lateinit var masterFragment: MasterFragment
    private lateinit var roomMasterRepo: RoomMasterRepo
    private lateinit var detailFragment: DetailFragment
    private lateinit var roomDetailRepo: RoomDetailRepo

    private lateinit var roomMasterObjList: MutableList<RoomMasterObj>
    private val app = App.getApp() as App
    private var deSerializers = DeSerializers()
    private var bubbleSort = BubbleSort()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            masterFragment = MasterFragment()
            supportFragmentManager.beginTransaction().replace(R.id.mainActivityFragmentContainer, masterFragment, "MasterFragment").commit()
        }

        roomMasterRepo = RoomMasterRepo(this)
        roomMasterRepo.getRoomsMasterList()
        roomMasterObjViewModel = ViewModelProvider(this).get(RoomMasterObjViewModel::class.java)
    }

    override fun roomMasterRepoCallBack(roomMasterJson: String) {
        if(roomMasterJson != "") {
            roomMasterObjList = deSerializers.deserializeRoomMasterObj(roomMasterJson)
            roomMasterObjList = bubbleSort.ascending(roomMasterObjList)
            roomMasterObjViewModel.selected.value = roomMasterObjList
        }
    }

    companion object {
        lateinit var roomMasterObjViewModel: RoomMasterObjViewModel
    }

    override fun roomMasterObjAdapterCallBack(roomMasterObj: RoomMasterObj) {
        roomDetailRepo = RoomDetailRepo(this)
        roomDetailRepo.getRoomsDetail(roomMasterObj.getKey()!!)
    }

    override fun roomDetailRepoCallBack(roomDetailJson: String) {
        val bundle = Bundle()
        detailFragment = DetailFragment()
        bundle.putString("roomDetailJson", roomDetailJson)
        detailFragment.arguments = bundle
        detailFragment.isCancelable = true
        detailFragment.show(supportFragmentManager, "DetailFragment")
        //Toast.makeText(this, roomDetailJson, Toast.LENGTH_LONG).show()
    }
}