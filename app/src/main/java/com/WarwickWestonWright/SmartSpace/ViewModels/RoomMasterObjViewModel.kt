package com.WarwickWestonWright.SmartSpace.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.WarwickWestonWright.SmartSpace.DataObjects.RoomMasterObj

class RoomMasterObjViewModel: ViewModel() {
    val selected = MutableLiveData<MutableList<RoomMasterObj>>()
    fun select(roomMasterObjList: MutableList<RoomMasterObj>) {
        selected.value = roomMasterObjList
    }
}