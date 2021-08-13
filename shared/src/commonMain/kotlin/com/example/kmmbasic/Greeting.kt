package com.example.kmmbasic

import com.example.kmmbasic.model.MyTicket
import com.example.kmmbasic.model.MyticketList
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*


class Greeting {
    private val httpClient = HttpClient(){
        install(Logging){
            level = LogLevel.ALL
            logger = object : Logger{
                override fun log(message: String) {
                    logMessage(message)
                }
            }
        }
        install(JsonFeature){
            val json = kotlinx.serialization.json.Json{ ignoreUnknownKeys = true}
            serializer = KotlinxSerializer(json)
        }
    }

    @Throws(Exception::class)
    suspend fun greeting(): MyTicket {
        return getData().myTicket.random()
    }
    private suspend fun getData(): MyticketList {
        return  httpClient.get("https://my-json-server.typicode.com/cursed007/demo/myT"){
            parameter("name","Tarun")
            parameter("height",173)
        }
    }
}