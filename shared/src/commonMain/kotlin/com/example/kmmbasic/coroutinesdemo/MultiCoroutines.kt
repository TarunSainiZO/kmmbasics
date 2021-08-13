package com.example.kmmbasic.coroutinesdemo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MultiCoroutines {
    private val scope = CoroutineScope(Dispatchers.Default)

    suspend fun run(message: String, time: Long, throwException: Boolean = false) {
            delay(time)
            println("for learning $message")
        if(throwException){
            throw Exception(message)
        }
    }
}