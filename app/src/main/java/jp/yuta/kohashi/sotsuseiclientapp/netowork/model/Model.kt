package jp.yuta.kohashi.sotsuseiclientapp.netowork.model

/**
 * Author : yutakohashi
 * Project name : SotsuseiCameraApp
 * Date : 17 / 10 / 2017
 */
object Model{
    data class Result(val query: Query)
    data class Query(val searchinfo: SearchInfo)
    data class SearchInfo(val totalhits: Int)
    data class Employee(val eId:Int,val name:String)
    data class NumberPlate(val sid:Int, val humanid:String,val imageid:String,val shiyohonkyochi:String,val bunruibango:String,val jigyoyohanbetsumoji:String,val ichirenshiteibango:String,val cartype:Int,val colortype:Int,val makertype:Int, val comment:String, val datetime:String)
}