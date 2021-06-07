package uz.urinov.offlinewatcher.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.urinov.offlinewatcher.data.room.AppDataBase
import uz.urinov.offlinewatcher.data.room.DownloadModel
import uz.urinov.offlinewatcher.ui.fragments.Utils
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {
    private val TAG = "TTTMainViewModel"
    private var fileFolder = ""

    private val _showAddFileUrlDialog = MutableLiveData<Unit>()
    val showAddFileUrlDialog: LiveData<Unit> = _showAddFileUrlDialog

    private val _openExoPlayerLiveData = MutableLiveData<String>()
    val openExoPlayerLiveData: LiveData<String> = _openExoPlayerLiveData

    private val _showErrorMessage = MutableLiveData<String>()
    val showErrorMessage: LiveData<String> = _showErrorMessage

    private val _itemListLiveData = MutableLiveData<List<DownloadModel>>()
    val itemListLiveData: LiveData<List<DownloadModel>> = _itemListLiveData


    init {
        fileFolder = Utils.getRootDirPath(context)
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                _itemListLiveData.postValue(AppDataBase.dataBase.downloadDao().getAllModels())
                delay(500)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun actionAddFileUrl() {
        _showAddFileUrlDialog.value = Unit
    }

    fun addFileUrl(url: String) {

        if (url.isEmpty()) {
            _showErrorMessage.value = "URL Bo'sh!"
            return
        }

        if (!url.endsWith(".mp4")) {
            _showErrorMessage.value = "Faqat .mp4 video!"
            return
        }

        if (!url.startsWith("https://")) {
            _showErrorMessage.value = "URL noto'g'ri formatda!"
            return
        }

        val fileName = url.split("/").last()

        val id = uz.urinov.offlinewatcher.downloader.utils.Utils.getUniqueId(
            url,
            fileFolder,
            fileName
        )

        if (AppDataBase.dataBase2.downloadDao().find(id) == null) {
            AppDataBase.dataBase2.downloadDao()
                .insert(DownloadModel(id, url, fileFolder, fileName, 1, 0))
        }
    }

    fun openExoPlayer(model: DownloadModel?) {
        model?.let {
            if (it.downloadedBytes == it.totalBytes) {

                val path = it.dirPath + File.separator + it.fileName
                _openExoPlayerLiveData.value = path

            } else {
                _showErrorMessage.postValue("Fayl to'liq yuklanmangan!")
            }
        }

    }

}