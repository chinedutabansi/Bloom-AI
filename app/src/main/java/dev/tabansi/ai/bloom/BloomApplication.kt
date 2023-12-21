package dev.tabansi.ai.bloom

import android.app.Application
import dev.tabansi.ai.bloom.data.AppContainer
import dev.tabansi.ai.bloom.data.AppDataContainer

class BloomApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}