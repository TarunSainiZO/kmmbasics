package com.example.kmmbasic.model

import kotlinx.serialization.Serializable

@Serializable
data class MyTicket(val ticketId : Int, val refferalName : String, val referralimageUrl:String,
                    val refferedDate:String)
@Serializable
class MyticketList(val myTicket: List<MyTicket>)