package uz.urinov.offlinewatcher.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private val TAG = "TTTSplashViewModel"
    private val _openMainScreenLiveData = MutableLiveData<Unit>()
    val openMainScreenLiveData: LiveData<Unit> = _openMainScreenLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            _openMainScreenLiveData.postValue(Unit)
        }
    }

}