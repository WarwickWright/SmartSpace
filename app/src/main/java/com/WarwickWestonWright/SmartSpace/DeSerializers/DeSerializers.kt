package com.WarwickWestonWright.SmartSpace.DeSerializers

import com.WarwickWestonWright.SmartSpace.DataObjects.EquipmentDetailObj
import com.WarwickWestonWright.SmartSpace.DataObjects.RoomMasterObj
import org.json.JSONArray
import org.json.JSONObject

class DeSerializers {

    fun deserializeRoomMasterObj(jsonStr: String?) : MutableList<RoomMasterObj> {
        val returnVal = mutableListOf<RoomMasterObj>()
        val jsa = JSONArray(jsonStr)
        val jsaLen = jsa.length()
        for (i in 0 until jsaLen) {
            val item = jsa.getJSONObject(i)
            val rmo = RoomMasterObj(
                item.getString("key"),
                item.getString("name"),
                item.getString("thumbnailUrl"),
                item.getInt("capacity")
            )
            rmo.getThumbnailUrl()
            returnVal.add(rmo)
        }
        return returnVal
    }

    fun deserializeRoomRootDetail(jsonStr: String?) : MutableList<NameValuePair> {
        val jso = JSONObject(jsonStr)
        val returnVal = mutableListOf<NameValuePair>()
        returnVal.add(NameValuePair("key", jso.getString("key")))
        returnVal.add(NameValuePair("name", jso.getString("name")))
        returnVal.add(NameValuePair("heroImageUrl", jso.getString("heroImageUrl")))
        returnVal.add(NameValuePair("capacity", jso.getInt("capacity").toString()))
        return returnVal
    }

    fun deserializeLocationDetail(jsonStr: String?) : MutableList<NameValuePair> {
        val jso = JSONObject(jsonStr).getJSONObject("location")
        val returnVal = mutableListOf<NameValuePair>()
        returnVal.add(NameValuePair("region_key", jso.getJSONObject("region").getString("key")))
        returnVal.add(NameValuePair("region_name", jso.getJSONObject("region").getString("name")))
        returnVal.add(NameValuePair("site_key", jso.getJSONObject("site").getString("key")))
        returnVal.add(NameValuePair("site_name", jso.getJSONObject("site").getString("name")))
        returnVal.add(NameValuePair("building_key", jso.getJSONObject("building").getString("key")))
        returnVal.add(NameValuePair("building_name", jso.getJSONObject("building").getString("name")))
        returnVal.add(NameValuePair("floor_key", jso.getJSONObject("floor").getString("key")))
        returnVal.add(NameValuePair("floor_name", jso.getJSONObject("floor").getString("name")))
        return returnVal
    }

    fun deserializeEquipmentDetail(jsonStr: String?) : MutableList<EquipmentDetailObj> {
        val jso = JSONObject(jsonStr)
        val jsa = jso.getJSONArray("equipment")
        val returnVal = mutableListOf<EquipmentDetailObj>()
        val jsaLen = jsa.length()
        for (i in 0 until jsaLen) {
            val item = jsa.getJSONObject(i)
            returnVal.add(EquipmentDetailObj( item.getString("key"), item.getString("name"), item.getString("iconUrl")))
        }
        return returnVal
    }

/*
  "equipment":[
    {
      "key":"711",
      "name":"Cisco Monitor",
      "iconUrl":"https://www.smartspaceglobal.com/img/icons/CiscoDisplay.png"
    },
    {
      "key":"712",
      "name":"Conference Calling",
      "iconUrl":"https://www.smartspaceglobal.com/img/icons/VideoConference.png"
    },
    {
      "key":"713",
      "name":"Printer",
      "iconUrl":"https://www.smartspaceglobal.com/img/icons/Printer.png"
    },
    {
      "key":"714",
      "name":"Projector",
      "iconUrl":"https://www.smartspaceglobal.com/img/icons/Projector.png"
    }
  ]
* */
}