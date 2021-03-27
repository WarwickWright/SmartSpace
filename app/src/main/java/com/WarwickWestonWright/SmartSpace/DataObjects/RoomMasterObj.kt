package com.WarwickWestonWright.SmartSpace.DataObjects

import android.os.Parcel
import android.os.Parcelable

class RoomMasterObj(): Parcelable {
    private var key: String? = ""
    private var name: String? = ""
    private var thumbnailUrl: String? = ""
    private var capacity: Int? = 0

    constructor(key: String?, name: String?, thumbnailUrl: String?, capacity: Int?) : this() {
        this.key = key
        this.name = name
        this.thumbnailUrl = thumbnailUrl
        this.capacity = capacity
    }

    //Accessors
    fun getKey(): String? { return this.key }
    fun getName(): String? { return this.name }
    fun getThumbnailUrl(): String? { return this.thumbnailUrl }
    fun getCapacity(): Int? { return this.capacity }

    //Mutators
    fun setKey(key: String?) { this.key = key }
    fun setName(name: String?) { this.name = name }
    fun setThumbnailUrl(thumbnailUrl: String?) { this.thumbnailUrl = thumbnailUrl }
    fun setCapacity(capacity: Int?) { this.capacity = capacity }

    //IDE Generated Code for Parcelable implementation
    constructor(parcel: Parcel) : this() {
        key = parcel.readString()
        name = parcel.readString()
        thumbnailUrl = parcel.readString()
        capacity = parcel.readValue(Int::class.java.classLoader) as? Int
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeString(thumbnailUrl)
        parcel.writeValue(capacity)
    }
    override fun describeContents(): Int { return 0 }
    companion object CREATOR : Parcelable.Creator<RoomMasterObj> {
        override fun createFromParcel(parcel: Parcel): RoomMasterObj { return RoomMasterObj(parcel) }
        override fun newArray(size: Int): Array<RoomMasterObj?> { return arrayOfNulls(size) }
    }
}