package com.WarwickWestonWright.SmartSpace.Utilities

import com.WarwickWestonWright.SmartSpace.DataObjects.RoomMasterObj
import java.util.*

class BubbleSort {

    fun ascending(roomMasterObjList: MutableList<RoomMasterObj>) : MutableList<RoomMasterObj> {
        val sortableItemsSize = roomMasterObjList.size
        if(sortableItemsSize < 1) {
            return roomMasterObjList
        }
        var swap: Boolean
        var n: Int = sortableItemsSize - 1
        do {
            swap = false
            for (i in 0 until n) {
                //> for ascending < for descending
                if(roomMasterObjList[i].getName()!! > roomMasterObjList[i + 1].getName()!!) {
                    Collections.swap(roomMasterObjList, i, i + 1)
                    swap = true
                }
            }
            n--
        } while (swap)
        return roomMasterObjList
    }

    fun descending(roomMasterObjList: MutableList<RoomMasterObj>) : MutableList<RoomMasterObj> {
        val sortableItemsSize = roomMasterObjList.size
        if(sortableItemsSize < 1) {
            return roomMasterObjList
        }
        var swap: Boolean
        var n: Int = sortableItemsSize - 1
        do {
            swap = false
            for (i in 0 until n) {
                //> for ascending < for descending
                if(roomMasterObjList[i].getName()!! < roomMasterObjList[i + 1].getName()!!) {
                    Collections.swap(roomMasterObjList, i, i + 1)
                    swap = true
                }
            }
            n--
        } while (swap)
        return roomMasterObjList
    }
}