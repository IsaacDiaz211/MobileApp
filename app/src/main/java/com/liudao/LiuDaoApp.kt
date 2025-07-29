package com.liudao

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.liudao.data.local.preload.Preloader

@HiltAndroidApp
class LiuDaoApp : Application() {
    // Hilt inyecta esta instancia automáticamente
    // Acá precargamos los datos de los grupos musculares
    @Inject lateinit var preloader: Preloader

    override fun onCreate() {
        super.onCreate()
        // lanzar en CoroutineScope
        CoroutineScope(Dispatchers.IO).launch {
            preloader()
        }
    }
}