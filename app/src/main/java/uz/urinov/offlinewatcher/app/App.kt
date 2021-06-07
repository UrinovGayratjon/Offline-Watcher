package uz.urinov.offlinewatcher.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.urinov.offlinewatcher.data.room.AppDataBase
import uz.urinov.offlinewatcher.downloader.DownloaderConfig
import uz.urinov.offlinewatcher.downloader.VideoDownloader


@HiltAndroidApp
class App : Application() {
    private val TAG = "TTTApp"

    override fun onCreate() {
        super.onCreate()

        AppDataBase.init(applicationContext)
        val config = DownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .setReadTimeout(30_000)
            .setConnectTimeout(30_000)
            .build()
        VideoDownloader.initialize(applicationContext, config)

    }

}