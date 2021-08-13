package com.example.kmmbasic

import io.ktor.client.*
import platform.UIKit.UIDevice
import java.util.concurrent.TimeUnit

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}
actual fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}) = HttpClient(Ios){
    config(this)
    config {
        retryOnConnectionFailure(true)
        connectTimeout(5, TimeUnit.SECONDS)
    }
}


actual fun logMessage(message: String) {
}