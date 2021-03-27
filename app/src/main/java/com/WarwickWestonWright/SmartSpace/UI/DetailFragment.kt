package com.WarwickWestonWright.SmartSpace.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.WarwickWestonWright.SmartSpace.DataObjects.EquipmentDetailObj
import com.WarwickWestonWright.SmartSpace.DeSerializers.DeSerializers
import com.WarwickWestonWright.SmartSpace.DeSerializers.NameValuePair
import com.WarwickWestonWright.SmartSpace.R
import com.squareup.picasso.Picasso

class DetailFragment : DialogFragment() {

    private lateinit var rootView: View
    private lateinit var lblName: TextView
    private lateinit var lblCapacityDetail: TextView
    private lateinit var lblLocationDetail: TextView
    private lateinit var lblEquipmentDetail: TextView
    private lateinit var imgHeroImageUrl: ImageView
    private var roomDetailJson = ""
    private var deSerializers = DeSerializers()
    private lateinit var rootObjectPairs: MutableList<NameValuePair>
    private lateinit var locationPairs: MutableList<NameValuePair>
    private lateinit var equipmentPairs: MutableList<EquipmentDetailObj>
    private val nvp = NameValuePair()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MY_DIALOG_STYLE)
        arguments?.let {
            roomDetailJson = it.getString("roomDetailJson", "")
            rootObjectPairs = deSerializers.deserializeRoomRootDetail(roomDetailJson)
            locationPairs = deSerializers.deserializeLocationDetail(roomDetailJson)
            equipmentPairs = deSerializers.deserializeEquipmentDetail(roomDetailJson)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.detail_fragment, container, false)
        //dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //Toast.makeText(requireActivity(), roomDetailJson, Toast.LENGTH_LONG).show()
        lblName = rootView.findViewById(R.id.lblName)
        imgHeroImageUrl = rootView.findViewById(R.id.imgHeroImageUrl)
        lblCapacityDetail = rootView.findViewById(R.id.lblCapacityDetail)
        lblLocationDetail = rootView.findViewById(R.id.lblLocationDetail)
        lblEquipmentDetail = rootView.findViewById(R.id.lblEquipmentDetail)
        lblName.text = nvp.getValueFromName("name", rootObjectPairs)
        Picasso.get().load(nvp.getValueFromName("heroImageUrl", rootObjectPairs)).into(imgHeroImageUrl)
        lblCapacityDetail.text = "Capacity = " + nvp.getValueFromName("capacity", rootObjectPairs)
        lblLocationDetail.text = "Location = " + nvp.getValueFromName("region_name", locationPairs)
        lblEquipmentDetail.text = buildEquipmentString(equipmentPairs)

        return rootView
    }

    private fun buildEquipmentString(equipmentPairs: MutableList<EquipmentDetailObj>) : String {
        var returnVal = "Equipment:\n"
        for (equipmentDetailObj in equipmentPairs) {
            returnVal += equipmentDetailObj.getName() + "\n"
        }
        return if(returnVal != "Equipment:\n") {
            returnVal.trim()
        } else {
            "Equipment: None"
        }
    }
}