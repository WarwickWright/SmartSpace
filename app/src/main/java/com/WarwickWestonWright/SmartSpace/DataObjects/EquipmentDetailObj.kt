package com.WarwickWestonWright.SmartSpace.DataObjects

class EquipmentDetailObj {

    private var key: String? = ""
    private var name: String? = ""
    private var iconUrl: String? = ""

    constructor(key: String?, name: String?, iconUrl: String?) {
        this.key = key
        this.name = name
        this.iconUrl = iconUrl
    }

    //Accessors
    fun getKey(): String? { return this.key }
    fun getName(): String? { return this.name }
    fun getIconUrl(): String? { return this.iconUrl }

    //Mutators
    fun setKey(key: String?) { this.key = key }
    fun setName(name: String?) { this.name = name }
    fun setIconUrl(iconUrl: String?) { this.iconUrl = iconUrl }
}