package uz.urinov.offlinewatcher.ui.adapters

import android.view.View
import uz.urinov.offlinewatcher.data.room.AppDataBase
import uz.urinov.offlinewatcher.data.room.DownloadModel
import uz.urinov.offlinewatcher.downloader.VideoDownloader
import java.io.File

fun cancelDownload(model: DownloadModel) {

    try {
        AppDataBase.dataBase2.downloadDao().remove(model.id)
        VideoDownloader.cancel(model.id)
        val fileName = model.dirPath + File.separator + model.fileName
        val file = File(fileName)
        val tempFileName = fileName.split(".")[0] + ".temp"
        val fileTemp = File(tempFileName)
        if (file.exists()) {
            file.delete();
        }
        if (fileTemp.exists()) {
            fileTemp.delete();
        }

    } catch (ex: Exception) {

    }
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}