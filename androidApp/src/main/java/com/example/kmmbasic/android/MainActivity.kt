package com.example.kmmbasic.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.kmmbasic.Greeting
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.example.kmmbasic.coroutinesdemo.MultiCoroutines
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private val greeting = Greeting()
    private val mainScope = MainScope()
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        println("for learning ${e.localizedMessage}")
    }
    private val ioScope = CoroutineScope(Dispatchers.IO + exceptionHandler)
    private val name: TextView
        get() = findViewById(R.id.refferalName)
    private val date: TextView
        get() = findViewById(R.id.refferalDate)
    private val number: TextView
        get() = findViewById(R.id.ticketId)
    private val imageView: ImageView
        get() = findViewById(R.id.imageView)
    private val progressBar: ProgressBar
        get() = findViewById(R.id.progressBar)
    private val group: Group
        get() = findViewById(R.id.group)
    private val refreshPage: Button
        get() = findViewById(R.id.refreshPage)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        refresh()
        refreshPage.setOnClickListener {
            refresh()
        }
    }

//    private fun runCoroutine() {

//        ioScope.launch {
//            kotlin.runCatching {
//                MultiCoroutines().run(
//                    message = "second 5000 Thread",
//                    time = 5000,
//                    throwException = true
//                )
//            }.onFailure {
//                println("for learning ${it.stackTraceToString()}")
//            }
//        }
//        ioScope.launch {
//            kotlin.runCatching {
//                MultiCoroutines().run(message = "first 1000 Thread", time = 1000)
//            }.onFailure {
//                println("for learning ${it.stackTraceToString()}")
//            }
//
//
//            kotlin.runCatching {
//                MultiCoroutines().run(message = "third 3000 Thread", time = 3000)
//            }.onFailure {
//                println("for learning ${it.stackTraceToString()}")
//            }
//        }

        // Job get cancel --> above coroutine throw failed
//        ioScope.launch {
//            throw Exception("for learning manual exception")
//        }
//    }

    private fun refresh() {
        progressBar.visibility = View.VISIBLE
        refreshPage.visibility = View.GONE
        group.visibility = View.GONE
        ioScope.launch {
            kotlin.runCatching {
                greeting.greeting()
            }.onSuccess {
                mainScope.launch {
                    progressBar.visibility = View.GONE
                    group.visibility = View.VISIBLE
                    name.text = it.refferalName
                    date.text = it.refferedDate
                    number.text = it.ticketId.toString()
                    refreshPage.visibility = View.VISIBLE
                    Glide.with(applicationContext).load(it.referralimageUrl).into(imageView)
                }

            }.onFailure {
                Log.d("MainActivity", it.stackTraceToString())
                mainScope.launch {
                    progressBar.visibility = View.GONE
                    refreshPage.visibility = View.VISIBLE
                    Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
        ioScope.cancel()
    }
}
