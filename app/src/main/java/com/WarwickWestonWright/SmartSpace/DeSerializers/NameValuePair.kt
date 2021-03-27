package com.WarwickWestonWright.SmartSpace.DeSerializers

class NameValuePair {
    /* Accessors *//* Mutators */
    var name: String
    var value: String

    constructor() {
        name = ""
        value = ""
    }

    constructor(name: String, value: String) {
        this.name = name
        this.value = value
    }

    fun getValueFromName(name: String, nameValuePairsList: MutableList<NameValuePair>) : String {
        for (nameValuePair in nameValuePairsList) {
            if(nameValuePair.name == name) {
                return nameValuePair.value
            }
        }
        return ""
    }

}