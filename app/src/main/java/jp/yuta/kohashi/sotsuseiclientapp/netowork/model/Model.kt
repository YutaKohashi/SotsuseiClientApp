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

}