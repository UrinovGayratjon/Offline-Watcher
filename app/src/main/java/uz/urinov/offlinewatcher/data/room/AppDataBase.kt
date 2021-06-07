package uz.urinov.offlinewatcher.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DownloadModel::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun downloadDao(): DownloadDao
    companion object {
        lateinit var dataBase: AppDataBase
        lateinit var dataBase2: AppDataBase
        fun init(context: Context) {
            dataBase =
                Room.databaseBuilder(context, AppDataBase::class.java, "video_downloader")
                    .allowMainThreadQueries()
                    .build()

            dataBase2 =
                Room.databaseBuilder(context, AppDataBase::class.java, "video_downloader")
                    .allowMainThreadQueries()
                    .build()


        }
    }
}