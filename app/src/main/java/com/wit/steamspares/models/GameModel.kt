package com.wit.steamspares.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

@Parcelize
data class GameModel(var appid : Int, var name: String = "Missing", var code : String, var status : Boolean, var notes : String? = null, var url : String? = "", var bannerUrl : String? = "") : Parcelable, AnkoLogger{
    //Game code will always be unique so we can use that as internal storage id
    var id = code.hashCode()

    init{
        url = "https://store.steampowered.com/app/$appid"
        bannerUrl = "https://cdn.cloudflare.steamstatic.com/steam/apps/${appid}/header.jpg"
        name = if (name.isEmpty()) "Name not given" else name
        code = if (code.isEmpty()) "Code not given" else code

        info { "Superdebug: Created game ${toString()}" }
    }

    override fun toString(): String {
        return "ID: $id, Appid: $appid, Name: $name, Code: $code, Status: $status, Notes: $notes, URL: $url, Banner: $bannerUrl"
    }
}