package com.WarwickWestonWright.SmartSpace.Repos

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.WarwickWestonWright.SmartSpace.App.App
import com.WarwickWestonWright.SmartSpace.Comms.NetUtils
import com.WarwickWestonWright.SmartSpace.Comms.OnlineData
import com.WarwickWestonWright.SmartSpace.Constants.END_POINT
import com.WarwickWestonWright.SmartSpace.Constants.ROOMS_MASTER
import com.WarwickWestonWright.SmartSpace.Constants.RPC_ACTION_GET_ROOMS_MASTER
import java.net.URL

class RoomMasterRepo(private var callingActivity: AppCompatActivity) : NetUtils.IConnectionStatus {

    private val app = App.getApp() as App
    private var onlineData = OnlineData(callingActivity)
    private var netUtils = NetUtils(this, callingActivity)

    fun getRoomsMasterList() {
        netUtils.setIsNetworkAvailable()
    }

    override fun netStatusSet(isConnected: Boolean) {
        if(isConnected) {
            app.setRpcAction(RPC_ACTION_GET_ROOMS_MASTER)
            getRoomsMasterData()
        }
        else {
            Toast.makeText(callingActivity, "No Net", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRoomsMasterData() {
        onlineData.getHttpsDataAsString(URL("$END_POINT$ROOMS_MASTER"), "GET")
    }
}