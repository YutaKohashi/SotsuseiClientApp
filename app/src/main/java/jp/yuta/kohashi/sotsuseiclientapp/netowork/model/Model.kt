package jp.yuta.kohashi.sotsuseiclientapp.netowork.model

import java.util.*

/**
 * Author : yutakohashi
 * Project name : SotsuseiCameraApp
 * Date : 17 / 10 / 2017
 */
object Model {
    data class StoreInfo(val sid: String, val gid: String, val sname: String, val ownername: String, val address: String, val latitude: Float, val longitude: Float)
    data class TokenResponse(val token: String)
    data class DefaultResponse(val res: String)
    data class Result(val query: Query)
    data class Query(val searchinfo: SearchInfo)
    data class SearchInfo(val totalhits: Int)
//    data class Employee(val eid: Int, val employeename: String,val datetime:Date)
    data class Employee(val eid:String, val employeename:String, val sid:String, val status:Int, val plebel:Int, val date:Date)
    data class NumberPlate(val eid:String, val sid: String, val humanid: String, val imageid: String, val shiyohonkyochi: String, val bunruibango: Int, val jigyoyohanbetsumoji: String, val ichirenshiteibango: Int, val cartype: Int, val colortype: Int, val makertype: Int, val comment: String, val datetime: Date)
}