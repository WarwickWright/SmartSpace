package com.WarwickWestonWright.SmartSpace.Repos

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.WarwickWestonWright.SmartSpace.App.App
import com.WarwickWestonWright.SmartSpace.Comms.NetUtils
import com.WarwickWestonWright.SmartSpace.Comms.OnlineData
import com.WarwickWestonWright.SmartSpace.Constants.END_POINT
import com.WarwickWestonWright.SmartSpace.Constants.ROOMS_DETAIL
import com.WarwickWestonWright.SmartSpace.Constants.RPC_ACTION_GET_ROOMS_DETAIL
import java.net.URL

class RoomDetailRepo(private var callingActivity: AppCompatActivity) : NetUtils.IConnectionStatus {

    private val app = App.getApp() as App
    private var onlineData = OnlineData(callingActivity)
    private var netUtils = NetUtils(this, callingActivity)
    private var roomKey = ""

    fun getRoomsDetail(roomKey: String) {
        this.roomKey = roomKey
        netUtils.setIsNetworkAvailable()
    }

    override fun netStatusSet(isConnected: Boolean) {
        if(isConnected) {
            app.setRpcAction(RPC_ACTION_GET_ROOMS_DETAIL)
            getRoomsDetailData()
        }
        else {
            Toast.makeText(callingActivity, "No Net", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRoomsDetailData() {
        val detail = ROOMS_DETAIL.replace("<%room_key%>", roomKey, true)
        onlineData.getHttpsDataAsString(URL("$END_POINT$detail"), "GET")
    }

}